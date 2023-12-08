/**
 * @author Jay Lee, Dimitry Kudrayvtsev
 * @version 1.0
 *
 * Based off Dimitry Kudrayvtsev's gantt chart (re-created for specific purposes)
 *
 */
d3.gantt = function (selector) {
    /**
     * ============================ Start Default Options ============================
     * */
    selector = selector || '#chart';
    var svgClass = selector.replace("#", "") + "-svg";
    var svg;

    var FIT_TIME_DOMAIN_MODE = "fit";
    var FIXED_TIME_DOMAIN_MODE = "fixed";

    var margin = {
        top: 20,
        right: 20,
        bottom: 20,
        left: 20
    };
    // Main chart timeDomain
    var timeDomainStart = d3.time.day.offset(new Date(), -3);
    var timeDomainEnd = d3.time.hour.offset(new Date(), +3);
    var timeDomainMode = FIT_TIME_DOMAIN_MODE;// fixed or fit

    // Day of the week Array
    var dayOfTheWeekArray = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"];

    var taskTypes = [];
    var taskCategories = [];
    var taskStatus = [];
    var height = /*document.body.clientHeight*/800 - margin.top - margin.bottom - 5;
    var width = /*document.body.clientWidth*/2000 - margin.right - margin.left - 5;

    // width of SVG
    var svgWidth = width + margin.left + margin.right;
    // Initialized on Gantt startup
    var originalSvgWidth = 0;

    var tickFormat = "%H:%M";

    var chart = d3.select(selector);
    // Check if selector exists.
    if (chart[0][0] == null) {
        throwError("element using selector " + selector + " cannot be found");
        throw new Error(getStackTrace("element using selector " + selector + " cannot be found"));
    }

    // Custom user-defined event
    // Structure is [ { eventType: eventType, fn: fn }, ... ]
    var customEvents = [];
    var customMouseOverEvent;
    var customMouseOutEvent;

    // TooltipSettings
    var tooltipClass = "tooltip";
    // Container for the tooltip. Initialized later on
    var tooltip;
    var tooltipCurrentClass;        // cache the currentClass of the tooltip

    // x2AxisInfo
    var x2YearRow, x2MonthRow, x2DayRow, x2DateRow;
    var minColumnWidth = 45;
    var maxColumnWidth = width;

    // Text key value
    var barTextKey;

    // Bar color filter cb
    var barColorFilter = function (d) {
        if (taskStatus[d.status]["color"] == null) {
            return "#000";
        }
        return taskStatus[d.status]["color"];
    };

    // Add text to Bar predicate. By Default, do not add text to bar
    var textToBarPredicate = false;

    /**
     * Default tooltip function. This will be used if this is not overwritten by the user.
     * In all cases, override the tooltip function plz.
     * */
    var tooltipTemplateFn = function (d, element) {

        this.attr("class", tooltipCurrentClass);
        var color = element.attr("fill");

        var message = "<table style='border: none;'>" +
            "<tr>" +
            "<td style='width: 100px; font-weight: bold;'>작업명: </td>" +
            "<td style='width: 200px;'>" + d.taskName + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td style='width: 100px; font-weight: bold;'>가동상태: </td>" +
            "<td style='width: 200px; font-weight: bold; color: " + color + ";'>" + d.status + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td style='width: 100px; font-weight: bold;'>시작일시: </td>" +
            "<td style='width: 200px;'>" + generateDateString(d.startDate) + "</td>" +
            "</tr>" +
            "<tr>" +
            "<td style='width: 100px; font-weight: bold;'>종료일시: </td>" +
            "<td style='width: 200px;'>" + generateDateString(d.endDate) + "</td>" +
            "</tr>" +
            "</table>";
        return message;
    };

    // Generate
    var xScaleFunction = function (timeDomainStart, timeDomainEnd) {
        return d3.time.scale().domain([timeDomainStart, timeDomainEnd]).range([0, width]).clamp(true);
    };

    // Generator function for x axis
    var xAxisFunction = function (xScale, orient, timeFormat, cb) {
        timeFormat = timeFormat || "%H:%M:%S";
        orient = orient || "top";
        var scale = d3.svg.axis().scale(xScale).orient(orient).tickFormat(d3.time.format(timeFormat)).tickSubdivide(true);
        return cb ? cb(scale) : scale;
    };

    var keyFunction = function (d) {
        return d.startDate + d.taskName + d.endDate;
    };

    var rectTransform = function (d) {
        return "translate(" + x(d.startDate) + "," + y(d.taskName) + ")";
    };

    var x = xScaleFunction(timeDomainStart, timeDomainEnd);

    var xAxis = xAxisFunction(x, "bottom", tickFormat, function(xScale) {
        return xScale.tickSize(8).tickPadding(8);
    });

    // x2 Axis for showing hours and minutes. Major ticks at the hours
    var xHourAndMinuteAxis;

    // x2 Axis for showing the hours of the day. This will go towards the top of the chart.
    var xHourAxis;

    // x2 Axis for showing the hours of the day. This will go towards the top of the chart.
    var x3HourAxis;

    var y = d3.scale.ordinal().domain(taskTypes).rangeRoundBands([0, height - margin.top - margin.bottom], .1);

    var yAxis = d3.svg.axis().scale(y).orient("left").tickSize(0);

    /**
     * ============================ End Default Options ============================
     * */

    /**
     * initialize the time domain on chart initialization and redraw.
     * */
    var initTimeDomain = function (tasks) {
        if (timeDomainMode === FIT_TIME_DOMAIN_MODE) {
            if (tasks === undefined || tasks.length < 1) {
                timeDomainStart = d3.time.day.offset(new Date(), -3);
                timeDomainEnd = d3.time.hour.offset(new Date(), +3);
                return;
            }
            tasks.sort(function (a, b) {
                return a.endDate - b.endDate;
            });
            timeDomainEnd = tasks[tasks.length - 1].endDate;
            tasks.sort(function (a, b) {
                return a.startDate - b.startDate;
            });
            timeDomainStart = tasks[0].startDate;
        }
    };

    /**
     * Get hours from milliseconds
     * Add ceiling in case that it doesn't add up since we are getting the number of ticks
     * */
    function msToTime(ms, timeUnit, unit) {
        timeUnit = timeUnit || "hour";       // 1 hour
        unit = unit || 1;                    // number of unit of time. E.g. if 2 and case is hour, divide milliseconds by 2 hours
        switch (timeUnit) {
            case "hour" :
                timeUnit = 3600000;              // 3600000  milliseconds in an hour ... duh!
                break;
            case "day"  :
                timeUnit = 86400000;
                break;
            case "week" :
                timeUnit = 604800000;
                break;
            default:
                throw new Error(getStackTrace(timeUnit + " is an invalid option for function msToTime"));
        }
        return Math.ceil(ms / (timeUnit * unit));
    }

    /**
     * Hard coded date time string generator. If the need arises, I will make one that is more general-purpose.
     * */
    function generateDateString(d) {
        return d.getFullYear() + "년 " + (1 + d.getMonth()) + "월 " + d.getDate() + "일 " +
            d.getHours() + "시 " + d.getMinutes() + "분";
    }

    /**
     * Generate the task types when user passes in taskCategories. Called in initAxis()
     * */
    function generateTaskTypes(taskCategories) {
        if (!taskTypes && taskCategories) {
            taskTypes = taskCategories.map(function (v) {
                return v.nodes;
            });
        }
    }

    /**
     * Initialize chart x and y axis
     * */
    var initAxis = function () {

        var fromToHour = msToTime(timeDomainEnd - timeDomainStart);
        var multiplier = fromToHour >= 12 ? 2 : 4;                  // If interval is more than half a day, show by 30 minutes. Otherwise, by 15 minutes.

        generateTaskTypes(taskCategories);

        x = d3.time.scale().domain([timeDomainStart, timeDomainEnd]).range([0, width]).clamp(true);

        y = d3.scale.ordinal().domain(taskTypes).rangeRoundBands([0, height - margin.top - margin.bottom], .1);     // height - margin.top - margin.bottom = 715

        xAxis = d3.svg.axis().scale(x).orient("bottom").tickFormat(""/*d3.time.format(tickFormat)*/).tickSubdivide(true)
            .tickSize(8).tickPadding(8).tickSize(-height + margin.top + margin.bottom, 0, 0);

        yAxis = d3.svg.axis().scale(y).orient("left").tickSize(0);

        // xHourAndMinuteAxis that goes to the top
        xHourAndMinuteAxis = d3.svg.axis().scale(x).orient("top").tickFormat(d3.time.format("%H:%M")).ticks(fromToHour * multiplier).tickSize(-height + margin.top + margin.bottom, 0, 0);

        // hour axis that goes to the top
        xHourAxis = xAxisFunction(xScaleFunction(timeDomainStart, timeDomainEnd),
            "top", "%H", function (xScale) {
                return xScale.ticks(fromToHour).tickSize(-height + margin.top + margin.bottom, 0, 0);
            }
        );

        // 3 hour axis that goes to the top
        x3HourAxis = xAxisFunction(xScaleFunction(timeDomainStart, timeDomainEnd),
            "top", "%H", function (xScale) {
                return xScale.ticks(fromToHour / 3).tickSize(-height + margin.top + margin.bottom, 0, 0);
            }
        );
    };

    function gantt(tasks) {

        // Init original svg Width
        originalSvgWidth = width;

        generateYAxisTable();

        initTimeDomain(tasks);
        initAxis();

        svg = d3.select(selector)
            .append("div")         // Wrap inside of span for table y axis
            .attr("class", "gantt-container")
            .attr("style", "display: inline-block; max-width: 95%; position: relative; " +
                "overflow-x: auto; overflow-y: hidden; white-space: nowrap;");

        generateX2AxisTable();       // Generate x Axis on the gantt container. It depends on the svg element

        svg = svg.append("svg")
            .attr("class", svgClass)
            .attr("id", svgClass)
            .attr("width", svgWidth)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("class", "gantt-chart")
            .attr("width", svgWidth)
            .attr("height", height + margin.top + margin.bottom)
            .attr("transform", "translate(" + margin.left + ", " + margin.top + ")");

        var rect = svg.selectAll("." + svgClass)
            .data(tasks, keyFunction).enter()

            .append("rect")
            .attr("data-series", function(d, i) { return "series-" + i })
            .attr("rx", 5)
            .attr("ry", 5)
            .attr("fill", barColorFilter)
            .attr("class", function (d) {
                if (taskStatus[d.status]["className"] == null) {
                    return "bar";
                }
                return taskStatus[d.status]["className"];
            })
            .attr("y", 0)
            .attr("transform", rectTransform)
            .attr("height", function (d) {
                return y.rangeBand();
            })
            .attr("width", function (d) {
                return Math.max(1, (x(d.endDate) - x(d.startDate)));
            });

        var textContainer = svg.append("g").attr("class", "gantt-text");

        // Add text to gantt bars
        addTextToBar(textContainer);

        // Create tooltip element
        tooltip = d3.select(".gantt-container").append("div")
            .attr("class", tooltipClass)
            .style("opacity", 0);

        // Add tooltip
        addTooltip(rect, tooltipTemplateFn.bind(tooltip));

        // Add custom events
        if (customEvents.length > 0) {
            addCustomEvents();
        }

        // Adjust Y axis Table margin by x2 Axis height
        //adjustYAxisMargin();

        // Regular x Axis
        svg
            .append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0, " + (height - margin.top - margin.bottom) + ")")
            .transition()
            .call(xAxis);

        var axisXHour = svg.append("g").attr("class", "x-hour-axis grid");

        var hours = msToTime((timeDomainEnd - timeDomainStart));

        // Time frame is less than or equal to a week.
        if (hours <= 168) {
            if (hours >= 36) {
                axisXHour.transition().call(xHourAxis);
            } else {
                axisXHour.transition().call(xHourAndMinuteAxis);
            }
        // If less than two weeks show ticks every 3 hours
        } else if (hours <= 336) { // Hide this Jawn
            axisXHour.transition().call(x3HourAxis);
        }

        return gantt;
    }

    /**
     * Returns an array of dates in between
     * */
    function generateDateDifference(from, to, timeUnit, scale) {
        var result = [], start, end, dateIncrement;
        timeUnit = timeUnit || "day";
        var d = new Date(from.getTime());
        switch (timeUnit) {
            case "year":
                start = from.getFullYear();
                end = to.getFullYear();
                dateIncrement = getNextYearDate;
                break;
            case "month":
                start = 0;
                end = getMonthDifference(from, to);
                dateIncrement = getNextMonthDate;
                break;
            case "day":
                start = 0;
                var startDay = getNextDayDate(from),
                    endDay = getNextDayDate(to);
                var oneDay = 24*60*60*1000; // hours*minutes*seconds*milliseconds
                // get absolute number of days
                var diffDays = Math.round(Math.abs((startDay.getTime() - endDay.getTime())/(oneDay)));

                // if we are not starting off from start of day, add a day to the equation
                if (from.getHours() !== 0 || from.getMinutes() !== 0) {
                    diffDays++;
                }
                // if we are not ending at the start of a day, subtract a day from the equation
                if (to.getHours() !== 0 || to.getMinutes() !== 0) {
                    diffDays--;
                }

                end = diffDays;
                dateIncrement = getNextDayDate;
                break;
            default:
                throw new Error(getStackTrace(timeUnit + " is an invalid option for function generateDateDifference()"));
        }
        for (; start <= end; start++) {
            var curDate = new Date(d.getTime());
            result.push([curDate, scale(curDate)]);
            d = dateIncrement(d);
        }
        return result;
    }

    /**
     * Get month difference
     * */
    var getMonthDifference = function (from, to) {
        return to.getMonth() - from.getMonth() + (12 * (to.getFullYear() - from.getFullYear()));
    };

    /**
     * Helpers for GenerateDateDifference()
     * */
    var getNextYearDate = function (d) {
        return new Date(d.getFullYear() + 1, 0, 1);
    };

    var getNextMonthDate = function (d) {
        return new Date(d.getFullYear(), d.getMonth() + 1);
    };

    var getNextDayDate = function (d) {
        return new Date(d.getFullYear(), d.getMonth(), (d.getDate() + 1));
    };

    /**
     * Helper function for generating different types of rows.
     * */
    var rowGenerator = function (fn, timeUnit) {
        timeUnit = timeUnit || "day";
        return function (curRow, data) {
            var dataLen = data.length;
            if (dataLen === 1) {
                generateOneColumn(curRow, data, timeUnit);      // one columns
            } else {
                for (var i = 0; i < dataLen; i++) {             // multiple columns
                    fn(curRow, i, data);
                }
            }
        };
    };
    /**
     * Append column element to the current row
     * */
    var appendColumn = function (rowEl, time, prefix, colWidth) {
        colWidth = colWidth || getDimensions(rowEl).width;            // If no row width specified, size it equal to row width
        // Append Element
        rowEl.append("div").attr("class", "chart-xAxis-column")
            .attr("style", "display: inline-block;  width: " + colWidth + "px;")    // Note: subtract margin.left and margin.right to fit in chart.
            .text(time + prefix);
    };
    // Make last column width fit into the size of the chart
    var calculateColumnWidth = function (loopCount, data) {
        return loopCount === data.length - 1 ?
        svgWidth - data[loopCount][1] :                         // for last column in the row
        data[loopCount + 1][1] - data[loopCount][1];            // Rest of the rows
    };

    /**
     * Generate 1 column of any type in the x2 Axis
     * */
    var generateOneColumn = function (curRow, data, timeUnit) {
        var prefix = "일", time;
        data = data[0][0];

        switch (timeUnit) {
            case "year":
                time = data.getFullYear();
                prefix = "년";
                break;
            case "month":
                time = data.getMonth() + 1;
                prefix = "월";
                break;
            case "date":
                time = data.getDate();
                prefix = "일";
                break;
            case "day":
                time = dayOfTheWeekArray[data.getDay()];
                prefix = "";
                break;
            default:
                throw new Error(getStackTrace(timeUnit + " is an invalid option for function generateOneColumn()"));
        }
        appendColumn(curRow, time, prefix);
    };

    /**
     * Generate rows and columns for years.
     * */
    var generateYearRow = rowGenerator(function (curRow, loopCount, data) {
        var colWidth = calculateColumnWidth(loopCount, data);
        appendColumn(curRow, data[loopCount][0].getFullYear(), "년", colWidth);
    }, "year");

    /**
     * Generate rows and columns for months
     * */
    var generateMonthRow = rowGenerator(function (curRow, loopCount, data) {
        var colWidth = calculateColumnWidth(loopCount, data);
        appendColumn(curRow, (data[loopCount][0].getMonth() + 1), "월", colWidth);
    }, "month");

    /**
     * Generate rows and columns for dates
     * */
    var generateDateRow = rowGenerator(function (curRow, loopCount, data) {
        var colWidth = calculateColumnWidth(loopCount, data);
        appendColumn(curRow, data[loopCount][0].getDate(), "", colWidth);
    }, "date");

    /**
     * Generate rows and columns for days
     * */
    var generateDayRow = rowGenerator(function (curRow, loopCount, data) {
        var colWidth = calculateColumnWidth(loopCount, data);
        appendColumn(curRow,
            dayOfTheWeekArray[data[loopCount][0].getDay()],
            "", colWidth);
    }, "day");

    /**
     * Resize chart if there are too many days.
     * */
    var resizeX2Axis = function (dayRowData) {
        var dataLen = dayRowData.length;
        var dateColWidth = svgWidth / dataLen;
        // Date time range is too wide so that dateCol is smaller than minimum size
        if (dateColWidth < minColumnWidth) {
            width = minColumnWidth * dataLen;
            svgWidth = width + margin.left + margin.right;
            // Change svg and group width
            d3.select("." + svgClass).attr("width", width);
            svg.attr("width", width);
        }  else if (dateColWidth > (maxColumnWidth / 2) && dataLen > 5) {
            width = maxColumnWidth;
            svgWidth = maxColumnWidth + margin.left + margin.right;
            document.getElementById(''+svgClass+'').scrollLeft = 0;     // set scroll left to zero for proper resizing. TODO: Add a setter for ID later on.
            // Change svg and group width
            d3.select("." + svgClass).attr("width", originalSvgWidth);
            svg.attr("width", originalSvgWidth);
        } // resize width accordingly
        else if (dateColWidth > minColumnWidth * 3) {
            width = originalSvgWidth - margin.left - margin.right;
            svgWidth = width;
            document.getElementById(''+svgClass+'').scrollLeft = 0;     // set scroll left to zero for proper resizing
            d3.select("." + svgClass).attr("width", svgWidth);
            svg.attr("width", svgWidth);
        }
        
    };

    /**
     * Generate xAxisDisplayData on request.
     * */
    function generateXAxisDisplayData(from, to, scale) {
        return [
            generateDateDifference(from, to, "year", scale),
            generateDateDifference(from, to, "month", scale),
            generateDateDifference(from, to, "day", scale),
            generateDateDifference(from, to, "day", scale)
        ];
    }

    /**
     * generate x2Axis.
     * Note: This depends on the SVG element being already initialized.
     * */
    function generateX2AxisTable() {
        var tableDiv = d3.select(".gantt-container")        // Hard coded for now but we can convert it into an "option" on demand.
            .append("div")                                  // Wrap all elements inside of a div
            .attr("class", "gantt-xAxis-container")
            .append("div")
            .attr("class", "gantt-xAxis-table")
            .style("margin-left", margin.left + "px")              // To make the x2AxisTable in line with chart
            .style("width", svgWidth + "px");

        // Generate from-to data using the current time domain info.
        var data = generateXAxisDisplayData(new Date(timeDomainStart), new Date(timeDomainEnd), xScaleFunction(timeDomainStart, timeDomainEnd));

        var rows = tableDiv.selectAll("div").data(data)    // Table rows
            .enter().append("div")
            .attr("class", "chart-xAxis-row");

        var
            rowArray = rows[0];                 // Get access to the rows.

        // update the row elements
        x2YearRow = d3.select(rowArray[0]);
        x2MonthRow = d3.select(rowArray[1]);
        x2DateRow = d3.select(rowArray[2]);
        x2DayRow = d3.select(rowArray[3]);

        // Generate Year Row
        generateYearRow(x2YearRow, data[0]);

        // Generate x Axis month row
        generateMonthRow(x2MonthRow, data[1]);

        // Generate x Axis date row
        generateDateRow(x2DateRow, data[2]);

        // Generate x Axis day row
        generateDayRow(x2DayRow, data[2]);

        // Adjust Y axis Table margin by x2 Axis height
       // adjustYAxisMargin();
    }
    /**
     * Adding text to bar when needed.
     * */
    function addTextToBar(element) {

        // Dont add text unless specified.
        if (!textToBarPredicate) {
            return false;
        }

        // Append text: Maybe use option to generate text later
        var texts = element.selectAll("text");
        if (typeof textToBarPredicate === "function") {
           texts = texts.data(tasks.filter(textToBarPredicate));
        } else {
            texts = texts.data(tasks);
        }
        texts
            .enter()
            .append("text")
            .attr("data-series", function(d, i) { return "series-" + i })
            .attr("x", function(d) {
                return x(d.startDate) + ((x(d.endDate) - x(d.startDate)) / 2) - 30;
            })
            .attr("y", function(d) {
                return y(d.taskName) + (y.rangeBand() / 2) + 4;
            })
            .attr("fill", "black")
            .style("letter-spacing", 2)
            .style("font-weight", "bold")
            .style("font-size", 18)
            .text(function(d) {
                return d[barTextKey];
            });
    }

    /**
     * Called when gantt.redraw function is called. used to update the x2 Axis table.
     * */
    function updateX2AxisTable(data) {

        var tableDiv = d3.select(".gantt-xAxis-table").style("width", svgWidth + "px");

        var rows = tableDiv.selectAll("div.chart-xAxis-row").data(data)    // Table rows
            .enter();

        if (rows.length > 0) {

            removeChildren(x2YearRow); // Remove all previous info

            // Generate Year Row
            generateYearRow(x2YearRow, data[0]);

            removeChildren(x2MonthRow); // Remove all previous info

            // Generate x Axis month row
            generateMonthRow(x2MonthRow, data[1]);

            removeChildren(x2DateRow); // Remove all previous info

            // Generate x Axis date row
            generateDateRow(x2DateRow, data[2]);

            removeChildren(x2DayRow); // Remove all previous info

            // Generate x Axis day row
            generateDayRow(x2DayRow, data[2]);

            // Adjust Y axis Table margin by x2 Axis height
        //    adjustYAxisMargin();
        }
    }

    /**
     * Remove all nodes from d3 element
     * */
    function removeChildren(d3Selector) {
        d3Selector.html("");
    }

    /**
     * Private function for generating the y Axis category and task type table.
     * */
    function generateYAxisTable() {
        var container = d3.select(".gantt-yAxis-container");
        var table = d3.select(".gantt-yAxis");
        if (!table.empty()) {
            var x2AxisRowHeight = getDimensions(d3.select(".chart-xAxis-column")).height;
            table.remove();
            // y Axis container
            table = container
                .append("table")
                .attr("class", "gantt-yAxis")
                .attr("height", height - margin.top - margin.bottom)
                .style("margin-top",
                    // Added an extra row to the gantt Chart. So added that height to it ...
                    (margin.top + x2AxisRowHeight - 2)  + "px");
        } else {
            // First time the chart is created
            // y Axis container
            table = d3.select(selector)
                .append("div")                                  // Wrap all elements inside of a div
                .attr("class", "gantt-yAxis-container")
                .style("display", "inline-block")
                .style("vertical-align", "top")
                .style("max-width", "5%")
                .append("table")
                .attr("class", "gantt-yAxis")
                .attr("height", height - margin.top - margin.bottom)
                .style("margin-top",
                    // Added an extra row to the gantt Chart. So added that height to it ...
                    (margin.top + 28)  + "px");
        }

        var rows = table.selectAll("tr").data(taskTypes)    // Table rows
            .enter().append("tr")
            .attr("class", "chart-yAxis-row");

        var
            rowArray = rows[0],                 // Get access to the rows.
            rowCounter = 0,                     // For iterating over the rows.
            taskCatLen = taskCategories.length;

        // Loop over taskCategories and create table data
        for (var i = 0; i < taskCatLen; i++) {
            var
                currentCat = taskCategories[i],
                nodeCounter = 0,                            // Count the number of TDs that fits inside a TD with rowspan "n"
                nodeLen = currentCat.nodes.length,
                curRow = d3.select(rowArray[rowCounter]),
                categoryName = currentCat.name;

            // First row.
            if(categoryName) {
                curRow.append("td").text(categoryName).attr("rowspan", nodeLen);
                curRow.append("td").text(currentCat.nodes[nodeCounter++]);      // Increment
            } else {
                curRow.append("td").text(currentCat.nodes[nodeCounter++])
                    .attr("colspan", "2").style("text-align", "center");      // Cover the whole row.
            }

            // TODO: Refactor later on
            for (; nodeCounter < nodeLen; nodeCounter++) {
                curRow = d3.select(rowArray[++rowCounter]);         // Onto the next row with ++rowCounter
                if(categoryName) {
                    curRow.append("td").text(currentCat.nodes[nodeCounter]);
                } else {
                    curRow.append("td").text(currentCat.nodes[nodeCounter])
                        .attr("colspan", "2").style("text-align", "center");      // Cover the whole row.
                }
            }
            rowCounter++;                                           // Move onto the next row.
        }

        // Shift x Axis table to the right
        //d3.select(".gantt-xAxis-container").attr("style", "margin-left: " + (getDimensions(table).width + margin.left) + "px;");

    }

    /**
     * Add margin-top to yAxis container equal to x2 axis height
     * */
    function adjustYAxisMargin() {
        d3.select(".gantt-yAxis-container").style("margin-top", getDimensions(d3.select(".gantt-xAxis-table")).height + "px");
    }

    /**
     * Selects all existing bars
     * */
    function getGanttBars() {
        return d3.select(selector).selectAll("." + svgClass + " rect");
    }

    /**
     * Function for updating the tooltip
     * */
    function updateTooltip(cb) {
        addTooltip(getGanttBars(), cb);
    }

    /**
     * Add tooltip to the chart.
     * updateTooltip() is what the user calls to update the tooltip of the Chart
     * */
    function addTooltip(elements, cb) {

        elements.on("mouseover", function (d) {

            var curEl = d3.select(this);

            // Take care of added mouse over events if specified by user.
            if (customMouseOverEvent) {
                customMouseOverEvent.call(curEl, d);
            }

            // Set tooltip current Class TODO: Fix this hardcoded john
            // TODO: FIX THIS SOON
            var status = d.status;
            if (status == "정지" && d.stopName != null) {
                curEl.attr("class", "bar-fault");
                tooltipCurrentClass = tooltipClass + " bar-fault";
            } else {
                tooltipCurrentClass = tooltipClass + " " + taskStatus[status]["className"];
            }

            // change opacity of rectangle
            curEl.attr("fill-opacity", 0.8);

            // Animate the appearance of the tooltip
            tooltip.transition().duration(200).style("opacity", 0.8);

            // Adjust tooltip's HTML via CB
            tooltip.html(cb(d, curEl));

            // Get the relative coordinates of the selected rectangle
            var coordinates = curEl.attr("transform").replace(/[^0-9]+/, ""),
                // And the svg margin (transform)
                svgCoordinates = svg.attr("transform").replace(/[^0-9]+/, "");

            // IE transforms are separated by space, not ","s
            if (coordinates.split(",").length > 1) {
                coordinates = coordinates.split(",")
                svgCoordinates = svgCoordinates.split(",");
            } else {
                coordinates = coordinates.split(/\s/g);
                svgCoordinates = svgCoordinates.split(/\s/g);
            }

            // Top position of selected rectangle.
            var top = Number(coordinates[1].replace(")", "")) + Number(svgCoordinates[1].replace(")", "")) +
                getDimensions(d3.select(".gantt-xAxis-table")).height;

            // Chart container dimensions
            var
                body = chart.select("." + svgClass),
                bodyDimension = getDimensions(body),
                bodyWidth = bodyDimension.width;

            // Rectangle dimensions
            var
                rectDimensions = getDimensions(curEl),
                left = rectDimensions.left - bodyDimension.left,
                right = rectDimensions.right - bodyDimension.left;

            // Tooltip width
            var tooltipDimensions = getDimensions(tooltip),
                tooltipWidth = tooltipDimensions.width;

            // Conditionals to be tested for positioning the tooltip
            var
                closeToRight = bodyWidth - right <= tooltipWidth,  // If rect is close to right side of browser
                closeToLeft = left <= tooltipWidth;

            // Tooltip color and Class name
            var
                tooltipColor = curEl.attr("fill"),
                className = taskStatus[d.status]["className"];

            // ArrowDirection. This will be controlled using an external stylesheet
            var tooltipDirection = "tooltip-arrow-";

            // Dynamic horizontal tooltip positioning
            if (closeToRight) {
                // In case that the bar is long and it is close to both right and left.
                if (closeToLeft) {
                    tooltipDirection += "bottom";     // Set tooltip direction
                    // Calculate roughly the center of the rectangle
                    var xCenterPx = (bodyWidth - tooltipWidth / 2) / 2;

                    // Place in the center of the page relative to chart container
                    tooltip.style("left", xCenterPx + "px");
                    top -= tooltipDimensions.height + 20;

                    // Tooltip to the right
                } else {
                    tooltipDirection += "right";
                    tooltip.style("left", left - tooltipWidth - 20 + "px");
                }
                // Just close to the left
            } else {
                tooltipDirection += "left";
                tooltip.style("left", right + 20 + "px");
            }

            tooltip
                .style("top", top + "px")
                .style("border", "2px solid " + tooltipColor)                       // Set border color of tooltip
                .attr("class", tooltipCurrentClass + " " + tooltipDirection +       // Add appropriate css classes to create "css triangles"
                    " " + tooltipDirection + "-" + className);

            resetTooltipClass();                                                    // Reset all the added tooltip classes.

        }).on("mouseout", function (d) {
            // Take care of added mouse out events if specified by user.
            if (customMouseOutEvent) {
                customMouseOutEvent.call(d3.select(this), d);
            }
            // change opacity of rectangle
            d3.select(this).attr("fill-opacity", 1);
            // Hide the tooltip
            tooltip.transition().duration(300)
                .style("opacity", 0);
        });
    }


    /**
     *  Remove the all classes starting with "tooltip-arrow" from cache.
     * */
    function resetTooltipClass() {
        tooltipCurrentClass.replace(/tooltip-arrow-[\w+]+/, "");
    }

    // reference to container element. Used for getting the current x and y coordinates of mouse
    // when generating the tooltips.
    addTooltip.chartContainer = d3.select(selector)[0][0];

    /**
     * Generate and add custom events when creating charts.
     * */
    function addCustomEvents() {
        var bars = getGanttBars();
        for (var i = 0, len = customEvents.length; i < len; i++) {
            var curEvent = customEvents[i], eventType = curEvent.eventType, curFn = curEvent.fn;
            // To ensure that we don't overwrite existing tooltip event handler.
            switch (eventType) {
                case "mouseover":
                    customMouseOverEvent = curFn;
                    break;
                case "mouseout" :
                    customMouseOutEvent = curFn;
                    break;
                default:
                    bars.on(curEvent.eventType, curFn);
            }
        }
    }

    /**
     * Get the dimensions of the element
     * */
    function getDimensions(d3Selector) {
        var result;
        try {
            result = d3Selector.node().getBoundingClientRect();
        } catch (err) {
            throw new Error(getStackTrace("passed in undefined to getDimensions()"));
        }
        return result;
    }

    /**
     * ==================== Debugging features ====================
     * */

    /**
     * get stack trace
     * */
    function getStackTrace(errMsg) {
        var err = new Error(errMsg);
        return err.stack;
    }

    /**
     * Throw error
     * */
    function throwError(errMsg) {
        throw new Error(getStackTrace(errMsg));
    }

    /**
     * ==================== End Debugging Features ====================
     * */

    gantt.redraw = function (tasks) {

        generateYAxisTable();

        initTimeDomain(tasks);

        var timeDomainData = generateXAxisDisplayData(new Date(timeDomainStart), new Date(timeDomainEnd), xScaleFunction(timeDomainStart, timeDomainEnd));

        // Resize if there are too many days to fit in the svg element, given the current width of the svg element.
        resizeX2Axis(timeDomainData[2], svgWidth);

        // Regenerate new Data;
        timeDomainData = generateXAxisDisplayData(new Date(timeDomainStart), new Date(timeDomainEnd), xScaleFunction(timeDomainStart, timeDomainEnd));

        initAxis();

        updateX2AxisTable(timeDomainData);

        var svg = d3.select("." + svgClass);

        var ganttChartGroup = svg.select(".gantt-chart");

        var rect = ganttChartGroup.selectAll("rect").data(tasks, keyFunction);

        rect.enter()
      /*      .insert("g", ":first-child")
            .attr("class", "gantt-bar")*/
            .append("rect")
            .attr("data-series", function(d, i) { return "series-" + i })
            .attr("rx", 5)
            .attr("ry", 5)
            .attr("fill", barColorFilter)
            .attr("class", function (d) {
                if (taskStatus[d.status]["className"] == null) {
                    return "bar";
                }
                return taskStatus[d.status]["className"];
            })
            .transition()
            .attr("y", 0)
            .attr("transform", rectTransform)
            .attr("height", function (d) {
                return y.rangeBand();
            })
            .attr("width", function (d) {
                return Math.max(0, (x(d.endDate) - x(d.startDate)));        // Don't show old data. To show, change 0 to 1
            });

        rect
            .transition()
            .attr("transform", rectTransform)                                // Error here
            .attr("height", function () {
                return y.rangeBand();
            })
            .attr("width", function (d) {
                return Math.max(0, (x(d.endDate) - x(d.startDate)));        // Don't show old data. To show, change 0 to 1
            });

        // text container remove
        d3.select(".gantt-text").remove();

        var textContainer = ganttChartGroup.append("g").attr("class", "gantt-text");

        // Add text to gantt bars
        addTextToBar(textContainer);

        // Add custom events
        if (customEvents.length > 0) {
            addCustomEvents();
        }

        addTooltip(rect, tooltipTemplateFn.bind(tooltip));                   // Add tooltip

        rect.exit().remove();

        // x Axis transform
        svg.select(".x")
            .attr("transform", "translate(0, " + (height - margin.top - margin.bottom) + ")")
            .transition().call(xAxis);

        var axisXHour = svg.select(".x-hour-axis");

        var hours = msToTime((timeDomainEnd - timeDomainStart));

        // Time frame is less than or equal to a week.
        if (hours <= 168) {
            if (hours <= 36) {
                axisXHour.style("display", "block").transition().call(xHourAndMinuteAxis);
                // Show hour only when needed. Show only minutes
                var ticks = d3.selectAll(".x-hour-axis text");
                ticks.text(function(d,i){
                    var min = d.getMinutes();
                    return min === 0 ?
                            d.getHours() + ":00" :
                            min;
                });

            } else {
                axisXHour.style("display", "block").transition().call(xHourAxis);
            }
        } else if (hours <= 336) { // Hide this Jawn
            axisXHour.style("display", "block").transition().call(x3HourAxis);
        } else {
            axisXHour.style("display", "none");
        }

        /*updateX2AxisTable();*/

        svg.select(".y").transition().call(yAxis);

        return gantt;
    };

    /**
     * Temporary getter // TODO: Remove this if this is uneeded later o
     * */
    gantt.getXScale = function() {
        return x;
    };

    gantt.addTextToBar = function(predicate) {
        if (!arguments.length)
            return textToBarPredicate;
        textToBarPredicate = predicate;
        return gantt;
    };

    gantt.margin = function (value) {
        if (!arguments.length)
            return margin;
        margin = value;
        return gantt;
    };

    gantt.timeDomain = function (value) {
        if (!arguments.length)
            return [timeDomainStart, timeDomainEnd];
        timeDomainStart = +value[0];
        timeDomainEnd   = +value[1];
        return gantt;
    };

    /**
     * @param value
     * The value can be "fit" - the domain fits the data or
     *                "fixed" - fixed domain.
     */
    gantt.timeDomainMode = function (value) {
        if (!arguments.length)
            return timeDomainMode;
        timeDomainMode = value;
        return gantt;

    };

    /**
     * @param {Array} arr: An array with length of 7
     * representing the days of the week e.g. Sun, Mon, Tue, Wed, etc ...
     * representing the days of the week e.g. Sun, Mon, Tue, Wed, etc ...
     * */
    gantt.dayText = function(arr) {
        if(!arguments.length || arr.length !== 7)
            return dayOfTheWeekArray;
        dayOfTheWeekArray = arr;
        return gantt;
    };

    gantt.barTextKey = function (val) {
        if (!arguments.length)
            return barTextKey;
        barTextKey = val;
        return gantt;
    };

    /**
     * Define own condition specifying color
     * */
    gantt.barColorFilter = function (fn) {
        if (!arguments.length)
            return barColorFilter;
        barColorFilter = fn;
        return gantt;
    };

    gantt.taskCategories = function (value) {
        if (!arguments.length)
            return taskCategories;
        taskCategories = value;
        //Set the task Type
        gantt.taskTypes(
            [].concat.apply([],
                value.map(function (v) {
                    return v.nodes;
                })
            )
        );
        return gantt;
    };

    /**
     * is now no longer a part of the public API
     * */
    gantt.taskTypes = function (value) {
        if (!arguments.length)
            return taskTypes;
        taskTypes = value;
        return gantt;
    };

    /**
     * @Param {function, string}
     * Define tooltip. Also rename class from default "tooltip" if desired.
     * */
    gantt.tooltip = function (fn, className) {
        tooltipTemplateFn = fn;                         // Update the tooltip generating function
        updateTooltip(fn);
        tooltipClass = tooltipClass || className;       // Set tooltipClass if defined
        return gantt;
    };

    /**
     * Add custom events to the chart
     * */
    gantt.addEvent = function (eventType, fn) {
        customEvents.push({eventType: eventType, fn: fn});
        return gantt;
    };

    gantt.taskStatus = function (value) {
        if (!arguments.length)
            return taskStatus;
        taskStatus = value;
        return gantt;
    };

    gantt.width = function (value) {
        if (!arguments.length)
            return width;
        width = +value;
        return gantt;
    };

    gantt.height = function (value) {
        if (!arguments.length)
            return height;
        height = +value;
        return gantt;
    };

    // Tick interval
    gantt.tickInterval = function (value) {
        if (!arguments.length)
            return tickFormat;
        tickFormat = value;
        return gantt;
    };

    gantt.tickFormat = function (value) {
        if (!arguments.length)
            return tickFormat;
        tickFormat = value;
        return gantt;
    };

    gantt.selector = function (value) {
        if (!arguments.length)
            return selector;
        selector = value;
        return gantt;
    };

    return gantt;
};
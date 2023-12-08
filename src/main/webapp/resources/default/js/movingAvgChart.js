(function() {"use strict";

    var movingAvgChart = {
        init: MovingAvgChart
    };

    /**
     * D3 이동평균 차트영역
     * @param {string} selector - css selector
     * @param {string} $target - 하이차트 참조하는 jQuery element. Width 맞출 때 사용함.
     * @param {Array} data - 차트 데이타. Empty 배열 넣도 됨.
     * @param {number} maSampleCount - 이동평균 sample 갯수
     * @param {number} sampleRange - 판정할 샘플 수
     * @param {number} judgeSampleCount - 이동평균 증가/하락 추세 기준
     * @Return {object} 이동평균차트 object
     * */
    function MovingAvgChart(selector, $target, data, maSampleCount, sampleRange, judgeSampleCount){

        // "new" 예약어 제거
        if(!(this instanceof MovingAvgChart)) {
            return new MovingAvgChart(selector, $target, data, maSampleCount,
                sampleRange, judgeSampleCount);
        }

        var that = this;      // d3 scope 안에서 'this' 사용할 수 있게

        // 차트 instance 변수 초기화 및 선언
        this.chartData              = data || [];                   // 차트 데이타
        this.movingAvg              = [];                           // 차트 이동평균데이타
        this.movingAvgDiff          = [];                           // 차트 이동평균차이 데이타
        this.judgeArr               = [];                           // 판정 색상 array
        this.selector               = selector;                     // 사용자 지정 css selector
        this.currentMovingAvgDiff   = 0;                            // 현재 이동 평균차이
        this.maSum                  = 0;                            // 중량합계
        this.maSampleCount          = maSampleCount || 100;         // 이동평균 sample count
                                                                    // responsive highchart Element 대상
        this.target                 = $target.children(".highcharts-container");
        // 판정관련된변수
        this.sampleRange            = sampleRange || 5;             // 판정할 샘플 수
        this.judgeSampleCount       = judgeSampleCount || 4;        // 이동평균 증가/하락 추세 기준

        var
            margin  = {right: 20, left: 20},
            width   = Number($target.width()) - margin.right - margin.left,
            height  = 50,
            // Svg Element
            svg     = d3.select(selector).append("svg")
                .attr("width", width + margin.right + margin.left - 20)
                .attr("height", height)
                .attr("fill", "grey")
                .append("g")
                .attr("transform", "translate("+ margin.left +", 0)"),

            // Scales
            xAxis = d3.scaleTime().rangeRound([0, width - 20]),
            yAxis = d3.scaleLinear()
                .rangeRound([height, 0]);

        // d3 차도 domain 지정
        xAxis.domain(d3.extent(data, function(d) { return d[0]; }));
        yAxis.domain(d3.extent(data, function(d) { return d[1]; }));

        // Bar width
        var dataLen = data.length, barWidth = (width / dataLen) * 10;

        // Add moving avg Data.
        this.addMovingAvg();

        // bar DOM 에 append 하기
        svg.selectAll("rect").data(data).enter()
            .append("rect")
            .attr("x", function(d){ return xAxis(d[0]); } )
            .attr("height", 40)
            .attr("width", barWidth)
            .attr("fill", function(d, i){
                return that.judgeArr[i];
            });

    }
    /**
     * 여기서 'this' 는 'MovingAvgChart' 객채다
     * @return {number} 표준편차 차이
     * */
    var fnGenerateMovingAvgDiff = function(firstVal, secondVal){
        secondVal = secondVal || firstVal;     // secondVal 이 없으면 0이 나와야되니까.
        var sum = firstVal - secondVal;
        this.movingAvgDiff.push(sum);
        return firstVal - secondVal;
    };

    /**
     * 여기서 'this' 는 'MovingAvgChart' 객채다
     * 이동평균을 판정하는 함수
     * */
    var fnMovingAvgColor = function(index, currentMovingAvgDiff){
        var     differenceArr   = this.movingAvgDiff,           // 이동평균차이 배열
            len             = differenceArr.length,
            judgeCount      = this.judgeSampleCount,        // 이동평균 증가/하락 추세 기준
            sampleRange     = this.sampleRange,             // 판정할 샘플 수
            color,
            plusCnt = 0,
            minusCnt = 0;
        // differenceArr 에서 iteration 시작하는 index
        var startingIndex = len - sampleRange;

        // 이동평균데이타 갯수가 샘플 수가 판정할 샘플 수보다 작을 경우
        if (startingIndex < 0) {
            // Judge based only on current Data, not past samples
            this.judgeArr.push(currentMovingAvgDiff >= 0 ?  "#df7895" : "#62a8d0");
            return;
        }

        var result = [];

        // 판정하기
        for (var i = startingIndex; i < len; i++) {
            var curData = differenceArr[i];
            result.push(curData);
            if (curData >= 0) {
                plusCnt++;
            } else {
                minusCnt++;
            }
        }

        // 판정결과에 따라 색상 선택
        if (plusCnt >= judgeCount) {
            color = "#df7895";
        } else if (minusCnt >= judgeCount) {
            color = "#62a8d0";
        } else {
            color = "#3ac3bc";
        }

        // 판정결과 배열에 최신 결과 추가
        this.judgeArr.push(color);

    };



    /**
     * 현재 이동평균차이값을 가져오기
     * @return {number} 현재 이동평균차이값
     * */
    MovingAvgChart.prototype.getCurrentMovingAverageDiff = function(){
        return this.currentMovingAvgDiff;
    };
    
    /**
     * 현재 이동평균값 가져오기
     * @return {number} 현재 이동평균값
     * */
    MovingAvgChart.prototype.getCurrentMovingAverage = function() {
        var ma = this.movingAvg;
        return ma[ma.length - 1];
    };

    /**
     * 이동평균값 결과와 계산할 때 필요한 값들 결과로 그린다
     * @return {object} this
     * */
    MovingAvgChart.prototype.addMovingAvg = function(){
        var
            dataArr         = this.chartData,
            dataLen         = dataArr.length,
            movingAvg       = 0;                    // 이동평균값.

        if (dataLen === 0) {
            return;     // Do nothing if there is no data.
        }

        // 현재 중량값을 해당 차트 sum 에 추가
        var curVal = dataArr[dataLen - 1][1];
        this.maSum += curVal;
        movingAvg = Number((this.maSum / dataLen).toFixed(2));

        // 배열에 이동평균 데이타 추가
        this.movingAvg.push(+movingAvg.toFixed(2));

        // 배열에 이동평균 차이 데이타 추가. -2는 이전값 가져욀 때 쓰기 위해서
        var maDifference = fnGenerateMovingAvgDiff.call(this, movingAvg, this.movingAvg[dataLen - 2]);

        // 현재 이동평균차이 세팅
        this.currentMovingAvgDiff = maDifference;

        // 배열에 이동평균 색상 판정
        fnMovingAvgColor.call(this, dataLen - 1, maDifference);

        return this;
    };

    /**
     * D3 이동평균 차트에 Data append 하기
     * */
    MovingAvgChart.prototype.addData = function (pushedData, isShift){

        var that        = this,
            data        = this.chartData,
            selector    = this.selector,
            $target     = this.target;      // 하이차트 element

        data.push(pushedData);

        // Data shift 하기
        if (isShift) {
            // 중량 데이타 shift
            var firstData = data.shift();
            // 합계 조정
            this.maSum -= firstData[1];
            // 이동평균 및 이동평균차이 배열 shift
            this.movingAvg.shift();
            this.movingAvgDiff.shift();
            this.judgeArr.shift();
        }



        // D3 차트 bar 조정
        var
            dataLen     = data.length,
            svg         = d3.select(selector).select("svg"),
            group       = svg.select("g"),
            margin      = {right: 20, left: 20},
            width       = +$target.width() - margin.right - margin.left,
            height      = +$target.height(),

            // Scales
            xAxis       = d3.scaleTime().rangeRound([0, width]),
            yAxis       = d3.scaleLinear().rangeRound([height, 0]),

            // Bar width
            barWidth    = (width / dataLen) * 10;

        // set svg width
        svg.attr("width", width + margin.left + margin.right - 20);

        // Set domain
        xAxis.domain(d3.extent(data, function(d){ return d[0]; }));
        yAxis.domain(d3.extent(data, function(d){ return d[1]; }));

        // Remove before drawing
        group.selectAll("rect").remove();

        // Add moving avg Data.
        this.addMovingAvg();

        // bar 그리기
        group.selectAll("rect").data(data).enter()
            .append("rect")
            .attr("x", function(d){ return xAxis(d[0]); } )
            .attr("height", 40)
            .attr("width", barWidth)
            .attr("fill", function(d, i){
                return that.judgeArr[i];
            });
    };

    //Exposure to global window. Improve later if needed/
    window.movingAvgChart = movingAvgChart;

})();

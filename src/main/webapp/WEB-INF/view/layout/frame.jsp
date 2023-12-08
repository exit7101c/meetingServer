<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% 
response.setHeader("cache-control","no-cache"); 
response.setHeader("expires","0"); 
response.setHeader("pragma","no-cache"); 
%>

<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
		<meta http-equiv="Cache-Control" content="no-cache">
		<meta http-equiv="Pragma" content="no-cache"> 
		<meta http-equiv="Expires" content="0">    
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>농심안양PMS</title>
		<script type="text/javascript" src="resources/default/js/jquery-1.11.2.js"></script>
    </head>
    
	<body style="margin: 0 auto;">
		<div id="frame1">
			<iframe src="http://192.168.22.160:8080/weightBagNew?p=25" frameBorder="0" style="height:1000px;width:99.9%;"></iframe>
		</div>
		<div id="frame2">
			<iframe src="http://192.168.22.160:8080/weightBag?p=26" frameBorder="0" style="height:1000px;width:99.9%;"></iframe>
		</div>
	</body>
	
</html>
<script>
	var frameSeq = false;
	var hiddenCssProperties = {
        "position":"absolute",
		"top": "-1500px"
	};

    var shownCssProperties = {
        "position": "relative",
        "top": "0"
    };

	function changeFrame(){
		if (frameSeq) {
            $("#frame1").css(shownCssProperties);
            $("#frame2").css(hiddenCssProperties);
            frameSeq = false;
		} else {
            $("#frame1").css(hiddenCssProperties);
            $("#frame2").css(shownCssProperties);
            frameSeq = true;
		}
	}

    $(document).ready(function() {
        //$("#frame2").hide();
        $("#frame2").css(hiddenCssProperties);

        setInterval(function(){ changeFrame() }, 15000);
    });
</script>


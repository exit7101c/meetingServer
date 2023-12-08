<%@ taglib prefix="tiles" 	uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core"%>
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
		<title>SERVER-INFO</title>
		
		<!-- css -->
		<link type="text/css" rel="stylesheet" href="resources/default/css/login.css">
		<link type="text/css" rel="stylesheet" href="resources/nbpms/css/nbpms.css">

	    <!-- script -->
	    <script src="resources/default/js/jquery-1.11.2.js"></script>
	    <script src="resources/default/js/jquery-migrate-1.2.1.min.js"></script>
    		
	</head>
	
	<body>
	
    	<span id="loginMessage" style="display:none">${loginCheck}</span> 

		<form id="frmLogin">
			<input id="frmUserId" 		type="hidden" name="ssUserId" /> 
			<input id="frmUserPass" 	type="hidden" name="ssUserPass" />
			<input id="frmUserLang" 	type="hidden" name="ssUserLang" />
		</form>

		<div id="loginWrap">

			<p style="font-size: 20px;">Server operation is completed.</p>
			<p style="font-size: 20px;">This is Nongshim Engineering's REST-API service.</p>
			<p style="font-size: 20px;">User service is not provided. If you have any problems with your use. Please contact Nongshim Engineering </p>
			<br/>
			<p>#########################[Server Infomation]###############################</p>
			<p><label>java-version : 1.8</label></p>
			<p><label>springframework-version : 4.1.1.RELEASE</label></p>
			<p><label>Server current time : ${serverTime}</label></p>
			<p><label>requestURL : ${requestURL}</label></p>
			<p><label>requestURI : ${requestURI}</label></p>
			<p><label>servletPath : ${servletPath}</label></p>
			<p><label>Client ip address : ${clientIp}</label></p>
			<p><label>Connection : ${Connection}</label></p>
			<p>host : ${host}</p>
			<p>user-agent : ${user-agent}</p>
			<p>accept : ${accept}</p>
    		<%--<div class="login">

			    <form>
			        <div class="set">
			            <label>UserID</label>
			            <input id="inpId"  type="text" placeholder="UserID" value="admin">
			        </div>

			        <div class="set">
			            <label>Password</label> 
			            <input id="inpPass" type="password" placeholder="Password" value="1">
			        </div>

<!-- 			        <div class="set"> -->
<!-- 				        <label style="cursor: pointer;"><input type="radio" value="ko" name="radLang"> <img src="resources/default/img/icon/flag_kr.png"> Korean</label> -->
<!-- 						<label style="cursor: pointer;"><input type="radio" value="zh" name="radLang"> <img src="resources/default/img/icon/flag_ch.png"> Chinese</label> -->
<!--         			</div> -->

			        <button id="btnLogin" type="button">LOGIN</button>
			    </form>
		    </div>--%>

		</div>
	
	</body>
	
</html>

<script type="text/javascript">

/**=============================================================
* 프로그램명: 로그인
*/

/**=============================================================
* etc
*/

function fnTrimProcess(x) {
    return x.replace(/^\s+|\s+$/gm,'');
}

//마지막으로 사용한 (한국어, 중국어) 로컬스토리지 저장
function fnSetStorage(){
// 	var chkVal = $('input[name=radLang]:checked').val(); 
	var chkVal = 'ko';
	localStorage.setItem('lastLang', chkVal);
}
//마지막으로 사용한 언어 checked 처리
function fnSetLastlang(){
// 	var lastLang = localStorage.getItem('lastLang');
// 	if(lastLang == null){
// 		lastLang = $('input[name=radLang]').eq(0).val();
// 	}
// 	$('input[name=radLang][value='+lastLang+']').prop('checked', true);
// 	if($('input[name=radLang]:checked').val()==null){
// 		$('input[name=radLang]').eq(0).prop('checked', true);
// 	}
}
/**=============================================================
* Remote
*/

/**
 * login check
 */
function fnRemoteGetLogin() {
	//세션스토리지에 선택한 언어저장
	fnSetStorage();
	
	$('#loginMessage').text('');
	
	$('#frmUserId').val( $('#inpId').val() );
	$('#frmUserPass').val( $('#inpPass').val() );
	var lang = 'ko';
// 	var lang = $('input[name=radLang]:checked').val();
	$('#frmUserLang').val( lang );

	$('#frmLogin').attr('method', 'POST');
	$('#frmLogin').attr('action', 'login');
	
	$('#frmLogin').submit();
};

/**=============================================================
 * Validate
 */
function fnValidate(){

	var result = true;
	var id = $('#inpId').val();
	var ps = $('#inpPass').val();
	id = fnTrimProcess(id);
	ps = fnTrimProcess(ps);
	$('#inpId').val(id);
	$('#inpPass').val(ps);
	
	if(id == null || id == undefined || id == ''){
		alert('ID 를 입력하세요');
		$('#inpId').focus();
		result = false;
		return result;
	} else {
		if( id.length > 20 ){
			alert('ID는 20자를 초과할 수 없습니다.');
			$('#inpId').focus();
			result = false;
			return result;
		}
	}
	
	if(ps == null || ps == undefined || ps == ''){
		alert('Password 를 입력하세요');
		  $('#inpPass').focus();
		result = false;
		return result;
	} else {
		if( ps.length > 20 ){
			alert('Password는 20자를 초과할 수 없습니다.');
			  $('#inpPass').focus();
			result = false;
			return result;
		}
	}
	
	return result;
}

/**=============================================================
* Event Listener
*/
var fnEventListener = function(){
	
	$( "#btnLogin" ).click(function() {
		if(fnValidate()) {
			fnRemoteGetLogin();
		}
	});
	
	$( "#inpId" ).keypress(function( event ) {
		  if ( event.which == 13 ) {
			  $('#inpPass').focus();
			  event.preventDefault();
		  }
	});	
	
	$( "#inpPass" ).keypress(function( event ) {
		if ( event.which == 13 ) {
			if(fnValidate()) {
				fnRemoteGetLogin();
			}
		}
	});	
	
};

/**=============================================================
* 브라우저 check
*/

var fnBrowserCheck = function(){
	
	var bro = $.browser;
	var ver = parseInt($.browser.version);
	var chk = false;
	
	if(bro['safari'] != undefined && bro['safari'] == true){
		chk = true;
	}else if(bro['mozilla'] != undefined && bro['mozilla'] == true){
		chk = true;
	}else if(bro['chrome'] != undefined && bro['chrome'] == true){
		chk = true;
	}else if(bro['Opera'] != undefined && bro['Opera'] == true){
		chk = true;
	}else if(bro['msie'] != undefined && bro['msie'] == true){
		chk = (ver < 10) ? false : true;
	};
	if(!chk){
		var msg = '\n';
		msg 	= '지원하지 않는 브라우저입니다. !!' + '\n\n';
		msg 	+= '[사용가능 브라우저] ' + '\n';
		msg 	+= 'Internet Explorer: 10.0 이상' + '\n';
		msg 	+= 'Chrome' + '\n';
		msg 	+= 'Firefox' + '\n';
		msg 	+= 'Safari' + '\n';
		msg 	+= 'Mozilla' + '\n';
		msg 	+= 'Opera' + '\n';
		alert(msg);
		
		$('#inpId').prop("disabled", true);
		$('#inpPass').prop("disabled", true);
		$('#btnLogin').prop("disabled", true);
	};
	
};

/**=============================================================
 * 프로그램시작
 */
function createComp(){
	
	fnBrowserCheck();
	
	//마지막으로 사용한 언어 checked 처리
	fnSetLastlang();
	
	// 화면에서 사용될 이벤트 등록
	fnEventListener();
	
	// 로그인 결과 메시지
	var meg = $('#loginMessage').text();
	if(meg === 'err'){
		alert('등록하신 ID 혹은 비밀번호가 틀립니다.');
		return;
	};
	
	
	var pz = '<%=request.getParameter("pz")%>';
	var px = '<%=request.getParameter("px")%>';
	var lang = '<%=request.getParameter("lang")%>';
	
	console.log(pz, px);
	
	if(pz!='null'&&px!='null'&&lang!='null'){
		$("#inpId").val(pz);
		$("#inpPass").val(px);
		
		if(lang=='k'){
			$('input[name=radLang]').eq(0).prop('checked', true);
		} else {
			$('input[name=radLang]').eq(1).prop('checked', true);
		}
		
		fnRemoteGetLogin();
	}
	
	
	
};

$(document).ready(function() {
	createComp();
});
//-------------------------------------------------------------


</script>


  
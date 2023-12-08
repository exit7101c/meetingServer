<%--
  Created by IntelliJ IDEA.
  User: pyj
  Date: 2023-07-17
  Time: 오후 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NICE신용평가정보 본인인증 서비스</title>
    <script type="text/javascript">
        function load() {
            var ua = '${data.userAgent}';
            var myForm = document.popForm;
            var url = "https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb";

            if (ua == 'ios') {
                myForm.action=url;
                myForm.method="post";
                myForm.target="_self";
                myForm.submit();
            } else {
                // var popup = window.open("http://139.150.69.51:8034/join/selfClose.do" ,"popForm");
                // var win = window;
                // if(!popup) {
                //     alert('팝업 차단되어 있습니다. 본인인증을 연결하기위해 팝업차단을 허용으로 변경하거나 본인인증 시작하기를 눌러주세요.');
                // } else {
                    myForm.action =url;
                    myForm.method="post";
                    // myForm.target="popForm";
                    myForm.target="_self";
                    myForm.submit();

                    // window.close();
                // }
            }
        }
    </script>
    <style>
        body {
            padding: 30px 50px;
        }

        body .main {
            width: 100%;

            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;

            font-size: 40px;
        }

        button {
            background: #7B76F7;
            margin: 50px 0px;

            width: 80%;
            height: 100px;
            color: #fff;
            font-size: 50px !important;

            border: 1px solid #fff;
            border-radius: 20px;

            font-weight: 800;
        }

        button:hover {
            cursor: pointer;
        }

        /*div.dropdown {*/
        /*    font-size: 30px !important;*/
        /*}*/

        /*div.dropdown ol#drop-content {*/
        /*    font-size: 30px !important;*/
        /*    display: none;*/
        /*}*/
    </style>
</head>
<body style="background: #191A37;" onload="load()">
<div class="main">
    <form id="form" name="popForm" action="https://nice.checkplus.co.kr/CheckPlusSafeModel/service.cb" method="post">
        <input type="hidden" id="m" name="m" value="service" />
        <input type="hidden" id="token_version_id" name="token_version_id" value="${ data.token_version_id }" />
        <input type="hidden" id="enc_data" name="enc_data" value="${ data.enc_data }" />
        <input type="hidden" id="integrity_value" name="integrity_value" value="${ data.integrity_value }" />
    </form>
    <p style="text-align: center; color: #fff; margin-top: 50px;">본인인증화면으로 자동으로 이동합니다.</p>
    <p style="text-align: center; color: #fff; margin-top: 50px;">자동으로 이동되지 않는다면 <br />팝업창 권한을 허용해 주시고 <br />아래 "본인인증 시작하기"를 눌러주세요.</p>
    <p style="text-align: center; color: #fff; margin-top: 50px;">팝업창 권한을 허용하지 않으면 <br />본인인증을 시작할 수 없습니다.</p>

    <button type="button" onclick="load()">본인인증 시작하기 !</button>

    <%--        <div class="dropdown" style="color: #fff; font-size: 30px;">--%>
    <%--            <p onclick="dropMenuOpen()" id="clickTitle" style="text-align: center; color: #fff; margin-top: 50px;">--%>
    <span style="color: #fff;">**권한을 거부/차단 하여 팝업창이 동작하지 않는경우<br />아래와 같이 조치해주세요.</span>
    <%--                <span id="clickText" onclick="dropMenuOpen()" style="text-decoration: underline; margin-left: 20px; font-size: 35px !important;">보기 ⬇</span>--%>
    <%--            </p>--%>
    <ol start="1" id="drop-content" style="font-size: 30px;">
        <li style="text-align: left; color: #fff; margin-top: 50px;">휴대폰 설정에서 <span style="font-weight: 700; font-size: 35px; color: #fcfc1a;">기본 브라우저를 "크롬"으로 변경 후</span> 다시 시도해주세요.</li>
        <li style="text-align: left; color: #fff; margin-top: 50px;">기본브라우저 설정을 하는 방법은 휴대폰 기종에 맞는 인터넷 검색을 하시면 알 수 있습니다.</li>
        <li style="text-align: left; color: #fff; margin-top: 50px;">그럼에도 해결이 되지 않는다면 info@cmdg.co.kr 로 문의주시면 빠르게 답변드리겠습니다.</li>
    </ol>
    <%--        </div>--%>
    <%--    </div>--%>

    <%--    <script type="text/javascript">--%>
    <%--        function dropMenuOpen() {--%>
    <%--            let click = document.getElementById("drop-content");--%>
    <%--            let clickTitle = document.getElementById("clickTitle");--%>
    <%--            let clickText = document.getElementById("clickText");--%>
    <%--            if(click.style.display == "none"){--%>
    <%--                click.style.display = "block";--%>
    <%--                clickText.style.display = "none";--%>
    <%--                clickTitle.style.display = "none";--%>
    <%--            } else {--%>
    <%--                click.style.display = "none";--%>
    <%--                clickText.style.display = "block";--%>
    <%--            }--%>
    <%--        }--%>
    <%--    </script>--%>
</body>
</html>

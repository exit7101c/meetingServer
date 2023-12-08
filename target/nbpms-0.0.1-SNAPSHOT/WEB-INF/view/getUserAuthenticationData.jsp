<%--
  Created by IntelliJ IDEA.
  User: pyj
  Date: 2023-07-15
  Time: 오후 6:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NICE신용평가정보 본인인증 서비스</title>
    <script type="text/javascript">
        function load() {
            window.close();
        }
    </script>
    <style>
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
    </style>
</head>
<body style="background: #191A37; text-align: center;" onload="load()">
    <h1 style="text-align: center; color: #fff; margin-top: 100px; font-size: 50px;">본인인증이 완료되었습니다.</h1>
    <h1 style="text-align: center; color: #fff; margin-top: 100px; font-size: 50px;">앱으로 돌아가서 다음 단계를 진행해 주세요 !</h1>
<%--    <button type="button" onclick="load()">닫기</button>--%>
    <%--<h1 style="text-align: center; color: #fff; margin-top: 100px; text-align: center;">현재 창을 닫아 주세요.</h1>--%>
    <%--<button style="text-align: center; color: black; margin-top: 100px; font-size: 50px;" onclick="load()">돌아가서 계속하기</button>--%>
</body>
</html>
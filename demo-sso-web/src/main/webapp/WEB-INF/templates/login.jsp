<%--
 Created by IntelliJ IDEA.
 User: Lenovo
 Date: 2019/12/10
 Time: 17:46
 To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>统一认证中心</title>
</head>
<body>
${errorMsg}
<form id="login" method="post" action="/login">
    <input id="redirectUrl" name="redirectUrl" type="hidden" value=${requestScope.redirectUrl}>

    <br/>
    <br/>
    <label>username</label><input id="username" name="username" type="text" placeholder="账号">
    <br/>
    <br/>
    <label>password</label><input id="password" name="password" type="password" placeholder="密码">
    <br/>
    <br/>
    <input type="submit" value="登录">

</form>

</body>
</html>

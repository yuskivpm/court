<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
    <h3>Welcome admin</h3>
    <hr />
    ${user}, hello!
    <hr />
    <form name="LogoutForm" method="POST" action="/api/v1">
        <input type="hidden" name="command" value="Logout" />
        <input type="submit" value="Log out" />
    </form>
    <br />
    <a href="/api/v1?command=Logout">Log out wia get</a>
</body>
</html>

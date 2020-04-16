<jsp:directive.page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"/>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form name="LoginForm" method="POST" action="api">
    <input type="hidden" name="command" value="Login"/>
    Login:
    <br/>
    <input type="text" name="login" value="admin"/>
    <br/>
    Password:
    <br/>
    <input type="password" name="password" value="admin"/>
    <br/>
    ${errorFailLoginPassMessage}
    <br/>
    <input type="submit" value="Log in"/>
</form>
<br/>
${wrongAction}
<br/>
${nullPage}

</body>
</html>

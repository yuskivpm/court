<jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>

<html>

<head>
    <title>Login</title>
</head>

<body>
    <form name="LoginForm" method="POST" action="api">
        <input type="hidden" name="command" value="Login"/>
        Login:
        <br/>
        <input type="text" name="login" value="localJudge1"/>
        <br/>
        Password:
        <br/>
        <input type="password" name="password" value="localJudge1"/>
        <br/>
        ${errorFailLoginPassMessage}
        <br/>
        <input type="submit" value="Log in"/>
    </form>
    <br/>
    ${wrongAction}
    <br/>
    ${nullPage}

    <table>
        <tr><td>admin</td><td>admin</td></tr>
        <tr><td>attorney1</td><td>attorney1</td></tr>
        <tr><td>attorney2</td><td>attorney2</td></tr>
        <tr><td>supreme1</td><td>supreme1</td></tr>
        <tr><td>appeal1</td><td>appeal1</td></tr>
        <tr><td>localJudge1</td><td>localJudge1</td></tr>
    </table>
</body>

</html>

<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<html>

<head>
    <title>Main</title>
</head>

<body>
<h3>Welcome</h3>
<hr/>
${user}, hello!
<hr/>
<form name="LogoutForm" method="POST" action="api">
    <input type="hidden" name="command" value="Logout"/>
    <input type="submit" value="Log out"/>
</form>
<br/>
<a href="api?command=Logout">Log out wia get</a>
</body>

</html>

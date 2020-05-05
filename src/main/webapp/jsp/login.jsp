<%@ page import="com.dsa.domain.user.UserDao" %>
<jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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

<p>Fast login for lazy "Hackers")))</p>
<c:set var="users" scope="page" value="<%=new UserDao()%>"/>
<table>
    <tr>
        <td>Name</td>
        <td>Login</td>
        <td>Password</td>
        <td>Action</td>
    </tr>
    <c:forEach var="user" items="${users.readAll()}">
        <tr>
            <form name="LoginForm" ${user.id} method="POST" action="api">
                <input type="hidden" name="command" value="Login"/>
                <td>
                        ${user.name}
                </td>
                <td>
                    <input type="text" name="login" value="${user.login}"/>
                </td>
                <td>
                    <input type="text" name="password" value="${user.password}"/>
                </td>
                <td>
                    <input type="submit" value="Log in"/>
                </td>
            </form>
        </tr>
    </c:forEach>
</table>
</body>

</html>

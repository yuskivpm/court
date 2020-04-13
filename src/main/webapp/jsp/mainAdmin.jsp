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
    <h1>Users</h1>


    <a href="/api/v1/ADMIN?command=AddUser">Add new user</a>
    <table>
        <thead>
            <tr>
                <th>id</th>
                <th>login</th>
                <th>password</th>
                <th>role</th>
                <th>name</th>
                <th>court</th>
                <th>isActive</th>
                <th>edit</th>
                <th>delete</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td>id</td>
                <td>login</td>
                <td>password</td>
                <td>role</td>
                <td>name</td>
                <td>court</td>
                <td>isActive</td>
                <td>edit</td>
                <td>delete</td>
            </tr>
        </tbody>
    </table>


    <h1>Courts</h1>

    <a href="/api/v1?command=Logout">Log out wia get</a>

<%--    <script>--%>
<%--        function fetchThis(url, formData){--%>
<%--            fetch(url, {--%>
<%--                method: 'POST',--%>
<%--                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },--%>
<%--                body: formData--%>
<%--            })--%>
<%--        };--%>

<%--        function logout(){--%>
<%--            let formData = new FormData();--%>
<%--            formData.append('command','Logout');--%>
<%--            fetchThis('/api/v1/','command=Logout');--%>
<%--            // fetchThis('/api/v1/',formData);--%>
<%--        };--%>
<%--    </script>--%>
</body>
</html>

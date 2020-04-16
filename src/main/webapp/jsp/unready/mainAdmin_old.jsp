<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="../css/main.css">
</head>
<body>
<h3>Welcome admin</h3>
    <hr />
    ${user}, hello!
    <button
        form="simpleForm"
        formaction="api"
        formmethod="post"
        type="submit"
        name="command"
        value="Logout"
    >
        Logout
    </button>
    <hr />
    <form id="simpleForm" hidden> </form>

    <br />
    <h1>Users</h1>
    <a href="/jsp/unready/addUserForm.jsp" class="likeButton">Add User</a>

    <table>
        <thead>
            <tr>
                <th>id</th>
                <th>name</th>
                <th>login</th>
                <th>password</th>
                <th>role</th>
                <th>court</th>
                <th>isActive</th>
                <th>edit</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody id="users">
        <c:forEach var="user" items="${users}">
            <tr>
                <td>${user.id}</td>
                <td>${user.login}</td>
                <td>${user.password}</td>
                <td>${user.role}</td>
                <td>${user.name}</td>
                <td>${user.court.courtName}</td>
                <td>${user.isActive}</td>
                <td>edit</td>
                <td>
                    <button type="button" onclick="remove('users',${user.id})" >delete</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <hr />

    <h1>Courts</h1>
        <table>
        <thead>
        <tr>
            <th>id</th>
            <th>courtName</th>
            <th>courtInstance</th>
            <th>mainCourt</th>
            <th>edit</th>
            <th>delete</th>
        </tr>
        </thead>
        <tbody id="courts">
        <c:forEach var="court" items="${courts}">
            <tr>
                <td>${court.id}</td>
                <td>${court.courtName}</td>
                <td>${court.courtInstance}</td>
                <td>${court.mainCourt.courtName}</td>
                <td>edit</td>
                <td>
                    <button type="button" onclick="remove('courts',${court.id})" >delete</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    <hr />

    <a href="api?command=Logout">Log out wia get</a>

    <script>

        function create(formId){

        }

        function remove(entityType,id){
            fetchThis('api/'+entityType+'/'+id,'DELETE','command=MainPage');
        };

        function fetchThis(url, method='POST', formData){
            fetch(url, {
                method,
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: formData
            })
        };
    </script>
</body>
</html>

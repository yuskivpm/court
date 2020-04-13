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
    <hr />
    <form id="simpleForm" >
        <button
                form="simpleForm"
                formaction="/api/v1"
                formmethod="post"
                type="submit"
                name="command"
                value="Logout"
        >
            Logout
        </button>
    </form>
<%--    <form hidden id="deleteUser" method="post" action="/api/v1"> </form>--%>
<%--    <form hidden id="deleteUser" method="post" action="/api/v1"> </form>--%>

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
                <th>Delete</th>
            </tr>
        </thead>
        <tbody>
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
<%--                    <button--%>
<%--                            form="simpleForm"--%>
<%--                            formaction="/api/v1"--%>
<%--                            formmethod="post"--%>
<%--                            type="submit"--%>
<%--                            name="command"--%>
<%--                            value="DeleteUser/${user.id}"--%>
<%--                    >--%>
<%--                        delete--%>
<%--                    </button>--%>
                    <button onclick="remove('user',${user.id})" type="button">delete</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <hr />

    <h1>Courts</h1>
    <a href="/api/v1/ADMIN?command=AddCourt">Add new court</a>
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
        <tbody>
        <c:forEach var="court" items="${courts}">
            <tr>
                <td>${court.id}</td>
                <td>${court.courtName}</td>
                <td>${court.courtInstance}</td>
                <td>${court.mainCourt.courtName}</td>
                <td>edit</td>
                <td>
                    <button
                            form="simpleForm"
                            formaction="/api/v1"
                            formmethod="post"
                            type="submit"
                            name="command"
                            value="DeleteCourt/${court.id}"
                    >
                        delete
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>


    <hr />

    <a href="/api/v1?command=Logout">Log out wia get</a>

    <script>

        function remove(entityType,id){
            // let formData = new FormData();
            // formData.append('command','MainPage');
            // fetchThis('/api/v1/remove/'+entityType+'/'+id,'DELETE',formData);//'DELETE'
            fetchThis('/api/v1/remove/'+entityType+'/'+id,'DELETE','command=MainPage');
            // fetchThis('/api/v1/remove/'+entityType+'/'+id,'DELETE',{'command':'MainPage'});
        };

        function fetchThis(url, method='POST', formData){
        // function fetchThis(url){
            // fetch(url, {method });
            //
            // fetch(url, {method: 'DELETE'});
            fetch(url, {
                method,
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                // headers: { 'Content-Type': 'multipart/form-data' },
                body: formData
            })

            // body: formData
        };

        // function logout(){
        //     let formData = new FormData();
        //     formData.append('command','Logout');
        //     fetchThis('/api/v1/','command=Logout');
        //     // fetchThis('/api/v1/',formData);
        // };
    </script>
</body>
</html>

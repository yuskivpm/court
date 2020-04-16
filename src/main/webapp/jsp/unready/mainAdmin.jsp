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
    <label for="newUser" class="likeButton">Add new user</label>
    <p></p>
    <input id="newUser" class="form" type="checkbox" hidden checked/>
    <form id="newUserForm" action="api/users" method="post" >
<%--        <input type="hidden" name="command" value="MainPage" />--%>
<%--        <label class="id hide" >--%>
<%--            Name:--%>
<%--            <input type="text" name="id" />--%>
<%--        </label>--%>
        <br />
        <label>
            Name:
            <input type="text" name="name" />
        </label>
        <br />
        <label>
            Login:
            <input type="text" name="login" />
        </label>
        <br />
        <label>
            Password:
            <input type="password" name="password" />
        </label>
        <br />
        <label>
            Role:
            <select name="role">
                <c:forEach var="role" items="<%=com.dsa.model.Role.values()%>" >
                    <option value="${role}">${role}</option>
                </c:forEach>
            </select>
        </label>
        <br />
        <label>
            Court:
            <select name="courtId">
                <c:forEach var="court" items="${courts}">
                    <option value="${court.id}">${court.courtName}</option>
                </c:forEach>
            </select>
        </label>
        <br />
        <input type="checkbox" name="isActive" />
        <br />
        <input type="submit" value="Create court" />
        <input type="reset" value="Clear" />
    </form>

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
    <label for="newCourt" class="likeButton">Add new court</label>
    <p></p>
    <input id="newCourt" class="form" type="checkbox" hidden checked/>
        <form id="newCourtForm" action="api/courts" method="post" >
            <label>
                Court name:
                <input type="text" name="courtName" />
            </label>
            <br />
            <label>
                Court instance:
                <select name="courtInstance">
                    <c:forEach var="instance" items="<%=com.dsa.model.CourtInstance.values()%>">
                        <option value="${instance}">${instance}</option>
                    </c:forEach>
                </select>
            </label>
            <br />
            <label>
                Main court:
                <select name="mainCourtId">
                    <c:forEach var="court" items="${courts}">
                        <option value="${court.id}">${court.courtName}</option>
                    </c:forEach>
                </select>
            </label>
            <br />
            <input type="submit" value="Create court" />
            <input type="reset" value="Clear" />
        </form>
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

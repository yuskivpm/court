<jsp:directive.include file="mainHeader.jsp"/>
    <h3>Welcome admin</h3>
    <hr/>
    ${curUser.name}, hello!
    <button form="simpleForm" type="submit" name="command" value="Logout">Logout</button>
    <hr/>
    <br/>

    <h1>Users</h1>
    <button type="button" onclick="sendGetRequest('api?command=redirect&page=/jsp/models/UserForm.jsp')">
        Add New User
    </button>

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
                <td>${user.name}</td>
                <td>${user.login}</td>
                <td>${user.password}</td>
                <td>${user.role}</td>
                <td>${user.court.courtName}</td>
                <td>${user.isActive}</td>
                <td>
                    <button
                            type="button"
                            onclick="sendGetRequest('api/users?id=${user.id}&redirect=1&page=/jsp/models/UserForm.jsp')"
                    >
                        Edit
                    </button>
                </td>
                <td>
                    <button
                            type="button"
                            onclick="deleteEntity('api/users', ${user.id},()=>sendGetRequest('api?command=mainPage'))"
                    >
                        Delete
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <hr/>

    <h1>Courts</h1>
    <button type="button" onclick="sendGetRequest('api?command=redirect&page=/jsp/models/CourtForm.jsp')">
        Add New Court
    </button>
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
                <td>
                    <button
                            type="button"
                            onclick="sendGetRequest('api/courts?id=${court.id}&redirect=1&page=/jsp/models/CourtForm.jsp')"
                    >
                        Edit
                    </button>
                </td>
                <td>
                    <button
                            type="button"
                            onclick="deleteEntity('api/courts', ${court.id},()=>sendGetRequest('api?command=mainPage'))"
                    >
                        Delete
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <hr/>

<jsp:directive.include file="mainFooter.jsp"/>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--<a href="viewUsers.jsp">View All Records</a><br/>--%>

<h1>Add New User</h1>
<form id="userForm" action="addUser.jsp" method="post">
    <table>
        <tr>
            <td>Name:</td>
            <td>
                <input type="text" name="name"/>
            </td>
        </tr>
        <tr>
            <td>Login:</td>
            <td>
                <input type="text" name="login"/>
            </td>
        </tr>
        <tr>
            <td>Password:</td>
            <td>
                <input type="password" name="password"/>
            </td>
        </tr>
        <tr>
            <td>Role:</td>
            <td>
                <select name="role">
                    <c:forEach var="role" items="<%=com.dsa.model.Role.values()%>" >
                        <option value="${role}">${role}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Court:</td>
            <td>
                <select name="courtId">
                    <c:set var = "courts" scope = "page" value = "<%=new com.dsa.dao.entity.CourtDao()%>"/>
                    <c:forEach var="court" items="${courts.readAll()}">
                        <option value="${court.id}">${court.courtName}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <td>Active:</td>
            <td>
                <input type="checkbox" name="isActive" />
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Add User"/>
            </td>
        </tr>
    </table>
</form>

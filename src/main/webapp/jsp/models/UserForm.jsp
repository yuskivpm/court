<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>User Management</h1>
<h2>
<%--    <button form="simpleForm" type="submit" name="command" value="mainPage">Back to main page</button>--%>
    <button type="button" onclick="sendGetRequest('api?command=mainPage')">
        Back to main page
    </button>
</h2>
<form id="userForm">
    <c:if test="${editUser != null}">
        <input type="hidden" name="id" value="${editUser.id}"/>
    </c:if>
    <table border="1" cellpadding="5">
        <caption>
            <h2>
                <c:if test="${editUser != null}">
                    Edit User
                </c:if>
                <c:if test="${editUser == null}">
                    Add New User
                </c:if>
            </h2>
        </caption>
        <tr>
            <th>Name:</th>
            <td>
                <input type="text" name="name" size="45" value="${editUser.name}" required/>
            </td>
        </tr>
        <tr>
            <th>Login:</th>
            <td>
                <input type="text" name="login" size="45" value="${editUser.login}" required/>
            </td>
        </tr>
        <tr>
            <th>Password:</th>
            <td>
                <input type="password" name="password" size="45" value="${editUser.password}" required/>
            </td>
        </tr>
        <tr>
            <th>Role:</th>
            <td>
                <select name="role"/>
                <c:forEach var="role" items="<%=com.dsa.model.Role.values()%>">
                    <option
                            value="${role}"
                            <c:if test="${editUser.role == role}">
                                selected
                            </c:if>
                    >
                            ${role}
                    </option>
                </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>Court:</th>
            <td>
                <select name="courtId"/>
                <c:set var="courts" scope="page" value="<%=new com.dsa.dao.entity.CourtDao()%>"/>
                <c:forEach var="court" items="${courts.readAll()}">
                    <option
                            value="${court.id}"
                            <c:if test="${editUser.courtId == court.id}">
                                selected
                            </c:if>
                    >
                            ${court.courtName}
                    </option>
                </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>Active:</th>
            <td>
                <input type="checkbox" name="isActive"
                        <c:if test="${editUser.isActive}">
                            checked
                        </c:if>
                />
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button
                        type="button"
                        onclick="createOrUpdateEntity('userForm','api/users',()=>sendGetRequest('api?command=mainPage'))">
                    Save
                </button>
            </td>
        </tr>
    </table>
</form>

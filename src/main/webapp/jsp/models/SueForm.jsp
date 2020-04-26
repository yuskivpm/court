<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"/>
<%@ page import="com.dsa.model.Role" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.dsa.model.Sue" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>--%>
<h1>Sue Management</h1>
<h2>
    <button type="button" onclick="sendGetRequest('api?command=mainPage')">
        Back to main page
    </button>
</h2>

<form id="sueForm">
    <c:if test="${editEntity != null}">
        <input type="hidden" name="id" value="${editEntity.id}"/>
    </c:if>
    <input type="hidden" name="suitorId" value="${curUser.id}"/>
    <table border="1" cellpadding="5">
        <caption>
            <h2>
                <c:if test="${editEntity != null}">
                    Edit Sue
                </c:if>
                <c:if test="${editEntity == null}">
                    Add New Sue
                </c:if>
            </h2>
        </caption>
        <tr>
            <c:set var="attorney" scope="page" value="<%=Role.ATTORNEY%>"/>
            <th>Defendant: ${attorney}</th>
            <td>
                <select name="defendantId"/>
                <c:set var="users" scope="page" value="<%=new com.dsa.dao.entity.UserDao()%>"/>
                <c:forEach var="aUser" items="${users.readAll()}">
                    <c:if test="${aUser.id != curUser.id && aUser.role == attorney }">
                        <option
                                value="${aUser.id}"
                                <c:if test="${editEntity.defendantId == aUser.id}">
                                    selected
                                </c:if>
                        >
                                ${aUser.name}
                        </option>
                    </c:if>
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
                            <c:if test="${editEntity.courtId == court.id}">
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
        <th>Sue date:</th>
            <td>
                <input type="text" name="sueDate" size="45" required
                    <c:if test="${editEntity != null}">
                        value='<%= new SimpleDateFormat("dd.MM.yyyy").format(((Sue)request.getAttribute("editEntity")).getSueDate()) %>'
                    </c:if>
                    <c:if test="${editEntity == null}">
                        value='<%= new SimpleDateFormat("dd.MM.yyyy").format(new Date())%>'
                    </c:if>
                />
            </td>
        </tr>
        <th>Claim text:</th>
            <td>
                <input type="text" name="claimText" size="45" value="${editEntity.claimText}" required/>
            </td>
        </tr>

        <tr>
            <td colspan="2" align="center">
                <button
                        type="button"
                        onclick="createOrUpdateEntity('sueForm','api/sues',()=>sendGetRequest('api?command=mainPage'))">
                    Save
                </button>
            </td>
        </tr>
    </table>
</form>

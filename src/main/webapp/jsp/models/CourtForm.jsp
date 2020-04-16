<jsp:directive.page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<h1>Court Management</h1>
<h2>
    <button form="simpleForm" type="submit" name="command" value="mainPage">Back to main page</button>
</h2>
<form id="courtForm">
    <c:if test="${editCourt != null}">
        <input type="hidden" name="id" value="${editCourt.id}"/>
    </c:if>
    <table border="1" cellpadding="5">
        <caption>
            <h2>
                <c:if test="${editCourt != null}">
                    Edit Court
                </c:if>
                <c:if test="${editCourt == null}">
                    Add New Court
                </c:if>
            </h2>
        </caption>
        <tr>
            <th>Court name:</th>
            <td>
                <input type="text" name="courtName" size="45" value="${editCourt.courtName}" required/>
            </td>
        </tr>
        <tr>
            <th>Court Instance:</th>
            <td>
                <select name="courtInstance"/>
                <c:forEach var="courtInstance" items="<%=com.dsa.model.CourtInstance.values()%>">
                    <option
                            value="${courtInstance}"
                            <c:if test="${editCourt.courtInstance == courtInstance}">
                                selected
                            </c:if>
                    >
                            ${courtInstance}
                    </option>
                </c:forEach>
                </select>
            </td>
        </tr>
        <tr>
            <th>Main Court:</th>
            <td>
                <select name="mainCourtId"/>
                <c:set var="courts" scope="page" value="<%=new com.dsa.dao.entity.CourtDao()%>"/>
                <c:forEach var="court" items="${courts.readAll()}">
                    <option
                            value="${court.id}"
                            <c:if test="${editCourt.mainCourtId == court.id}">
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
            <td colspan="2" align="center">
                <button
                        type="button"
                        onclick="createOrUpdateEntity('courtForm','api/courts',()=>sendGetRequest('api?command=mainPage'))">
                    Save
                </button>
            </td>
        </tr>
    </table>
</form>

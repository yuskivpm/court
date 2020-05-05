<jsp:directive.page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.dsa.domain.MyEntity" %>
<%@ page import="com.dsa.domain.court.CourtInstance" %>

<h1>Lawsuit Management</h1>
<h2>
    <button type="button" onclick="sendGetRequest('api?command=main_Page')">
        Back to main page
    </button>
</h2>

<form id="lawsuitForm">
    <input type="hidden" name="id" value="${editEntity.id}"/>
    <c:set var="cassationCourt" scope="page" value="<%=CourtInstance.CASSATION%>"/>
    <c:if test="${editEntity.court.courtInstance == cassationCourt}">
        <input type="hidden" name="executionDate" value='<%= MyEntity.dateToStr(new Date())%>'/>
    </c:if>
    <table border="1" cellpadding="5">
        <caption>
            <h2>
                Adjudication
            </h2>
        </caption>
        <tr>
            <th>Verdict date:</th>
            <td>
                <input type="text" name="verdictDate" size="45" required readonly
                       value='<%= MyEntity.dateToStr(new Date())%>'
                />
            </td>
        </tr>
        <tr>
            <th>verdictText:</th>
            <td>
                <input type="text" name="verdictText" size="45" required/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button
                        type="button"
                        onclick="createOrUpdateEntity('lawsuitForm','api/lawsuits',
                            ()=>sendGetRequest('api?command=main_Page'))"
                >
                    Save
                </button>
            </td>
        </tr>
    </table>
</form>

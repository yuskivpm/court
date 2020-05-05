<jsp:directive.page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.dsa.model.Lawsuit" %>
<%@ page import="com.dsa.model.pure.MyEntity" %>

<h1>Appeal Management</h1>
<h2>
    <button type="button" onclick="sendGetRequest('api?command=main_Page')">
        Back to main page
    </button>
</h2>

<form id="appealForm">
    <input type="text" name="suitorId" hidden value="${curUser.id}"/>
    <table border="1" cellpadding="5">
        <caption>
            <h2>
                <c:if test="${editEntity != null}">
                    Edit appeal
                </c:if>
                <c:if test="${editEntity == null}">
                    Add New appeal
                </c:if>
            </h2>
        </caption>
        <tr>
            <th>Defendant:</th>
            <td>
                <c:if test="${curUser.id == editEntity.defendantId}">
                    <input type="hidden" name="defendantId" value="${editEntity.suitorId}"/>
                    <input type="text" name="defendantName" size="45" required readonly
                           value="${editEntity.suitor.name}"
                    />
                </c:if>
                <c:if test="${curUser.id != editEntity.defendantId}">
                    <input type="text" name="defendantId" hidden value="${editEntity.defendantId}"/>
                    <input type="text" name="defendantName" size="45" required readonly
                           value="${editEntity.defendant.name}"
                    />
                </c:if>
            </td>
        </tr>
        <tr>
            <th>Appeal Court:</th>
            <td>
                <input type="text" name="courtName" size="45" required readonly
                    value="${editEntity.court.mainCourt.courtName}"
                />
                <input type="text" name="courtId" hidden value="${editEntity.court.mainCourt.id}"/>
            </td>
        </tr>
        <tr>
        <th>Appeal date:</th>
            <td>
                <input type="text" name="sueDate" size="45" required readonly
                    <c:if test="${editEntity != null}">
                        value='<%= MyEntity.dateToStr(((Lawsuit)request.getAttribute("editEntity")).getSueDate()) %>'
                    </c:if>
                    <c:if test="${editEntity == null}">
                        value='<%= MyEntity.dateToStr(new Date())%>'
                    </c:if>
                />
            </td>
        </tr>
        <th>Appeal text:</th>
            <td>
                <input type="text" name="claimText" size="45" value="${editEntity.claimText}" required/>
            </td>
        </tr>

        <tr>
            <td colspan="2" align="center">
                <button
                        type="button"
                        onclick="createOrUpdateEntity('appealForm','api/sues?commit=command=/sues~method=PUT~id=${editEntity.id}~appealStatus=Appealed',()=>sendGetRequest('api?command=main_Page'))"
                >
                    Save
                </button>
<%--                <button--%>
<%--                        type="button"--%>
<%--                        onclick="createOrUpdateEntity('appealForm','api/sues?commit=1&appealedLawsuitId=${editEntity.id}',()=>sendGetRequest('api?command=main_Page'))"--%>
<%--                >--%>
<%--                    Save--%>
<%--                </button>--%>
            </td>
        </tr>
    </table>
</form>

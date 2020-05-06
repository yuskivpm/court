<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.dsa.domain.MyEntity" %>
<%@ page import="java.util.Date" %>
<jsp:directive.include file="mainHeader.jsp"/>

<h3>Welcome judge</h3>
<hr/>
${curUser.name}, hello!
<button form="simpleForm" type="submit" name="command" value="Logout">Logout</button>
<hr/>
<br/>

<h1>Sues</h1>

<table>
    <thead>
    <tr>
        <th>id</th>
        <th>suitor</th>
        <th>defendant</th>
        <th>court</th>
        <th>sueDate</th>
        <th>claimText</th>
        <th>accept</th>
    </tr>
    </thead>
    <tbody id="sues">
    <c:forEach var="sue" items="${sues}">
        <tr>
            <td>${sue.id}</td>
            <td>${sue.suitor.name}</td>
            <td>${sue.defendant.name}</td>
            <td>${sue.court.courtName}</td>
            <td>
                <fmt:formatDate pattern="dd.MM.yyyy" value="${sue.sueDate}"/>
            </td>
            <td>${sue.claimText}</td>
            <td>
                <button
                        type="button"
                        onclick="fetchThis({
                                url:'api/lawsuits',
                                method:'POST',
                                body:'id=${sue.id}&judgeId=${curUser.id}&startDate=<%= MyEntity.dateToStr(new Date())%>'
                                }, data=>{crudInformationCallback(data,()=>sendGetRequest('api?command=main_Page'))}
                                )"
                >
                    Accept
                </button>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<hr/>

<h1>Lawsuits</h1>
<table>
    <thead>
    <tr>
        <th>id</th>
        <th>sueDate</th>
        <%--        <th>Court</th>--%>
        <th>suitor</th>
        <th>claimText</th>
        <th>defendant</th>
        <th>defendantText</th>
        <%--        <th>judge</th>--%>
        <th>startDate</th>
        <th>verdictDate</th>
        <th>verdictText</th>
        <th>Appeal text</th>
        <th>appealedLawsuitId</th>
        <th>executionDate</th>
        <th>actions</th>
    </tr>
    </thead>
    <tbody id="defendantsLawsuits">
    <c:forEach var="lawsuit" items="${lawsuits}">
        <tr>
            <td>${lawsuit.id}</td>
            <td>
                <fmt:formatDate pattern="dd.MM.yyyy" value="${lawsuit.sueDate}"/>
            </td>
                <%--            <td>${lawsuit.court.courtName}</td>--%>
            <td>${lawsuit.suitor.name}</td>
            <td>${lawsuit.claimText}</td>
            <td>${lawsuit.defendant.name}</td>
            <td>${lawsuit.defendantText}</td>
                <%--            <td>${lawsuit.judge.name}</td>--%>
            <td>
                <fmt:formatDate pattern="dd.MM.yyyy" value="${lawsuit.startDate}"/>
            </td>
            <td>
                <fmt:formatDate pattern="dd.MM.yyyy" value="${lawsuit.verdictDate}"/>
            </td>
            <td>${lawsuit.verdictText}</td>
            <td>${lawsuit.appealStatus}</td>
            <td>
                <c:if test="${lawsuit.appealedLawsuitId != 0}">
                    ${lawsuit.appealedLawsuitId}
                </c:if>
            </td>
            <td>
                <fmt:formatDate pattern="dd.MM.yyyy" value="${lawsuit.executionDate}"/>
            </td>
            <td>
                <c:if test="${lawsuit.executionDate != null}">
                    Archived
                </c:if>
                <c:if test="${lawsuit.appealStatus != null && lawsuit.appealStatus != ''}">
                    Appealed
                </c:if>
                <c:if test="${lawsuit.executionDate == null && (lawsuit.appealStatus == null || lawsuit.appealStatus == '')}">
                    <c:if test="${lawsuit.verdictDate == null}">
                        <button
                                type="button"
                                onclick="sendGetRequest('api/lawsuits?id=${lawsuit.id}&redirect=1&page=/jsp/models/LawsuitForm.jsp')"
                        >
                            Verdict
                        </button>
                    </c:if>
                    <c:if test="${lawsuit.verdictDate != null}">
                        <button
                                type="button"
                                onclick="fetchThis({
                                        url:'api/lawsuits',
                                        method:'POST',
                                        body:'id=${lawsuit.id}&executionDate='+dateToStr(new Date())
                                        }, data=>{crudInformationCallback(data,()=>sendGetRequest('api?command=main_Page'))}
                                        )"
                        >
                            Start execution
                        </button>
                    </c:if>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<hr/>

<jsp:directive.include file="mainFooter.jsp"/>

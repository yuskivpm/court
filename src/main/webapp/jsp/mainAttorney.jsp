<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<jsp:directive.include file="mainHeader.jsp"/>

<h3>Welcome attorney</h3>
    <hr/>
    ${curUser.name}, hello!
    <button form="simpleForm" type="submit" name="command" value="Logout">Logout</button>
    <hr/>
    <br/>

    <h1>Sues</h1>
    <button type="button" onclick="sendGetRequest('api?command=redirect&page=/jsp/models/SueForm.jsp')">
        Add New Sue
    </button>

    <table>
        <thead>
        <tr>
            <th>id</th>
            <th>suitor</th>
            <th>defendant</th>
            <th>court</th>
            <th>sueDate</th>
            <th>claimText</th>
            <th>edit</th>
            <th>Delete</th>
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
                    <fmt:formatDate pattern = "dd.MM.yyyy" value = "${sue.sueDate}" />
                </td>
                <td>${sue.claimText}</td>
                <td>
                    <button
                            type="button"
                            onclick="sendGetRequest('api/sues?id=${sue.id}&redirect=1&page=/jsp/models/SueForm.jsp')"
                    >
                        Edit
                    </button>
                </td>
                <td>
                    <button
                            type="button"
                            onclick="deleteEntity('api/sues', ${sue.id},()=>sendGetRequest('api?command=mainPage'))"
                    >
                        Revoke
                    </button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <hr/>

    <h1>Lawsuits as suitor</h1>
    <table>
        <thead>
        <tr>
            <th>id</th>
<%--            <th>suitor</th>--%>
            <th>defendant</th>
            <th>jurisdictionCourt</th>
            <th>judge</th>
            <th>sueDate</th>
            <th>startDate</th>
            <th>claimText</th>
            <th>defendantText</th>
            <th>verdict</th>
            <th>suitorAppealDate</th>
            <th>suitorAppealText</th>
            <th>defendantAppealDate</th>
            <th>defendantAppealText</th>
            <th>executionDate</th>
            <th>actions</th>
        </tr>
        </thead>
        <tbody id="suitorsLawsuits">
        <c:forEach var="lawsuit" items="${ownLawsuits}">
            <tr>
                <td>${lawsuit.id}</td>
<%--                <td>${lawsuit.suitor.name}</td>--%>
                <td>${lawsuit.defendant.name}</td>
                <td>${lawsuit.jurisdictionCourt.courtName}</td>
                <td>${lawsuit.judge.name}</td>
                <td>
                    <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.sueDate}" />
                </td>
                <td>
                    <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.startDate}" />
                </td>
                <td>${lawsuit.claimText}</td>
                <td>${lawsuit.defendantText}</td>
                <td>
                    <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdict.effectiveDate}" />
                        ${lawsuit.verdict.verdictResult}
                        ${lawsuit.verdict.verdictText}
                </td>
                <td>
                    <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdict.suitorAppealDate}" />
                </td>
                <td>${lawsuit.suitorAppealText}</td>
                <td>
                    <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdict.defendantAppealDate}" />
                </td>
                <td>${lawsuit.defendantAppealText}</td>
                <td>
                    <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdict.executionDate}" />
                </td>
                <td>
                    UNREADY!!!!

                        <%-- TODO ATTORNEY  ТАБЛИЦЯ таблиця НП у провадженні--%>
                        <%-->- ТАБЛИЦЯ таблиця НП у провадженні (НП.Дата вступу рішення в силу == НУЛЛ) І (він==НП.ВідповідачІД або він==НП.ПозивачІД)--%>
                        <%---- якщо НП.СудІД - пустий (тобто не очікує на розписування судді)--%>
                        <%----- якщо РішенняІД - пусте--%>
                        <%------ може змінити або Позицію щодо позову (якщо позивач - Позовні вимоги); якщо відповідач - Заперечення відповідача)--%>
                        <%----- якщо РішенняІД - не пусте--%>
                        <%------ Ініціювати Оскарження--%>
                        <%------- зміст скарги (позивача/відповідача)--%>
                        <%------- Дата оскарження (позивачем/відповідачем) встановлюється автоматично--%>

                        <%--                    <button--%>
                        <%--                            type="button"--%>
                        <%--                            onclick="deleteEntity('api/courts', ${court.id},()=>sendGetRequest('api?command=mainPage'))"--%>
                        <%--                    >--%>
                        <%--                        Delete--%>
                        <%--                    </button>--%>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <hr/>

<h1>Lawsuits as defendant</h1>
<table>
    <thead>
    <tr>
        <th>id</th>
        <th>suitor</th>
<%--        <th>defendant</th>--%>
        <th>jurisdictionCourt</th>
        <th>judge</th>
        <th>sueDate</th>
        <th>startDate</th>
        <th>claimText</th>
        <th>defendantText</th>
        <th>verdict</th>
        <th>suitorAppealDate</th>
        <th>suitorAppealText</th>
        <th>defendantAppealDate</th>
        <th>defendantAppealText</th>
        <th>executionDate</th>
        <th>actions</th>
    </tr>
    </thead>
    <tbody id="defendantsLawsuits">
    <c:forEach var="lawsuit" items="${asDefendantLawsuits}">
        <tr>
            <td>${lawsuit.id}</td>
            <td>${lawsuit.suitor.name}</td>
<%--            <td>${lawsuit.defendant.name}</td>--%>
            <td>${lawsuit.jurisdictionCourt.courtName}</td>
            <td>${lawsuit.judge.name}</td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.sueDate}" />
            </td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.startDate}" />
            </td>
            <td>${lawsuit.claimText}</td>
            <td>${lawsuit.defendantText}</td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdict.effectiveDate}" />
                    ${lawsuit.verdict.verdictResult}
                    ${lawsuit.verdict.verdictText}
            </td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdict.suitorAppealDate}" />
            </td>
            <td>${lawsuit.suitorAppealText}</td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdict.defendantAppealDate}" />
            </td>
            <td>${lawsuit.defendantAppealText}</td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdict.executionDate}" />
            </td>
            <td>
                UNREADY!!!!


                    <%-- TODO ATTORNEY ТАБЛИЦЯ таблиця НП у провадженні--%>
                    <%-->- ТАБЛИЦЯ таблиця НП у провадженні (НП.Дата вступу рішення в силу == НУЛЛ) І (він==НП.ВідповідачІД або він==НП.ПозивачІД)--%>
                    <%---- якщо НП.СудІД - пустий (тобто не очікує на розписування судді)--%>
                    <%----- якщо РішенняІД - пусте--%>
                    <%------ може змінити або Позицію щодо позову (якщо позивач - Позовні вимоги); якщо відповідач - Заперечення відповідача)--%>
                    <%----- якщо РішенняІД - не пусте--%>
                    <%------ Ініціювати Оскарження--%>
                    <%------- зміст скарги (позивача/відповідача)--%>
                    <%------- Дата оскарження (позивачем/відповідачем) встановлюється автоматично--%>

            <%--                    <button--%>
                    <%--                            type="button"--%>
                    <%--                            onclick="deleteEntity('api/courts', ${court.id},()=>sendGetRequest('api?command=mainPage'))"--%>
                    <%--                    >--%>
                    <%--                        Delete--%>
                    <%--                    </button>--%>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<hr/>

<jsp:directive.include file="mainFooter.jsp"/>

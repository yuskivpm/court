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
            <th>sueDate</th>
            <th>court</th>
            <th>defendant</th>
            <th>claimText</th>
            <th>edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody id="sues">
        <c:forEach var="sue" items="${ownLawsuits}">
            <c:if test="${sue.judge == null}">
                <tr>
                    <td>${sue.id}</td>
                    <td>
                        <fmt:formatDate pattern = "dd.MM.yyyy" value = "${sue.sueDate}" />
                    </td>
                    <td>${sue.court.courtName}</td>
                    <td>${sue.defendant.name}</td>
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
            </c:if>
        </c:forEach>
        </tbody>
    </table>

    <hr/>

    <h1>Lawsuits as suitor</h1>
    <table>
        <thead>
        <tr>
            <th>id</th>
            <th>sueDate</th>
            <th>Court</th>
<%--            <th>suitor</th>--%>
            <th>claimText</th>
            <th>defendant</th>
            <th>defendantText</th>
            <th>judge</th>
            <th>startDate</th>
            <th>verdictDate</th>
            <th>verdictText</th>
            <th>executionDate</th>
            <th>actions</th>
        </tr>
        </thead>
        <tbody id="suitorsLawsuits">
        <c:forEach var="lawsuit" items="${ownLawsuits}">
            <c:if test="${lawsuit.judge != null}">
                <tr>
                    <td>${lawsuit.id}</td>
                    <td>
                        <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.sueDate}" />
                    </td>
                    <td>${lawsuit.court.courtName}</td>
                        <%-- <td>${lawsuit.suitor.name}</td>--%>
                    <td>${lawsuit.claimText}</td>
                    <td>${lawsuit.defendant.name}</td>
                    <td>${lawsuit.defendantText}</td>
                    <td>${lawsuit.judge.name}</td>
                    <td>
                        <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.startDate}" />
                    </td>
                    <td>
                        <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdictDate}" />
                    </td>
                    <td>${lawsuit.verdictText}</td>
                    <td>
                        <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.executionDate}" />
                    </td>
                    <td>
                        <c:if test="${lawsuit.executionDate != null}">
                            archived
                        </c:if>
                        <c:if test="${lawsuit.executionDate == null}">
                            <c:if test="${lawsuit.verdictDate == null}">
                                <button type="button" onclick="changePositionText('claimText','${lawsuit.claimText}',${lawsuit.id})">Change position</button>
                            </c:if>
                            <c:if test="${lawsuit.verdictDate != null}">
                                <%-- TODO ATTORNEY  ТАБЛИЦЯ таблиця НП у провадженні--%>
                                <%--                            --- якщо РішенняІД - не пусте--%>
                                <%--                            ---- Ініціювати Оскарження--%>
                                <%--                            ----- зміст скарги (позивача/відповідача)--%>
                                <%--                            ----- Дата оскарження (позивачем/відповідачем) встановлюється автоматично--%>
                            </c:if>
                        </c:if>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

    <hr/>

<h1>Lawsuits as defendant</h1>
<table>
    <thead>
    <tr>
        <th>id</th>
        <th>sueDate</th>
        <th>Court</th>
        <th>suitor</th>
        <th>claimText</th>
<%--        <th>defendant</th>--%>
        <th>defendantText</th>
        <th>judge</th>
        <th>startDate</th>
        <th>verdictDate</th>
        <th>verdictText</th>
        <th>executionDate</th>
        <th>actions</th>
    </tr>
    </thead>
    <tbody id="defendantsLawsuits">
    <c:forEach var="lawsuit" items="${asDefendantLawsuits}">
        <tr>
            <td>${lawsuit.id}</td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.sueDate}" />
            </td>
            <td>${lawsuit.court.courtName}</td>
            <td>${lawsuit.suitor.name}</td>
            <td>${lawsuit.claimText}</td>
<%--            <td>${lawsuit.defendant.name}</td>--%>
            <td>${lawsuit.defendantText}</td>
            <td>${lawsuit.judge.name}</td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.startDate}" />
            </td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.verdictDate}" />
            </td>
            <td>${lawsuit.verdictText}</td>
            <td>
                <fmt:formatDate pattern = "dd.MM.yyyy" value = "${lawsuit.executionDate}" />
            </td>
            <td>
                <c:if test="${lawsuit.executionDate != null}">
                    archived
                </c:if>
                <c:if test="${lawsuit.executionDate == null}">
                    <c:if test="${lawsuit.verdictDate == null}">
                        <button type="button" onclick="changePositionText('defendantText','${lawsuit.defendantText}',${lawsuit.id})">Change position</button>
                    </c:if>
                    <c:if test="${lawsuit.verdictDate != null}">
                        <%-- TODO ATTORNEY  ТАБЛИЦЯ таблиця НП у провадженні--%>
                        <%--                    --- якщо РішенняІД - пусте--%>
                        <%--                    ---- може змінити або Позицію щодо позову (якщо позивач - Позовні вимоги); якщо відповідач - Заперечення відповідача)--%>
                        <%--                    --- якщо РішенняІД - не пусте--%>
                        <%--                    ---- Ініціювати Оскарження--%>
                        <%--                    ----- зміст скарги (позивача/відповідача)--%>
                        <%--                    ----- Дата оскарження (позивачем/відповідачем) встановлюється автоматично--%>
                    </c:if>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<hr/>
<script>
    function changePositionText(fieldName,oldPosition, lawSuitId){
        const newPosition = prompt("Enter new position", oldPosition);
        if (newPosition !== oldPosition){
            fetchThis({
                    url:'api/sues',
                    method:'POST',
                    body:'id='+lawSuitId+'&'+fieldName+'='+newPosition
                },
                data=>{crudInformationCallback(data,()=>sendGetRequest('api?command=mainPage'))}
            )
        }
    }
</script>

<jsp:directive.include file="mainFooter.jsp"/>

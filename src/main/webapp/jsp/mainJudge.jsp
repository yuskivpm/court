<%@ page import="com.dsa.model.pure.MyEntity" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:directive.include file="mainHeader.jsp"/>

<h3>Welcome judge</h3>
<hr/>
${curUser.name}, hello!
<button form="simpleForm" type="submit" name="command" value="Logout">Logout</button>
<hr/>
<br/>

<h1>Sues</h1>
<%--> 1) ТАБЛИЦЯ Таблиця заявлених позовів (не відкрито провадження) Позов.СудІД==він.СудІД--%>
<%--// - по заявленому позову (для свого суду)--%>
<%---- відкрити проваження--%>
<%----- створюється НП за позовом, куди переносяться усі дані з позову--%>
<%----- позов видаляється--%>
<%----- заповнюється Судовий розгляд з датою "відкриття провадження"--%>
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
                    <%--                    todo: accept sue for judge --%>
                <button
                        type="button"
<%--                        onclick="sendGetRequest('api/lawsuits?sueId=${sue.id}',()=>sendGetRequest('api?command=mainPage'))"--%>
                        onclick="fetchThis({
                                        url:'api/lawsuits',
                                        method:'POST',
                                        body:'id=${sue.id}&judgeId=${curUser.id}&startDate=<%= MyEntity.dateToStr(new Date())%>'
                                    },
                                    data=>{crudInformationCallback(data,()=>sendGetRequest('api?command=mainPage'))}
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


<%--&lt;%&ndash; TODO ТАБЛИЦЯ таблиця НП у провадженні--%>
<%--ownLawsuits              !!!!!!!!!!!!!!!!!!!!!!!--%>
<%--asDefendantLawsuits         !!!!!!!!!!!!!!!!!--%>

<%--&ndash;%&gt;--%>
<%--&lt;%&ndash;>- ТАБЛИЦЯ таблиця НП у провадженні (НП.Дата вступу рішення в силу == НУЛЛ) І (він==НП.ВідповідачІД або він==НП.ПозивачІД)&ndash;%&gt;--%>
<%--&lt;%&ndash;-- якщо НП.СудІД - пустий (тобто не очікує на розписування судді)&ndash;%&gt;--%>
<%--&lt;%&ndash;--- якщо РішенняІД - пусте&ndash;%&gt;--%>
<%--&lt;%&ndash;---- може змінити або Позицію щодо позову (якщо позивач - Позовні вимоги); якщо відповідач - Заперечення відповідача)&ndash;%&gt;--%>
<%--&lt;%&ndash;--- якщо РішенняІД - не пусте&ndash;%&gt;--%>
<%--&lt;%&ndash;---- Ініціювати Оскарження&ndash;%&gt;--%>
<%--&lt;%&ndash;----- зміст скарги (позивача/відповідача)&ndash;%&gt;--%>
<%--&lt;%&ndash;----- Дата оскарження (позивачем/відповідачем) встановлюється автоматично&ndash;%&gt;--%>
<%--&lt;%&ndash;>=== посилання на сторінку з таблицею перегляду Публічних рішень&ndash;%&gt;--%>

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
            <td>
                <fmt:formatDate pattern="dd.MM.yyyy" value="${lawsuit.executionDate}"/>
            </td>
            <td>
                UNREADY!!!!
                    <%--                    TODO judge ТАБЛИЦЯ НП у мене на розгляді--%>
                    <%--> 2) ТАБЛИЦЯ НП у мене на розгляді. по Свої судових розглядах, якщо рішення немає (НП.СуддяІд==він І НП.РішенняІД - пусте)--%>
                    <%---- КНОПКА - Ухвалити рішення--%>
                    <%----- ФОРМА--%>
                    <%------ СЕЛЕКТ задовільнити/відхилити/(якщо інстанція !=1) - скерувати за підслідністю - в НП за позовом встановити СудІД!!!--%>
                    <%------ (якщо інстанція ==3) автоматично заповнити дату набрання чинності (заборонити будь-яке оскарження)--%>
                    <%--3) - по Свої судових розглядах, якщо рішення є і є оскарження Позивача/відповідача--%>
                    <%---- КНОПКА "передати в апеляцію"--%>
                    <%----- встановити новий СудІД (імітація передачі за підслідінстю)--%>
                    <%--4) - по НП, де є рішення суду (крім за підслідністю)--%>
                    <%---- заповнити дату набрання чинності (заборонити будь-яке оскарження)--%>

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


<%--Сторінка Судді--%>

<%--> 5) ТАБЛИЦЯ НП (які потребують відкриття) - по НП, де  судІД==свій суд (передане до цього суду і не розписане нікому)--%>
<%----- КНОПКА відкрити проваження--%>
<%------ перенести поточні дані в Судовий розгляд архів--%>
<%------ якщо є дані про оскарження - перенести їх в зміст позовних вимог/заперечення до позову, найменшу з дат - в дату заявлення--%>
<%------ очистити поточний розгляд і встановити дату відкриття--%>

<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 07.04.2020
  Time: 15:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page isErrorPage="true" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
    Request from ${pageContext.errorData.requestURI} is failed
    <br />
    Servlet name or type: ${pageContext.errorData.servletName}
    <br />
    Status code: ${pageContext.errorData.statusCode}
    <br />
    Exception: ${pageContext.errorData.throwable}
    <br />
    <a href="/index.jsp">Return to main page</a>
</body>
</html>

<%@page import="com.dsa.dao.entity.UserDao"%>
<jsp:useBean id="u" class="com.dsa.model.User"></jsp:useBean>
<jsp:setProperty property="*" name="u"/>

<%
    UserDao userDao=new UserDao();
    if(userDao.add(u)){
        response.sendRedirect("adduser-success.jsp");
    }else{
        response.sendRedirect("adduser-error.jsp");
    }
    userDao.close();
%>
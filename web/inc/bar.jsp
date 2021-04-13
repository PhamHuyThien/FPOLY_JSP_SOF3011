<%-- 
    Document   : bar
    Created on : Apr 13, 2021, 6:19:38 PM
    Author     : MSII
--%>

<%@page import="model.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<a href="<%=request.getContextPath()%>">HOME</a>
<%
    Users u = (Users) request.getSession().getAttribute("users");
    if (u == null) {
%>
<a href="login">Login</a>
<a href="register">Register</a>
<%
} else {
    if (u.getRole()) {
%>
<a href="<%=request.getContextPath()%>/admin/users-mng">Users Manager</a>
<a href="<%=request.getContextPath()%>/admin/videos-mng">Videos Manager</a>
<a href="<%=request.getContextPath()%>/admin/analysis-mng">Analysis Manager</a>
<%
    }
%>
<a href="<%=request.getContextPath()%>/change">Change Info</a>
<a href="<%=request.getContextPath()%>/logout">Logout</a>
<%
    }
%>
<br/>
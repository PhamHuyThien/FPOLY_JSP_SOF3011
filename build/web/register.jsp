<%-- 
    Document   : register
    Created on : Apr 12, 2021, 7:00:36 PM
    Author     : MSII
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/header.jsp" %>

<form method="post" action="register">
    username: <input type="text" name="user" value=""/><br/>
    name: <input type="text" name="name" value=""/><br/>
    password: <input type="text" name="pass" value=""/><br/>
    confirm password: <input type="text" name="confpass" value=""/><br/>
    <button name="register">Register</button><br/>
    <span style="color: red">${error}</span>
</form>
    
<%@include file="inc/footer.jsp" %>

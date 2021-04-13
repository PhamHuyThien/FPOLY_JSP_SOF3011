<%-- 
    Document   : login
    Created on : Apr 12, 2021, 6:42:10 PM
    Author     : MSII
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/header.jsp" %>

        <form method="post" action="login" />
            username: <input type="text" name ="user" value=""/><br/>
            password: <input type="text" name ="pass" value=""/><br/>
            <button name="login">Login</button><br/>
            <span style="color: red">${error}</span>
        </form>
<%@include file="inc/footer.jsp" %>

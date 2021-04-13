

<%@page import="model.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/header.jsp" %>

<%
    Users users = (Users) request.getSession().getAttribute("users");
%>
<form method="post" action="change">
    <input type="hidden" name="id"/>
    name: <input type="text" name="name" value="<%=users.getName()%>"/><br/>
    email: <input type="text" name="email" value="<%=users.getEmail()%>"/><br/>
    phone: <input type="text" name="phone" value="<%=users.getPhone()%>"/><br/>
    pass: <input type="text" name="pass" value="<%=users.getPass()%>"/><br/>
    <button name="submit" value="update">Update</button> 
</form>
<span style="color: red">${error}</span>


<%@include file="inc/footer.jsp" %>

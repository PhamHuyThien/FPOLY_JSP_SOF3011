<%-- 
    Document   : users-mng
    Created on : Apr 12, 2021, 7:44:08 PM
    Author     : MSII
--%>

<%@page import="util.Utils"%>
<%@page import="java.util.Date"%>
<%@page import="modelcontroler.UsersDAO"%>
<%@page import="modelcontroler.DataAccessObject"%>
<%@page import="model.Users"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="../inc/header.jsp" %>

        <form method="post" action="users-mng">
            <input type="hidden" name="id"/>
            user: <input type="text" name="user"/><br/>
            name: <input type="text" name="name"/><br/>
            email: <input type="text" name="email"/><br/>
            phone: <input type="text" name="phone"/><br/>
            pass: <input type="text" name="pass"/><br/>
            role: <select name="role">
                <option value="0">Người dùng</option>
                <option value="1">Admin</option>
            </select><br/>
            active: <select name="status">
                <option value="0">Bỏ kích hoạt</option>
                <option value="1" selected>kích hoạt</option>
            </select><br/>
            <button name="submit" value="add">Add</button> 
            <button name="submit" value="update">Update</button> 
            <button name="submit" value="delete"/>Delete</button> 
    </form>
    <br/>
    <span style="color: red">${error}</span>
    <br/>
    <table border="1">
        <thead>
        <th>id</th>
        <th>user</th>
        <th>name</th>
        <th>email</th>
        <th>phone</th>
        <th>password</th>
        <th>role</th>
        <th>active</th>
        <th>time</th>
        <th>action</th>
    </thead>
    <tbody>
        <%
            DataAccessObject<Users> usersDAO = new UsersDAO();
            List<Users> lUsers = usersDAO.getAll();
            for (int i = 0; i < lUsers.size(); i++) {
                Users users = lUsers.get(i);
                String line = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
                line = String.format(line,
                        users.getId(),
                        users.getUser(),
                        users.getName(),
                        users.getEmail(),
                        users.getPhone(),
                        users.getPass(),
                        users.getRole()?"Admin":"Người dùng",
                        users.getStatus()?"Kích hoạt":"Bỏ kích hoạt",
                        Utils.toVnTime(users.getTime()),
                        "<a href='#' onclick='insert(this);'>insert</a>"
                );
        %>
        <%=line%>
        <%
            };
        %>
    </tbody>
</table>
<script>
    function insert(btn) {
        let nodes = btn.parentNode.parentNode.childNodes;
        $("input[name='id']").val(nodes[0].textContent);
        $("input[name='user']").val(nodes[1].textContent);
        $("input[name='name']").val(nodes[2].textContent);
        $("input[name='email']").val(nodes[3].textContent);
        $("input[name='phone']").val(nodes[4].textContent);
        $("input[name='pass']").val(nodes[5].textContent);
        $("select[name='role'] > option").removeAttr('selected');
        $("select[name='role'] > option[value='" + (nodes[6].textContent == "Admin" ? 1 : 0) + "']").attr("selected", true);
        $("select[name='status'] > option").removeAttr('selected');
        $("select[name='status'] > option[value='" + (nodes[7].textContent == "Kích hoạt" ? 1 : 0) + "']").attr("selected", true);
    }

</script>

<%@include file="../inc/footer.jsp" %>

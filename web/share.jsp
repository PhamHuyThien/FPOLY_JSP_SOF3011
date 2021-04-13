<%-- 
    Document   : share.jsp
    Created on : Apr 13, 2021, 4:09:10 PM
    Author     : MSII
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="inc/header.jsp" %>

        <%
            String idVideos = request.getParameter("id_videos");
            if (idVideos == null) {
                response.sendRedirect("index");
            }
        %>
        <form method="POST" action="share">
            Nhập email cần share: <input type="text" name="email" value=""/>
            <button name="share" value="<%=idVideos%>">Share</button> 
        </form>
<%@include file="inc/footer.jsp" %>



<%@page import="model.Videos"%>
<%@page import="modelcontroler.VideosDAO"%>
<%@page import="modelcontroler.FavoritesDAO"%>
<%@page import="model.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../inc/header.jsp" %>
<%
    Users users = (Users) request.getSession().getAttribute("users");
    String id = request.getParameter("id_videos");
    String src = request.getParameter("id_ytb");
    if (id == null || src == null) {
        response.sendRedirect(request.getContextPath() + "/index");
        return;
    }
    Videos videos = new VideosDAO().get(Integer.parseInt(id));
    boolean isLike = false;
    if (users != null) {
        isLike = new FavoritesDAO().query(
                "select * from favorites where id_users=? and id_videos=?",
                -1,
                -1,
                users.getId() + "",
                videos.getId() + ""
        ).size() > 0;
    }
%>
<span style="color: red">${error}</span><br/>
<iframe width="100%" height="550" src="https://www.youtube.com/embed/<%=src%>?rel=0&autoplay=1" title="YouTube video player" frameborder="0" allow="autoplay;" allowfullscreen>
</iframe>
<h2><%=videos.getTitle()%> 
    <span style="font-weight: normal; font-size: 15px">[<%=videos.getDescription()%>]</span>
</h2>
<form method="post" action="view?<%=request.getQueryString()%>">
    <button name="like" value="<%=videos.getId()%>"><%=isLike ? "UNLIKE" : "LIKE"%></button>
    <button name="share" value="<%=videos.getId()%>">Share</button>
</form>
<%@include file="../inc/footer.jsp" %>

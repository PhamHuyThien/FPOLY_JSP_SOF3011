

<%@page import="modelcontroler.FavoritesDAO"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="modelcontroler.VideosDAO"%>
<%@page import="model.Videos"%>
<%@page import="java.util.List"%>
<%@page import="model.Users"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@include file="inc/header.jsp" %>


<span style="color: red">${error}</span>
<form method="post" action="index">
    <%
        Users users = (Users) request.getSession().getAttribute("users");
        String sql = "select * from videos where status=1";
        List<Videos> lVideos = new VideosDAO().query(sql);
        for (int i = 0; i < lVideos.size(); i++) {
            Videos videos = lVideos.get(i);
            String link = "https://www.youtube.com/embed/%s";
            Pattern p = Pattern.compile("v=(.+?)(&|$)");
            Matcher m = p.matcher(videos.getPoster());
            m.find();
            link = String.format(link, m.group().replaceAll("v=|&$", ""));

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
    <iframe width="700" height="500" src="<%=link%>" title="YouTube video player" frameborder="0" allow="accelerometer; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen>
    </iframe>
    <h2><%=videos.getTitle()%> <span style="font-weight: normal; font-size: 15px">[<%=videos.getDescription()%>]</span></h3>
        <button name="like" value="<%=videos.getId()%>"><%=isLike ? "UNLIKE" : "LIKE"%></button>
        <button name="share" value="<%=videos.getId()%>">Share</button>
        <br/>
        <br/>
        <%
            }
        %>
</form>

        
<%@include file="inc/footer.jsp" %>
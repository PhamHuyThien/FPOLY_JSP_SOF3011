

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
            Pattern p = Pattern.compile("v=(.+?)(&|$)");
            Matcher m = p.matcher(videos.getPoster());
            m.find();
            String src = m.group().replaceAll("v=|&$", "");
    %>
    <a href="view?id_videos=<%=videos.getId()%>&id_ytb=<%=src%>">
        <img src="https://img.youtube.com/vi/<%=src%>/maxresdefault.jpg"  width="700px"/>
    </a>
    <h2><%=videos.getTitle()%> 
        <span style="font-weight: normal; font-size: 15px">[<%=videos.getDescription()%>]</span>
        <a href="view?id_videos=<%=videos.getId()%>&id_ytb=<%=src%>">Xem video</a>
    </h2>
    <br/>
    <br/>
    <%
        }
    %>
</form>


<%@include file="inc/footer.jsp" %>

<%@page import="util.Utils"%>
<%@page import="modelcontroler.VideosDAO"%>
<%@page import="model.Videos"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../inc/header.jsp" %>

<form method="POST" action="videos-mng">
    <input type="hidden" name="id"/>
    link ytb: <input type="text" name="poster"/><br/>
    title: <input type="text" name="title"/><br/>
    description: <input type="text" name="description"/><br/>
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
    <th>poster</th>
    <th>title</th>
    <th>description</th>
    <th>view</th>
    <th>status</th>
    <th>time</th>
    <th>action</th>
</thead>
<tbody>
    <%
        List<Videos> lVideos = new VideosDAO().getAll();
        for (int i = 0; i < lVideos.size(); i++) {
            Videos videos = lVideos.get(i);
            String line = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>";
            line = String.format(line,
                    videos.getId(),
                    videos.getPoster(),
                    videos.getTitle(),
                    videos.getDescription(),
                    videos.getViews(),
                    videos.getStatus() == 1 ? "Kích hoạt" : "Bỏ kích hoạt",
                    Utils.toVnTime(videos.getTime()),
                    "<a href='#' onclick='insert(this);'>insert</a>"
            );
    %>
    <%=line%>
    <%
        }
    %>
</tbody>
</table>
<script>
    function insert(btn) {
        let nodes = btn.parentNode.parentNode.childNodes;
        $("input[name='id']").val(nodes[0].textContent);
        $("input[name='poster']").val(nodes[1].textContent);
        $("input[name='title']").val(nodes[2].textContent);
        $("input[name='description']").val(nodes[3].textContent);
        $("input[name='phone']").val(nodes[4].textContent);
        $("select[name='status'] > option").removeAttr('selected');
        $("select[name='status'] > option[value='" + (nodes[5].textContent == "Kích hoạt" ? 1 : 0) + "']").attr("selected", true);
    }
</script>
<%@include file="../inc/footer.jsp" %>


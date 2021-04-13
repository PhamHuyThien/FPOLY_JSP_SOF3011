

<%@page import="java.util.Date"%>
<%@page import="modelcontroler.UsersDAO"%>
<%@page import="model.Users"%>
<%@page import="util.Utils"%>
<%@page import="modelcontroler.VideosDAO"%>
<%@page import="model.Videos"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="modelcontroler.HibernateUtil"%>
<%@page import="modelcontroler.FavoritesDAO"%>
<%@page import="model.Favorites"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@include file="../inc/header.jsp" %>

        <h1>Favorite</h1>
        <table border="1">
            <thead>
            <th>id</th>
            <th>title</th>
            <th>views</th>
            <th>favorite count</th>
            <th>last date</th>
            <th>oldest date</th>
        </thead>
        <tbody>
            <%
                Connection connection = HibernateUtil.getConnection();
                String sql = "SELECT *, count(id) as favorite from favorites GROUP by id_videos";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int idVideos = resultSet.getInt("id_videos");
                    int countFav = resultSet.getInt("favorite");
                    Videos videos = new VideosDAO().get(idVideos);
                    String line = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>\n";
                    line = String.format(line,
                            videos.getId(),
                            videos.getTitle(),
                            videos.getViews(),
                            countFav,
                            Utils.toVnTime(videos.getTime()),
                            Utils.toVnTime(Utils.getTime())
                    );
            %><%=line%><%
                }
            %>
        </tbody>
    </table>
    <h1>Favorite Users</h1>
    <form>
        <select name="favorite-user">
            <%
                String viewFU = request.getParameter("view-favorite-user");
                String idFU = request.getParameter("favorite-user");
                List<Videos> lVideos = new VideosDAO().getAll();
                for (int i = 0; i < lVideos.size(); i++) {
                    Videos videos = lVideos.get(i);
                    String line = "<option value='%s' %s>%s</option>\n";
                    line = String.format(line,
                            videos.getId(),
                            idFU != null && idFU.equals("" + videos.getId()) ? "selected" : "",
                            videos.getTitle()
                    );
            %><%=line%><%
                }
            %>
        </select>
        <input type="submit" name="view-favorite-user" value="Xem"/>
    <table border="1">
        <thead>
        <th>id</th>
        <th>username</th>
        <th>fullname</th>
        <th>email</th>
        <th>favorite date</th>
    </thead>
    <tbody>
        <%
            if (viewFU!=null && idFU != null) {
                sql = "select * from favorites where id_videos=?";
                preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setInt(1, Integer.parseInt(idFU));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    int time = resultSet.getInt("time");
                    int idUser = resultSet.getInt("id_users");
                    Users users = new UsersDAO().get(idUser);
                    String line = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>\n";
                    line = String.format(line,
                            users.getId(),
                            users.getUser(),
                            users.getName(),
                            users.getEmail(),
                            Utils.toVnTime(time)
                    );
        %><%=line%><%
                }
            }
        %>
    </tbody>
</table>
<h1>Shared friends</h1>
    <select name="share-friend">
        <%
            String idSF = request.getParameter("share-friend");
            String viewSF = request.getParameter("view-share-friend");
            for (int i = 0; i < lVideos.size(); i++) {
                Videos videos = lVideos.get(i);
                String line = "<option value='%s' %s>%s</option>\n";
                line = String.format(line,
                        videos.getId(),
                        idSF != null && idSF.equals("" + videos.getId()) ? "selected" : "",
                        videos.getTitle()
                );
        %><%=line%><%
            }
        %>
    </select>
    <input type="submit" name="view-share-friend" value="Xem"/>
</form>
<table border="1">
    <thead>
    <th>id</th>
    <th>sender name</th>
    <th>sender email</th>
    <th>receiver email</th>
    <th>send date</th>
</thead>
<tbody>
    <%
        if (viewSF!=null && idSF != null) {
            sql = "select * from shares where id_videos=?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(idSF));
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int time = resultSet.getInt("time");
                String email = resultSet.getString("email");
                int idUser = resultSet.getInt("id_users");
                Users users = new UsersDAO().get(idUser);
                String line = "<tr><td>%s</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>\n";
                line = String.format(line,
                        users.getId(),
                        users.getUser(),
                        users.getEmail(),
                        email,
                        Utils.toVnTime(time)
                );
    %><%=line%><%
            }
        }
    %>
</tbody>
</table>
<%@include file="../inc/footer.jsp" %>

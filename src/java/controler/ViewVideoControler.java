/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Favorites;
import model.Users;
import model.Videos;
import modelcontroler.DataAccessObject;
import modelcontroler.FavoritesDAO;
import modelcontroler.VideosDAO;
import util.Utils;

/**
 *
 * @author MSII
 */
@WebServlet(name = "view", urlPatterns = {"/view"})
public class ViewVideoControler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users users = (Users) request.getSession().getAttribute("users");
        String idVideos = request.getParameter("id_videos");
        if (users != null && idVideos != null) {
            DataAccessObject<Videos> videosDAO = new VideosDAO();
            Videos videos = videosDAO.get(Integer.parseInt(idVideos));
            videos.setViews(videos.getViews() + 1);
            videosDAO.update(videos);
        }
        request.getRequestDispatcher("view.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users users = (Users) request.getSession().getAttribute("users");
        if (users == null) {
            response.sendRedirect("login");
            return;
        }
        String like = request.getParameter("like");
        String share = request.getParameter("share");
        if (like != null) {
            int idVideos = Integer.parseInt(like);
            DataAccessObject favoritesDAO = new FavoritesDAO();
            List<Favorites> lFavorites = favoritesDAO.query(
                    "select * from favorites where id_users=? and id_videos=?",
                    -1,
                    -1,
                    users.getId() + "",
                    idVideos + ""
            );
            if (lFavorites.isEmpty()) {
                Favorites favorites = new Favorites(users.getId(), idVideos, Utils.getTime());
                if (!favoritesDAO.save(favorites)) {
                    setError("Like thất bại!", request, response);
                    return;
                }
                setError("Like thành công!", request, response);
            } else {
                if (!favoritesDAO.delete(lFavorites.get(0))) {
                    setError("Unlike không thành công!", request, response);
                    return;
                }
                setError("UnLike thành công!", request, response);
            }
            return;
        } else if (share != null) {
            response.sendRedirect("share?id_videos=" + share);
            return;
        }
        request.getRequestDispatcher("view.jsp").forward(request, response);
    }

    private void setError(String error, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String url = req.getRequestURI();
        String idVideos = req.getParameter("id_videos");
        String idYoutube = req.getParameter("id_ytb");
        req.setAttribute("error", error);
        req.getRequestDispatcher("view.jsp?id_videos=" + idVideos + "&id_ytb=" + idYoutube).forward(req, res);
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Favorites;
import model.Users;
import modelcontroler.DataAccessObject;
import modelcontroler.FavoritesDAO;
import util.Utils;

/**
 *
 * @author MSII
 */
@WebServlet(name = "index", urlPatterns = {"/index"})
public class IndexControler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users users = (Users) request.getSession().getAttribute("users");
        if(users==null){
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
            response.sendRedirect("share?id_videos="+share);
            return;
        }
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    private void setError(String error, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.getRequestDispatcher("index.jsp").forward(req, res);
    }
}

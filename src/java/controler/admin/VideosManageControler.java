/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Videos;
import modelcontroler.DataAccessObject;
import modelcontroler.VideosDAO;
import util.Utils;

/**
 *
 * @author MSII
 */
@WebServlet(name = "videos-mng", urlPatterns = {"/admin/videos-mng"})
public class VideosManageControler extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("videos-mng.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        String id = request.getParameter("id");
        String poster = request.getParameter("poster");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String status = request.getParameter("status");
        DataAccessObject<Videos> videosDAO = new VideosDAO();
        Videos videos;
        switch (submit) {
            case "add":
                videos = new Videos(title, poster, 0, description, Integer.parseInt(status), Utils.getTime());
                if (!videosDAO.save(videos)) {
                    setError("Add videos thất bại!", request, response);
                    return;
                }
                setError("Add videos thành công!", request, response);
                break;
            case "update":
                videos = videosDAO.get(Integer.parseInt(id));
                videos.setPoster(poster);
                videos.setTitle(title);
                videos.setDescription(description);
                videos.setStatus(Integer.parseInt(status));
                if(!videosDAO.update(videos)){
                    setError("Cập nhật thất bại!", request, response);
                    return;
                }
                setError("Cập nhật thành công!", request, response);
                break;
            case "delete":
                videos = videosDAO.get(Integer.parseInt(id));
                videos.setStatus(0);
                if(!videosDAO.update(videos)){
                    setError("Xóa mềm thất bại!", request, response);
                    return;
                }
                setError("Xóa mềm thành công!", request, response);
                break;
            default:
                request.getRequestDispatcher("videos-mng.jsp").forward(request, response);
        }
    }

    private void setError(String error, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.getRequestDispatcher("videos-mng.jsp").forward(req, res);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Shares;
import model.Users;
import modelcontroler.DataAccessObject;
import modelcontroler.SharesDAO;
import util.Utils;

/**
 *
 * @author MSII
 */
@WebServlet(name = "share", urlPatterns = {"/share"})
public class ShareControler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("share.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Users users = (Users) request.getSession().getAttribute("users");
        String share = request.getParameter("share");
        if (share != null) {
            int idVideos = Integer.parseInt(share);
            String email = request.getParameter("email");
            DataAccessObject<Shares> sharesDAO = new SharesDAO();
            Shares shares = new Shares(users.getId(), idVideos, email, Utils.getTime());
            if (!sharesDAO.save(shares)) {
                setError("share thất bại!", request, response);
                return;
            }
            setError("share thành công!", request, response);
            return;
        }
        request.getRequestDispatcher("share.jsp").forward(request, response);
    }

    private void setError(String error, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.getRequestDispatcher("share.jsp").forward(req, res);
    }

}

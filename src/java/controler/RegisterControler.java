/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Users;
import modelcontroler.DataAccessObject;
import modelcontroler.UsersDAO;
import util.Utils;

/**
 *
 * @author MSII
 */
@WebServlet(name = "register", urlPatterns = {"/register"})
public class RegisterControler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String register = request.getParameter("register");
        if (register != null) {
            String user = request.getParameter("user").trim();
            String name = request.getParameter("name").trim();
            String pass = request.getParameter("pass").trim();
            String confpass = request.getParameter("confpass").trim();

            if (user.length() < 6) {
                setError("tài khoản phải trên 5 kí tự!", request, response);
                return;
            }
            if (pass.length() < 6) {
                setError("mật khẩu phải trên 5 kí tự!", request, response);
                return;
            }
            if (name.length() == 0) {
                setError("tên không được để trống!", request, response);
                return;
            }

            if (!pass.equals(confpass)) {
                setError("xác nhận mật khẩu không khớp với nhau!", request, response);
                return;
            }
            Users users = new Users(user, pass, name, "", "", Boolean.FALSE, Boolean.TRUE, Utils.getTime());
            DataAccessObject<Users> daoUsers = new UsersDAO();
            if (!daoUsers.save(users)) {
                setError("Tạo tài khoản thất bại!", request, response);
                return;
            }
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private void setError(String error, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.getRequestDispatcher("register.jsp").forward(req, res);
    }

}

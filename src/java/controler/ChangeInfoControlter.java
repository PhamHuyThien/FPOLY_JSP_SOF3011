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

/**
 *
 * @author MSII
 */
@WebServlet(name = "change", urlPatterns = {"/change"})
public class ChangeInfoControlter extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("change.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        if (submit != null) {
            boolean logout = false;
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");
            String pass = request.getParameter("pass");
            Users users = (Users) request.getSession().getAttribute("users");
            users.setName(name);
            users.setEmail(email);
            users.setPhone(phone);
            if (!pass.equals(users.getPass())) {
                users.setPass(pass);
                logout=true;
            }
            DataAccessObject<Users> usersDAO = new UsersDAO();
            if(!usersDAO.update(users)){
                setError("Cập nhật thông tin thất bại!", request, response);
                return;
            }
            if(logout){
                response.sendRedirect("logout");
                return;
            }
            setError("cập nhật thành công!", request, response);
        }
        request.getRequestDispatcher("change.jsp").forward(request, response);
    }
    
        private void setError(String error, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.getRequestDispatcher("change.jsp").forward(req, res);
    }
}

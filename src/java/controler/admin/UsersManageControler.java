/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.admin;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "users-mng", urlPatterns = {"/admin/users-mng"})
public class UsersManageControler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("users-mng.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String submit = request.getParameter("submit");
        String id = request.getParameter("id");
        String user = request.getParameter("user");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String pass = request.getParameter("pass");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        
        DataAccessObject<Users> usersDAO = new UsersDAO();
        Users users;
        switch (submit) {
            case "add":
                users = new Users(user, pass, name, email, phone, role.equals("1"), status.equals("1"), Utils.getTime());
                if (!usersDAO.save(users)) {
                    setError("Add users thất bại!", request, response);
                    return;
                }
                setError("Add Users thành công!", request, response);
                break;
            case "update":
                users = usersDAO.get(Integer.parseInt(id));
                users.setUser(user);
                users.setPass(pass);
                users.setName(name);
                users.setEmail(email);
                users.setPhone(phone);
                users.setRole(role.equals("1"));
                users.setStatus(status.equals("1"));
                if(!usersDAO.update(users)){
                    setError("Cập nhật thất bại!", request, response);
                    return;
                }
                setError("Cập nhật thành công!", request, response);
                break;
            case "delete":
                users = usersDAO.get(Integer.parseInt(id));
                users.setStatus(false);
                if(!usersDAO.update(users)){
                    setError("Xóa mềm thất bại!", request, response);
                    return;
                }
                setError("Xóa mềm thành công!", request, response);
                break;
            default:
                request.getRequestDispatcher("users-mng.jsp").forward(request, response);
        }
    }

    private void setError(String error, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setAttribute("error", error);
        req.getRequestDispatcher("users-mng.jsp").forward(req, res);
    }
}

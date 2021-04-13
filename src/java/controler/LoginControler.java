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
import model.Users;
import modelcontroler.DataAccessObject;
import modelcontroler.UsersDAO;

/**
 *
 * @author MSII
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class LoginControler extends HttpServlet {


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String login = request.getParameter("login");
        if(login!=null){
            String user = request.getParameter("user").trim();
            String pass = request.getParameter("pass").trim();
            
            if(user.length()<6){
                setError("tài khoản phải trên 5 kí tự!", request, response);
                return;
            }
            if(pass.length()<6){
                setError("mật khẩu phải trên 5 kí tự!", request, response);
                return;
            }
            
            DataAccessObject<Users> daoUsers = new UsersDAO();
            List<Users> lUsers = daoUsers.query("select * from users where user=? and pass=?", -1, -1, user, pass);
            if(lUsers.isEmpty()){
                setError("Tài khoản hoặc mật khẩu không chính xác!", request, response);
                return;
            }
            Users users = lUsers.get(0);
            if(!users.getStatus()){
                setError("Tài khoản đã bị khóa!", request, response);
                return;
            }
            request.getSession().setAttribute("users", users);
            response.sendRedirect("index");
            return;
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    private void setError(String error, HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
        req.setAttribute("error", error);
        req.getRequestDispatcher("login.jsp").forward(req, res);
    }

}


package com.mycompany.mycart.servlets;

import com.mycompany.mycart.dao.UserDao;
import com.mycompany.mycart.entities.User;
import com.mycompany.mycart.helper.FactoryProvider;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //coding area
            //Fetching data
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            //Validations
            //Authenticating user
            UserDao userDao = new UserDao(FactoryProvider.getFactory()); //providing session factory to userDao
            User user=userDao.getUserByEmailAndPassword(email, password);//return user by getUserByEmailAndPassword method of userDao
            //System.out.println(user);
            HttpSession httpSession = request.getSession();
            
            if(user==null)// check user is null or not
            {
                
                httpSession.setAttribute("message", "Invalid Details !! Try with another one");
                response.sendRedirect("login.jsp");
                return;
            }
            else
            {
                out.println("<h1> Welcome "+ user.getUserName() + "</h1>");
                
                //storing login data by help of session
                httpSession.setAttribute("current-user", user);//if user is present in httpSession,it means logged in otherwise logout
                if(user.getUserType().equals("admin"))
                {
                    //login for admin:- admin.jsp
                    response.sendRedirect("admin.jsp");
                }else if(user.getUserType().equals("normal")){
                     //login for normal:- normal.jsp
                    response.sendRedirect("normal.jsp");
                }else
                {
                    out.println("We have not identified user type");
                }
                
            
            }   
                    
                 
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

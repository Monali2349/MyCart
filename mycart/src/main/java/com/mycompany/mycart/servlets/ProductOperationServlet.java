
package com.mycompany.mycart.servlets;

import com.mycompany.mycart.dao.CategoryDao;
import com.mycompany.mycart.dao.ProductDao;
import com.mycompany.mycart.entities.Category;
import com.mycompany.mycart.entities.Product;
import com.mycompany.mycart.helper.FactoryProvider;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;


@MultipartConfig //Servlet should accept form of multipart type

@WebServlet(name = "ProductOperationServlet", urlPatterns = {"/ProductOperationServlet"})
public class ProductOperationServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            //servlet 2:
            //add category
            //add product
            String op = request.getParameter("operation");
            
            if(op.trim().equals("addcategory"))
            {
                //add category
                //fetching category data
            String title = request.getParameter("catTitle");
            String description = request.getParameter("catDescription");
            //making Category object
            Category category = new Category();
            category.setCategoryTitle(title);
            category.setCategoryDescription(description);
            
                //category database save:
                CategoryDao categoryDao = new CategoryDao(FactoryProvider.getFactory()); //get and pass factory
                int catId=categoryDao.saveCategory(category);
                //out.println("Category Saved");
                //For printing message,we need httpSession
                //putting message in session and redirecting it to admin page
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("message", "Category added successfully : "+catId);
                response.sendRedirect("admin.jsp");
                return;
                
            }else if(op.trim().equals("addproduct"))
            {
                //add product
                //fetching product details
                String pName = request.getParameter("pName");
                String pDesc = request.getParameter("pDesc");
                int pPrice = Integer.parseInt(request.getParameter("pPrice"));
                int pDiscount = Integer.parseInt(request.getParameter("pDiscount"));
                int pQuantity = Integer.parseInt(request.getParameter("pQuantity"));
                int catId = Integer.parseInt(request.getParameter("catId"));
                Part part=request.getPart("pPic");
                
                //saving product details to db
                Product p = new Product();
                p.setpName(pName);
                p.setpDesc(pDesc);
                p.setpPrice(pPrice);
                p.setpDiscount(pDiscount);
                p.setpQuantity(pQuantity);
                p.setpPhoto(part.getSubmittedFileName());
                
                //get category by id and save category to db
                CategoryDao cdao=new CategoryDao(FactoryProvider.getFactory());
                Category category = cdao.getCategoryById(catId);
                p.setCategory(category);
                
                //product save...
                ProductDao pdao=new ProductDao(FactoryProvider.getFactory());
                pdao.saveProduct(p);
                
                
                //pic upload
                //find out the path to upload photo
                String path=request.getRealPath("img")+File.separator+"products"+File.separator+part.getSubmittedFileName();
                System.out.println(path);
                
                //uploading code...
                try {
                    
               
                FileOutputStream fos=new FileOutputStream(path);//writing data
                InputStream is=part.getInputStream();
                
                //reading data
                
                byte []data=new byte[is.available()];//array will be of same size as that of photo
                
                is.read(data);
                
                //writing data
                fos.write(data);
                
                fos.close();
                
                
                 } catch (Exception e) {
                     e.printStackTrace();
                }
                out.println("Product saved success...");
                HttpSession httpSession = request.getSession();
                httpSession.setAttribute("message", "Product added successfully....");
                response.sendRedirect("admin.jsp");
                return;
                
                
                
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

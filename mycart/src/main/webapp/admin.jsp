<%@page import="java.util.Map"%>
<%@page import="com.mycompany.mycart.helper.Helper"%>
<%@page import="com.mycompany.mycart.entities.Category"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.mycart.helper.FactoryProvider"%>
<%@page import="com.mycompany.mycart.dao.CategoryDao"%>
<!--Protection of admin page-->
<%@page import="com.mycompany.mycart.entities.User"%>
<%
    User user=(User)session.getAttribute("current-user");//get value of current user from login page
    if(user==null) //if user doesn't exist in db
    {
        session.setAttribute("message", "You are not logged in !! Login first");
        response.sendRedirect("login.jsp");
        return;
    }
    else //if user is not admin but normal
    {
        if(user.getUserType().equals("normal"))
        {
            session.setAttribute("message", "You are not admin !! Do not access this page");
            response.sendRedirect("login.jsp");
            return;
        }
    }

 %>
 <%          CategoryDao cdao = new CategoryDao(FactoryProvider.getFactory());
     List<Category> list = cdao.getCategories();

//getting count

Map<String,Long> m=Helper.getCounts(FactoryProvider.getFactory());
 %>

 <%@page contentType="text/html" pageEncoding="UTF-8"%>
 <!DOCTYPE html>
 <html>
     <head>
         <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Admin Panel</title>
         <%@include file="components/common_css_js.jsp" %>
     </head>
     <body>

         <%@include file="components/navbar.jsp" %>
         <div class="container admin">
             
             <!--Message of category saved will be seen-->
             <div class="container-fluid mt-3">
                 <%@include file="components/message.jsp" %>
                 
                 
             </div>
             
             
             
             <!--first row-->
             <div class="row mt-3">
                 <!--first box-->
                 <div class="col-md-4">
                     <div class="card">
                         <div class="card-body text-center">
                             <div class="container">
                                 <img style="max-width:125px;" class="img-fluid" src="img/teamwork.png" alt="user_icon">

                                 </img>
                             </div>
                             <h1><%=m.get("userCount") %></h1>
                             <h1 class="text-uppercase text-muted">Users</h1>
                         </div>
                     </div> 
                 </div>
                 <!--second box-->
                 <div class="col-md-4">
                     <div class="card">
                         <div class="card-body text-center">
                             <div class="container">
                                 <img style="max-width:125px;" class="img-fluid" src="img/list.png" alt="category_icon"></img>
                             </div>
                             <h1><%= list.size() %></h1>
                             <h1 class="text-uppercase text-muted">Categories</h1>
                         </div>
                     </div> 
                 </div>
                 <!--third box-->
                 <div class="col-md-4">
                     <div class="card">
                         <div class="card-body text-center">
                             <div class="container">
                                 <img style="max-width:125px;" class="img-fluid" src="img/product.png" alt="product_icon"></img>
                             </div>
                             <h1><%=m.get("productCount") %></h1>
                             <h1 class="text-uppercase text-muted">Products</h1>
                         </div>
                     </div> 
                 </div>
             </div>
             <!--second row-->
             <div class="row mt-3">
                 <!--second row : first col-->
                 <div class="col-md-6">
                     <div class="card" data-toggle="modal" data-target="#add-category-modal">
                         <div class="card-body text-center">
                             <div class="container">
                                 <img style="max-width:125px;" class="img-fluid" src="img/keys.png" alt="category_icon"></img>
                             </div>
                             <p class="mt-2">Click here to add new category</p>
                             <h1 class="text-uppercase text-muted">Add Category</h1>
                         </div>
                     </div> 
                 </div>
                 <!--second row: second col-->
                 <div class="col-md-6">
                     <div class="card" data-toggle="modal" data-target="#add-product-modal">
                         <div class="card-body text-center">
                             <div class="container">
                                 <img style="max-width:125px;" class="img-fluid" src="img/plus.png" alt="category_icon"></img>
                             </div>
                             <p class="mt-2">Click here to add new product</p>
                             <h1 class="text-uppercase text-muted">Add Product</h1>
                         </div>
                     </div> 
                 </div>
             </div>
         </div>

         <!--Add category modal-->
        
         <!-- Modal -->
         <div class="modal fade" id="add-category-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
             <div class="modal-dialog modal-lg" role="document">
                 <div class="modal-content">
                     <div class="modal-header custom-bg text-white">
                         <h5 class="modal-title" id="exampleModalLabel">Fill category details</h5>
                         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                             <span aria-hidden="true">&times;</span>
                         </button>
                     </div>
                     <div class="modal-body">
                         <form action="ProductOperationServlet" method="post">
                             <input type="hidden" name="operation" value="addcategory">
                             <div class="form-group">
                                 <input type="text" class="form-control" name="catTitle" placeholder="Enter category title" required/>
                             </div>
                             <div class="form-group">
                                 <textarea style="height:300px;" class="form-control" placeholder="Enter category description" name="catDescription" required></textarea>
                             </div>
                             <div class="container text-center">
                                 <button class="btn btn-outline-success">Add category</button>
                                 
                                 <button class="btn btn-outline-danger" data-dismiss="modal">Close</button>
                             </div>
                             
                         </form>
                     </div>
                     
                 </div>
             </div>
         </div>
         <!--End category modal-->
         
         <!--Product Modal-->
        


         <!-- Modal -->
         <div class="modal fade" id="add-product-modal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
             <div class="modal-dialog modal-lg" role="document">
                 <div class="modal-content">
                     <div class="modal-header">
                         <h5 class="modal-title" id="exampleModalLabel">Product details</h5>
                         <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                             <span aria-hidden="true">&times;</span>
                         </button>
                     </div>
                     <div class="modal-body">
                         <!--form-->
                         <form action="ProductOperationServlet" method="post" enctype="multipart/form-data">
                             <input type="hidden" name="operation" value="addproduct"/>

                             <!--Product title-->
                             <div class="form-group">
                                 <input type="text" class="form-control" placeholder="Enter title of product" name="pName" required/>
                             </div>
                             <!--Product description-->
                             <div class="form-group">
                                 <textarea style="height:150px;" class="form-control" placeholder="Enter product description" name="pDesc"></textarea>
                             </div>  
                             
                             <!--Product Price-->
                             
                             <div class="form-group">
                                 <input type="number" class="form-control" placeholder="Enter price of product" name="pPrice" required/>
                             </div>
                             
                             <!--Product Discount-->
                             <div class="form-group">
                                 <input type="number" class="form-control" placeholder="Enter product discount" name="pDiscount" required/>
                             </div>
                             
                             <!--Product quantity-->
                             <div class="form-group">
                                 <input type="number" class="form-control" placeholder="Enter product Quantity" name="pQuantity" required/>
                             </div>
                             
                             <!--Product Category:Getting categories from database-->
                           
                             
                             
                             
                             <div class="form-group">
                                 <select name="catId"class="form-control" id="">
                                     <!--Printing dynamic categories-->
                                     <%
                                         for(Category c:list){
                                      %>
                                      <option value="<%=c.getCategoryId()%>"><%=c.getCategoryTitle()%></option>
                                     
                                     <%
                                         }
                                      %>   
                                     
                                 </select>
                             </div>
                             
                             <!--Product File-->
                             <div class="form-group">
                                 <label for="pPic">Select Picture of product</label>
                                 <br>
                                 <input type="file" id="pPic" name="pPic" required/>
                             </div>
                             
                             <!--submit button-->
                             <div class="container text-center">
                                 <button class="btn btn-outline-success">Add product</button>
                                 <button class="btn btn-outline-danger" data-dismiss="modal">Close</button>
                             </div>




                         </form>
                         <!--End form-->
                     </div>
                   
                 </div>
             </div>
         </div>


         <!--End Product Modal-->
         <%@include file="components/common_modals.jsp"%>
     </body>
 </html>

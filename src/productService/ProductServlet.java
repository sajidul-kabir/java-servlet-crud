package productService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet({"/products/*"})
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDAO;

    public void init() {
        productDAO = new ProductDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        System.out.println(action);
        switch (action){
            case "/insert":
                insertProduct(request, response);
                break;
            case "/update":
                updateProduct(request, response);
                break;
            default:
                showError(request,response);
                break;
        }
        
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            // If action is null or "/", list all products
            listProducts(request, response);
        } else {
            switch (action) {
                case "/new-product":
                    showNewForm(request, response);
                    break;
                case "/update-product":
                    showEditForm(request, response);
                    break;
                case "/delete-product":
                    deleteProduct(request, response);
                    break;
                default:
                    showError(request, response);
                    break;
            }
        }

    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product existingProduct = productDAO.selectProductById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath()+"/new-product.jsp");
        existingProduct.setId(id);
        request.setAttribute("product", existingProduct);
       dispatcher.forward(request, response);
    }

    private void insertProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String productName = request.getParameter("name");
        String productPriceStr = request.getParameter("price");
        // Convert the product price to a double
        double productPrice = Double.parseDouble(productPriceStr);

        Product product = new Product(productName,productPrice);
        productDAO.insertProduct(product);

        response.sendRedirect("/products");
    }

    private void showError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath()+"/new-product.jsp");
        dispatcher.forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String productPriceStr = request.getParameter("price");
        int productId= Integer.parseInt(request.getParameter("id"));
        // Convert the product price to a double
        double productPrice = Double.parseDouble(productPriceStr);
        Product updatedProduct = new Product(productId,name, productPrice);
        productDAO.updateProduct(updatedProduct);
        response.sendRedirect("/products");
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> listProducts = productDAO.selectAllProducts();
        request.setAttribute("listProducts", listProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-list.jsp");
        dispatcher.forward(request, response);
    }
    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        productDAO.deleteProduct(Integer.parseInt(id));
        response.sendRedirect("/products");

    }
}


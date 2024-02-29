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

@WebServlet({"/"})
public class ProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ProductDao productDAO;

    public void init() {
        productDAO = new ProductDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getServletPath();
        switch (action) {
                case "/new":
                showNewForm(request, response);
                break;
                case "/products": 
                listProducts(request, response);
                break;
//                case "/insert":
//                    insertUser(request, response);
//                    break;
//                case "/delete":
//                    deleteUser(request, response);
//                    break;
//                case "/edit":
//                    showEditForm(request, response);
//                    break;
            case "/update":
                updateProduct(request, response);
                break;
            default:
                showError(request, response);
                break;
        }

    }

    private void showError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("new-product.jsp");
        dispatcher.forward(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) {
    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> listProducts = productDAO.selectAllProducts();
        request.setAttribute("listProducts", listProducts);
        RequestDispatcher dispatcher = request.getRequestDispatcher("product-list.jsp");
        dispatcher.forward(request, response);
    }
}


package servlet;

import dao.OrderDao;
import entity.Order;
import entity.Product;
import utils.JdbcConnection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet({"/orders/*"})
public class OrderServlet extends HttpServlet {
    private OrderDao orderDao;

    public void init() {
        // JdbcConnection jdbcConnection=new JdbcConnection();
        orderDao = new OrderDao(JdbcConnection.getInstance());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getPathInfo();
        System.out.println(action);
        switch (action) {
            case "/insert":
                insertOrder(req, resp);
                break;
        }
    }



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getPathInfo();
        if (action == null || action.equals("/")) {
            // If action is null or "/", list all products
            listOrders(request, response);
        } else {
            switch (action) {
                case "/new-order":
                    showNewForm(request, response);
                    break;
            }
        }
}

    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> listOrders = orderDao.selectAllOrders();
        request.setAttribute("listOrders", listOrders);
        RequestDispatcher dispatcher = request.getRequestDispatcher("order/order-list.jsp");
        dispatcher.forward(request, response);
    }
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath()+"/order/new-order.jsp");
    dispatcher.forward(request, response);
    }
    private void insertOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String customerName = request.getParameter("customer_name");
        String customerAddress = request.getParameter("address");
        String productName = request.getParameter("product");
        String productPriceStr = request.getParameter("transaction");
        // Convert the product price to a double
        double productPrice = Double.parseDouble(productPriceStr);

        Order order = new Order(customerName,customerAddress,productName,productPrice);
        orderDao.insertOrder(order);

        response.sendRedirect("/orders");
    }
    }

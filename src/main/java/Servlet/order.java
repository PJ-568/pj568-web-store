package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtil.DBInitializer;
import Model.Order;
import Model.OrderItem;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/order")
public class order extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Order> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("errorMessage", "请先登录。");
            response.sendRedirect(request.getContextPath() + "/user.jsp");
            return;
        }

        // 获取该页的 page 参数，如果是空，则默认为 1
        int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        int pageSize = 5;
        int offset = (page - 1) * pageSize;

        try {
            conn = DBInitializer.getConnection();
            String sql = "SELECT * FROM orders WHERE user_id = ? ORDER BY id DESC LIMIT ? OFFSET ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.setInt(2, pageSize);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Order order = new Order(rs.getInt("id"), rs.getInt("user_id"), rs.getDouble("total_amount"), rs.getDate("created_at"));
                orders.add(order);
            }

            // 获取订单项
            for (Order order : orders) {
                String itemSql = "SELECT * FROM order_items WHERE order_id = ?";
                pstmt = conn.prepareStatement(itemSql);
                pstmt.setInt(1, order.getId());
                ResultSet itemRs = pstmt.executeQuery();
                while (itemRs.next()) {
                    OrderItem orderItem = new OrderItem(itemRs.getInt("id"), itemRs.getInt("order_id"), itemRs.getInt("product_id"), itemRs.getInt("quantity"), itemRs.getDouble("price"));
                    order.addOrderItem(orderItem);
                }
                itemRs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("orders", orders);
        request.setAttribute("page", page);
        request.getRequestDispatcher("order_back.jsp").forward(request, response);
    }
}
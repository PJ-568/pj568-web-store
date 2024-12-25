package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtil.DBInitializer;
import Model.Carts;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/cart")
public class cart extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Carts> carts = new ArrayList<>();
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
            String sql = "SELECT t0.id, t1.id as product_id, t1.name as product_name, t1.description as product_description, t1.price, t0.quantity as amount FROM cart_items t0, products t1 WHERE user_id == ? AND t0.product_id == t1.id LIMIT ? OFFSET ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            pstmt.setInt(2, pageSize);
            pstmt.setInt(3, offset);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Carts cart = new Carts(rs.getInt("id"), rs.getInt("product_id"), rs.getString("product_name"),
                        rs.getString("product_description"), rs.getDouble("price"), rs.getInt("amount"));
                carts.add(cart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Database error: " + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("carts", carts);
        request.setAttribute("page", page);
        request.getRequestDispatcher("cart_back.jsp").forward(request, response);
    }
}

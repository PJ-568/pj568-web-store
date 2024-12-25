package Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import Model.User;
import DBUtil.DBInitializer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/del_cart")
public class DelCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 设置请求参数的编码为 UTF-8
        response.setCharacterEncoding("UTF-8"); // 设置响应内容的编码为 UTF-8
        response.setContentType("text/html; charset=UTF-8"); // 设置响应内容类型为 HTML，并指定编码为 UTF-8

        String id = request.getParameter("id"); // 假设 id 是要删除的购物车项的 ID

        Connection conn = null;
        Statement stmt = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("errorMessage", "请先登录。");
            response.sendRedirect(request.getContextPath() + "/user.jsp.");
            return;
        }
        try {
            conn = DBInitializer.getConnection();
            stmt = conn.createStatement();

            String sql = "DELETE FROM cart_items WHERE user_id = " + user.getId() + " AND id = " + id;

            stmt.executeUpdate(sql);

            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "删除购物车项时出错：" + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/cart");
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}
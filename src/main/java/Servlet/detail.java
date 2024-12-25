package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBUtil.DBInitializer;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/detail")
public class detail extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int pID = 0;
        String currentCate = null;
        int cate = 0;

        int id = 0;
        String name = null;
        String description = null;
        double price = 0;
        int stock = 0;

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        // 获取该页的商品 ID 参数，如果是空，则默认为 0
        pID = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));
        
        if (pID == 0) {
            response.getWriter().write(pID);
            return;
        }

        try {
            conn = DBInitializer.getConnection();
            String sql = "SELECT id, name, description, price, stock, category_id FROM products WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                description = rs.getString("description");
                price = rs.getDouble("price");
                stock = rs.getInt("stock");
                cate = rs.getInt("category_id");
            }

            sql = "SELECT name FROM categories WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, cate);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                currentCate = rs.getString("name");
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

        request.setAttribute("currentCate", currentCate);
        request.setAttribute("id", id);
        request.setAttribute("name", name);
        request.setAttribute("description", description);
        request.setAttribute("price", price);
        request.setAttribute("stock", stock);
        request.getRequestDispatcher("detail_back.jsp").forward(request, response);
    }
}

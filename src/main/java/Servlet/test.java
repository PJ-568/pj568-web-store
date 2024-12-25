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

@WebServlet("/test")
public class test extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        try {
            conn = DBInitializer.getConnection();
            String sql = "SELECT * FROM products";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            // 表格输出获取的值
            // response.getWriter().write(DBInitializer.Path);
            response.getWriter().write("<table border='1'>");
            response.getWriter().write(
                    "<tr><td>ID</td><td>Name</td><td>Description</td><td>Price</td><td>Stock</td><td>Category ID</td></tr>");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                double price = rs.getDouble("price");
                int stock = rs.getInt("stock");
                int categoryId = rs.getInt("category_id");
                response.getWriter().write("<tr><td>" + id + "</td><td>" + name + "</td><td>" + description
                        + "</td><td>" + price + "</td><td>" + stock + "</td><td>" + categoryId + "</td></tr>");
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
        response.getWriter().write("</table>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

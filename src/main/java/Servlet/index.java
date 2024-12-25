package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DBUtil.DBInitializer;
import Model.Product;
import Model.itemCategory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/index")
public class index extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Product> products = new ArrayList<>();
        List<itemCategory> cates = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String currentCate = null;

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        // 获取该页的 cate 参数，如果是空，则默认为 0
        int cate = request.getParameter("cate") == null ? 0 : Integer.parseInt(request.getParameter("cate"));

        // 获取该页的 page 参数，如果是空，则默认为 1
        int page = request.getParameter("page") == null ? 1 : Integer.parseInt(request.getParameter("page"));
        int pageSize = 9;
        int offset = (page - 1) * pageSize;

        try {
            conn = DBInitializer.getConnection();
            if (cate == 0) {
                String sql = "SELECT id, name, description, price, stock, category_id FROM products LIMIT ? OFFSET ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, pageSize);
                pstmt.setInt(2, offset);
            } else {
                String sql = "SELECT id, name, description, price, stock, category_id FROM products WHERE category_id = ? LIMIT ? OFFSET ?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, cate);
                pstmt.setInt(2, pageSize);
                pstmt.setInt(3, offset);
            }
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                        rs.getDouble("price"), rs.getInt("stock"), rs.getInt("category_id"));
                products.add(product);
            }

            String sql = "SELECT id, name, description FROM categories";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                itemCategory category = new itemCategory(rs.getInt("id"), rs.getString("name"),
                        rs.getString("description"));
                cates.add(category);
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
        request.setAttribute("cate", cate);
        request.setAttribute("cates", cates);
        request.setAttribute("products", products);
        request.setAttribute("page", page);
        request.getRequestDispatcher("index_back.jsp").forward(request, response);
    }
}

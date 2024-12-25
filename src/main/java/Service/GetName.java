package Service;

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

@WebServlet("/get_name")
public class GetName extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        int id = request.getParameter("id") == null ? 0 : Integer.parseInt(request.getParameter("id"));

        if (id == 0) {
            response.getWriter().write("<i>无效商品编号</i>");
            return;
        }

        try {
            conn = DBInitializer.getConnection();
            String sql = "SELECT name FROM products WHERE id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            while (rs.next()) {
                response.getWriter().write(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().write("数据库错误：" + e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

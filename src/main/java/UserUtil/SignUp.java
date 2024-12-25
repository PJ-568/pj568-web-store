package UserUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/sign_up")
public class SignUp extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 设置请求参数的编码为 UTF-8
        response.setCharacterEncoding("UTF-8"); // 设置响应内容的编码为 UTF-8
        response.setContentType("text/html; charset=UTF-8"); // 设置响应内容类型为 HTML，并指定编码为 UTF-8

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            // 加载 SQLite JDBC 驱动
            Class.forName("org.sqlite.JDBC");

            // 连接到 SQLite 数据库
            conn = DriverManager
                    .getConnection("jdbc:sqlite:" + getServletContext().getRealPath("/") + "pj568-web-store.db");

            // 查询用户信息
            String sql = "SELECT * FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("注册失败。");
                request.setAttribute("errorMessage", "用户已存在");
                request.getRequestDispatcher("user.jsp").forward(request, response);
            } else {
                // 用户不存在，注册新用户
                sql = "INSERT INTO users (username, password, nickname, role) VALUES (?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS); // 返回生成的主键
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                pstmt.setString(3, nickname);
                pstmt.setString(4, "user"); // 假设默认角色为普通用户
                pstmt.executeUpdate();

                // 获取生成的主键
                rs = pstmt.getGeneratedKeys();
                int userId = -1;
                if (rs.next()) {
                    userId = rs.getInt(1);
                }

                System.out.println("用户注册成功。");
                HttpSession session = request.getSession();
                User user = new User(userId, username, nickname, password, 0);
                session.setAttribute("user", user);
                response.sendRedirect("index");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "注册时出错：" + e.getMessage());
            request.getRequestDispatcher("user.jsp").forward(request, response); // 注册失败时转发回登录页面
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
package UserUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginCheck extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 设置请求参数的编码为 UTF-8
        response.setCharacterEncoding("UTF-8"); // 设置响应内容的编码为 UTF-8
        response.setContentType("text/html; charset=UTF-8"); // 设置响应内容类型为 HTML，并指定编码为 UTF-8

        String username = request.getParameter("username");
        String password = request.getParameter("password");

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
                // 用户存在，验证密码
                if (rs.getString("password").equals(password)) {
                    System.out.println("用户登陆成功。");
                    HttpSession session = request.getSession();
                    User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("nickname"),
                            rs.getString("password"), rs.getInt("role"));
                    session.setAttribute("user", user);
                    // response.sendRedirect("index");
                    response.sendRedirect("user.jsp");
                } else {
                    System.out.println("登陆失败。");
                    request.setAttribute("errorMessage", "用户名或密码错误。");
                    request.getRequestDispatcher("user.jsp").forward(request, response);
                }
            } else {
                System.out.println("登陆失败。");
                request.setAttribute("errorMessage", "用户名或密码错误。");
                request.getRequestDispatcher("user.jsp").forward(request, response);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "登陆时出错：" + e.getMessage());
            request.getRequestDispatcher("user.jsp").forward(request, response); // 登录失败时转发回登录页面
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
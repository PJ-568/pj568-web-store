package Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBUtil.DBInitializer;
import Model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/manage")
public class manage extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String answer = null;

        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("errorMessage", "请先登录。");
            response.sendRedirect(request.getContextPath() + "/user.jsp");
            return;
        }
        if (user.getRole() != 1) {
            request.getSession().setAttribute("errorMessage", "权限不足。");
            response.sendRedirect(request.getContextPath() + "/user.jsp");
            return;
        }

        String messageInput = request.getParameter("messageInput");

        if (messageInput != null) {
            try {
                conn = DBInitializer.getConnection();
                pstmt = conn.prepareStatement(messageInput);
                rs = pstmt.executeQuery();

                // table 表格输出获取的值
                answer = "<table border='1'>";
                while (rs.next()) {
                    answer += "<tr>";
                    for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                        answer += "<td>" + rs.getString(i) + "</td>";
                    }
                    answer += "</tr>";
                }
                answer += "</table>";
            } catch (SQLException e) {
                e.printStackTrace();
                request.getSession().setAttribute("errorMessage", "数据库错误：" + e.getMessage());
                response.sendRedirect(request.getContextPath() + "/manage");
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            request.getSession().setAttribute("answer", answer);
            response.sendRedirect(request.getContextPath() + "/manage");
            return;
        }

        request.getRequestDispatcher("manage_back.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
package UserUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import Model.User;

@WebServlet("/del_user")
public class DelUser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processDelete(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processDelete(request, response);
    }

    private void processDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 获取当前会话
        HttpSession session = request.getSession(false);
        if (session != null) {
            // 获取当前用户
            User user = (User) session.getAttribute("user");
            if (user != null) {
                // 调用服务或 DAO 删除用户
                boolean isDeleted = deleteUser(user.getId());
                if (isDeleted) {
                    // 销毁会话
                    session.invalidate();
                    // 重定向到登录页面
                    response.sendRedirect("user.jsp");
                } else {
                    // 处理删除失败的情况
                    request.setAttribute("errorMessage", "用户删除失败，请重试。");
                    request.getRequestDispatcher("user.jsp").forward(request, response);
                }
            } else {
                // 用户未登录
                response.sendRedirect("user.jsp");
            }
        } else {
            // 会话不存在
            response.sendRedirect("user.jsp");
        }
    }

    private boolean deleteUser(int userId) {
        // 这里应该是调用 DAO 或服务层来删除用户
        // 为了示例，我们假设总是成功删除
        return true;
    }
}
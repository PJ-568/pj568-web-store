package Service;

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

@WebServlet("/add_cart")
public class AddCart extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 设置请求参数的编码为 UTF-8
        response.setCharacterEncoding("UTF-8"); // 设置响应内容的编码为 UTF-8
        response.setContentType("text/html; charset=UTF-8"); // 设置响应内容类型为 HTML，并指定编码为 UTF-8

        String id = request.getParameter("id");
        int amount = Integer.parseInt(request.getParameter("amount"));

        if (amount <= 0) {
            request.getSession().setAttribute("errorMessage", "商品数量必须大于0");
            response.sendRedirect(request.getContextPath() + "/detail");
            return;
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            request.getSession().setAttribute("errorMessage", "请先登录。");
            response.sendRedirect(request.getContextPath() + "/user.jsp");
            return;
        }
        try {
            conn = DBInitializer.getConnection();

            // 查询产品的库存数量
            String stockSql = "SELECT stock FROM products WHERE id = ?";
            pstmt = conn.prepareStatement(stockSql);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            int stock = 0;
            if (rs.next()) {
                stock = rs.getInt("stock");
            } else {
                request.getSession().setAttribute("errorMessage", "商品不存在");
                response.sendRedirect(request.getContextPath() + "/detail");
                return;
            }

            // 检查是否存在 user_id 和 product_id 都吻合的行
            String checkSql = "SELECT quantity FROM cart_items WHERE user_id = ? AND product_id = ?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, user.getId());
            pstmt.setString(2, id);
            rs = pstmt.executeQuery();

            int existingQuantity = 0;
            if (rs.next()) {
                existingQuantity = rs.getInt("quantity");
            }

            int newQuantity = existingQuantity + amount;

            // 检查总数量是否超过库存数量
            if (newQuantity > stock) {
                request.getSession().setAttribute("errorMessage", "商品数量超过库存");
                response.sendRedirect(request.getContextPath() + "/detail");
                return;
            }

            if (existingQuantity > 0) {
                // 如果存在，则更新之前的行的数量为原数量加现有数量
                String updateSql = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND product_id = ?";
                pstmt = conn.prepareStatement(updateSql);
                pstmt.setInt(1, newQuantity);
                pstmt.setInt(2, user.getId());
                pstmt.setString(3, id);
                pstmt.executeUpdate();
            } else {
                // 如果不存在，则插入新行
                String insertSql = "INSERT INTO cart_items (user_id, product_id, quantity) VALUES (?, ?, ?)";
                pstmt = conn.prepareStatement(insertSql);
                pstmt.setInt(1, user.getId());
                pstmt.setString(2, id);
                pstmt.setInt(3, amount);
                pstmt.executeUpdate();
            }

            response.sendRedirect(request.getContextPath() + "/cart");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "添加商品至购物车时出错：" + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/cart");
        } finally {
            // 确保资源被关闭
            try {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
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
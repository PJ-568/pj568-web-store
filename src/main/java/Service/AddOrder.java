package Service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import DBUtil.DBInitializer;
import Model.User;
import Model.Order;
import Model.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/add_order")
public class AddOrder extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 设置请求参数的编码为 UTF-8
        response.setCharacterEncoding("UTF-8"); // 设置响应内容的编码为 UTF-8
        response.setContentType("text/html; charset=UTF-8"); // 设置响应内容类型为 HTML，并指定编码为 UTF-8

        // 输入参数如下：
        String[] cart_ids = request.getParameterValues("p");

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
            Order order = new Order();
            order.setUserId(user.getId());
            order.setCreatedAt(new Date());

            Map<Integer, Integer> productQuantities = new HashMap<>();

            for (String cart_id : cart_ids) {
                String query = "SELECT t0.product_id, t0.quantity, t1.price, t1.stock FROM cart_items t0, products t1 WHERE t0.id = ? AND t0.product_id = t1.id";
                pstmt = conn.prepareStatement(query);
                pstmt.setInt(1, Integer.parseInt(cart_id));
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int productId = rs.getInt("product_id");
                    int quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    int stock = rs.getInt("stock");

                    // 检查总数量是否超过库存数量
                    if (quantity > stock) {
                        request.getSession().setAttribute("errorMessage", "商品数量超过库存");
                        response.sendRedirect(request.getContextPath() + "/cart");
                        return;
                    }

                    OrderItem orderItem = new OrderItem();
                    orderItem.setProductId(productId);
                    orderItem.setQuantity(quantity);
                    orderItem.setPrice(price);

                    order.addOrderItem(orderItem);

                    // 记录每个产品的数量
                    productQuantities.put(productId, quantity);
                }
            }

            // 插入订单信息
            String insertOrderQuery = "INSERT INTO orders (user_id, total_amount, created_at) VALUES (?, ?, ?)";
            pstmt = conn.prepareStatement(insertOrderQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, order.getUserId());
            pstmt.setDouble(2, order.getTotalAmount());
            pstmt.setTimestamp(3, new java.sql.Timestamp(order.getCreatedAt().getTime()));
            pstmt.executeUpdate();

            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int orderId = rs.getInt(1);
                order.setId(orderId);

                // 插入订单项信息
                for (OrderItem orderItem : order.getOrderItems()) {
                    String insertOrderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
                    pstmt = conn.prepareStatement(insertOrderItemQuery);
                    pstmt.setInt(1, orderId);
                    pstmt.setInt(2, orderItem.getProductId());
                    pstmt.setInt(3, orderItem.getQuantity());
                    pstmt.setDouble(4, orderItem.getPrice());
                    pstmt.executeUpdate();
                }

                // 删除对应的 cart_items 并更新产品库存
                for (String cart_id : cart_ids) {
                    // 获取 cart_item 的 product_id 和 quantity
                    String query = "SELECT product_id, quantity FROM cart_items WHERE id = ?";
                    pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, Integer.parseInt(cart_id));
                    rs = pstmt.executeQuery();

                    if (rs.next()) {
                        int productId = rs.getInt("product_id");
                        int quantity = rs.getInt("quantity");

                        // 更新产品库存
                        String updateStockQuery = "UPDATE products SET stock = stock - ? WHERE id = ?";
                        pstmt = conn.prepareStatement(updateStockQuery);
                        pstmt.setInt(1, quantity);
                        pstmt.setInt(2, productId);
                        pstmt.executeUpdate();
                    }

                    // 删除 cart_item
                    String deleteCartItemQuery = "DELETE FROM cart_items WHERE id = ?";
                    pstmt = conn.prepareStatement(deleteCartItemQuery);
                    pstmt.setInt(1, Integer.parseInt(cart_id));
                    pstmt.executeUpdate();
                }
            }

            response.sendRedirect(request.getContextPath() + "/order");
        } catch (SQLException e) {
            e.printStackTrace();
            request.getSession().setAttribute("errorMessage", "购买商品时出错：" + e.getMessage());
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
package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private double totalAmount;
    private Date createdAt;
    private List<OrderItem> orderItems;

    // 无参构造函数
    public Order() {
        this.orderItems = new ArrayList<>();
    }

    // 参构造函数
    public Order(int id, int userId, double totalAmount, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
        this.orderItems = new ArrayList<>();
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // 添加订单项
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        this.totalAmount += orderItem.getPrice() * orderItem.getQuantity();
    }

    // 移除订单项
    public void removeOrderItem(OrderItem orderItem) {
        this.orderItems.remove(orderItem);
        this.totalAmount -= orderItem.getPrice() * orderItem.getQuantity();
    }

    // 重写 toString 方法
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", createdAt=" + createdAt +
                ", orderItems=" + orderItems +
                '}';
    }
}
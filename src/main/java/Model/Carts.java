package Model;

public class Carts {
    private int id;
    private int product_id;
    private String product_name;
    private String product_description;
    private double price;
    private int amount;

    // 无参构造函数
    public Carts() {
    }

    // 参构造函数
    public Carts(int id, int product_id, String product_name, String product_description, double price, int amount) {
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.price = price;
        this.amount = amount;
    }

    // Getter 和 Setter 方法
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    // 增加商品数量
    public void increaseAmount(int quantity) {
        this.amount += quantity;
    }

    // 减少商品数量
    public void decreaseAmount(int quantity) {
        if (this.amount >= quantity) {
            this.amount -= quantity;
        } else {
            System.out.println("商品数量不足");
        }
    }

    // 重写 toString 方法
    @Override
    public String toString() {
        return "Carts{" +
                "id=" + id +
                ", product_name='" + product_name + '\'' +
                ", product_description='" + product_description + '\'' +
                ", price=" + price +
                ", amount=" + amount +
                '}';
    }
}
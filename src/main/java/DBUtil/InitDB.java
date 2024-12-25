package DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {

    public static void main() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        try {
            // 删除旧的数据库文件
            System.out.println("开始初始化数据库");
            java.io.File dbFile = new java.io.File(DBInitializer.Path);
            if (dbFile.exists()) {
                dbFile.delete();
            }
            System.out.println("数据库文件已删除");

            // 连接到 SQLite 数据库（如果数据库不存在，则会自动创建）
            conn = DBInitializer.getConnection();
            stmt = conn.createStatement();

            // 创建表结构
            String sql = ""
                    + "CREATE TABLE IF NOT EXISTS users (\n"
                    + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "    username TEXT NOT NULL UNIQUE,\n"
                    + "    nickname TEXT NOT NULL,\n"
                    + "    password TEXT NOT NULL,\n"
                    + "    role INTEGER NOT NULL\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS categories (\n"
                    + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "    name TEXT NOT NULL,\n"
                    + "    description TEXT\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS products (\n"
                    + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "    name TEXT NOT NULL,\n"
                    + "    description TEXT,\n"
                    + "    price REAL NOT NULL,\n"
                    + "    stock INTEGER NOT NULL,\n"
                    + "    category_id INTEGER,\n"
                    + "    FOREIGN KEY (category_id) REFERENCES categories(id)\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS orders (\n"
                    + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "    user_id INTEGER NOT NULL,\n"
                    + "    total_amount REAL NOT NULL,\n"
                    + "    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,\n"
                    + "    FOREIGN KEY (user_id) REFERENCES users(id)\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS order_items (\n"
                    + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "    order_id INTEGER NOT NULL,\n"
                    + "    product_id INTEGER NOT NULL,\n"
                    + "    quantity INTEGER NOT NULL,\n"
                    + "    price REAL NOT NULL,\n"
                    + "    FOREIGN KEY (order_id) REFERENCES orders(id),\n"
                    + "    FOREIGN KEY (product_id) REFERENCES products(id)\n"
                    + ");\n"
                    + "\n"
                    + "CREATE TABLE IF NOT EXISTS cart_items (\n"
                    + "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                    + "    user_id INTEGER NOT NULL,\n"
                    + "    product_id INTEGER NOT NULL,\n"
                    + "    quantity INTEGER NOT NULL,\n"
                    + "    FOREIGN KEY (user_id) REFERENCES users(id),\n"
                    + "    FOREIGN KEY (product_id) REFERENCES products(id)\n"
                    + ");";

            stmt.executeUpdate(sql);

            // 插入初始数据
            // 插入用户信息
            stmt.executeUpdate(
                    "INSERT INTO users (username, nickname, password, role) VALUES ('202235010623', 'PJ568', '202235010623', 1)");
            stmt.executeUpdate(
                    "INSERT INTO users (username, nickname, password, role) VALUES ('user', '用户哥', 'user', 0)");

            // 分类信息
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('电子产品', '包括各种消费电子设备，如智能手机、平板电脑、笔记本电脑、电视等。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('书籍', '涵盖各种类型的书籍，包括小说、教科书、参考书、杂志等。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('服装', '提供各种风格和类别的服装，如休闲装、正装、运动装等。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('家用电器', '包括厨房电器、清洁电器、个人护理电器等，满足家庭日常需求。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('玩具', '适合各个年龄段的玩具，包括益智玩具、户外玩具、模型等。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('运动与户外用品', '提供各种运动装备和户外活动用品，如健身器材、露营装备、运动服装等。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('美妆和个人护理', '涵盖护肤品、彩妆、洗护用品等，帮助用户保持美丽和健康。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('食品和高端美食', '提供各种食品和饮品，包括健康食品、进口食品、高端美食等。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('健康与家居用品', '包括健康监测设备、家居装饰品、清洁用品等，提升生活质量。')");
            stmt.executeUpdate(
                    "INSERT INTO categories (name, description) VALUES ('宠物用品', '涵盖宠物食品、玩具、护理用品等，满足宠物日常需求。')");

            // 插入商品信息
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('笔记本电脑', '高性能笔记本电脑，适合办公和娱乐', 3999.99, 10, 1)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('瑜伽垫', '专业瑜伽垫，适合各种瑜伽练习', 199.99, 50, 6)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('防晒霜', '高倍数防晒霜，有效阻挡紫外线', 99.99, 30, 7)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('有机蔬菜', '新鲜有机蔬菜，健康选择', 49.99, 20, 8)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('空气净化器', '高效空气净化器，改善室内空气质量', 299.99, 15, 9)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('宠物狗粮', '高质量宠物狗粮，营养均衡', 59.99, 40, 10)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('智能手机', '最新款智能手机，拍照性能卓越', 3499.99, 20, 1)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('科幻小说', '经典科幻小说，适合科幻迷', 69.99, 50, 2)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('文学名著', '世界名著，值得收藏', 39.99, 40, 2)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('T恤', '纯棉T恤，夏季必备', 13.62, 100, 3)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('牛仔裤', '经典牛仔裤，百搭时尚', 59.99, 80, 3)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('电冰箱', '大容量电冰箱，保鲜效果好', 1299.99, 15, 4)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('洗衣机', '高效洗衣机，省水节能', 899.99, 25, 4)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('遥控汽车', '遥控汽车，适合儿童', 19.99, 60, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('拼图', '益智拼图，锻炼思维', 9.99, 70, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('炫彩积木', '积木拼图，锻炼思维', 19.99, 50, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('飞行机', '飞行机，玩具飞机', 99.99, 30, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('可控核聚变反应堆', '可控核聚变反应堆，用于发电', 999999.99, 10, 4)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('笔记本电脑', '高性能笔记本电脑，适合办公和娱乐', 3999.99, 10, 1)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('智能手机', '最新款智能手机，拍照性能卓越', 3499.99, 20, 1)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('科幻小说', '经典科幻小说，适合科幻迷', 69.99, 50, 2)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('文学名著', '世界名著，值得收藏', 39.99, 40, 2)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('T恤', '纯棉T恤，夏季必备', 13.62, 100, 3)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('牛仔裤', '经典牛仔裤，百搭时尚', 59.99, 80, 3)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('电冰箱', '大容量电冰箱，保鲜效果好', 1299.99, 15, 4)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('洗衣机', '高效洗衣机，省水节能', 899.99, 25, 4)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('遥控汽车', '遥控汽车，适合儿童', 19.99, 60, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('拼图', '益智拼图，锻炼思维', 9.99, 70, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('炫彩积木', '积木拼图，锻炼思维', 19.99, 50, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('飞行机', '飞行机，玩具飞机', 99.99, 30, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('可控核聚变反应堆', '可控核聚变反应堆，用于发电', 999999.99, 10, 4)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('笔记本电脑', '高性能笔记本电脑，适合办公和娱乐', 3999.99, 10, 1)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('智能手机', '最新款智能手机，拍照性能卓越', 3499.99, 20, 1)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('科幻小说', '经典科幻小说，适合科幻迷', 69.99, 50, 2)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('文学名著', '世界名著，值得收藏', 39.99, 40, 2)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('T恤', '纯棉T恤，夏季必备', 13.62, 100, 3)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('牛仔裤', '经典牛仔裤，百搭时尚', 59.99, 80, 3)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('电冰箱', '大容量电冰箱，保鲜效果好', 1299.99, 15, 4)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('洗衣机', '高效洗衣机，省水节能', 899.99, 25, 4)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('遥控汽车', '遥控汽车，适合儿童', 19.99, 60, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('拼图', '益智拼图，锻炼思维', 9.99, 70, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('炫彩积木', '积木拼图，锻炼思维', 19.99, 50, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('飞行机', '飞行机，玩具飞机', 99.99, 30, 5)");
            stmt.executeUpdate(
                    "INSERT INTO products (name, description, price, stock, category_id) VALUES ('可控核聚变反应堆', '可控核聚变反应堆，用于发电', 999999.99, 10, 4)");

            // 插入订单信息
            stmt.executeUpdate("INSERT INTO orders (user_id, total_amount) VALUES (1, 4299.98)");
            stmt.executeUpdate("INSERT INTO orders (user_id, total_amount) VALUES (2, 219.98)");
            stmt.executeUpdate("INSERT INTO orders (user_id, total_amount) VALUES (1, 599.97)");
            stmt.executeUpdate("INSERT INTO orders (user_id, total_amount) VALUES (2, 309.97)");

            // 插入订单详情信息
            // 订单1: 用户1购买了笔记本电脑和防晒霜
            stmt.executeUpdate(
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (1, 1, 1, 3999.99)");
            stmt.executeUpdate(
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (1, 3, 1, 299.99)");

            // 订单2: 用户2购买了瑜伽垫和T恤
            stmt.executeUpdate(
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (2, 2, 1, 199.99)");
            stmt.executeUpdate(
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (2, 10, 1, 19.99)");

            // 订单3: 用户1购买了智能手机和文学名著
            stmt.executeUpdate(
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (3, 7, 1, 3499.99)");
            stmt.executeUpdate(
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (3, 12, 1, 149.98)");

            // 订单4: 用户2购买了电冰箱和宠物狗粮
            stmt.executeUpdate(
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (4, 11, 1, 1299.99)");
            stmt.executeUpdate(
                    "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (4, 6, 1, 199.98)");

            // 插入购物车信息
            stmt.executeUpdate("INSERT INTO cart_items (user_id, product_id, quantity) VALUES (1, 1, 1)");
            stmt.executeUpdate("INSERT INTO cart_items (user_id, product_id, quantity) VALUES (2, 2, 2)");
            stmt.executeUpdate("INSERT INTO cart_items (user_id, product_id, quantity) VALUES (1, 3, 1)");
            stmt.executeUpdate("INSERT INTO cart_items (user_id, product_id, quantity) VALUES (2, 4, 1)");
            stmt.executeUpdate("INSERT INTO cart_items (user_id, product_id, quantity) VALUES (1, 5, 2)");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            conn.close();
        }
    }

}

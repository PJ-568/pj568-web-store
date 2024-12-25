import os
import sqlite3

# 构建数据库路径
base_dir = os.path.dirname(os.path.abspath(__file__))
db_path = os.path.join(base_dir, '../src/main/resources/data/pj568-web-store.db')

print(f"Database path: {db_path}")

# 连接到 SQLite 数据库（如果数据库不存在，则会自动创建）
def connect_to_database():
    conn = sqlite3.connect(db_path)
    cursor = conn.cursor()
    return conn, cursor

# 重建数据库
def reset_database():
    if os.path.exists(db_path):
        os.remove(db_path)
    initialize_database()
    initialize_data()
    return '数据库重建成功'

# 初始化数据库结构
def initialize_database():
    # 连接到 SQLite 数据库（如果数据库不存在，则会自动创建）
    conn, cursor = connect_to_database()

    # 创建表结构
    # 用户信息
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS users (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            username TEXT NOT NULL UNIQUE,
            nickname TEXT NOT NULL,
            password TEXT NOT NULL,
            role INTEGER NOT NULL
        )
    ''')

    # 分类信息
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS categories (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            description TEXT
        )
    ''')

    # 商品信息
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS products (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            name TEXT NOT NULL,
            description TEXT,
            price REAL NOT NULL,
            stock INTEGER NOT NULL,
            category_id INTEGER,
            FOREIGN KEY (category_id) REFERENCES categories(id)
        )
    ''')

    # 订单信息
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS orders (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER NOT NULL,
            total_amount REAL NOT NULL,
            created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
            FOREIGN KEY (user_id) REFERENCES users(id)
        )
    ''')

    # 订单详情信息
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS order_items (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            order_id INTEGER NOT NULL,
            product_id INTEGER NOT NULL,
            quantity INTEGER NOT NULL,
            price REAL NOT NULL,
            FOREIGN KEY (order_id) REFERENCES orders(id),
            FOREIGN KEY (product_id) REFERENCES products(id)
        )
    ''')

    # 购物车信息
    cursor.execute('''
        CREATE TABLE IF NOT EXISTS cart_items (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            user_id INTEGER NOT NULL,
            product_id INTEGER NOT NULL,
            quantity INTEGER NOT NULL,
            FOREIGN KEY (user_id) REFERENCES users(id),
            FOREIGN KEY (product_id) REFERENCES products(id)
        )
    ''')

    # 提交事务
    conn.commit()
    # 关闭连接
    conn.close()

# 初始化数据库内容
def initialize_data():
    # 连接到 SQLite 数据库
    conn, cursor = connect_to_database()
    
    # 插入用户信息
    cursor.execute("INSERT INTO users (username, nickname, password, role) VALUES ('202235010623', '李佑天', '202235010623', 1)")
    cursor.execute("INSERT INTO users (username, nickname, password, role) VALUES ('user', '用户哥', 'user', 0)")

    # 分类信息
    cursor.execute("INSERT INTO categories (name, description) VALUES ('Electronics', '电子产品')")
    cursor.execute("INSERT INTO categories (name, description) VALUES ('Books', '书籍')")
    cursor.execute("INSERT INTO categories (name, description) VALUES ('Clothing', '服装')")
    cursor.execute("INSERT INTO categories (name, description) VALUES ('Home Appliances', '家用电器')")
    cursor.execute("INSERT INTO categories (name, description) VALUES ('Toys', '玩具')")

    # 商品信息
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('笔记本电脑', '高性能笔记本电脑，适合办公和娱乐', 3999.99, 10, 1)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('智能手机', '最新款智能手机，拍照性能卓越', 3499.99, 20, 1)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('科幻小说', '经典科幻小说，适合科幻迷', 69.99, 50, 2)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('文学名著', '世界名著，值得收藏', 39.99, 40, 2)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('T恤', '纯棉T恤，夏季必备', 13.62, 100, 3)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('牛仔裤', '经典牛仔裤，百搭时尚', 59.99, 80, 3)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('电冰箱', '大容量电冰箱，保鲜效果好', 1299.99, 15, 4)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('洗衣机', '高效洗衣机，省水节能', 899.99, 25, 4)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('遥控汽车', '遥控汽车，适合儿童', 19.99, 60, 5)")
    cursor.execute("INSERT INTO products (name, description, price, stock, category_id) VALUES ('拼图', '益智拼图，锻炼思维', 9.99, 70, 5)")

    # 提交事务
    conn.commit()
    # 关闭连接
    conn.close()

if __name__ == '__main__':
    print(reset_database())
    conn, cursor = connect_to_database()
    cursor.execute("SELECT * FROM products")
    print(cursor.fetchall())
    conn.close()

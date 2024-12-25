# PJ568 Web Store

## 项目介绍

这是一个基于 Servlet 和 SQLite 的 Web 应用程序，用于创建一个在线商店。

## 页面功能

- 首页：展示商品分类，支持点击跳转到对应商品详情。展示指定类别商品。
- 商品详情页：展示商品的详细信息，包括图片、价格、描述等。提供加入购物车的选项。
- 购物车：允许用户查看已加入购物车的商品，并进行批量购买。支持删除。购买后，跳转到订单页。
- 订单页：支持订单信息查询。
- 管理员管理页面：管理员可通过执行 SQL 命令来管理商品、用户等。
- 用户中心页：用户登录或注册功能。展示用户的个人信息。

**注意：**
系统内默认无图片，请自行添加。

### 默认用户账号

- 管理员，账号和密码：202235010623/202235010623
- 用户，账号和密码：user/user

## 部署步骤

1. 安装 JDK 11、Tomcat 10 并添加至环境变量。
2. 安装 maven 并添加至环境变量。
3. （可选）配置 maven 的软件源。
4. 在项目根目录下运行 `mvn clean install` 命令构建项目。
5. 运行 `mvn package` 命令打包项目。
6. 将打包好的项目 `pj568-web-store.war` 放置到 Tomcat 的 webapps 目录下。
7. 启动 Tomcat 服务器并访问如 `http://localhost:8080/pj568-web-store` 查看项目。

**注意：**
数据库文件将在项目首次运行时生成，并保存在项目部署目录下：`/pj568-web-store.db`。

## 仓库结构

```plaintext
pj568-web-store
├── README.md：项目介绍
├── src
│   ├── main
│   │   ├── java：java 和 Servlet 代码
│   │   └── resources：资源文件
│   └── webapp：web 相关代码和资源
├── target：打包好的项目
└── scripts：测试用的脚本
```

## 表结构

- **用户表 (users)**
  - `id` (INTEGER, 主键, 自增): 用户唯一标识
  - `username` (TEXT, 唯一, 非空): 用户名
  - `nickname` (TEXT, 非空): 昵称
  - `password` (TEXT, 非空): 密码
  - `role` (INTEGER, 非空): 用户角色（0：普通用户，1：管理员）

- **分类表 (categories)**
  - `id` (INTEGER, 主键, 自增): 分类唯一标识
  - `name` (TEXT, 非空): 分类名称
  - `description` (TEXT): 分类描述

- **商品表 (products)**
  - `id` (INTEGER, 主键, 自增): 商品唯一标识
  - `name` (TEXT, 非空): 商品名称
  - `description` (TEXT): 商品描述
  - `price` (REAL, 非空): 商品价格
  - `stock` (INTEGER, 非空): 库存数量
  - `category_id` (INTEGER): 分类ID，外键关联到 `categories(id)`

- **订单表 (orders)**
  - `id` (INTEGER, 主键, 自增): 订单唯一标识
  - `user_id` (INTEGER, 非空): 用户ID，外键关联到 `users(id)`
  - `total_amount` (REAL, 非空): 订单总金额
  - `created_at` (DATETIME, 默认当前时间): 创建时间

- **订单详情表 (order_items)**
  - `id` (INTEGER, 主键, 自增): 订单详情唯一标识
  - `order_id` (INTEGER, 非空): 订单ID，外键关联到 `orders(id)`
  - `product_id` (INTEGER, 非空): 商品ID，外键关联到 `products(id)`
  - `quantity` (INTEGER, 非空): 商品数量
  - `price` (REAL, 非空): 商品单价

- **购物车表 (cart_items)**
  - `id` (INTEGER, 主键, 自增): 购物车项唯一标识
  - `user_id` (INTEGER, 非空): 用户ID，外键关联到 `users(id)`
  - `product_id` (INTEGER, 非空): 商品ID，外键关联到 `products(id)`
  - `quantity` (INTEGER, 非空): 商品数量

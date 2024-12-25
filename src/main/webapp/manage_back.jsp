<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="zh-Hans">

    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=400, initial-scale=1">
      <meta name="description" content="PJ568 云上商城用户管理页面。">
      <title>PJ568 云上商城 - 管理</title>
      <jsp:include page="cpn/head.jsp" />
    </head>

    <body>
      <!-- 背景 -->
      <jsp:include page="cpn/bg.jsp" />

      <!-- 导航栏 -->
      <jsp:include page="cpn/navbar.jsp">
        <jsp:param name="loc" value="manage" />
      </jsp:include>

      <!-- 主要内容 -->
      <div class="container max-width">
        <c:if test="${not empty errorMessage}">
          <div class="error-message">${errorMessage}</div>
        </c:if>
        <div class="content">
          <form action="manage" method="get">
            <fieldset>
              <legend>管理员测试页面</legend>
              <fieldset class="content">
                <p>运行结果：</p>
                <c:choose>
                  <c:when test="${not empty answer}">
                    ${answer}
                  </c:when>
                  <c:otherwise>
                    <i>暂无。</i>
                  </c:otherwise>
                </c:choose>
              </fieldset>
              <br>
              <label for="messageInput">请键入指令：</label>
              <textarea id="messageInput" name="messageInput"></textarea>
              <button type="submit">发送</button>
            </fieldset>
          </form>
          <fieldset>
            <legend>参考</legend>
            <h2>指令格式</h2>

            <p>直接输入 SQL 命令。</p>

            <h2 id="pj568-web-store">PJ568 Web Store</h2>
            <h3 id="表结构">表结构</h3>
            <ul>
              <li>
                <p><strong>用户表 (users)</strong></p>
                <ul>
                  <li><code>id</code> (INTEGER, 主键, 自增): 用户唯一标识</li>
                  <li><code>username</code> (TEXT, 唯一, 非空): 用户名</li>
                  <li><code>nickname</code> (TEXT, 非空): 昵称</li>
                  <li><code>password</code> (TEXT, 非空): 密码</li>
                  <li><code>role</code> (INTEGER, 非空): 用户角色（0：普通用户，1：管理员）</li>
                </ul>
              </li>
              <li>
                <p><strong>分类表 (categories)</strong></p>
                <ul>
                  <li><code>id</code> (INTEGER, 主键, 自增): 分类唯一标识</li>
                  <li><code>name</code> (TEXT, 非空): 分类名称</li>
                  <li><code>description</code> (TEXT): 分类描述</li>
                </ul>
              </li>
              <li>
                <p><strong>商品表 (products)</strong></p>
                <ul>
                  <li><code>id</code> (INTEGER, 主键, 自增): 商品唯一标识</li>
                  <li><code>name</code> (TEXT, 非空): 商品名称</li>
                  <li><code>description</code> (TEXT): 商品描述</li>
                  <li><code>price</code> (REAL, 非空): 商品价格</li>
                  <li><code>stock</code> (INTEGER, 非空): 库存数量</li>
                  <li><code>category_id</code> (INTEGER): 分类ID，外键关联到 <code>categories(id)</code></li>
                </ul>
              </li>
              <li>
                <p><strong>订单表 (orders)</strong></p>
                <ul>
                  <li><code>id</code> (INTEGER, 主键, 自增): 订单唯一标识</li>
                  <li><code>user_id</code> (INTEGER, 非空): 用户ID，外键关联到 <code>users(id)</code></li>
                  <li><code>total_amount</code> (REAL, 非空): 订单总金额</li>
                  <li><code>created_at</code> (DATETIME, 默认当前时间): 创建时间</li>
                </ul>
              </li>
              <li>
                <p><strong>订单详情表 (order_items)</strong></p>
                <ul>
                  <li><code>id</code> (INTEGER, 主键, 自增): 订单详情唯一标识</li>
                  <li><code>order_id</code> (INTEGER, 非空): 订单ID，外键关联到 <code>orders(id)</code></li>
                  <li><code>product_id</code> (INTEGER, 非空): 商品ID，外键关联到 <code>products(id)</code></li>
                  <li><code>quantity</code> (INTEGER, 非空): 商品数量</li>
                  <li><code>price</code> (REAL, 非空): 商品单价</li>
                </ul>
              </li>
              <li>
                <p><strong>购物车表 (cart_items)</strong></p>
                <ul>
                  <li><code>id</code> (INTEGER, 主键, 自增): 购物车项唯一标识</li>
                  <li><code>user_id</code> (INTEGER, 非空): 用户ID，外键关联到 <code>users(id)</code></li>
                  <li><code>product_id</code> (INTEGER, 非空): 商品ID，外键关联到 <code>products(id)</code></li>
                  <li><code>quantity</code> (INTEGER, 非空): 商品数量</li>
                </ul>
              </li>
            </ul>
          </fieldset>
        </div>
      </div>

      <!-- 脚注 -->
      <jsp:include page="cpn/footer.jsp" />
    </body>

    </html>
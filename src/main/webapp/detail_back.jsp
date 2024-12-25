<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="zh-Hans">

    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=width=400, initial-scale=1">
      <meta name="description" content="PJ568 云上商城的商品详情页面。${description}">
      <title>PJ568 云上商城 - ${name}</title>
      <jsp:include page="cpn/head.jsp" />
    </head>

    <body>
      <!-- 背景 -->
      <jsp:include page="cpn/bg.jsp" />

      <!-- 导航栏 -->
      <jsp:include page="cpn/navbar.jsp">
        <jsp:param name="loc" value="detail" />
      </jsp:include>

      <!-- 主要内容 -->
      <div class="container max-width">
        <div class="content">
          <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
          </c:if>
          <div class="on-load" style="height: 256px; background-color: rgba(255, 255, 255, 0.2);"></div>
          <h2>${name}&nbsp;&nbsp;￥${price}</h2>
          <h3>${currentCate}</h3>
          <c:choose>
            <c:when test="${not empty sessionScope.user}">
              <form action="add_cart" method="get">
                <input type="hidden" name="id" value="${id}">
                <input type="number" name="amount" value="1" min="1" max="${stock}"><span>库存：${stock}</span>
                <br>
                <button type="submit"><i class="fa fa-cart-shopping"></i> 加入购物车</button>
              </form>
            </c:when>
            <c:otherwise>
              <p>库存：${stock}</p>
              <a href="user.jsp">登录</a>
            </c:otherwise>
          </c:choose>
          <p>${description}</p>
          <div class="on-load" style="height: 48px; background-color: rgba(0, 0, 0, 0.2);"></div>
        </div>
      </div>

      <!-- 脚注 -->
      <jsp:include page="cpn/footer.jsp" />
    </body>

    </html>
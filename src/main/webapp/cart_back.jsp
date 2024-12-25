<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="zh-Hans">

    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=width=400, initial-scale=1">
      <meta name="description" content="PJ568 云上商城的购物车。">
      <title>PJ568 云上商城 - 购物车</title>
      <jsp:include page="cpn/head.jsp" />
    </head>

    <body>
      <!-- 背景 -->
      <jsp:include page="cpn/bg.jsp" />

      <!-- 导航栏 -->
      <jsp:include page="cpn/navbar.jsp">
        <jsp:param name="loc" value="cart" />
      </jsp:include>

      <!-- 主要内容 -->
      <div class="container max-width">
        <form id="buy" action="add_order" method="get">
        </form>
        <div class="content" style="align-items: center; display: flex; flex-direction: column;">
          <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
          </c:if>
          <c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
          <c:choose>
            <c:when test="${not empty carts}">
              <div class="pagination">
                <c:choose>
                  <c:when test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}&cate=${cate}">上一页</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#" style="opacity: 0;">上一页</a>
                  </c:otherwise>
                </c:choose>
                <a href="?page=1&cate=${cate}">第 ${currentPage} 页</a>
                <a href="?page=${currentPage + 1}&cate=${cate}">下一页</a>
              </div>
              <button id="buy-btn" form="buy" type="submit">购买</button>

              <div class="row" style="width: 100%; display: flex; flex-wrap: wrap;">
                <c:forEach var="cart" items="${carts}">
                  <div style="min-width: 128px; width: 100%; display: flex; gap: 1rem; justify-content: space-between;">
                    <input type="checkbox" form="buy" name="p" value="${cart.id}">
                    <a href="detail?id=${cart.product_id}" style="width: 85%; display: flex; gap: 1rem;">
                      <div class="on-load"
                        style="height: 64px; width: 64px; background-color: rgba(255, 255, 255, 0.2);">
                      </div>
                      <div>
                        <p>${cart.product_name}</p>
                        <p>${cart.product_description}</p>
                        <h3>x${cart.amount}&nbsp;&nbsp;￥${cart.price}</h3>
                      </div>
                    </a>
                    <form id="del" action="del_cart" method="get">
                      <input type="hidden" name="id" value="${cart.id}">
                      <button type="submit"><i class="fa fa-trash"></i> 删除</button>
                    </form>
                  </div>
                </c:forEach>
              </div>

              <button id="buy-btn" form="buy" type="submit">购买</button>
              <div class="pagination">
                <c:choose>
                  <c:when test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}&cate=${cate}">上一页</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#" style="opacity: 0;">上一页</a>
                  </c:otherwise>
                </c:choose>
                <a href="?page=1&cate=${cate}">第 ${currentPage} 页</a>
                <a href="?page=${currentPage + 1}&cate=${cate}">下一页</a>
              </div>
            </c:when>
            <c:otherwise>
              <div class="pagination">
                <c:choose>
                  <c:when test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}&cate=${cate}">上一页</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#" style="opacity: 0;">上一页</a>
                  </c:otherwise>
                </c:choose>
                <a href="?page=1&cate=${cate}">第 ${currentPage} 页</a>
                <a href="#" style="opacity: 0;">下一页</a>
              </div>

              <i>空</i>

              <div class="pagination">
                <c:choose>
                  <c:when test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}&cate=${cate}">上一页</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#" style="opacity: 0;">上一页</a>
                  </c:otherwise>
                </c:choose>
                <a href="?page=1&cate=${cate}">第 ${currentPage} 页</a>
                <a href="#" style="opacity: 0;">下一页</a>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>

      <!-- 脚注 -->
      <jsp:include page="cpn/footer.jsp" />
    </body>

    </html>
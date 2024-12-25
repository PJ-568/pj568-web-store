<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="zh-Hans">

    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta name="description" content="PJ568 云上商城的订单页面。">
      <title>PJ568 云上商城 - 订单</title>
      <jsp:include page="cpn/head.jsp" />
    </head>

    <body>
      <!-- 背景 -->
      <jsp:include page="cpn/bg.jsp" />

      <!-- 导航栏 -->
      <jsp:include page="cpn/navbar.jsp">
        <jsp:param name="loc" value="order" />
      </jsp:include>

      <!-- 主要内容 -->
      <div class="container max-width">
        <div class="content" style="align-items: center; display: flex; flex-direction: column;">
          <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
          </c:if>
          <c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
          <c:choose>
            <c:when test="${not empty orders}">
              <div class="pagination">
                <c:choose>
                  <c:when test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}">上一页</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#" style="opacity: 0;">上一页</a>
                  </c:otherwise>
                </c:choose>
                <a href="?page=1">第 ${currentPage} 页</a>
                <a href="?page=${currentPage + 1}">下一页</a>
              </div>

              <div class="row" style="width: 100%; display: flex; flex-wrap: wrap; gap: 1rem;">
                <c:forEach var="order" items="${orders}">
                  <details
                    style="min-width: 128px; width: 100%; display: flex; gap: 1rem; justify-content: space-between;">
                    <summary>
                      <p>订单号: ${order.id}</p>
                      <p>创建时间: ${order.createdAt}</p>
                      <p>总金额: ￥${order.totalAmount}</p>
                    </summary>
                    <div>
                      <c:forEach var="orderItem" items="${order.orderItems}">
                        <div style="display: flex; gap: 1rem; width: 100%;">
                          <a href="detail?id=${orderItem.productId}" style="width: 100%; display: flex; gap: 1rem;">
                            <div class="on-load"
                              style="height: 64px; width: 64px; background-color: rgba(255, 255, 255, 0.2);">
                            </div>
                            <div>
                              <p>
                                <jsp:include page="get_name">
                                  <jsp:param name="id" value="${orderItem.productId}" />
                                </jsp:include>
                              </p>
                              <p>数量: ${orderItem.quantity}</p>
                              <h3>￥${orderItem.price}</h3>
                            </div>
                          </a>
                        </div>
                      </c:forEach>
                    </div>
                  </details>
                </c:forEach>
              </div>

              <div class="pagination">
                <c:choose>
                  <c:when test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}">上一页</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#" style="opacity: 0;">上一页</a>
                  </c:otherwise>
                </c:choose>
                <a href="?page=1">第 ${currentPage} 页</a>
                <a href="?page=${currentPage + 1}">下一页</a>
              </div>
            </c:when>
            <c:otherwise>
              <div class="pagination">
                <c:choose>
                  <c:when test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}">上一页</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#" style="opacity: 0;">上一页</a>
                  </c:otherwise>
                </c:choose>
                <a href="?page=1">第 ${currentPage} 页</a>
                <a href="#" style="opacity: 0;">下一页</a>
              </div>

              <i>空</i>

              <div class="pagination">
                <c:choose>
                  <c:when test="${currentPage > 1}">
                    <a href="?page=${currentPage - 1}">上一页</a>
                  </c:when>
                  <c:otherwise>
                    <a href="#" style="opacity: 0;">上一页</a>
                  </c:otherwise>
                </c:choose>
                <a href="?page=1">第 ${currentPage} 页</a>
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
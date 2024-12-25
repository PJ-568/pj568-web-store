<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="zh-Hans">

    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=width=400, initial-scale=1">
      <meta name="description" content="PJ568 云上商城是一个开放自由的开放平台。">
      <title>PJ568 云上商城</title>
      <jsp:include page="cpn/head.jsp" />
    </head>

    <body>
      <!-- 背景 -->
      <jsp:include page="cpn/bg.jsp" />

      <!-- 导航栏 -->
      <jsp:include page="cpn/navbar.jsp">
        <jsp:param name="loc" value="index" />
      </jsp:include>

      <!-- 主要内容 -->
      <div class="container max-width">
        <div class="content" style="align-items: center; display: flex; flex-direction: column;">
          <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
          </c:if>
          <c:set var="cate" value="${param.cate != null ? param.cate : 0}" />
          <details>
            <c:choose>
              <c:when test="${not empty currentCate}">
                <summary>${currentCate}</summary>
              </c:when>
              <c:otherwise>
                <summary>全类商品</summary>
              </c:otherwise>
            </c:choose>
            <ul>
              <li><a href="?cate=0">全类商品</a></li>
              <c:forEach var="item" items="${cates}">
                <li><a href="?cate=${item.id}">${item.name}</a></li>
              </c:forEach>
            </ul>
          </details>
          <c:set var="currentPage" value="${param.page != null ? param.page : 1}" />
          <c:choose>
            <c:when test="${not empty products}">
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

              <div class="row" style="width: 100%; display: flex; flex-wrap: wrap;">
                <c:forEach var="product" items="${products}">
                  <a class="col-md-4" style="min-width: 128px; width: 33%" href="detail?id=${product.id}">
                    <div class="on-load" style="height: 64px; background-color: rgba(255, 255, 255, 0.2);"></div>
                    <h3>${product.name}&nbsp;&nbsp;￥${product.price}</h3>
                    <p>${product.description}</p>
                  </a>
                </c:forEach>
              </div>

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

              <p>没有找到商品。</p>

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
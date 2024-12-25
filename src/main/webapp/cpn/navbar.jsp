<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <nav>
      <div class="nav-content max-width">
        <a class="logo" href="index">
          <img alt="PJ568 图标" title="PJ568 图标" src="img/PJ568-Icon.png" />
        </a>
        <div class="ul">
          <a title="返回" onclick="javascript:window.history.back(); return false;">
            <i class="fa fa-rotate-left"></i>
            <div class="narrow-hide">返回</div>
          </a>
          <c:choose>
            <c:when test="${param.loc == 'index'.toString()}">
              <a title="主页" href="#">
                <i class="fa fa-home"></i>
                <div class="narrow-hide">主页</div>
              </a>
            </c:when>
            <c:otherwise>
              <a title="主页" href="index">
                <i class="fa fa-home"></i>
                <div class="narrow-hide">主页</div>
              </a>
            </c:otherwise>
          </c:choose>
          <c:if test="${not empty sessionScope.user}">
            <c:choose>
              <c:when test="${param.loc == 'cart'.toString()}">
                <a title="购物车" href="#"><i class="fa fa-cart-shopping"></i>
                  <div class="narrow-hide">购物车</div>
                </a>
              </c:when>
              <c:otherwise>
                <a title="购物车" href="cart"><i class="fa fa-cart-shopping"></i>
                  <div class="narrow-hide">购物车</div>
                </a>
              </c:otherwise>
            </c:choose>
            <c:choose>
              <c:when test="${param.loc == 'order'.toString()}">
                <a title="订单" href="#">
                  <i class="fa fa-list"></i>
                  <div class="narrow-hide">订单</div>
                </a>
              </c:when>
              <c:otherwise>
                <a title="订单" href="order">
                  <i class="fa fa-list"></i>
                  <div class="narrow-hide">订单</div>
                </a>
              </c:otherwise>
            </c:choose>
            <c:if test="${sessionScope.user.getRole() == 1}">
              <c:choose>
                <c:when test="${param.loc == 'manage'.toString()}">
                  <a title="管理" href="#">
                    <i class="fa fa-lock"></i>
                    <div class="narrow-hide">管理</div>
                  </a>
                </c:when>
                <c:otherwise>
                  <a title="管理" href="manage">
                    <i class="fa fa-lock"></i>
                    <div class="narrow-hide">管理</div>
                  </a>
                </c:otherwise>
              </c:choose>
            </c:if>
          </c:if>
          <a title="更改语言" id="translate"><i class="fa fa-language"></i>
            <div class="narrow-hide">语言</div>
          </a>
          <c:choose>
            <c:when test="${param.loc == 'user'.toString()}">
              <a title="用户信息" href="#">
            </c:when>
            <c:otherwise>
              <a title="用户信息" href="user.jsp">
            </c:otherwise>
          </c:choose>
          <i class="fa fa-user"></i>
          <div class="narrow-hide">
            <!-- JSDB 实现显示用户昵称或 <i>游客</i> -->
            <c:choose>
              <c:when test="${not empty sessionScope.user}">
                ${sessionScope.user.nickname}
              </c:when>
              <c:otherwise>
                <i>游客</i>
              </c:otherwise>
            </c:choose>
          </div>
          </a>
        </div>
      </div>
    </nav>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="zh-Hans">

    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=400, initial-scale=1">
      <meta name="description" content="PJ568 云上商城用户登录页面。">
      <c:choose>
        <c:when test="${not empty sessionScope.user}">
          <title>PJ568 云上商城 - ${sessionScope.user.nickname}的个人空间</title>
        </c:when>
        <c:otherwise>
          <title>PJ568 云上商城 - 登录</title>
        </c:otherwise>
      </c:choose>
      <jsp:include page="cpn/head.jsp" />
    </head>

    <body>
      <!-- 背景 -->
      <jsp:include page="cpn/bg.jsp" />

      <!-- 导航栏 -->
      <jsp:include page="cpn/navbar.jsp">
        <jsp:param name="loc" value="user" />
      </jsp:include>

      <!-- 主要内容 -->
      <c:choose>
        <c:when test="${not empty sessionScope.user}">
          <div class="container max-width">
            <div class="content">
              <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
              </c:if>
              <h2>您好，${sessionScope.user.nickname}。</h2>
              <div class="on-load" style="width: 64px; height: 64px; background-color: rgba(255, 255, 255, 0.2);"></div>
              <table>
                <tr>
                  <th>用户 ID</th>
                  <th>用户名</th>
                  <th>昵称</th>
                  <th>角色</th>
                </tr>
                <tr>
                  <td>${sessionScope.user.id}</td>
                  <td>${sessionScope.user.username}</td>
                  <td>${sessionScope.user.nickname}</td>
                  <td>
                    <c:choose>
                      <c:when test="${sessionScope.user.role == 1}">管理员</c:when>
                      <c:otherwise>用户</c:otherwise>
                    </c:choose>
                  </td>
                </tr>
              </table>
              <form action="logout" method="post">
                <button type="submit">登出</button>
              </form>
              <!-- <form action="del_user" method="post">
              <button type="submit" class="warring">删除</button>
            </form> -->
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <div class="container max-width">
            <div class="content">
              <c:if test="${not empty errorMessage}">
                <div class="error-message">${errorMessage}</div>
              </c:if>
              <div style="display: flex; flex-direction: row; justify-content: space-evenly;">
                <form action="login" method="post">
                  <h2>用户登录</h2>
                  <div class="form-group">
                    <label for="username">用户名:</label>
                    <input type="text" id="username" name="username" required>
                  </div>
                  <div class="form-group">
                    <label for="password">密码:</label>
                    <input type="password" id="password" name="password" required>
                  </div>
                  <button style="margin-top: 30px;" type="submit">登录</button>
                </form>
                <form action="sign_up" method="post">
                  <h2>用户注册</h2>
                  <div class="form-group">
                    <label for="username">用户名:</label>
                    <input type="text" id="username" name="username" required>
                  </div>
                  <div class="form-group">
                    <label for="password">密码:</label>
                    <input type="password" id="password" name="password" required>
                  </div>
                  <div class="form-group">
                    <label for="nickname">昵称:</label>
                    <input type="nickname" id="nickname" name="nickname" required>
                  </div>
                  <button type="submit">注册</button>
                </form>
              </div>
            </div>
          </div>
        </c:otherwise>
      </c:choose>

      <!-- 脚注 -->
      <jsp:include page="cpn/footer.jsp" />
    </body>

    </html>
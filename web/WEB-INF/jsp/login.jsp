<%--
  Created by IntelliJ IDEA.
  User: Lenar
  Date: 10.05.2024
  Time: 12:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <label for="email">Email:
            <input type="text" name="email" id="email" required>
        </label><br>

        <label for="password">Password:
            <input type="password" name="password" id="password" required>
        </label><br>
        
        <button type="submit">Login</button>

        <a href="${pageContext.request.contextPath}/registration">
            <button type="button">Register</button>
        </a>
        <div>
            <c:if test="${param.error != null}">
                <div>
                    <span style="color: red">Bad credentials</span>
                </div>
            </c:if>
        </div>
    </form>

</body>
</html>

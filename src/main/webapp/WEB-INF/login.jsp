<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization" var="bundle"/>

<custom:layout>
    <jsp:attribute name="header">
        <title>Login</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css"/>
    </jsp:attribute>
    <jsp:body>
        <div class="text-center box">
            <form accept-charset="UTF-8" role="form" class="form-signin" method="post" action="${pageContext.request.contextPath}/login">
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="signin" bundle="${bundle}"/></h1>

                <c:if test="${loginSuccess != null && !loginSuccess}"><div class="alert alert-danger"><fmt:message key="invalidLogin" bundle="${bundle}"/></div></c:if>
                <c:if test="${logout == true}"><div class="alert alert-warning"><fmt:message key="loggedOut" bundle="${bundle}"/></div></c:if>

                <input class="form-control" placeholder="<fmt:message key="email" bundle="${bundle}"/>" name="email" type="email" required>
                <input class="form-control" placeholder="<fmt:message key="password" bundle="${bundle}"/>" name="password" type="password" required>
                <input class="btn btn-lg btn-primary btn-block" type="submit" value="<fmt:message key="signin" bundle="${bundle}"/>">
            </form>

            <form class="form-signin" action="${pageContext.request.contextPath}/registration">
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="signup" bundle="${bundle}"/></button>
            </form>
        </div>
    </jsp:body>
</custom:layout>
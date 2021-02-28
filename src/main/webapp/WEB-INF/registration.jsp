<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization" var="bundle"/>

<custom:layout>
    <jsp:attribute name="header">
        <title>Registration</title>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/login.css"/>
    </jsp:attribute>
    <jsp:body>
        <div class="text-center box">
            <form accept-charset="UTF-8" role="form" class="form-registration" method="post" action="${pageContext.request.contextPath}/registration">
                <h1 class="h3 mb-3 font-weight-normal"><fmt:message key="signup" bundle="${bundle}"/></h1>
                <input class="form-control" placeholder="<fmt:message key="email" bundle="${bundle}"/>" name="email" type="email" required>
                <input class="form-control" placeholder="<fmt:message key="password" bundle="${bundle}"/>" name="password" type="password" required>
                <input class="form-control" placeholder="<fmt:message key="firstNamePlaceholder" bundle="${bundle}"/>" name="firstname" type="firstname" required>
                <input class="form-control" placeholder="<fmt:message key="lastNamePlaceholder" bundle="${bundle}"/>" name="lastname" type="lastname" required>
                <input class="form-check-input align-text-bottom mt-9" type="checkbox"
                       name="isSpeaker" id="isSpeaker" onChange="checked"/>
                <label class="form-check-label align-text-bottom mt-9" for="isSpeaker">
                    <fmt:message key="speakerCheckbox" bundle="${bundle}"/>
                </label>
                <button class="btn btn-lg btn-primary btn-block" type="submit"><fmt:message key="registerButton" bundle="${bundle}"/></button>
            </form>
        </div>
    </jsp:body>
</custom:layout>
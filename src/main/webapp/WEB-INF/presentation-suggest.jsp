<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization" var="bundle"/>

<custom:layout>
    <jsp:attribute name="header">
        <title></title>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <form action="#" method="post">
                <div class="form-group">
                    <label for="topic"><fmt:message key="topicInscription" bundle="${bundle}"/></label>
                    <fmt:message key="topicInscription" var="topidInscription"/>
                    <input class="form-control" type="text" id="topic" name="topic" placeholder="${topicInscription}"
                           value="${topic}">
                </div>
                <div class="form-group">
                    <label for="time"><fmt:message key="timeInscription" bundle="${bundle}"/></label>
                    <fmt:message key="timeInscription" var="timeInscription"/>
                    <input class="form-control" type="time" id="time" name="time" placeholder="${timeInscription}"
                           value="${time}">
                </div>

                <button type="submit" class="btn btn-primary">
                    <fmt:message key="suggestPresentationButton" bundle="${bundle}"/>
                </button>
            </form>
        </div>
    </jsp:body>
</custom:layout>
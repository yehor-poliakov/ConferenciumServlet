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
            <form action="conference/${conferenceId}/edit" method="post">
                <div class="form-group">
                    <label for="title"><fmt:message key="TitleInscription" bundle="${bundle}"/></label>
                    <fmt:message key="TitleInscription" var="titleInscription" />
                    <input class="form-control" type="text" id="title" name="title" placeholder="${titleInscription}" value="${conference.title}">
                </div>
                <div class="form-group">
                    <label for="location"><fmt:message key="LocationInscription" bundle="${bundle}"/></label>
                    <fmt:message key="LocationInscription" var="locationInscription" />
                    <input class="form-control" type="text" id="location" name="location" placeholder="${locationInscription}" value="${conference.location}">
                </div>
                <div class="form-group">
                    <label for="date"><fmt:message key="DateInscription" bundle="${bundle}"/></label>
                    <fmt:message key="DateInscription" var="dateInscription" />
                    <input class="form-control" type="date" id="date" name="date" placeholder="${dateInscription}" value="${conference.date}">
                </div>
                <button type="submit" class="btn btn-primary"><fmt:message key="editButton" bundle="${bundle}"/></button>
            </form>
        </div>
    </jsp:body>
</custom:layout>
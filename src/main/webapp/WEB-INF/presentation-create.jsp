<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib uri="/WEB-INF/localdate.tld" prefix="d"%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization" var="bundle"/>

<custom:layout>
    <jsp:attribute name="header">
        <title></title>
    </jsp:attribute>
    <jsp:body>
        <div class="row">
            <form role="form" method="post" action="presentation/create/conference/${presentation.conferenceid}">
                <div class="form-group">
                    <label>
                        <input class="form-control" placeholder="<fmt:message key="topicInscription" bundle="${bundle}"/>" name="topic" type="topic" required>
                        <fmt:message key="topicInscription" bundle="${bundle}"/>
                    </label>
                </div>
                <div class="form-group">
                    <label for="time" th:text="#{timeInscription}"></label>
                    <input class="form-control" type="time" th:field="*{time}" id="time" th:placeholder="#{timeInscription}">
                </div>

                <select class="form-select" th:field="*{speakerId}">
                    <option th:value="${null}" selected th:text="#{noSpeakerYetForm}"></option>
                    <option th:each="speaker: ${speakers}" th:value="${speaker.id}" th:text="${speaker.name}">
                </select>

                <div class="form-check mt-1">
                    <input class="form-check-input" type="checkbox" th:field="*{speakerApproved}" id="speakerApproved" th:placeholder="#{speakerApprovedInscription}">
                    <label for="speakerApproved" th:text="#{speakerApprovedInscription}"></label>
                </div>

                <div class="form-check-inline">
                    <input class="form-check-input " type="checkbox" th:field="*{presentationApproved}"
                           id="presentationApproved" th:placeholder="#{presentationApprovedInscription}">
                    <label for="presentationApproved" th:text="#{presentationApprovedInscription}"></label>
                </div>

                <input type="hidden" th:field="*{conferenceId}"/>
                <button type="submit" class="btn btn-primary" th:text="#{addPresentationButton}"></button>
            </form>
        </div>
    </jsp:body>
</custom:layout>
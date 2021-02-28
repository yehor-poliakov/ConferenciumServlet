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
                    <fmt:message key="topicInscription" var="topicInscription"/>
                    <input class="form-control" type="text" id="topic" name="topic" placeholder="${topicInscription}"
                           value="${presentation.topic}" required>
                </div>
                <div class="form-group">
                    <label for="time"><fmt:message key="timeInscription" bundle="${bundle}"/></label>
                    <fmt:message key="timeInscription" var="timeInscription"/>
                    <input class="form-control" type="time" id="time" name="time" placeholder="${timeInscription}"
                           value="${presentation.time}" required>
                </div>

                <select class="form-select" name="speakerId">
                    <option value="${null}" selected><fmt:message key="noSpeakerYetForm" bundle="${bundle}"/></option>
                    <c:forEach items="${speakers}" var="speaker">
                        <option ${speaker.key == presentation.speakerId ? 'selected' : ''}
                                value="${speaker.key}">
                                ${speaker.value}
                        </option>
                    </c:forEach>
                </select>

                <div class="form-check mt-1">
                    <input class="form-check-input" type="checkbox" name="speakerApproved" id="speakerApproved" ${presentation.speakerApproved ? 'checked' : ''}>
                    <label for="speakerApproved"><fmt:message key="speakerApprovedInscription" bundle="${bundle}"/></label>
                </div>

                <div class="form-check mt-1">
                    <input class="form-check-input" type="checkbox" name="presentationApproved" id="presentationApproved" ${presentation.presentationApproved ? 'checked' : ''}>
                    <label for="presentationApproved"><fmt:message key="presentationApprovedInscription" bundle="${bundle}"/></label>
                </div>

                <input type="hidden" name="conferenceId" value="${presentation.conferenceId}"/>

                <button type="submit" class="btn btn-primary"><fmt:message key="editPresentationButton"
                                                                           bundle="${bundle}"/></button>
            </form>
        </div>
    </jsp:body>
</custom:layout>
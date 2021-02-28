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
            <form action="#" action="presentation/create/conference/${conferenceid}" method="post">
                <div class="form-group">
                    <label for="topic"><fmt:message key="topicInscription" bundle="${bundle}"/></label>
                    <fmt:message key="topicInscription" var="topidInscription"/>
                    <input class="form-control" type="text" id="topic" name="topic" placeholder="${topicInscription}"
                           value="${topic}">
                </div>
                <div class="form-group">
                    <label for="time"><fmt:message key="timeInscription" bundle="${bundle}"/></label>
                    <fmt:message key="timeInscription" var="timeInscription"/>
                    <input class="form-control" type="text" id="time" name="time" placeholder="${timeInscription}"
                           value="${time}">
                </div>
                <div class="form-group">
                    <label for="time"><fmt:message key="timeInscription" bundle="${bundle}"/></label>
                    <fmt:message key="timeInscription" var="timeInscription"/>
                    <input class="form-control" type="text" id="time" name="time" placeholder="${timeInscription}"
                           value="${time}">
                </div>

                <div class="input-group mb-2 align-items-center">
                    <label class="filter-col align-text-bottom mb-0 mr-0" for="speaker">
                        <fmt:message key="speakerInscription" bundle="${bundle}"/>
                    </label>
                    <select name="speaker" id="speaker"
                            class="form-control custom-select custom-select-sm ml-2 mb-0 align-center"
                            onChange="this.form.submit()">
                        <c:forEach items="${speakers}" var="speaker">
                            <option ${speakers.toString() == orderbyOption ? 'selected' : ''}
                                    value="${orderbyOption}">
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-check align-items-center h-100">
                    <input class="form-check-input mt-9" type="checkbox"
                           name="speakerApproved" ${presentation.speakerApproved ? 'checked' : ''}
                           id="speakerApproved"/>
                    <label class="form-check-label align-text-bottom mt-9" for="speakerApproved">
                        <fmt:message key="speakerApprovedInscription" bundle="${bundle}"/>
                    </label>
                </div>

                <div class="form-check align-items-center h-100">
                    <input class="form-check-input mt-9" type="checkbox"
                           name="presentationApproved" ${presentation.presentationApproved ? 'checked' : ''}
                           id="presentationApproved"/>
                    <label class="form-check-label align-text-bottom mt-9" for="speakerApproved">
                        <fmt:message key="presentationApprovedInscription" bundle="${bundle}"/>
                    </label>
                </div>

                <input type="hidden" name="presentationid" value="${presentation.conferenceId}"/>
                <button type="submit" class="btn btn-primary"><fmt:message key="addPresentationButton"
                                                                           bundle="${bundle}"/></button>
            </form>
        </div>
    </jsp:body>
</custom:layout>
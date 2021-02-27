<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization" var="bundle"/>

<custom:layout>
    <jsp:attribute name="header">
        <title>${conference.title}</title>
    </jsp:attribute>
    <jsp:body>
        <div>
            <div>
                <h1>${conference.title}</h1>
                <p>${conference.location}</p>
                <p>
                    <c:if test="${pageContext.response.locale == 'en'}">
                        <fmt:parseDate value="${conference.date}"
                                       pattern="yyyy-MM-dd"
                                       var="parsedDate" type="date"
                                       parseLocale="en"/>
                        <fmt:formatDate value="${parsedDate}" type="date"
                                        pattern="MMM-dd-yyyy"/>
                    </c:if>
                    <c:if test="${pageContext.response.locale == 'ua'}">
                        ${conference.date}
                    </c:if>
                </p>

                <c:if test="${sessionScope.role == 'MODERATOR'}">
                    <a href="conference/${conference.id}" class="btn btn-primary">
                        <fmt:message key="editButton" bundle="${bundle}"/>
                    </a>
                </c:if>

                <c:if test="${sessionScope.role == 'PARTICIPANT'}">
                    <c:if test="${!conference.registered}">
                        <form action="/conference/${conference.id}/signup" method="post">
                            <button class="btn btn-primary" type="submit"><fmt:message key="registerButton"
                                                                                       bundle="${bundle}"/></button>
                        </form>
                    </c:if>
                    <c:if test="${conference.registered}">
                        <form action="/conference/${conference.id}/signout" method="post">
                            <button class="btn btn-danger" type="submit"><fmt:message key="unregisterButton"
                                                                                      bundle="${bundle}"/></button>
                        </form>
                    </c:if>
                </c:if>
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col"><fmt:message key="topicInscription" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="timeInscription" bundle="${bundle}"/></th>
                        <th scope="col"><fmt:message key="speakerInscription" bundle="${bundle}"/></th>

                        <c:if test="${sessionScope.role == 'MODERATOR' || sessionScope.role == 'SPEAKER'}">
                            <th scope="col">
                                <fmt:message key="speakerApprovedInscription" bundle="${bundle}"/>
                            </th>
                            <th scope="col">
                                <fmt:message key="presentationApprovedInscription" bundle="${bundle}"/>
                            </th>
                        </c:if>

                        <c:if test="${sessionScope.role == 'MODERATOR'}">
                            <th scope="col"></th>
                        </c:if>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${presentations}" var="presentation">
                    <tr style="transform: rotate(0);">
                        <th scope="col">
                            <c:if test="${sessionScope.role == 'MODERATOR'}">
                                <a href="presentation/${presentation.id}">${presentation.topic}&nbsp;</a>
                            </c:if>
                            <c:if test="${sessionScope.role == 'SPEAKER' || sessionScope.role == 'PARTICIPANT'}">
                                <b>${presentation.topic}</b>
                            </c:if>
                        </th>
                        <td th:text="${presentation.time}"></td>

                        <c:if test="${presentation.speaker == ''}">
                            <td>
                                <c:if test="${sessionScope.role == 'SPEAKER'}">
                                    <form action="presentation/${presentation.id}/suggest-speaker" method="post">
                                        <button class="btn-primary" type="submit"><fmt:message
                                                key="speakerApplyInscription" bundle="${bundle}"/></button>
                                    </form>
                                </c:if>
                            </td>
                        </c:if>
                        <c:if test="${presentation.speaker != ''}">
                            <td>${presentation.speaker}</td>
                        </c:if>
                        <c:if test="${sessionScope.role == 'SPEAKER' || sessionScope.role == 'MODERATOR'}">
                            <td><input type="checkbox" disabled ${presentation.speakerApproved ? 'checked' : ''}/></td>
                            <td><input type="checkbox" disabled ${presentation.presentationApproved ? 'checked' : ''}/>
                            </td>
                        </c:if>

                        <c:if test="${sessionScope.role == 'MODERATOR'}">
                            <td>
                                <form action="presentation/${presentation.id}/delete" method="post">
                                    <button class="btn btn-danger mt-1" type="submit">
                                        <fmt:message key="deletePresentationButton" bundle="${bundle}"/>
                                    </button>
                                </form>
                            </td>
                        </c:if>
                    </tr>
                    </tbody>
                </table>
            </div>

            <c:if test="${sessionScope.role == 'MODERATOR'}">
                <form action="presentation/create/${conference.id}">
                    <button class="btn btn-success" type="submit"><fmt:message key="newPresentationLink"
                                                                               bundle="${bundle}"/></button>
                </form>
                <form action="conference/${conference.id}/delete" method="post">
                    <button class="btn btn-danger mt-1" type="submit">
                        <fmt:message key="deleteConferenceButton" bundle="${bundle}"/>
                    </button>
                </form>
            </c:if>
            <c:if test="${sessionScope.role == 'SPEAKER'}">
                <form action="presentation/suggest/${conference.id}">
                    <button class="btn btn-success mt-1" type="submit">
                        <fmt:message key="suggestPresentationButton" bundle="${bundle}"/>
                    </button>
                </form>
            </c:if>
        </div>
    </jsp:body>
</custom:layout>
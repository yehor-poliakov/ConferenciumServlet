<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization" var="bundle"/>

<custom:layout>
    <jsp:attribute name="header">
        <title>Conferences</title>
    </jsp:attribute>
    <jsp:body>
        <div>
            <div>
                <div class="row mt-2">
                    <div class="filter-panel">
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <c:if test="${sessionScope.role == 'MODERATOR'}">
                                    <form class="form-check-inline" action="conference/create">
                                        <button class="btn btn-success" type="submit" style="margin-top:-.5rem;">
                                            <fmt:message key="newConferenceLink" bundle="${bundle}"/>
                                        </button>
                                    </form>
                                </c:if>
                                <form method="get">
                                    <div class="form-row">
                                        <div class="col-auto ml-3 pt-1">
                                            <div class="form-check align-items-center h-100">
                                                <input class="form-check-input align-text-bottom mt-9" type="checkbox"
                                                       name="showPast" ${filters.showPast ? 'checked' : ''}
                                                       id="showPast" onChange="this.form.submit()"/>
                                                <label class="form-check-label align-text-bottom mt-9" for="showPast">
                                                    <fmt:message key="pastEventsCheckbox" bundle="${bundle}"/>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-auto pt-1">
                                            <div class="form-check align-items-center h-100">
                                                <input class="form-check-input align-text-bottom mt-9" type="checkbox"
                                                       name="showFuture" ${filters.showFuture ? 'checked' : ''}
                                                       id="showFuture" onChange="this.form.submit()"/>
                                                <label class="form-check-label align-text-bottom mt-9" for="showFuture">
                                                    <fmt:message key="futureEventsCheckbox" bundle="${bundle}"/>
                                                </label>
                                            </div>
                                        </div>
                                        <div class="col-auto">
                                            <div class="input-group mb-2 align-items-center">
                                                <label class="filter-col align-text-bottom mb-0 mr-0" for="orderby">
                                                    <fmt:message key="orderByOption" bundle="${bundle}"/>
                                                </label>
                                                <select name="orderby" name="orderby" id="orderby"
                                                        class="form-control custom-select custom-select-sm ml-2 mb-0 align-center"
                                                        onChange="this.form.submit()">
                                                    <c:forEach items="${orderbyOptions}" var="orderbyOption">
                                                        <option ${filters.orderby.toString() == orderbyOption ? 'selected' : ''}
                                                                value="${orderbyOption}">
                                                            <fmt:message key="${orderbyOption}" bundle="${bundle}"/>
                                                        </option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <div>
                    <c:forEach items="${page.items}" var="c" varStatus="state">
                        <c:if test="${state.index % 3 == 0}">
                            <div class="row">
                                <c:forEach items="${page.items}" var="conference" varStatus="counter">
                                    <c:if test="${counter.index >= state.index && counter.index < state.index + 3}">
                                        <div class="col-xs-6 col-lg-4 card">
                                            <div class="card-body">
                                                <h5 class="card-title">${conference.title}</h5>
                                                <h6 class="card-subtitle mb-2 text-muted">${conference.location}</h6>
                                                <ul class="list-group list-group-flush">
                                                    <li class="list-group-item">
                                                        <fmt:message key="DateInscription" bundle="${bundle}"/>
                                                        :&nbsp;
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
                                                    </li>
                                                    <li class="list-group-item">
                                                        <fmt:message key="PresentationsNumberInscription"
                                                                     bundle="${bundle}"/>
                                                        :&nbsp;
                                                            ${conference.presentationsCount}
                                                    </li>
                                                    <li class="list-group-item">
                                                        <fmt:message key="ParticipantsNumberInscription"
                                                                     bundle="${bundle}"/>
                                                        :&nbsp;
                                                            ${conference.participantsCount}
                                                    </li>
                                                </ul>
                                                <a href="${conference.id}" class="btn btn-primary link">
                                                    <fmt:message key="viewButton" bundle="${bundle}"/>
                                                </a>

                                            </div>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </c:if>
                    </c:forEach>


                </div>

            </div>
            <nav style="margin-top:40px;">
                <ul class="pagination justify-content-center">
                    <li class="${page.first ? 'page-item disabled' : 'page-item'}">
                        <a class="page-link"
                           href="conferences?pageNumber=${page.number-1}&pageSize=${page.size}&showPast=${filters.showPast ? 'on' : 'off'}&showFuture=${filters.showFuture ? 'on' : 'off'}&orderby=${filters.orderby}">Previous</a>
                    </li>
                    <c:forEach begin="1" end="${page.totalPages}" var="i">
                        <li class="${page.number == i ? 'page-item active' : 'page-item'}">
                            <a class="page-link"
                               href="conferences?pageNumber=${i}&pageSize=${page.size}&showPast=${filters.showPast ? 'on' : 'off'}&showFuture=${filters.showFuture ? 'on' : 'off'}&orderby=${filters.orderby}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="${page.last ? 'page-item disabled' : 'page-item'}">
                        <a class="page-link"
                           href="conferences?pageNumber=${page.number+1}&pageSize=${page.size}&showPast=${filters.showPast ? 'on' : 'off'}&showFuture=${filters.showFuture ? 'on' : 'off'}&orderby=${filters.orderby}">Next</a>
                    </li>
                </ul>
            </nav>
        </div>
    </jsp:body>
</custom:layout>
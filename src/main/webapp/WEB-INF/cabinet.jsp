<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="custom" %>
<%@ taglib uri="/WEB-INF/localdate.tld" prefix="d"%>

<%@page contentType="text/html" pageEncoding="UTF-8" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization" var="bundle"/>

<custom:layout>
    <jsp:attribute name="header">
        <title>Conferences</title>
    </jsp:attribute>
    <jsp:body>
        <h1>
            <fmt:message key="speakerCabinetInscription" bundle="${bundle}"/>
        </h1>
        <table class="table table-striped">
            <thead>
            <tr>
                <th class="align-text-top" scope="col">
                    <fmt:message key="TitleInscription" bundle="${bundle}"/>
                </th>
                <th class="align-text-top" scope="col">
                    <fmt:message key="topicInscription" bundle="${bundle}"/>
                </th>
                <th class="align-text-top" scope="col">
                    <fmt:message key="LocationInscription" bundle="${bundle}"/>
                </th>
                <th class="align-text-top" scope="col">
                    <fmt:message key="DateInscription" bundle="${bundle}"/>
                </th>
                <th class="align-text-top" scope="col">
                    <fmt:message key="timeInscription" bundle="${bundle}"/>
                </th>
                <th class="align-text-top" scope="col">
                    <fmt:message key="presentationApprovedInscription" bundle="${bundle}"/>
                </th>
                <th class="align-text-top" scope="col">
                    <fmt:message key="speakerApprovedInscription" bundle="${bundle}"/>
                </th>
            </tr>
            </thead>
            <tbody>
                <c:forEach items="${page.items}" var="presentation">
                    <tr style="transform: rotate(0);">
                        <td><a href="conference/${presentation.conferenceId}">${presentation.conferenceTitle}</a></td>
                        <td>${presentation.presentationTopic}</td>
                        <td>${presentation.conferenceLocation}</td>
                        <td>
                            <d:localdate date="${presentation.conferenceDate}" locale="${pageContext.response.locale}"/>
                        </td>
                        <td>${presentation.presentationTime}</td>
                        <td>
                            <input type="checkbox" disabled ${presentation.speakerApproved ? 'checked' : ''}/>
                        </td>
                        <td>
                            <input type="checkbox" disabled ${presentation.presentationApproved ? 'checked' : ''}/>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <nav style="margin-top:40px;">
            <ul class="pagination justify-content-center">
                <li class="${page.first ? 'page-item disabled' : 'page-item'}">
                    <a class="page-link" href="cabinet?pageNumber=${page.number-1}&pageSize=${page.size}">Previous</a>
                </li>
                <c:forEach begin="1" end="${page.totalPages}" var="i">
                    <li class="${page.number == i ? 'page-item active' : 'page-item'}">
                        <a class="page-link" href="cabinet?pageNumber=${i}&pageSize=${page.size}">${i}</a>
                    </li>
                </c:forEach>
                <li class="${page.last ? 'page-item disabled' : 'page-item'}">
                    <a class="page-link" href="cabinet?pageNumber=${page.number+1}&pageSize=${page.size}">Next</a>
                </li>
            </ul>
        </nav>
    </jsp:body>
</custom:layout>
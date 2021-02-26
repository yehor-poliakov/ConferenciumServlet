<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@tag description="Page navigation bar" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@attribute name="header" fragment="true" required="false" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="localization" var="bundle"/>

<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"/>
        <jsp:invoke fragment="header"/>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="${pageContext.request.contextPath}/conferences" style="font-weight:bold">
                <fmt:message key="conferencium" bundle="${bundle}"/>
            </a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                    aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav mr-auto">
                    <c:if test="${sessionScope.authenticated}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/conferences">
                                <fmt:message key="conferences" bundle="${bundle}"/>
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${sessionScope.role == 'SPEAKER'}">
                        <li class="nav-item">
                            <a class="nav-link" href="${pageContext.request.contextPath}/cabinet">
                                <fmt:message key="cabinet" bundle="${bundle}"/>
                            </a>
                        </li>
                    </c:if>
                </ul>
                <div class="my-2 my-lg-0">
                    <input class="form-inline-check-input" type="radio" name="locale" id="flexRadioDefault1" value="ua"
                            ${sessionScope.locale != 'en' ? 'checked' : ''} />
                    <label class="form-check-label mr-1" for="flexRadioDefault1">
                        Українська
                    </label>
                    <input class="form-inline-check-input" type="radio" name="locale" id="flexRadioDefault2" value="en"
                           ${sessionScope.locale == 'en' ? 'checked' : ''}/>
                    <label class="form-check-label" for="flexRadioDefault2">
                        English
                    </label>
                </div>
                <div class="my-2 my-lg-0 ml-2">
                    <c:if test="${sessionScope.authenticated}">
                        <form class="form-inline my-2 my-lg-0" action="${pageContext.request.contextPath}/logout"
                              method="post">
                            <button class="btn btn-primary my-2 my-sm-0" type="submit">
                                <fmt:message key="signout" bundle="${bundle}"/>
                            </button>
                        </form>
                    </c:if>
                </div>
            </div>
        </nav>

        <div class="container">
            <jsp:doBody />
        </div>

        <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/static/js/internationalization.js"></script>

    </body>
</html>
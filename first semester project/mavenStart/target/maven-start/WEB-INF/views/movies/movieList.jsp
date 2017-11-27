<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <%--<t:head title="movie_list" cssFilePath="/css/movie_list.css"/>--%>

    <head>
        <title>searchActors</title>
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/menu.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/movie_list.css"/>">
        <script src="<c:url value="/js/jquery-3.2.1.js"/>"></script>
        <script src="<c:url value="/js/common/common_people_functions.js"/>"></script>
        <script src="<c:url value="/js/search/menu_search.js"/>" async></script>
        <script src="<c:url value="/js/search/search_movies.js"/>"></script>

    </head>

    <body>

    <c:set var="numPage" value="${requestScope.numPage}"/>

    <jsp:include page="/WEB-INF/views/main/menu_main.jsp"/>

        <div id = "allForms">

            <input type="hidden" id = "numPage" value="${numPage}"/>

            <div id = "back" >
                <div class="arrow_div">
                    <img class="arrow" src="<c:url value="/images/list_page/back.png"/>"/>
                </div>
            </div>

            <div id="searchDiv">
                <form id="searchForm">
                    <input type="hidden" id = "searchType" name="searchType" value="user"/>
                    <input type="text" id="searchInput" placeholder="Search movie...">
                </form>
            </div>

            <div id = "dataDiv">

                <c:if test="${requestScope.size == 0}">
                    <input id = "isLast" type="hidden" value="last"/>
                </c:if>

                <h2 class = "h2_title">Самые популярные фильмы</h2>

                <ul class="list">
                    <c:forEach var="movie" items="${requestScope.list}">
                        <li class = "li_item">
                            <div class = "list_item">
                                <div class = "div_photo">
                                    <a href="${pageContext.request.contextPath}/movie_content?movie_id=${movie.id}">
                                        <img class = "image" src="${movie.photo}" alt = "${movie.name}"/>
                                    </a>
                                </div>

                                <div class="div_content">
                                    <div class = "content">
                                    <p>Название фильма :</p>
                                        <a href="${pageContext.request.contextPath}/movie_content?movie_id=${movie.id}">
                                            <span class="span_name">${movie.name}</span>
                                        </a>
                                    </div>
                                </div>
                                <div class = "div_mark">
                                    <p>Оценка :</p>
                                    <span class="span_mark">${movie.mark}</span>
                                </div>
                                <div class="clear_wrapper">
                                    <div class="clear"></div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>

            </div>


            <div id = "forward" >
                <div class="arrow_div">
                    <img class="arrow" src="<c:url value="/images/list_page/forward.png"/>"/>
                </div>
            </div>

        </div>

    </body>

</html>

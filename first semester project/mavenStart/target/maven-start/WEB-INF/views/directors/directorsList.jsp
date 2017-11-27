<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>
        <title>searchActors</title>
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/menu.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/group_list.css"/>">
        <script src="<c:url value="/js/jquery-3.2.1.js"/>"></script>
        <script src="<c:url value="/js/common/common_people_functions.js"/>"></script>
        <script src="<c:url value="/js/search/search_directors.js"/>"></script>
        <script src="<c:url value="/js/search/menu_search.js"/>" async></script>

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
                    <input type="text" id="searchInput" placeholder="Search actor...">
                </form>
            </div>
            <div id = "dataDiv">

                <c:if test="${requestScope.size == 0}">
                    <input id = "isLast" type="hidden" value="last"/>
                </c:if>

                <h2 class = "h2_title">Самые популярные режиссеры</h2>

                <ul class="list">
                    <c:forEach var="director" items="${requestScope.directors}" varStatus="number">
                        <li class="li_item">
                            <div class="list_item">

                                <div class = "div_photo">
                                    <a href="${pageContext.request.contextPath}/director_content?director_id=${director.id}">
                                        <img class="image" src="${director.photo}" alt = "${director.name}"/>
                                    </a>
                                </div>

                                <div class="div_content">
                                    <div class = "content">
                                        <p>Имя режиссера :
                                            <a href="${pageContext.request.contextPath}/director_content?director_id=${director.id}">
                                                <span class="span_name"> ${director.name.concat(" ").concat(director.surname)}</span>
                                            </a></p>
                                        <p>Страна : ${director.getMotherlandName()}</p>
                                        <p>День рождения : ${director.getBirthdayStr()}</p>
                                        <p>Оценка : ${director.mark}</p>
                                    </div>
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


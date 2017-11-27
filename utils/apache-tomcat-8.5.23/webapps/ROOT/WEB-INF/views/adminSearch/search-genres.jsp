<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <title>searchActors</title>
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/menu.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/search.css"/>">
        <script src="<c:url value="/js/jquery-3.2.1.js"/>"></script>
        <script src="<c:url value="/js/search/search_genres.js"/>"></script>
        <script src="<c:url value="/js/common/common_movie_functions.js"/>"></script>
        <script src="<c:url value="/js/search/menu_search.js"/>" async></script>

    </head>

    <body>

    <jsp:include page="/WEB-INF/views/main/menu_main.jsp"/>

        <div id="allForms">

            <h2>Найдите нужный жанр и добавьте его</h2>

            <form name="actorsForm">
                <input type="text" id="searchInput" placeholder="Search genre...">
            </form>

            <div id = "some">

                <div id = "forms">

                    <button id="add" value="" class="button">Добавить</button>

                    <button id="delete" value="" class="button">Удалить</button>

                    <form class="searchBtn" id = "searchForm" method="post" action="<c:url value="/bind-genres"/>">
                        <input type="hidden" id = "idList" name="movie_genres" value="">
                        <input type="hidden" id = "namesList" name="genresNames" value="">
                        <input type="submit" id="saveAll" value="Сохранить/Вернуться" class="button" style="margin-left: 0;">
                    </form>

                    <p id = "added">Добавленные :
                        <span id="addList"></span>
                    </p>

                </div>

            </div>

            <div id = "dataDiv">
            </div>

        </div>

    </body>

</html>
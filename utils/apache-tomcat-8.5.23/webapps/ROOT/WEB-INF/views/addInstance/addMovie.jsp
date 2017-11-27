<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <title>addMovie</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/movie/add_movie.css"/>"/>
        <script src="<c:url value="/js/jquery-3.2.1.js"/>"></script>
        <script src="<c:url value="/js/common/save_movie_session.js"/>"></script>
    </head>

    <body>

        <% response.setHeader( "Cache-Control","no-cache, max-age=0, must-revalidate, no-store");%>

        <div id = "registration_div">

            <div id = "registration_form">

                <c:set var="name" value="${sessionScope.movie_name}" />
                <c:set var="description" value="${sessionScope.movie_subscription}" />
                <c:set var="budget" value="${sessionScope.movie_budget}" />
                <c:set var="money" value="${sessionScope.movie_money}" />
                <c:set var="photo" value="${sessionScope.movie_photo}" />
                <c:set var="year" value="${sessionScope.movie_year}" />

                <c:set var="actorsId" value="${sessionScope.movie_actors}" />
                <c:set var="actorsNames" value="${sessionScope.actorsNames}" />

                <c:set var="directorsId" value="${sessionScope.movie_directors}" />
                <c:set var="directorsNames" value="${sessionScope.directorsNames}" />

                <c:set var="countriesId" value="${sessionScope.movie_countries}" />
                <c:set var="countriesNames" value="${sessionScope.countriesNames}" />

                <c:set var="genresId" value="${sessionScope.movie_genres}"/>
                <c:set var="genresNames" value="${sessionScope.genresNames}"/>

                <t:formBtn actionPath="/bind-actors" btnValue="Добавить актеров"/>
                <t:formBtn actionPath="/bind-directors" btnValue="Добавить режиссеров"/>
                <t:formBtn actionPath="/bind-countries" btnValue="Добавить страны"/>
                <t:formBtn actionPath="/bind-genres" btnValue="Добавить жанры"/>

                <form action = "${pageContext.request.contextPath}/add-movie" method="post" id = "form">

                    <p><input type="text" name="movie_name" placeholder="Название" class="text" value="${name}"> </p>
                    <p><input type="text" name="movie_photo" placeholder="Адрес фото" class="text" value="${photo}"> </p>
                    <p><textarea id="movie_description" rows="1" cols="1" name="movie_subscription" placeholder="Описание фильма">
                        ${description}
                    </textarea></p>

                    <input type="hidden" name="movie_actors" value="${actorsId}">
                    <p>Актеры : ${actorsNames}</p>

                    <input type="hidden" name="movie_directors" value="${directorsId}">
                    <p>Режиссеры : ${directorsNames}</p>

                    <input type="hidden" name="movie_countries" value="${countriesId}">
                    <p>Страны : ${countriesNames}</p>

                    <input type="hidden" name="movie_genres" value="${genresId}">
                    <p>Жанры : ${genresNames}</p>

                    <p><input type = "date" name = "movie_year" placeholder="Дата выхода" class="text" value="${year}"></p>
                    <p><input type = "text" name = "movie_budget" placeholder="Бюджет" class="text" value="${budget}"/></p>
                    <p><input type = "text" name = "movie_money" placeholder="Касса" class="text" value="${money}"/></p>

                    <input id="saveMovie" type="submit" value="Сохранить" class="button">

                    <%--Ощибка добавления --%>
                    <t:popup_data
                        data="${sessionScope.popup_data}"
                    />
                </form>

            </div>

        </div>

    </body>

</html>


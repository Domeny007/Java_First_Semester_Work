<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <title>add_genre</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/registration.css"/>"/>
    </head>

    <body>

        <%--Очистка кэша,чтобы не возвращаться по кнопке назад,если авторизован--%>

        <% response.setHeader( "Cache-Control","no-cache, max-age=0, must-revalidate, no-store");%>

        <div id = "registration_div">

            <%--Основная форма регистрации --%>
            <div id = "registration_form">

                <form action = "${pageContext.request.contextPath}/add-genre" method="post" id = "form">

                    <p><input type="text" name="genre_name" placeholder="Название жанра" class="text"> </p>

                    <input type="submit" value="Сохранить" class="button">

                </form>

            </div>

        </div>

    </body>

</html>
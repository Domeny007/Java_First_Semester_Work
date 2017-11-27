<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <title>Personal</title>
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/menu.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/personal_page.css"/>">
        <script src="<c:url value="/js/jquery-3.2.1.js"/>"></script>
        <script src="<c:url value="/js/search/menu_search.js"/>"></script>

    </head>

    <body>

        <jsp:include page="/WEB-INF/views/main/menu_main.jsp"/>

        <c:set var = "user" value="${sessionScope.current_user}"/>

        <c:set var = "rights" value="${current_user.rights.getName()}"/>

        <c:set var="adminRight" value="admin"/>

        <div id = "personal_div">

            <div id = "user_data">
                <h2 class = "user_p">Персональные данные</h2>
                <p class = "user_p">Имя пользователя : ${user.username}</p>
                <p class = "user_p">Страна : ${user.country.getName()}</p>
                <p class = "user_p">Пол : ${user.gender}</p>
                <p class = "user_p">Оценка : ${user.mark}</p>
                <p class = "user_p">Права : ${rights}</p>

                <c:if test="${rights.equals(adminRight)}">
                    <form action="<c:url value="/add-movie"/>">
                        <input type="hidden"  name="addMoviePage" value="addMoviePage"/>
                        <input type="submit" value="Добавить фильм" class="button"/>
                    </form>
                    <t:formBtn actionPath="/add-director" btnValue="Добавить режиссера"/>
                    <t:formBtn actionPath="/add-actor" btnValue="Добавить актера"/>
                    <t:formBtn actionPath="/add-country" btnValue="Добавить страну"/>
                    <t:formBtn actionPath="/add-genre" btnValue="Добавить жанр"/>
                </c:if>

                <t:formBtn actionPath="/change-data" btnValue="Изменить данные"/>

                <t:formBtn actionPath="/delete-user" btnValue="Удалить аккаунт"/>

                <div id = "registration_div">

                </div>

                <%--Изменены данные user--%>
                <t:popup_data
                    data="${sessionScope.popup_data}"
                />


                <c:if test="${user.rights.equals(adminRight)}">
                    <%-- Добавлено что-то --%>
                    <t:popup_data
                        data="${sessionScope.popup_data}"
                    />
                </c:if>

            </div>

        </div>

    </body>

</html>
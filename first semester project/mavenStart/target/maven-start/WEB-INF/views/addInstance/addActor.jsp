<%@ page import="utils.Const" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

    <head>
        <title>add_actor</title>
        <link rel="stylesheet" type="text/css" href="<c:url value="/css/registration.css"/>"/>
    </head>

    <body>

        <%--Очистка кэша,чтобы не возвращаться по кнопке назад,если авторизован--%>

        <% response.setHeader( "Cache-Control","no-cache, max-age=0, must-revalidate, no-store");%>

        <c:set var="countries" value="${sessionScope.countries}"/>

        <c:set var="name" value="${sessionScope.actor_name}"/>
        <c:set var="surname" value="${sessionScope.actor_surname}"/>
        <c:set var="photo" value="${sessionScope.actor_photo}"/>
        <c:set var="selCountry" value="${sessionScope.actor_motherland}"/>
        <c:set var="birthday" value="${sessionScope.actor_birthday}"/>

        <div id = "registration_div">

            <%--Основная форма регистрации --%>
            <div id = "registration_form">

                <form action = "${pageContext.request.contextPath}/add-actor" method="post" id = "form">

                    <p><input type="text" name="actor_name" placeholder="Имя" class="text" value="${name}"> </p>
                    <p><input type="text" name="actor_surname" placeholder="Фамилия" class="text" value="${surname}"> </p>
                    <p><input type="text" name="actor_photo" placeholder="Адрес фото" class="text" value="${photo}"> </p>
                    <p><input type = "date" name = "actor_birthday" placeholder="Дата рождения" class="text" value="${birthday}"></p>
                    <p>Выберите страну : <select required name="actor_motherland">

                        <c:forEach var="country" items="${countries}">
                            <c:choose>
                                <c:when test="${(empty selCountry) && (country.id == 1)}">
                                    <option selected value="${country.id}">${country.name}</option>
                                </c:when>
                                <c:when test="${country.id == selCountry}">
                                    <option selected value="${selCountry}">${country.name}</option>
                                </c:when>

                                <c:otherwise>
                                    <option value="${country.id}">${country.name}</option>
                                </c:otherwise>
                            </c:choose>

                        </c:forEach>
                    </select></p>

                    <input type="submit" value="Сохранить" class="button">

                    <t:popup_data
                        data="${sessionScope.popup_data}"
                    />

                    <%
                        session.removeAttribute("actor_name");
                        session.removeAttribute("actor_surname");
                        session.removeAttribute("actor_birthday");
                        session.removeAttribute("actor_motherland");
                        session.removeAttribute("actor_photo");
                        session.removeAttribute("countries");
                    %>
                </form>

            </div>

        </div>

    </body>

</html>

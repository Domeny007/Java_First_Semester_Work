<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <t:head title="changeData" cssFilePath="/css/registration.css"/>

    <body>

        <c:set var="user" scope="session" value="${sessionScope.current_user}"/>
        <c:set var="countries" scope="session" value="${sessionScope.countries}"/>

        <%--Очистка кэша,чтобы не возвращаться по кнопке назад,если авторизован--%>
        <% response.setHeader( "Cache-Control","no-cache, max-age=0, must-revalidate, no-store");%>


        <div id = "registration_div">

            <%--Основная форма регистрации --%>
            <div id = "registration_form">

                <form action = "${pageContext.request.contextPath}/change-data" method="post" id = "form">
                    <p><input type="text" name="username" placeholder="Username"
                              class="text" value="${user.username}"  autocomplete="off"></p>

                    <jsp:include page= "/WEB-INF/views/user/userDopData.jsp"/>

                    <input type="submit" value="Сохранить" class="button">

                </form>

            </div>

        </div>

    </body>

</html>

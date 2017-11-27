<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>


<html>

    <t:head
            title="Registration"
            cssFilePath="/css/registration.css"
            scriptFilePath="/js/form_reset.js"
    />

    <body>

        <%--Очистка кэша,чтобы не возвращаться по кнопке назад,если авторизован--%>

        <% response.setHeader( "Cache-Control","no-cache, max-age=0, must-revalidate, no-store");%>

        <c:set var="email" value="${sessionScope.email}"/>
        <c:set var="countries" value="${sessionScope.countries}"/>
        <c:set var="selCountry" value="${sessionScope.countryId}"/>
        <c:set var="username" value="${sessionScope.username}"/>
        <c:set var="gender" value="${sessionScope.gender}"/>
        <c:set var="subscribed" value="${sessionScope.subscribed}"/>

        <div id = "registration_div">

            <%--Основная форма регистрации --%>
            <div id = "registration_form">

                <form action = "${pageContext.request.contextPath}/registration_serv" method="post" id = "form">

                    <p><input type="email" name="email" placeholder="Email" class="text" value="${email}" autocomplete="off"> </p>
                    <p><input type="text" name="username" placeholder="Username" class="text" value="${username}" autocomplete="off"></p>
                    <p><input type = "password" name = "password" placeholder="Password" class="text" autocomplete="off"></p>
                    <p><input type = "password" name = "passwordAgain" placeholder="Confirm the password" class="text" autocomplete="off"></p>

                    <jsp:include page= "/WEB-INF/views/user/userDopData.jsp"/>

                    <input type="submit" value="Отправить" class="button">

                    <%--Ощибка регистрации --%>
                    <t:popup_data data="${sessionScope.popup_data}" />

                    <%
                        session.removeAttribute("email");
                        session.removeAttribute("countryId");
                        session.removeAttribute("username");
                        session.removeAttribute("gender");
                        session.removeAttribute("subscribed");
                    %>

                </form>

            </div>

        </div>


    </body>

</html>

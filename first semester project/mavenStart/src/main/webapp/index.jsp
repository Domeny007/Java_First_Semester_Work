<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>

    <t:head
            title="Authentication"
            cssFilePath="/css/start_page.css"
            scriptFilePath="/js/form_reset.js"
    />

    <body>


        <div id = "allForms">


            <%--Очистка кэша,чтобы не возвращаться по кнопке назад,если авторизован--%>
            <% response.setHeader( "Cache-Control","no-cache, max-age=0, must-revalidate, no-store");%>

            <c:set var="email" value="${sessionScope.email}"/>
            <c:set var="rights" value="${sessionScope.userRights}"/>
            <c:set var="remember" value="${sessionScope.cookieRemember}"/>

            <%--Основная форма аутентификации --%>
            <div id = "authentication">

                <form action = "authorization" method="post" id = "form">
                    <p><input type="email" name="email" class="text" placeholder="Email" value="${email}"></p>
                    <p><input type = "password" name = "password" class="text" placeholder="Password"></p>
                    <p>Запомнить меня :
                        <c:choose>

                            <c:when test = "${remember == true}">
                                <input type = "checkbox" name = "cookieRemember" checked>
                            </c:when>

                            <c:otherwise>
                                <input type = "checkbox" name = "cookieRemember">
                            </c:otherwise>

                        </c:choose>

                    </p>

                    <input type="submit" value="Войти" class="button">

                </form>

                <%--Форма для регистрации --%>
                <div id = "registration">
                    <t:formBtn actionPath="/registration_serv" btnValue="Зарегистрироваться" />
                </div>


                <t:popup_data data="${sessionScope.popup_data}" />

            </div>

        </div>


        <%
            session.removeAttribute("email");
            session.removeAttribute("userRights");
            session.removeAttribute("rememberKey");
        %>

    </body>

</html>

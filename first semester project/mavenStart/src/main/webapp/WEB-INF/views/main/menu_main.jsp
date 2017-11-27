<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8"%>

<div id = "menuDiv">

    <ul class = ul_menu>
        <li id = "movie_part" ><a href="<c:url value="${pageContext.request.contextPath}/movieList"/>">Фильмы</a></li>
        <li id = "people_part"  class="dropdown">
            <a href="#dropdown" class="dropbtn">Люди</a>
            <div class="dropdown-content">
                <a href="<c:url value="${pageContext.request.contextPath}/actorList"/>">Актеры</a>
                <a href="<c:url value="${pageContext.request.contextPath}/directorList"/>">Режиссеры</a>
            </div>
        </li>
        <%--<li id = "group_part"><a href="<c:url value="${pageContext.request.contextPath}/groupList"/>">Группы</a></li>--%>
        <li id = "search_part">
            <div id = "menu_search">
                <form id="menu_search_form">
                    <input type="hidden" id = "menuType" name="searchType" value="menu"/>
                    <input type="search" id="menuInput" placeholder="Search movie..." autocomplete="off">
                </form>
            </div>
                <div id = div_autocomplete>
                </div>


        </li>

        <li id = "quit_part" class="right_li"><a href="<c:url value="${pageContext.request.contextPath}/exit"/>" class="right">Выйти</a></li>
        <li id = "personal_part" class="right_li"><a href="<c:url value="/personal_page"/>" class="right">Моя страница</a></li>
    </ul>

</div>
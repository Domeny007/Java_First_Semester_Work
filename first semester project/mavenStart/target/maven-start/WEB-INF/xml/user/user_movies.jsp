<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<data>

    <c:choose>

    <c:when test="${requestScope.size == 0}">
        <input id = "isLast" type="hidden" value="last"/>
        <h2 class = "h2_title">Ничего не найдено</h2>
    </c:when>
    <c:otherwise>

    <input id = "isLast" type="hidden" value="not_last"/>
    <h2 class = "h2_title">${requestScope.title}</h2>

    <ul class="list">
        <c:forEach var="movie" items="${requestScope.movies}">
            <li class = "li_item">
                <div class = "list_item">
                    <div class = "div_photo">
                        <a href="${pageContext.request.contextPath}/movie_content?movie_id=${movie.id}">
                            <img class = "image" src="${movie.photo}" alt = "${movie.name}"/>
                        </a>
                    </div>

                    <div class="div_content">
                        <div class = "content">
                            <p>Название фильма :</p>
                            <a href="${pageContext.request.contextPath}/movie_content?movie_id=${movie.id}">
                                <span class="span_name">${movie.name}</span>
                            </a>
                        </div>
                    </div>
                    <div class = "div_mark">
                        <p>Оценка :</p>
                        <span class="span_mark">${movie.mark}</span>
                    </div>
                    <div class="clear_wrapper">
                        <div class="clear"></div>
                        <input type = "hidden" id = "size" value = "${requestScope.size}"/>
                    </div>
                </div>
            </li>
        </c:forEach>
    </ul>
    </c:otherwise>
    </c:choose>
</data>
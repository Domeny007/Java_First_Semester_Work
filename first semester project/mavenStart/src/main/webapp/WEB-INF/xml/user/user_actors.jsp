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
                <c:forEach var="actor" items="${requestScope.actors}" varStatus="number">
                    <li class="li_item">
                        <div class="list_item">

                            <div class = "div_photo">
                                <a href="${pageContext.request.contextPath}/actor_content?actor_id=${actor.id}">
                                    <img class="image" src="${actor.photo}" alt = "${actor.name}"/>
                                </a>
                            </div>

                            <div class="div_content">
                                <div class = "content">
                                    <p>Имя актера :
                                        <a href="${pageContext.request.contextPath}/actor_content?actor_id=${actor.id}">
                                            <span class="span_name">${actor.name.concat("  ").concat(actor.surname)}</span>
                                        </a></p>
                                    <p>Страна : ${actor.getMotherlandName()}</p>
                                    <p>День рождения : ${actor.getBirthdayStr()}</p>
                                    <p>Оценка : ${actor.mark}</p>
                                </div>
                            </div>
                            <div class="clear_wrapper">
                                <div class="clear"></div>
                            </div>

                        </div>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
        </c:choose>
</data>
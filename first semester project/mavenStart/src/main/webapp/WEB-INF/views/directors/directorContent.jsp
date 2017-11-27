<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <c:set var = "director" value="${sessionScope.director}" />
    <c:set var = "reviews" value="${sessionScope.director_reviews}" />

    <c:set var = "user" value="${sessionScope.current_user}" />
    <c:set var = "userMark" value="${sessionScope.user_movie_mark}" />

    <head>
        <title>${actor.name}</title>
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/menu.css"/>">
        <link rel="stylesheet" type="text/css" href="<c:url value = "/css/movie_content.css"/>">
        <script src="<c:url value="/js/jquery-3.2.1.js"/>"></script>
        <script src="<c:url value="/js/common/contentMarkPages.js"/>"></script>
        <script src="<c:url value="/js/search/menu_search.js"/>" async></script>

    </head>


    <body>

        <jsp:include page="/WEB-INF/views/main/menu_main.jsp"/>

        <div id = "allForms">

            <div id = mainColor>

                <div id = "general_inf">

                    <div id = "div_photo">
                        <img src="${director.photo}" alt="${director.name}">
                    </div>

                    <div id = "div_short_inf">

                        <p><span>Имя : ${director.name.concat(" ").concat(director.surname)}</span></p>
                        <div class="clear_wrapper">
                            <div class="clear"></div>
                        </div>

                        <p><span>День рождения : ${sessionScope.director_birthday}</span></p>
                        <div class="clear_wrapper">
                            <div class="clear"></div>
                        </div>

                        <p><span>Страна рождения : ${sessionScope.director_motherland}</span></p>
                        <div class="clear_wrapper">
                            <div class="clear"></div>
                        </div>

                        <p><span>Оценка режиссера : ${director.mark}</span></p>
                        <div class="clear_wrapper">
                            <div class="clear"></div>
                        </div>

                    </div>

                </div>

                <div class="clear_wrapper">
                    <div class="clear"></div>
                </div>

                <div id = "div_main">

                    <div id = "div_description">
                        <p id = "title_description">Описание :</p>
                        <p id = "description"><span>${director.subscription}</span></p>
                    </div>

                    <div class = "div_mark">
                        <c:choose>
                            <c:when test="${userMark != null}">
                                <span id = "mark_span">Ваша оценка : ${userMark}</span>
                            </c:when>
                            <c:otherwise>
                                <span id = "mark_span">Оцените режиссера :</span>
                                <div id = "div_mark_wr" class = "div_mark_wrapper">
                                    <div class = "select_wrap">
                                        <div class="select">
                                            <select required name="movieMark" id="movieSelect">
                                                <c:forEach var="number" items="${sessionScope.numList}">
                                                    <c:choose>
                                                        <c:when test="${mark == number}">
                                                            <option selected value="${number}">${number}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${number}">${number}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>

                                    <div class = "btn_wrap">
                                        <input type="hidden" id="typeMark" value="director">
                                        <input type="hidden" id="userId" name="userId" value="${user.id}">
                                        <input type="hidden" id="movieId" name="actorId" value="${director.id}">
                                        <input class = "mark_btn button" id="setMarkBtn" type="button" value="Оценить"/>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="clear_wrapper">
                        <div class="clear"></div>
                    </div>

                    <div id = "reviews">
                        <c:forEach var="review" items="${reviews}">
                            <div class = "review">
                                <p class="review_title">${review.writer.username}</p>
                                <p class="review_title">${review.title}</p>
                                <p class="review_content">${review.content}</p>
                                <p class="review_content">Оценка : ${review.mark}</p>
                            </div>

                            <div class = "div_mark">

                                <div class = "div_mark_wrapper">
                                    <div class = "select_wrap">
                                        <div class="select">
                                            <select required name="movieMark" id="${review.id}">
                                                <c:forEach var="number" items="${sessionScope.numList}">
                                                    <c:choose>
                                                        <c:when test="${mark == number}">
                                                            <option selected value="${number}">${number}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${number}">${number}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class = "btn_wrap">
                                        <input type="hidden" id="writerId" name="writerId" value="${review.writer.id}">
                                        <input type="hidden" id="reviewId" name="reviewId" value="${review.id}">
                                        <input class = "mark_btn button" type="button" value="Оценить"/>
                                    </div>
                                </div>
                            </div>
                            <c:if test="${not number.last}">
                                <div class="clear_wrapper">
                                    <div class="clear"></div>
                                </div>
                            </c:if>
                        </c:forEach>
                    </div>

                    <div class="clear_wrapper">
                        <div class="clear"></div>
                    </div>

                    <div id = "my_review">
                        <form id = "form_new_review" method="post" action="${pageContext.request.contextPath}/addReview">

                            <p class ="sender">Отправитель : ${user.username}</p>

                            <input type="text" name="title" id = "my_title"
                                   placeholder="Название рецензии" autocomplete="off">

                            <textarea id = "my_content" rows="1" cols="1" name="content" placeholder="Текст рецензии">
                                ${reviewContent}
                            </textarea>


                            <div>
                                <input type="hidden" name="obj_id" value="${director.id}">
                                <input type="hidden" name="review_type" value="director">
                                <input type="hidden" id="authorId" name="authorId" value="${user.id}">
                                <input type="submit" value="Добавить" class="button">
                            </div>
                        </form>
                    </div>
                </div>

            </div>

        </div>

    </body>

</html>

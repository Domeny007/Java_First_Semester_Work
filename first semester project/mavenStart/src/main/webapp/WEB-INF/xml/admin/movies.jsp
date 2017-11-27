<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<data>

    <c:forEach items="${requestScope.movies}" var="movie">
        <div id = "general_inf">

            <div id = "div_photo">
                <img src="${movie.photo}" alt="${movie.name}"/>
            </div>

            <div id = "div_short_inf">

                <table>

                    <tr>
                        <td>
                            <span>Название фильма : ${movie.name}</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <span>Год выхода : ${movie.getYearStr()}</span>
                        </td>
                    </tr>


                    <tr>
                        <td>
                            <span>Бюджет : ${movie.budget}</span>
                        </td>
                    </tr>


                    <tr>
                        <td>
                            <span>Касса : ${movie.money}</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <span>Оценка : ${movie.mark}</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Добавить : <input type = "checkbox"
                                              name = "${movie.name}"
                                              value = "${movie.id}"
                                              class = "checkbox"/>
                        </td>
                    </tr>

                </table>

            </div>
        </div>

        <div class="clear_wrapper">
            <div class="clear"></div>
        </div>
    </c:forEach>

</data>
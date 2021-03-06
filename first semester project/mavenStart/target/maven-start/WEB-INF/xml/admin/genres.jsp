<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<data>

    <c:forEach items="${requestScope.genres}" var="genre">

        <div id = "general_inf" style = "height:50px">

            <div id = "div_short_inf" style = "padding:0">

                <table>

                    <tr>
                        <td>
                            <span>Название жанра : ${genre.name}</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Добавить : <input type = "checkbox"
                                              name = "${genre.name}"
                                              value = "${genre.id}"
                                              class = "checkbox_add"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Удалить : <input type = "checkbox"
                                             name = "${genre.name}"
                                             value = "${genre.id}"
                                             class = "checkbox_del"/>
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

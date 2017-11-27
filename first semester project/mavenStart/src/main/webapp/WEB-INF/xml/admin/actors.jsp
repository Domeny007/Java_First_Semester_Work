<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<data>

    <c:forEach items="${requestScope.actors}" var="actor">
        <div id = "general_inf">

            <div id = "div_photo">
                <img src="${actor.photo}" alt="${actor.name}"/>
            </div>

            <div id = "div_short_inf">

                <table>

                    <tr>
                        <td>
                            <span>Имя актера : ${actor.name.concat("  ").concat(actor.surname)}</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <span>День рождения : ${actor.birthday}</span>
                        </td>
                    </tr>


                    <tr>
                        <td>
                            <span>Страна рождения : ${actor.getMotherlandName()}</span>
                        </td>
                    </tr>


                    <tr>
                        <td>
                            <span>Оценка актера : ${actor.mark}</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Добавить : <input type = "checkbox"
                                              name = "${actor.name.concat("  ").concat(actor.surname)}"
                                              value = "${actor.id}"
                                              class = "checkbox_add"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Удалить : <input type = "checkbox"
                                              name = "${actor.name.concat("  ").concat(actor.surname)}"
                                              value = "${actor.id}"
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

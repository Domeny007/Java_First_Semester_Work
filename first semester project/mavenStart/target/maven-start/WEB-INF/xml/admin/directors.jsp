<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<data>

    <c:forEach items="${requestScope.directors}" var="director">

        <div id = "general_inf">

            <div id = "div_photo">
                <img src="${director.photo}" alt="${director.name}"/>
            </div>

            <div id = "div_short_inf">

                <table>

                    <tr>
                        <td>
                            <span>Имя режиссера : ${director.name.concat("  ").concat(director.surname)}</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <span>День рождения : ${director.birthday}</span>
                        </td>
                    </tr>


                    <tr>
                        <td>
                            <span>Страна рождения : ${director.getMotherlandName()}</span>
                        </td>
                    </tr>


                    <tr>
                        <td>
                            <span>Оценка режиссера : ${director.mark}</span>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Добавить : <input type = "checkbox"
                                              name = "${director.name.concat("  ").concat(director.surname)}"
                                              value = "${director.id}"
                                              class = "checkbox_add"/>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            Удалить : <input type = "checkbox"
                                             name = "${director.name.concat("  ").concat(director.surname)}"
                                             value = "${director.id}"
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

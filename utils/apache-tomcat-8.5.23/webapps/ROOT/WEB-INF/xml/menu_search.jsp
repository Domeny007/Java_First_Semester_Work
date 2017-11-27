<?xml version="1.0" encoding="UTF-8"?>
<%@page contentType="application/xml" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<data>
    <table id="searchData" style = "display:table">
        <c:forEach items="${requestScope.movies}" var="movie">
            <tr>
                <td>
                    <a class = "searchLink" href="${pageContext.request.contextPath}/movie_content?movie_id=${movie.id}">
                        <span>${movie.name}</span>
                    </a>
                </td>
            </tr>
        </c:forEach>
    </table>
</data>

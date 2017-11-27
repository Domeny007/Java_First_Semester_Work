<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:set var="selCountry" value="${sessionScope.countryId}"/>

<c:set var="gender" value="${sessionScope.gender}"/>

<p>Выберите страну : <select required name="countryId">

        <c:forEach var="country" items="${countries}">
            <c:choose>
                <c:when test="${empty selCountry && country.id == 1}">
                    <option selected value="${country.id}">${country.name}</option>
                </c:when>
                <c:when test="${country.id == selCountry}">
                    <option selected value="${selCountry}">${country.name}</option>
                </c:when>

                <c:otherwise>
                    <option value="${country.id}">${country.name}</option>
                </c:otherwise>
            </c:choose>

        </c:forEach>
</select></p>

<p>Выберите пол : <select required name="gender">
    <c:choose>

        <c:when test = "${gender.equals('мужской')}">
            <t:option
                    selVal="мужской"
                    secVal="женский"
            />

        </c:when>

        <c:otherwise>
            <t:option
                    selVal="женский"
                    secVal="мужской"
            />
        </c:otherwise>

    </c:choose>

</select></p>

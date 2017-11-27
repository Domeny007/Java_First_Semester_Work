<%@ tag import="utils.Const" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@tag description="Default Layout Tag" pageEncoding="UTF-8"%>

<%@attribute name="data"%>

<c:if test="${data != null}">
    <div id = "popup_data">
        <p>
            ${data}
            <% session.removeAttribute(Const.POPUP_DATA); %>
        </p>
    </div>
</c:if>

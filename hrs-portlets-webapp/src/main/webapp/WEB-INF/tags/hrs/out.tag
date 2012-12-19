<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>

<%@ attribute name="prefix"         required="false" %>
<%@ attribute name="data"           required="true" %>
<%@ attribute name="suffix"         required="false" %>

<c:if test="${not empty data || fn:length(data) > 0}">${prefix}</c:if>${data}<c:if test="${not empty data || fn:length(data) > 0}">${suffix}</c:if>
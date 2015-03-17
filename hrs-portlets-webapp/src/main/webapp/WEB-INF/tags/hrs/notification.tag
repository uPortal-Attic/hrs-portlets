<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>

<c:if test="${not empty notification}">
  <div class="fl-widget hrs-notification-wrapper alert alert-info">
      <div class="hrs-notification-content">${notification}</div>
  </div>
</c:if>

<%@ include file="/WEB-INF/jsp/include.jsp"%>

<%@ tag dynamic-attributes="attributes" isELIgnored="false" %>

<%@ attribute name="address"        required="true"  type="edu.wisc.hr.dm.person.Address" %>
<%@ attribute name="messagePrefix"  required="true" %>

<c:if test="${not empty address}">
<div class="contact-info-address">
 <h4 class="contact-info-address-type"><strong><spring:message code="${messagePrefix}Address" /></strong></h4>

  <div class="contact-info-room_mail">
    <hrs:out prefix='<span class="contact-info-room">Room ' data="${fn:escapeXml(address.roomNumber)}" suffix="</span>"/><hrs:out prefix='<span class="contact-info-maildrop">, Mail Drop ID: ' data="${fn:escapeXml(address.mailDrop)}" suffix="</span>"/>
  </div>
  <div class="contact-info-address">
    <hrs:out prefix='<span class="contact-info-location">' data="${fn:escapeXml(address.location)}" suffix=" - </span>"/><span class="contact-info-address1">${fn:escapeXml(address.address1)}</span><hrs:out prefix='<span class="contact-info-address2">, ' data="${fn:escapeXml(address.address2)}" suffix="</span>"/><hrs:out prefix='<span class="contact-info-address3">, ' data="${fn:escapeXml(address.address3)}" suffix="</span>"/>
  </div>
  <div class="contact-info-csz"><hrs:out data="${fn:escapeXml(address.city)}" suffix=", "/><hrs:out data="${fn:escapeXml(address.state)}" suffix=" "/>${fn:escapeXml(address.zip)}</div>

  <div class="contact-info-phone">
    <span><strong><spring:message code="${messagePrefix}PhoneLabel" /></strong></span>
    <span> ${fn:escapeXml(address.primaryPhone)}</span>
  </div>  
</div>
</c:if>
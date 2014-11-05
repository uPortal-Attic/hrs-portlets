<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>

<div id="${n}dl-email-admin" class="dl-email-admin">
    <portlet:renderURL var="newSearchUrl" />

    <c:choose>
      <c:when test="${emailUpdateSuccess == 'true'}">
        <div>Update pending. It may take several hours for the change to be reflected.</div>
      </c:when>
      <c:when test="${emailUpdateSuccess == 'false'}">
        <div>Email update failed, please enter a valid email address.</div>
      </c:when>
    </c:choose>
    <portlet:actionURL var="emailUpdateUrl">
      <portlet:param name="action" value="updateEmail"/>
    </portlet:actionURL>
    <form class="user-email-update" action="${emailUpdateUrl}" method="post" novalidate>
      <fieldset>
        <input type="hidden" name="userEmplId" value="${preferredEmail.emplid}">
        <table class="dl-email-results">
          <tbody>
            <tr>
              <th class="dl-email-update-label">Name:</th>
              <td>${preferredEmail.name}</td>
            </tr>
            <tr>
              <th class="dl-email-update-label">HR EmplID:</th>
              <td>${preferredEmail.emplid}</td>
            </tr>
            <tr>
              <th class="dl-email-update-label">HRS Campus Business Email:</th>
              <td>${contactInformation.email}</td>
            </tr>
            <!--
            preferredEmail.email=${preferredEmail.email}
            contactInformation.email=${contactInformation.email}
            not empty preferredEmail.email=${not empty preferredEmail.email}
            not ut:equalsIgnoreCase(preferredEmail.email, contactInformation.email)=${not ut:equalsIgnoreCase(preferredEmail.email, contactInformation.email)}
             -->
            <c:if test="${not empty preferredEmail.email and not ut:equalsIgnoreCase(preferredEmail.email, contactInformation.email)}">
              <tr>
                <th class="dl-email-update-label">Pending Campus Business Email:</th>
                <td>${preferredEmail.email}</td>
              </tr>
            </c:if>
            <tr>
              <th class="dl-email-update-label"><label for="email1"><strong>New Campus Business Email:</strong></label></th>
              <td><input type="email" name="email1" required="required" /></td>
            </tr>
            <tr>
              <th class="dl-email-update-label"><label for="email2"><strong>Confirm Campus Business Email:</strong></label></th>
              <td><input type="email" name="email2" required="required" /></td>
            </tr>
            <tr>
              <td id="${n}validator_errors"></td>
            </tr>
            <tr>
              <td>
                <input type="submit" value="Update Email Address" />
              </td>
              <td>
                <a href="${newSearchUrl}">New Search</a>
              </td>
            </tr>
          </tbody>
        </table>
      </fieldset>
    </form>
</div>

<script type="text/javascript" language="javascript">
<rs:compressJs>
(function($) {
    $(function() {
        var validatorConfig = {
            errorClass: 'dl-invalid-field',
            messageClass: 'dl-validator-error'
        };
        
        var form = $("#${n}dl-email-admin form.user-email-update").validator(validatorConfig);
        var validator = form.data("validator");
        
        form.submit(function(e) {
            if (!e.isDefaultPrevented()) {
              validator.checkValidity();
  
              var email1 = form.find("input[name='email1']").val();
              var email2 = form.find("input[name='email2']").val();
              
              $.log("Update email: " + email1 + " " + email2);
              
              if (email1 != email2) {
                  validator.invalidate({
                      email2: "Email addresses must match"
                  });
                  e.preventDefault();
              }
              else if (email1 == undefined || $.trim(email1) == "") {
                  validator.invalidate({
                      email1: "An email address must be provided"
                  });
                  e.preventDefault();
              }
            }
        });
    });    
})(dl_v1.jQuery);
</rs:compressJs>
</script>

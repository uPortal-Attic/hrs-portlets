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

<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="time" uri="http://org.apereo.portlet/HRSPortlets/time" %>

<c:set var="n"><portlet:namespace/></c:set>
<c:set var="context" value="${pageContext.request.contextPath}"/>

<link rel="stylesheet" href="<c:url value="/css/sickVacation.css"/>" type="text/css"></link>

<div id="${n}container" class="sickVacation bootstrap-styles">
    <div class="container-fluid sick-vacation-balances">
    <c:choose>
        <c:when test="${error}">
            <div id="error">
                <p class="portlet-msg-error"><c:out value="${error}"/>&nbsp;<spring:message code="leave.lookup.error"/></p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="row">
                <div class="col-sm-10 col-sm-offset-1 alert alert-info"><strong><spring:message code="leave.balances.info.title"/></strong><br /><spring:message code="leave.balances.info.message"/></div>
            </div>
                <div class="table-responsive">
                    <h4><spring:message code="leave.table.title"/></h4>
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th></th>
                                <th><spring:message code="leave.table.title.earned"/></th>
                                <th><spring:message code="leave.table.title.taken"/></th>
                                <th><spring:message code="leave.table.title.balance"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="jobDescription" items="${leaveSummary.jobDescriptions}" varStatus="index">
                                <tr>
                                    <td><c:out value="${jobDescription.jobTitle}"/></td>
                                    <td><c:out value="${time:toHhMm(leaveSummary.leaveEarnedAsMap[jobDescription.jobCode])}"/></td>
                                    <td><c:out value="${time:toHhMm(leaveSummary.leaveTakenAsMap[jobDescription.jobCode])}"/></td>
                                    <td><c:out value="${time:toHhMm(leaveSummary.leaveBalanceAsMap[jobDescription.jobCode])}"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <a href="${prefs['leaveHistoryLink'][0]}" target="_blank" class="btn btn-default">My Leave History</a>
            </div> <!-- container -->
            <%--
            <div id="information">
                <h3><spring:message code="leave.balances.info.title"/></h3>
                <spring:message code="leave.balances.info.message"/>
            </div>
            <div id="balances">
                <h3><spring:message code="leave.table.title"/></h3>
                As of <joda:format value="${currentDate}" pattern="MMM d, YYYY"/>, your balance is as follows:
                <br/>
                <table>
                    <tr>
                        <th/>
                        <th align="right"><h4><spring:message code="leave.table.title.earned"/></h4></th>
                        <th align="right"><h4><spring:message code="leave.table.title.taken"/></h4></th>
                        <th align="right"><h4><spring:message code="leave.table.title.balance"/></h4></th>
                    </tr>
                    <c:forEach var="jobDescription" items="${leaveSummary.jobDescriptions}" varStatus="index">
                        <tr>
                            <td align="right"><c:out value="${jobDescription.jobTitle}"/></td>
                            <td align="right"><c:out value="${time:toHhMm(leaveSummary.leaveEarnedAsMap[jobDescription.jobCode])}"/></td>
                            <td align="right"><c:out value="${time:toHhMm(leaveSummary.leaveTakenAsMap[jobDescription.jobCode])}"/></td>
                            <td align="right"><c:out value="${time:toHhMm(leaveSummary.leaveBalanceAsMap[jobDescription.jobCode])}"/></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
            --%>
        </c:otherwise>
    </c:choose>
    </div>
    <%--
    <div id="psLinks"> <!-- https://hrms.byu.edu/psp/ps/EMPLOYEE/HRMS/c/Y_EMPLOYEE_SELF_SERVICE.Y_SS_SIC_VAC.GBL -->
        <p><a href="${prefs['leaveHistoryLink'][0]}"
              target="_blank">My Leave History</a>
        </p>
    </div>
    --%>
</div>

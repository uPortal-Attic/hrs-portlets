<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<%@ taglib prefix="time" uri="http://org.apereo.portlet/HRSPortlets/time" %>
<c:set var="n"><portlet:namespace/></c:set>

<%-- values are in minutes.  day = 24x60, week = 40x60 --%>
<c:set var="weekMaxTime">2400</c:set>
<c:set var="dayMaxTime">1440</c:set>

<c:set var="cellValue"></c:set>

<portlet:renderURL var="previousPayPeriod"><portlet:param name="payDate"
                                                          value="${previousPayDate}"/></portlet:renderURL>
<portlet:renderURL var="nextPayPeriod"><portlet:param name="payDate" value="${nextPayDate}"/></portlet:renderURL>

<style type="text/css">
    .leave-entry-alerts {
        display: none;
    }

        /*        .leave-entry .leave-entry-input tbody tr td.leave-entry-total-error {
                        color: #900;
                }*/
</style>

<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

<form class="leave-entry-form" name="leave-entry-form" action='<portlet:resourceURL id="updateLeave"/>' method="POST">
    <div class="bootstrap-styles leave-entry">
        <div class="alert alert-info hours-notice">Sick or vacation hours may only be used to reach 40 hours.</div>
        <div class="leave-entry-alerts alert"></div>

        <div class="leave-entry-nav">
            <a href="${previousPayPeriod}" class="pull-left"> <i class="fa fa-arrow-circle-o-left"></i>
                <span class="sr-only">Previous</span>
            </a>
            <span class="leave-entry-period"><spring:message code="leave.reporting.date.start"/>&nbsp;${startDateString} - ${endDateString}</span>
            <a href="${nextPayPeriod}" class="pull-right"> <i class="fa fa-arrow-circle-o-right"></i>
                <span class="sr-only">Next</span>
            </a>
        </div>

        <div class="leave-entry-input">
            <div class="leave-entry-weekend-toggle">
                <label>
                    <input type="checkbox" class="toggleWeekends" name="${n}toggleWeekends" id="${n}toggleWeekends"/>
                    <spring:message code="leave.reporting.show.weekends"/></label>
            </div>

            <c:forEach var="tableDates" items="${listOfTableDates}" varStatus="tableNumber">
                <table class="table table-bordered table-striped leave-entry-table">
                    <thead>
                    <tr>
                        <th>
                            <spring:message code="leave.reporting.type"/>
                        </th>
                        <c:forEach var="date" items="${tableDates}">
                            <th>
                                <spring:message code="leave.reporting.dow.${date.dayOfWeek}"/>
                                <br/>
                                    ${date.dayOfMonth}
                            </th>
                        </c:forEach>
                        <th>
                            <spring:message code="leave.reporting.total"/>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="jobDescription" items="${summary.jobDescriptions}">
                        <tr>
                            <td title='<c:out value="${jobDescription.jobTitle}"/>'>
                                <c:out value="${jobDescription.jobTitle}"/>
                            </td>
                            <c:forEach var="date" items="${tableDates}">
                                <c:set var="jobCodeAndDate">${jobDescription.jobCode}${sep}${date}</c:set>
                                <c:set var="inputName">
                                    ${prefix}${sep}${jobDescription.jobCode}${sep}${date}${sep}${n}
                                </c:set>
                                <c:choose>
                                    <c:when test="${entriesMap[jobCodeAndDate] > dayMaxTime}">
                                        <c:set var="cellErrorClass">danger</c:set>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="cellErrorClass"></c:set>
                                    </c:otherwise>
                                </c:choose>

                                    <c:choose>
                                        <c:when test="${ut:contains(summary.displayOnlyJobCodes,jobDescription.jobCode)}">
                                            <c:set var="cellValue">${time:toHhMm(entriesMap[jobCodeAndDate])}</c:set>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="cellValue">
                                                <label>
                                                <span class="sr-only"><spring:message code="leave.reporting.time.entry.for"/>&nbsp;${date}</span>
                                                <c:choose>
                                                    <c:when test="${blankZeroTimeValues && empty entriesMap[jobCodeAndDate]}">
                                                        <input type="text" class="form-control input-sm" id="${inputName}"
                                                               name="${inputName}" value=""/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="fieldValue">${time:toHhMm(entriesMap[jobCodeAndDate])}</c:set>
                                                        <c:choose>
                                                            <c:when test="${entriesMap[jobCodeAndDate] > dayMaxTime}">
                                                                <c:set var="fieldErrorClass">leave-entry-error</c:set>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <c:set var="fieldErrorClass"></c:set>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <input type="text" class="form-control input-sm ${fieldErrorClass}"
                                                               value="${fieldValue}" name="${inputName}" id="${inputName}"/>
                                                        </label>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:set>
                                        </c:otherwise>
                                    </c:choose>
                                <td class="${cellErrorClass}">${cellValue}</td>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${perTableJobCodeTotals[tableNumber.index][jobDescription.jobCode] > weekMaxTime}">
                                    <c:set var="rowTotalClass">leave-entry-total-error</c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="rowTotalClass"></c:set>
                                </c:otherwise>
                            </c:choose>
                            <td class="${jobDescription.jobTitle}-total ${rowTotalClass}">${time:toHhMm(perTableJobCodeTotals[tableNumber.index][jobDescription.jobCode])}</td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td><spring:message code="leave.reporting.total"/></td>
                        <c:forEach var="date" items="${tableDates}">
                            <c:choose>
                                <c:when test="${dayTotals[date] > dayMaxTime}">
                                    <c:set var="columnTotalClass">leave-entry-total-error</c:set>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="columnTotalClass"></c:set>
                                </c:otherwise>
                            </c:choose>
                            <td class="${columnTotalClass}">${time:toHhMm(dayTotals[date])}</td>
                        </c:forEach>
                        <td class="leave-entry-grand-total">${time:toHhMm(perTableTotals[tableNumber.index])}</td>
                    </tr>
                    </tbody>
                </table>
            </c:forEach>
        </div>

        <div class="leave-entry-balances">
            <table class="table">
                <thead>
                <tr>
                    <td></td>
                    <c:forEach var="jobDescription" items="${summary.jobDescriptions}">
                        <td>${jobDescription.jobTitle}</td>
                    </c:forEach>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><spring:message code="leave.start.balance"/></td>
                    <c:forEach var="jobDescription" items="${summary.jobDescriptions}">
                        <!-- Display values only for leave job codes and not worked time -->
                        <c:choose>
                            <c:when test="${not empty leaveStartBalances[jobDescription.jobCode]}">
                                <td>${time:toHhMm(leaveStartBalances[jobDescription.jobCode])}</td>
                            </c:when>
                            <c:otherwise>
                                <td></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
                <tr class="period-balances">
                    <td><spring:message code="leave.reported.this.period"/></td>
                    <c:forEach var="jobDescription" items="${summary.jobDescriptions}">
                        <td>${time:toHhMm(jobTotals[jobDescription.jobCode])}</td>
                    </c:forEach>
                </tr>
                <tr class="end-balances">
                    <td><spring:message code="leave.end.balance"/></td>
                    <!-- Display values only for leave job codes and not worked time -->
                    <c:forEach var="jobDescription" items="${summary.jobDescriptions}">
                        <c:choose>
                            <c:when test="${not empty leaveEndBalances[jobDescription.jobCode]}">
                                <td>${time:toHhMm(leaveEndBalances[jobDescription.jobCode])}</td>
                            </c:when>
                            <c:otherwise>
                                <td></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </tr>
                </tbody>
            </table>
            <div class="leave-entry-buttons">
                <a href="${leaveHistoryLink}" target="_blank" class="btn btn-default btn-sm" title="My Timesheet" role="button"><spring:message code="leave.history"/></a>
                <a href="${timesheetLink}" target="_blank" class="btn btn-default btn-sm" title="My ACA Hours" role="button"><spring:message code="leave.timesheet"/></a>
                <a href="" class="btn btn-primary btn-sm pull-right leave-entry-submit disabled" title="Submit"
                   role="button"><spring:message code="leave.submit"/></a>
            </div>
        </div>
    </div>
</form>

<script src="${pageContext.request.contextPath}/js/leaveEntry.js" type="text/javascript"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $('form.leave-entry-form').leaveEntryTimeTotalCalculations();
    });
</script>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="/WEB-INF/jsp/include.jsp"%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ taglib prefix="time" uri="http://edu.byu.portlet.hrs/HRSPortlets/time"%>

<link href="//netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.css" rel="stylesheet">

<portlet:actionURL var="refreshUrl">
    <portlet:param name="action" value="refresh"/>
    <portlet:param name="refresh" value="true"/>
</portlet:actionURL>

<div class="time-entry bootstrap-styles">
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger"><c:out value="${errorMessage}"/></div>
    </c:if>
    <div class="time-entry-totals">
        <span class="week-total">Week Total: <span>${time:toHhMm(weekTotal)}</span> </span>
        <span class="pay-period-total">Pay Period Total: <span>${time:toHhMm(payPeriodTotal)}</span></span>
    </div>
    <table class="table table-bordered table-striped">
        <thead>
            <tr>
                <th>Job Description</th>
                <th>Week</th>
                <th>Pay Period</th>
                <th></th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="timePunchEntry" items="${jobEntries}">
            <portlet:actionURL var="punchInUrl">
                <portlet:param  name="action" value="punchIn"/>
                <portlet:param name="jobCode" value="${timePunchEntry.job.jobCode}"/>
            </portlet:actionURL>
            <portlet:actionURL var="punchOutUrl">
                    <portlet:param name="action" value="punchOut"/>
                    <portlet:param name="jobCode" value="${timePunchEntry.job.jobCode}"/>
            </portlet:actionURL>

        <c:choose>
            <c:when test="${timePunchEntry.punchedIn}">
                <tr class="success">
            </c:when>
            <c:otherwise>
                <tr>
            </c:otherwise>
        </c:choose>
                <td title="(${timePunchEntry.job.jobCode}) ${timePunchEntry.job.jobDescription}">${timePunchEntry.job.jobTitle}</td>
                <td>${time:toHhMm(timePunchEntry.weekTimeWorked)}</td>
                <td>${time:toHhMm(timePunchEntry.payPeriodTimeWorked)}</td>
        <c:choose>
            <c:when test="${timePunchEntry.punchedIn}">
                <td>
                    <div class="btn-toggle-grp">
                        <a href="${punchInUrl}" class="btn btn-xs btn-success btn-clocked-in" role="button">Clocked In</a>
                        <a href="${punchOutUrl}" class="btn btn-default btn-xs btn-toggle-right" role="button">Out</a>
                    </div>
                </td>
            </c:when>
            <c:otherwise>
                <td>
                    <div class="btn-toggle-grp">
                        <a href="${punchInUrl}" title="Clock In" class="btn btn-default btn-xs btn-toggle-left clock-in">In</a>
                        <a href="${punchOutUrl}" title="Clocked Out" class="btn btn-xs btn-danger btn-clocked-out">Clocked Out</a>
                    </div>
                </td>
            </c:otherwise>
        </c:choose>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="timesheet-buttons">
        <a href="${timesheetLink}" target="_blank" class="btn btn-default btn-sm" title="My Timesheet" role="button">My Timesheet</a>
        <a href="${timesheetLink2}" target="_blank" class="btn btn-default btn-sm" title="My ACA Hours" role="button">My ACA Hours</a>
        <a href="${refreshUrl}" class="btn btn-default btn-sm pull-right" title="Refresh Totals" role="button"><i class="fa fa-refresh"></i> Refresh Totals</a>
    </div>

    <div class="alert alert-info text-center">Expect a 2-5 minute delay for updating the Timesheet</div>
</div>
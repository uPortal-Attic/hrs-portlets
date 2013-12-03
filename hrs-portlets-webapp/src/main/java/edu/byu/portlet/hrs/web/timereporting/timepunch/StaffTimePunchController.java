package edu.byu.portlet.hrs.web.timereporting.timepunch;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import edu.byu.hr.HrPortletRuntimeException;
import edu.byu.hr.model.timereporting.TimePunchEntry;
import edu.byu.hr.timereporting.service.StaffTimePunchService;
import edu.wisc.web.security.portlet.primaryattr.PrimaryAttributeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

@Controller
@RequestMapping("VIEW")
public class StaffTimePunchController {
    public static final String ACAHOURS_DEEP_LINK = "hoursDeepLink2";
    public static final String TIMESHEET_DEEP_LINK = "timesheetDeepLink";

    private static final String PUNCHED_IN_MESSAGE="time.reporting.punched.in";
    private static final String PUNCHED_OUT_MESSAGE="time.reporting.punched.out";
    private static final String ERROR_MESSAGE_PARAM = "errorMessage";

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StaffTimePunchService service;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(params="action=refresh")
    public void refresh(ActionRequest request, ActionResponse response) {
        log.debug("Refreshing data");
        response.setRenderParameter("refresh", "true");
    }

    // Render phase
    @RequestMapping
    public String viewEmployeeTimeReportingInfo(ModelMap model, PortletRequest request,
                                                @RequestParam(value = "refresh", required = false) boolean refresh,
                                                @RequestParam(value = ERROR_MESSAGE_PARAM, required = false) String errorMessage) {
        final String emplId = PrimaryAttributeUtils.getPrimaryId();

        log.debug("Rendering time punch for employee ID {}, refresh={}", emplId, refresh);
        List<TimePunchEntry> jobEntries = service.getTimePunchEntries(request, emplId, refresh);
        model.addAttribute("jobEntries", jobEntries);
        addSummaryTimeEntries(model, jobEntries, emplId);

        PortletPreferences prefs = request.getPreferences();
        model.addAttribute("timesheetLink", prefs.getValue(TIMESHEET_DEEP_LINK, "http://localhost:8080/specify-timesheetDeepLink-preference"));
        model.addAttribute("timesheetLink2", prefs.getValue(ACAHOURS_DEEP_LINK, "http://localhost:8080/specify-hoursDeepLink2-preference"));

        model.addAttribute("errorMessage", errorMessage);
        return "employeeTimeReporting";
    }

    private void addSummaryTimeEntries(ModelMap model, List<TimePunchEntry> jobEntries, String emplId) {
        int weekTotal = 0;
        int payPeriodTotal = 0;

        for (TimePunchEntry jobEntry : jobEntries) {
            weekTotal += jobEntry.getWeekTimeWorked();
            payPeriodTotal += jobEntry.getPayPeriodTimeWorked();
        }
        model.addAttribute("weekTotal", weekTotal);
        model.addAttribute("payPeriodTotal", payPeriodTotal);
    }

    @ActionMapping(params = "action=punchOut")
    public void punchOut(ActionRequest request, ActionResponse response, @RequestParam("jobCode") final int jobCode) {
        final String emplId = PrimaryAttributeUtils.getPrimaryId();
        log.debug("Punching out employee ID {} for job code {}", emplId, jobCode);

        try {
            service.punchOutTimeClock(request, emplId, jobCode, request.getProperty("REMOTE_ADDR"));
            response.setRenderParameter("message", messageSource.getMessage(PUNCHED_OUT_MESSAGE, null, "You have been punched out", request.getLocale()));
        } catch (HrPortletRuntimeException e) {
            log.debug("Punch Out action failed for employee {} job code {}", emplId, jobCode, e);
            response.setRenderParameter(ERROR_MESSAGE_PARAM, e.getMessage());
        }
    }

    @ActionMapping(params = "action=punchIn")
    public void punchIn(ActionRequest request, ActionResponse response, @RequestParam("jobCode") final int jobCode) {
        final String emplId = PrimaryAttributeUtils.getPrimaryId();
        log.debug("Punching in employee ID {} for job code {}", emplId, jobCode);

        try {
            service.punchInTimeClock(request, emplId, jobCode, request.getProperty("REMOTE_ADDR"));
            response.setRenderParameter("message", messageSource.getMessage(PUNCHED_IN_MESSAGE, null, "You have been punched in", request.getLocale()));
        } catch (HrPortletRuntimeException e) {
            log.debug("Punch in action failed for employee {} job code {}", emplId, jobCode, e);
            response.setRenderParameter(ERROR_MESSAGE_PARAM, e.getMessage());
        }
    }
}

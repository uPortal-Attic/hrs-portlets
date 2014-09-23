package edu.wisc.portlet.hrs.web.benefits;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.portlet.ResourceResponse;

import org.jasig.springframework.security.portlet.authentication.PrimaryAttributeUtils;
import org.jasig.springframework.web.client.PortletResourceProxyResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import edu.wisc.hr.dao.benconf.BenefitConfirmationDao;
import edu.wisc.hr.dm.benconf.BenefitConfirmation;
import edu.wisc.hr.dm.benconf.BenefitConfirmations;
import edu.wisc.portlet.hrs.util.HrsDownloadControllerUtils;

@Controller
@RequestMapping("VIEW")
public class BenefitConfirmationDataController {
    
    private BenefitConfirmationDao benefitConfirmationDao;
    private Set<String> ignoredProxyHeaders;
    
    @Resource(name="ignoredProxyHeaders")
    public void setIgnoredProxyHeaders(Set<String> ignoredProxyHeaders) {
        this.ignoredProxyHeaders = ignoredProxyHeaders;
    }

    @Autowired
    public void setBenefitConfirmationDao(BenefitConfirmationDao benefitConfirmationDao) {
        this.benefitConfirmationDao = benefitConfirmationDao;
    }
    
    @ResourceMapping("benefitConfirmations")
    public String getBenefitConfirmations(ModelMap modelMap) {
        final String emplid = PrimaryAttributeUtils.getPrimaryId();
        final BenefitConfirmations benefitConfirmations = this.benefitConfirmationDao.getBenefitConfirmations(emplid);

        List<BenefitConfirmation> benefitConfirmationsList = benefitConfirmations.getBenefitConfirmations();
        
        modelMap.addAttribute("report", benefitConfirmationsList);
        
        return "reportAttrJsonView";
    }

    @ResourceMapping("benefitConfirmation.pdf")
    public void getBenefitStatement(
            @RequestParam("docId") String docId,
            ResourceResponse response) {

        final String emplid = PrimaryAttributeUtils.getPrimaryId();
        HrsDownloadControllerUtils.setResponseHeaderForDownload(response, "benefit_confirmation", "PDF");
        this.benefitConfirmationDao.getBenefitConfirmation(docId, emplid, new PortletResourceProxyResponse(response, ignoredProxyHeaders));
    }

}

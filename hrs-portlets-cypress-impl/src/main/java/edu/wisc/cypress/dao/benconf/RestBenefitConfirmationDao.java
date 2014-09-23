package edu.wisc.cypress.dao.benconf;

import java.util.List;

import org.jasig.springframework.web.client.ExtendedRestOperations;
import org.jasig.springframework.web.client.ExtendedRestOperations.ProxyResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import com.googlecode.ehcache.annotations.Cacheable;

import edu.wisc.cypress.xdm.benconf.XmlBenefitConfirmation;
import edu.wisc.cypress.xdm.benconf.XmlBenefitConfirmations;
import edu.wisc.hr.dao.benconf.BenefitConfirmationDao;
import edu.wisc.hr.dm.benconf.BenefitConfirmation;
import edu.wisc.hr.dm.benconf.BenefitConfirmations;

@Repository("restBenefitConfirmationDao")
public class RestBenefitConfirmationDao implements BenefitConfirmationDao {

protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    private ExtendedRestOperations restOperations;
    private String statementsUrl;
    private String statementUrl;
    
    @Autowired
    public void setRestTemplate(ExtendedRestOperations restOperations) {
        this.restOperations = restOperations;
    }

    public void setStatementsUrl(String statementsUrl) {
        this.statementsUrl = statementsUrl;
    }
    public void setStatementUrl(String statementUrl) {
        this.statementUrl = statementUrl;
    }

    @Cacheable(cacheName="benefitConfirmations", exceptionCacheName="cypressUnknownExceptionCache")
    @Override
    public BenefitConfirmations getBenefitConfirmations(String emplid) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("HRID", emplid);
        
        final XmlBenefitConfirmations xmlBenefitConfirmations = 
                this.restOperations.getForObject(this.statementsUrl, XmlBenefitConfirmations.class, httpHeaders, emplid);

        return this.getBenefitConfirmations(xmlBenefitConfirmations);
    }
    
    @Override
    public void getBenefitConfirmation(String docId, String emplid, ProxyResponse proxyResponse) {
        final HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("HRID", emplid);
        this.restOperations.proxyRequest(proxyResponse, this.statementUrl, HttpMethod.GET, httpHeaders, docId);
    }
    
    protected BenefitConfirmations getBenefitConfirmations(XmlBenefitConfirmations xmlBenefitConfirmations) {
        final List<XmlBenefitConfirmation> xmlBenefitConfirmationList = xmlBenefitConfirmations.getBenefitConfirmations();
        
        final BenefitConfirmations benefitConfirmations = new BenefitConfirmations();
        final List<BenefitConfirmation> benefitConfirmationList = benefitConfirmations.getBenefitConfirmations();
        
        for (final XmlBenefitConfirmation input : xmlBenefitConfirmationList) {
            final BenefitConfirmation benefitStatement = new BenefitConfirmation();
            
            benefitStatement.setDocId(input.getDocId());
            benefitStatement.setDocType(input.getDocType());
            benefitStatement.setFullTitle(input.getFullTitle());
            benefitStatement.setName(input.getName());
            
            benefitConfirmationList.add(benefitStatement);
        }
        
        return benefitConfirmations;
    }

}

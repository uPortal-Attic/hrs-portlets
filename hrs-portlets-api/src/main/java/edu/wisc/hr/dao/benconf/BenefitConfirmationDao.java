package edu.wisc.hr.dao.benconf;

import org.jasig.springframework.web.client.ExtendedRestOperations.ProxyResponse;

import edu.wisc.hr.dm.benconf.BenefitStatements;

public interface BenefitConfirmationDao {
    /**
     * Get the benefit confirmations for the specified emplid
     */
    public BenefitStatements getBenefitConfirmations(String emplid);

    public void getBenefitConfirmation(String docId, String emplid, ProxyResponse proxyResponse);
}

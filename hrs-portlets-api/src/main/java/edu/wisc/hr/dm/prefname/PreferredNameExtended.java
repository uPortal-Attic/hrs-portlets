package edu.wisc.hr.dm.prefname;

import org.apache.commons.lang.StringUtils;

public class PreferredNameExtended extends PreferredName {
  
    public PreferredNameExtended() {
      super();
    }
  
    public PreferredNameExtended (PreferredName pn, String legalSurName) {
      this.legalLastName = legalSurName;
      this.setFirstName(pn.getFirstName());
      this.setMiddleName(pn.getMiddleName());
      this.setLastName(pn.getLastName());
    }

    private static final long serialVersionUID = 1L;
    
    private String legalFirstName;
    private String legalMiddleName;
    private String legalLastName;
    
    public String getLegalFirstName() {
        return legalFirstName;
    }
    public void setLegalFirstName(String legalFirstName) {
        this.legalFirstName = legalFirstName;
    }
    public String getLegalMiddleName() {
        return legalMiddleName;
    }
    public void setLegalMiddleName(String legalMiddleName) {
        this.legalMiddleName = legalMiddleName;
    }
    public String getLegalLastName() {
        return legalLastName;
    }
    public void setLegalLastName(String legalLastName) {
        this.legalLastName = legalLastName;
    }
    
    public String getLegalName() {
        StringBuilder sb = new StringBuilder();
        
        if(!StringUtils.isBlank(legalFirstName)) {
            sb.append(legalFirstName);
        }
        if(!StringUtils.isBlank(legalMiddleName)) {
            if(sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(legalMiddleName);
        }
        
        if(!StringUtils.isBlank(legalMiddleName)) {
            if(sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(legalLastName);
        }
        
        return sb.toString();
    }
}

package edu.wisc.hr.service;

import edu.wisc.hr.dm.prefname.PreferredName;

public interface PreferredNameService {
	public PreferredName getPreferredName(String pvi);
	
	public String getStatus(PreferredName ldapPn, PreferredName jdbcPn);
	
	public void setPreferredName(String pvi, PreferredName pn);
	
	public void deletePreferredName(String pvi);

}

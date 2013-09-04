package edu.wisc.hr.service;

import edu.wisc.hr.dm.prefname.PreferredName;

public interface PreferredNameService {
	public PreferredName getPreferredName(String pvi);
	
	public String getStatus(PreferredName pn);
	
	public void setPreferredName(PreferredName pn);

}

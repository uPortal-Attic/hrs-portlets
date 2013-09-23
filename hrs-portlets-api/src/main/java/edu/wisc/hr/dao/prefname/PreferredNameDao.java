package edu.wisc.hr.dao.prefname;

import edu.wisc.hr.dm.prefname.PreferredName;

public interface PreferredNameDao {
	public PreferredName getPreferredName(String pvi);
	
	public boolean isPending();
	
	public void setPreferredName(PreferredName pn);

	public void deletePreferredName(String pvi);
}

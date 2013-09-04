package edu.wisc.portlet.hrs.service;

import org.jvnet.jaxb2_commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.wisc.hr.dao.prefname.PreferredNameDao;
import edu.wisc.hr.dm.prefname.PreferredName;
import edu.wisc.hr.service.PreferredNameService;

@Service
public class PreferredNameServiceImpl implements PreferredNameService {

	private PreferredNameDao dao;

	@Autowired
	public void setPreferredNameDao(PreferredNameDao dao) {
		this.dao = dao;
	}
	
	@Override
	public PreferredName getPreferredName(String pvi) {
		return dao.getPreferredName(pvi);
	}

	@Override
	public String getStatus(PreferredName ldapPn) {
		PreferredName jdbcPn = dao.getPreferredName(ldapPn.getPvi());
		if(StringUtils.isEmpty(ldapPn.getFirstName()) 
				&& StringUtils.isEmpty(ldapPn.getMiddleName()) 
				&& (jdbcPn == null 
					|| (StringUtils.isEmpty(jdbcPn.getFirstName()) && StringUtils.isEmpty(jdbcPn.getMiddleName()))
				   )
		) {
			return "(not set)";
		} else {
			
			if(ldapPn.equals(jdbcPn)) {
				return "";
			} else {
				return "(change pending)";
			}
		}
	}

	@Override
	public void setPreferredName(PreferredName pn) {
		dao.setPreferredName(pn);
		
	}

}

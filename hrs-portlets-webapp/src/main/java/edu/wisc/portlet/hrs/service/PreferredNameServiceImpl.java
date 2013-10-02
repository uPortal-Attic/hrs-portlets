package edu.wisc.portlet.hrs.service;

import org.jvnet.jaxb2_commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.PartialCacheKey;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.Property;

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
	@Cacheable(cacheName="prefnameCache",
			keyGenerator = @KeyGenerator (
					name = "ListCacheKeyGenerator",
					properties = @Property( name="includeMethod", value = "false")
					))
	public PreferredName getPreferredName(String pvi) {
		return dao.getPreferredName(pvi);
	}

	@Override
	public String getStatus(PreferredName ldapPn, PreferredName jdbcPn) {
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
			} else if (jdbcPn == null && !StringUtils.isEmpty(ldapPn.getFirstName())) { 
				return "(deletion pending)";
			} else {
				return "(change pending)";
			}
		}
	}

	@Override
	@TriggersRemove(cacheName="prefnameCache",
					keyGenerator = @KeyGenerator (
							name = "ListCacheKeyGenerator",
							properties = @Property( name="includeMethod", value = "false")
							)
					)
	public void setPreferredName(@PartialCacheKey String pvi, PreferredName pn) {
		dao.setPreferredName(pn);
		
	}

	@Override
	@TriggersRemove(cacheName="prefnameCache")
	public void deletePreferredName(String pvi) {
		dao.deletePreferredName(pvi);
		
	}

}

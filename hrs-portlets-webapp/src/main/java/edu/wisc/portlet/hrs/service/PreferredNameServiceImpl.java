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
        if (StringUtils.isEmpty(ldapPn.getFirstName())
            && StringUtils.isEmpty(ldapPn.getMiddleName())
            && StringUtils.isEmpty(ldapPn.getLastName())
            && (jdbcPn == null
              || (StringUtils.isEmpty(jdbcPn.getFirstName()) && StringUtils
              .isEmpty(jdbcPn.getMiddleName()) && StringUtils.isEmpty(jdbcPn.getLastName()))
            )){
            return "(not set)";
        }else if(jdbcPn == null || 
                 (StringUtils.isEmpty(jdbcPn.getFirstName()) && 
                  StringUtils.isEmpty(jdbcPn.getMiddleName()) && 
                  StringUtils.isEmpty(jdbcPn.getLastName()))){ 
                      return "(deletion pending)";
        }else{
           //Logic does not detect a last name change only.
            String ldapPreferredName = new StringBuilder().append(ldapPn.getFirstName()).append(ldapPn.getMiddleName()).toString();
            String jdbcPreferredName = new StringBuilder().append(jdbcPn.getFirstName()).append(jdbcPn.getMiddleName()).toString();
            if(ldapPreferredName.equals(jdbcPreferredName)){ 
                return "";
            }
            else {
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

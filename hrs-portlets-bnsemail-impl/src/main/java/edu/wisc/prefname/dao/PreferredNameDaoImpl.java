package edu.wisc.prefname.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.wisc.hr.dao.prefname.PreferredNameDao;
import edu.wisc.hr.dm.prefname.PreferredName;

@Repository
public class PreferredNameDaoImpl implements PreferredNameDao {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
    private NamedParameterJdbcOperations jdbcTemplate;
    private UpdatePreferredNameProcedure updatePreferredName;
    private DeletePreferredNameUserFunction deletePreferredName;
	
	@Autowired
    public void setJdbcTemplate(@Qualifier("prefname") NamedParameterJdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
	
	@Autowired
    public void setUpdatePreferredName(UpdatePreferredNameProcedure updatePrefNameProc) {
        this.updatePreferredName = updatePrefNameProc;
    }
	
	@Autowired
    public void setDeletePreferredName(DeletePreferredNameUserFunction deletePreferredName) {
        this.deletePreferredName = deletePreferredName;
    }

	@SuppressWarnings("unchecked")
	@Override
	public PreferredName getPreferredName(String pvi) {
		final Map<String, String> args = new LinkedHashMap<String, String>();
        args.put("pvi", pvi);
        
        List<PreferredName> query = jdbcTemplate.query("select first_name, middle_name, pvi from msnprefname.msn_preferred_name where pvi = :pvi", 
                args, 
                PreferredNameRowMapper.INTANCE);
        
        return DataAccessUtils.singleResult(query);
	}

	@Override
	public boolean isPending() {
		// TODO check if what is in the db is the same as what is currently in LDAP
		return false;
	}

	@Override
	@Transactional
	public void setPreferredName(PreferredName pn) {
		updatePreferredName.updatePrefferedName(pn);
	}
	
	@Override
	@Transactional
	public void deletePreferredName(String pvi) {
		deletePreferredName.deletePreferredNameUser(pvi);
		
	}

}

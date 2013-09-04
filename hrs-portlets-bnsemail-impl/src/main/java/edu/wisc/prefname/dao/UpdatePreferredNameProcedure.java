package edu.wisc.prefname.dao;

import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

import edu.wisc.hr.dm.prefname.PreferredName;

public class UpdatePreferredNameProcedure extends StoredProcedure {

    public UpdatePreferredNameProcedure(DataSource dataSource) {
        super(dataSource, "msnprefname.LOAD_PREFERRED_NAME");
        
        this.declareParameter(new SqlParameter("pvi", Types.VARCHAR));
        this.declareParameter(new SqlParameter("firstName", Types.VARCHAR));
        this.declareParameter(new SqlParameter("middleName", Types.VARCHAR));
        
        this.compile();
    }

    public void updatePrefferedName(PreferredName pn) {
        final Map<String, String> args = new LinkedHashMap<String, String>();
        args.put("pvi", pn.getPvi());
        args.put("firstName", pn.getFirstName());
        args.put("middleName", pn.getMiddleName());
        
        this.execute(args);
    }

}

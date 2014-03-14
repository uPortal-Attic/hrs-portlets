package edu.wisc.prefname.dao;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

public class DeletePreferredNameUserFunction extends StoredProcedure {
    private static final String SQL = "msnprefname.DELETE_PREFERRED_NAME";
    private static final String PVI = "P_PVI_I";
    protected static final String FUNC_RETURN = "returnname";
    private static final String SUCCESS = "Success";
    
    public DeletePreferredNameUserFunction(DataSource dataSource) {
        setDataSource(dataSource);
        setFunction(true);
        setSql(SQL);
        //Note that the return statement must be declared first
        declareParameter(new SqlOutParameter(FUNC_RETURN, Types.VARCHAR));
        declareParameter(new SqlParameter(PVI, Types.VARCHAR));
        
        this.compile();
    }

    public boolean deletePreferredNameUser(String pvi) {
        final Map<String, String> args = new HashMap<String, String>();
        args.put(PVI, pvi);
        Map<String, Object> results = execute(args);
        String res = (String)results.get(FUNC_RETURN);
        return SUCCESS.equalsIgnoreCase(res);
    }
    
    
}


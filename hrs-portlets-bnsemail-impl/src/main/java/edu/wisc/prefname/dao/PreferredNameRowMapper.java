package edu.wisc.prefname.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import edu.wisc.hr.dm.prefname.PreferredName;

public class PreferredNameRowMapper  implements RowMapper<PreferredName> {
    public static final PreferredNameRowMapper INTANCE = new PreferredNameRowMapper();
    
    @Override
    public PreferredName mapRow(ResultSet rs, int rowNum) throws SQLException {
        final PreferredName preferredName = new PreferredName();
        
        preferredName.setFirstName(rs.getString("first_name"));
        preferredName.setMiddleName(rs.getString("middle_name"));
        preferredName.setLastName(rs.getString("last_name"));
        preferredName.setPvi(rs.getString("pvi"));
        
        return preferredName;
    }
}

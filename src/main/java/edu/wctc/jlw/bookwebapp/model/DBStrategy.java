package edu.wctc.jlw.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jwardell
 */
public interface DBStrategy {

    public abstract void openConnection(String driverClass, String url,
            String userName, String password)
            throws ClassNotFoundException, SQLException;

    public abstract void closeConnection() throws SQLException;

    public abstract List<Map<String, Object>> findAllRecords(String tableName,
            int maxRecords) throws SQLException;

    public int deleteRecordById(String tableName, String pkColName, Object value) throws SQLException;

    public int updateRecordById(String tableName, List<String> colNames,
            List<Object> colValues, String pkColName, Object value) throws SQLException;

    public boolean insertNewRecord(String tableName, List<String> columnNames, 
            List<Object> columnValues) throws SQLException;
    
    public Map<String, Object> findById(String tableName, String primaryKeyFieldName,
            Object primaryKeyValue) throws SQLException;
    
//    public void deleteRecordById(String deleteId, String tableName) throws SQLException;
}

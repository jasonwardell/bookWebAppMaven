package edu.wctc.jlw.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jwardell
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException;
    
    public DBStrategy getDb();
    
    public void setDb(DBStrategy db);
}

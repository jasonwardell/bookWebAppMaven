package edu.wctc.jlw.bookwebapp.model;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author jwardell
 */
public interface AuthorDaoStrategy {

    List<Author> getAuthorList() throws ClassNotFoundException, SQLException;
    
    public boolean editAuthor(Integer authorId, String authorName) throws ClassNotFoundException, SQLException;
    
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException;
    
    public Author getAuthorById(Integer authorId) throws ClassNotFoundException, SQLException;

    public boolean addAuthor(Integer id,String name) throws SQLException;
    
    public DBStrategy getDb();
    
    public void setDb(DBStrategy db);
    
    public void initDao(String driver, String url, String user, String pwd);

    public String getDriver();

    public void setDriver(String driver);

    public String getUrl();

    public void setUrl(String url);

    public String getUser();

    public void setUser(String user);

    public String getPwd();

    public void setPwd(String pwd);
}

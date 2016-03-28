package edu.wctc.jlw.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author jwardell
 */
@Dependent
public class AuthorDao implements AuthorDaoStrategy, Serializable {

    @Inject
    private DBStrategy db;
    private DataSource ds;
    private String driver;
    private String url;
    private String user;
    private String pwd;

    public DataSource getDs() {
        return ds;
    }

    public void setDs(DataSource ds) {
        this.ds = ds;
    }

    
    @Override
    public void initDao(DataSource ds) throws SQLException {
        setDs(ds);
    }
    
    @Override
    public DBStrategy getDb() {
        return db;
    }

    public AuthorDao() {
    }

    @Override
    public void setDb(DBStrategy db) {
        this.db = db;
    }

    @Override
    public String getDriver() {
        return driver;
    }

    @Override
    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUser() {
        return user;
    }

    @Override
    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String getPwd() {
        return pwd;
    }

    @Override
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Override
    public void initDao(String driver, String url, String user, String pwd) {
        setDriver(driver);
        setUrl(url);
        setUser(user);
        setPwd(pwd);
    }

    @Override
    public boolean addAuthor(Integer id,String name) throws SQLException {
        boolean result = false;
        try {
                if(ds == null) {
             db.openConnection(driver, url, user, pwd);
        } else {
            db.openConnection(ds);
        }
            result = db.insertNewRecord("author", Arrays.asList("author_name","date_added"), 
                                      Arrays.asList(name,new Date()));
            return result;
        } catch (SQLException sqlE) {
            throw sqlE;
        } catch (Exception e) {
            throw new SQLException(e.getMessage());
        } finally {
            db.closeConnection();
        }
    }

    @Override
    public Author getAuthorById(Integer authorId) throws SQLException, ClassNotFoundException {
        if(ds == null) {
             db.openConnection(driver, url, user, pwd);
        } else {
            db.openConnection(ds);
        }
        
        Map<String,Object> rawRec = db.findById("author", "author_id", authorId);
        Author author = new Author();
        author.setAuthorId((Integer)rawRec.get("author_id"));
        author.setAuthorName(rawRec.get("author_name").toString());
        author.setDateAdded((Date)rawRec.get("date_added"));
        
        return author;
    }

    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
            if(ds == null) {
             db.openConnection(driver, url, user, pwd);
        } else {
            db.openConnection(ds);
        }

        int result = db.deleteRecordById("author", "author_id", id);
        db.closeConnection();
        return result;
    }

    @Override
    public boolean editAuthor(Integer authorId, String authorName) throws ClassNotFoundException, SQLException {
           if(ds == null) {
             db.openConnection(driver, url, user, pwd);
        } else {
            db.openConnection(ds);
        }
        boolean result = false;

        if (authorId == null || authorId.equals(0)) {

            result = db.insertNewRecord("author", Arrays.asList("author_name", "date_added"),
                    Arrays.asList(authorName, new Date()));
        } else {

            int recsUpdated = db.updateRecordById("author", Arrays.asList("author_name"),
                    Arrays.asList(authorName),
                    "author_id", authorId);
            if (recsUpdated > 0) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
          if(ds == null) {
             db.openConnection(driver, url, user, pwd);
        } else {
            db.openConnection(ds);
        }

        List<Map<String, Object>> rawData
                = db.findAllRecords("author", 0);
        List<Author> authors = new ArrayList<>();

        for (Map rec : rawData) {
            Author author = new Author();
            Integer id = new Integer(rec.get("author_id").toString());
            author.setAuthorId(id);
            String name = rec.get("author_name") == null ? "" : rec.get("author_name").toString();
            author.setAuthorName(name);
            Date date = rec.get("date_added") == null ? null : (Date) rec.get("date_added");
            author.setDateAdded(date);
            authors.add(author);
        }

        db.closeConnection();
        return authors;
    }

//    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        AuthorDaoStrategy dao = new AuthorDao();
//        
//        List<Author> authors = dao.getAuthorList();
//        System.out.println(authors);
//    }
}

package edu.wctc.jlw.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author jwardell
 */
@SessionScoped
public class AuthorDao implements AuthorDaoStrategy, Serializable {
    @Inject
    private DBStrategy db;

    public DBStrategy getDb() {
        return db;
    }

    public AuthorDao() {
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }
    
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/book";
    private final String USER = "root";
    private final String PWD = "admin";
    
    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER, URL, USER, PWD);
        int result = db.deleteRecordById("author", "author_id", id);
        db.closeConnection();
        return result;
    }
    
    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        db.openConnection(DRIVER, URL, USER, PWD);
        
        List<Map<String,Object>> rawData =
                db.findAllRecords("author", 0);
        List<Author> authors = new ArrayList<>();
        
        for(Map rec : rawData) {
            Author author = new Author();
            Integer id = new Integer(rec.get("author_id").toString());
            author.setAuthorId(id);
            String name = rec.get("author_name") == null ? "" : rec.get("author_name").toString();
            author.setAuthorName(name);
            Date date = rec.get("date_added") == null ? null : (Date)rec.get("date_added");
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

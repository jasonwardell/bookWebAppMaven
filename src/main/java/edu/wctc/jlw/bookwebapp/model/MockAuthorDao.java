package edu.wctc.jlw.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static javafx.css.StyleOrigin.USER;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Alternative;
import static javax.servlet.SessionTrackingMode.URL;

/**
 *
 * @author jwardell
 */
@Alternative
@Dependent
public class MockAuthorDao implements AuthorDaoStrategy, Serializable {

    private DBStrategy db;

    private List<Author> authors = new ArrayList<>();

    public MockAuthorDao() {
    }

    Author aL1 = new Author();

    public DBStrategy getDb() {
        return db;
    }

    public void setDb(DBStrategy db) {
        this.db = db;
    }
    Author aL2 = new Author();
    Author aL3 = new Author();

    public List getAuthors() {
        aL1.setAuthorName("James");
        aL1.setAuthorId(0);
        aL1.setDateAdded(new Date(2 / 7 / 16));
        aL2.setAuthorName("George");
        aL2.setAuthorId(1);
        aL2.setDateAdded(new Date(2 / 7 / 16));
        aL3.setAuthorName("Susan");
        aL3.setAuthorId(2);
        aL3.setDateAdded(new Date(2 / 7 / 16));

        authors.add(aL1);
        authors.add(aL2);
        authors.add(aL3);

        return authors;
    }

    @Override
    public List<Author> getAuthorList() throws ClassNotFoundException, SQLException {
        return authors;
    }

    @Override
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
        return 1;
    }

    @Override
    public void initDao(String driver, String url, String user, String pwd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getDriver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDriver(String driver) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUrl() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUrl(String url) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setUser(String user) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getPwd() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setPwd(String pwd) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

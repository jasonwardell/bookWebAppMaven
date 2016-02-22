package edu.wctc.jlw.bookwebapp.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static javafx.css.StyleOrigin.USER;
import static javax.servlet.SessionTrackingMode.URL;

/**
 *
 * @author jwardell
 */
public class MockAuthorDao implements AuthorDaoStrategy{
 private List<Author> authors = new ArrayList<>();

Author aL1 = new Author();
Author aL2 = new Author();
Author aL3 = new Author();
 
    public List getAuthors() {
        aL1.setAuthorName("James");
        aL1.setAuthorId(0);
        aL1.setDateAdded(new Date(2/7/16));
        aL2.setAuthorName("George");
        aL2.setAuthorId(1);
        aL2.setDateAdded(new Date(2/7/16));
        aL3.setAuthorName("Susan");
        aL3.setAuthorId(2);
        aL3.setDateAdded(new Date(2/7/16));
        
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
    
}

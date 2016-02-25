package edu.wctc.jlw.bookwebapp.model;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author jwardell
 */
@SessionScoped
public class AuthorService implements Serializable {
    @Inject
    private AuthorDaoStrategy dao;

    public AuthorService() {     
    }
    
    public AuthorDaoStrategy getDao() {
        return dao;
    }

    public void setDao(AuthorDaoStrategy dao) {
        this.dao = dao;
    }
    
    public List<Author> getAuthorList() 
            throws ClassNotFoundException, SQLException {
        
        return dao.getAuthorList();
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        AuthorService srv = new AuthorService();
        List<Author> authors = srv.getAuthorList();
        System.out.println(authors);
    }
    
    public int deleteAuthorById(Object id) throws ClassNotFoundException, SQLException {
        return dao.deleteAuthorById(id);
    }
}

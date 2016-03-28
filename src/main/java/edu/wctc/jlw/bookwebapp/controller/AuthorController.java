package edu.wctc.jlw.bookwebapp.controller;

import edu.wctc.jlw.bookwebapp.model.Author;
import edu.wctc.jlw.bookwebapp.model.AuthorService;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author jwardell
 */
@WebServlet(name = "AuthorController", urlPatterns = {"/AuthorController"})
public class AuthorController extends HttpServlet {

    private static final String AUTH_LIST = "authorList";
    private static final String ERR = "errorMsg";
    private static final String AUTHOR_JSP = "/authorInfo.jsp";
    private static final String AUTH_LIST_ACTION = "authorList";
    private static final String ADD_EDIT_JSP = "/addEdit.jsp";
    private static final String ADD_EDIT_DELETE_ACTION = "addEditDelete";
    private static final String ACTION = "action";
    private static final String ADD = "add";
    private static final String EDIT = "edit";
    private static final String DELETE = "delete";
    private static final String SAVE = "Save";

    private String driverClass;
    private String url;
    private String username;
    private String password;
    private int count;
    private String dbJndiName;

    @Inject
    private AuthorService authorService;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter(ACTION);
        String destination = AUTH_LIST;

        try {
            configDbConnection();
            String subAction = request.getParameter("submit");

            if (action.equals(AUTH_LIST_ACTION)) {
                this.refreshList(request, authorService);

                RequestDispatcher view
                        = request.getRequestDispatcher(AUTHOR_JSP);
                view.forward(request, response);

            } else if (action.equals(ADD_EDIT_DELETE_ACTION)) {
                if (subAction.equals(ADD)) {
                    count = 1;
                    RequestDispatcher view
                            = request.getRequestDispatcher(ADD_EDIT_JSP);
                    view.forward(request, response);

                } else if (subAction.equals(EDIT)) {
                    String[] authorIds = request.getParameterValues("authorId");
                    String authorId = authorIds[0];
                    Author author = authorService.getAuthorById(authorId);
                    request.setAttribute("author", author);

                    RequestDispatcher view
                            = request.getRequestDispatcher(ADD_EDIT_JSP);
                    view.forward(request, response);

                } else if (subAction.equals(DELETE)) {
                    String[] authorIds = request.getParameterValues("authorId");
                    for (String id : authorIds) {
                        authorService.deleteAuthorById(id);
                    }
                    this.refreshList(request, authorService);

                    RequestDispatcher view
                            = request.getRequestDispatcher(AUTHOR_JSP);
                    view.forward(request, response);

                }

            }

            if (action.equals(SAVE)) {
                if (count == 1) {
                    String authName = request.getParameter("authorName");
                    authorService.addAuthor(authName);
                    count = 0;
                } else {

                    String authorName = request.getParameter("authorName");
                    String authorId = request.getParameter("authorId");
                    authorService.saveOrUpdateAuthor(authorId, authorName);
                }
            }

            this.refreshList(request, authorService);
            RequestDispatcher view
                    = request.getRequestDispatcher(AUTHOR_JSP);
            view.forward(request, response);

        } catch (Exception e) {
            request.setAttribute(ERR, e.getMessage());
        }

        RequestDispatcher dispatcher
                = getServletContext().getRequestDispatcher(response.encodeURL(destination));
        dispatcher.forward(request, response);

    }

    private void refreshList(HttpServletRequest request, AuthorService authorService) throws Exception {
        List<Author> authors = authorService.getAuthorList();
        request.setAttribute(AUTH_LIST, authors);
    }

    private void configDbConnection() throws SQLException, NamingException {
           if(dbJndiName == null) {
            authorService.getDao().initDao(driverClass, url, username, password);   
        } else {
            /*
             Lookup the JNDI name of the Glassfish connection pool
             and then use it to create a DataSource object.
             */
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(dbJndiName);
            authorService.getDao().initDao(ds);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    public void init() throws ServletException {
//        driverClass = getServletContext().getInitParameter("db.driver.class");
//        url = getServletContext().getInitParameter("db.url");
//        username = getServletContext().getInitParameter("db.username");
//        password = getServletContext().getInitParameter("db.password");
        dbJndiName = getServletContext().getInitParameter("db.jndi.name");
    }
}

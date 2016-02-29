package edu.wctc.jlw.bookwebapp.controller;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
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
import edu.wctc.jlw.bookwebapp.model.MockAuthorDao;
import java.sql.SQLException;
import java.util.Arrays;
import javax.inject.Inject;

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
    private static final String SAVE = "save";

    private String driverClass;
    private String url;
    private String username;
    private String password;

    String page = AUTHOR_JSP;
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

        configDbConnection();
        String action = request.getParameter(ACTION);

        try {
            String subAction = request.getParameter("submit");

            if (action.equals(AUTH_LIST_ACTION)) {
                List responseMsg = authorService.getAuthorList();
                request.setAttribute(AUTH_LIST, responseMsg);
                RequestDispatcher view
                        = request.getRequestDispatcher(AUTHOR_JSP);
                view.forward(request, response);

            } else if (action.equals(ADD_EDIT_DELETE_ACTION)) {
                if (subAction.equals(ADD)) {

                    RequestDispatcher view
                            = request.getRequestDispatcher(ADD_EDIT_JSP);
                    view.forward(request, response);

                } else if (subAction.equals(EDIT)) {
                    RequestDispatcher view
                            = request.getRequestDispatcher(ADD_EDIT_JSP);
                    view.forward(request, response);

                    String[] authorIds = request.getParameterValues("authorId");
                    String[] authorNames = request.getParameterValues("authorName");
                    String authorName = Arrays.toString(authorNames);
                    for (String authorId : authorIds) {
                        authorService.saveOrUpdateAuthor(authorId, authorName);
                    }
//
//                        String authorId = authorIds[0];
//                            Author author = authorService.getAuthorById(authorId);
//                            request.setAttribute("author", author);
                    

                } else if (subAction.equals(DELETE)) {
                    String[] authorIds = request.getParameterValues("authorId");
                    for (String id : authorIds) {
                        authorService.deleteAuthorById(id);
                    }

                }

            }
            if (action.equals(SAVE)) {

                String authorName = request.getParameter("authorName");
                String authorId = request.getParameter("authorId");
                authorService.saveOrUpdateAuthor(authorId, authorName);
                List responseMsg = authorService.getAuthorList();
                request.setAttribute(AUTH_LIST, responseMsg);
                RequestDispatcher view
                        = request.getRequestDispatcher(AUTHOR_JSP);
                view.forward(request, response);
            }

            List responseMsg = authorService.getAuthorList();
            request.setAttribute(AUTH_LIST, responseMsg);
            RequestDispatcher view
                    = request.getRequestDispatcher(AUTHOR_JSP);
            view.forward(request, response);

        } catch (Exception e) {
            request.setAttribute(ERR, e.getMessage());
        }

    }

    private void configDbConnection() {
        authorService.getDao().initDao(driverClass, url, username, password);
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
        driverClass = getServletContext().getInitParameter("db.driver.class");
        url = getServletContext().getInitParameter("db.url");
        username = getServletContext().getInitParameter("db.username");
        password = getServletContext().getInitParameter("db.password");
    }
}

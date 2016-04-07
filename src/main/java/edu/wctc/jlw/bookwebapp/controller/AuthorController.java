package edu.wctc.jlw.bookwebapp.controller;

import edu.wctc.jlw.bookwebapp.entity.Author;
import edu.wctc.jlw.bookwebapp.service.AuthorService;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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

    private int count;

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
        Author author = null;

        try {
            String subAction = request.getParameter("submit");

            if (action.equals(AUTH_LIST_ACTION)) {
                this.refreshList(request);

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
//                    Author author = authorService.find(new Integer(authorId));
                    author = authorService.findByIdAndFetchBooksEagerly(authorId);
                    request.setAttribute("author", author);

                    RequestDispatcher view
                            = request.getRequestDispatcher(ADD_EDIT_JSP);
                    view.forward(request, response);

                } else if (subAction.equals(DELETE)) {
                    String[] authorIds = request.getParameterValues("authorId");
                    for (String id : authorIds) {

                        //// BIG CHANGE DUE TO SPRING JPA DUE TO LAZY LOADING OF BOOKS //////
                        author = authorService.findByIdAndFetchBooksEagerly(id);
                        if (author == null) {
                            author = authorService.findById(id);
                            author.setBookCollection(new LinkedHashSet<>());
                        }
                        ////////////////////////////////////////

                        authorService.remove(author);
                    }
                    this.refreshList(request);

                    RequestDispatcher view
                            = request.getRequestDispatcher(AUTHOR_JSP);
                    view.forward(request, response);

                }

            }

            if (action.equals(SAVE)) {
                String authorName = request.getParameter("authorName");
                String authorId = request.getParameter("authorId");
                if (count == 1) {
                    
                    author = new Author(0);
                    author.setAuthorName(authorName);
                    author.setDateAdded(new Date());
                    count = 0;
                } else {
                    author = authorService.findByIdAndFetchBooksEagerly(authorId);
                    if (author == null) {
                        author = authorService.findById(authorId);
                        author.setBookCollection(new LinkedHashSet<>());
                    }
                    ////////////////////////////////////////    
                    author.setAuthorName(authorName);
                }
                authorService.edit(author);
            }

            this.refreshList(request);
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

    private void refreshList(HttpServletRequest request) throws Exception {
        List<Author> authors = authorService.findAll();
        request.setAttribute(AUTH_LIST, authors);
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
     * Called after the constructor is called by the container. This is the
     * correct place to do one-time initialization.
     *
     * @throws ServletException
     */
    @Override
    public void init() throws ServletException {
        // Ask Spring for object to inject
        ServletContext sctx = getServletContext();
        WebApplicationContext ctx
                = WebApplicationContextUtils.getWebApplicationContext(sctx);
        authorService = (AuthorService) ctx.getBean("authorService");

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

}

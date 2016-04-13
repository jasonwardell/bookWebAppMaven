<%-- 
    Document   : authorInfo
    Created on : Feb 7, 2016, 10:43:10 PM
    Author     : jwardell
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">    
        <link href="bookWebApp.css" rel="stylesheet" type="text/css"/>
        <title>Author Information</title>
    </head>
    <body>
        <header>
            <h1 id="title">
                Book Authors
            </h1> 
        </header>
        <ul>
            <li><a href="index.html">Home</a></li>
            <li><a href="AuthorController?action=authorList">View all Authors</a></li>
           <li><a href="BookController?action=list">View All Books</a></li>
        </ul>
        <p>
        <form method="POST" action="AuthorController?action=addEditDelete">
            <table id="authors" BORDER="3" CELLSPACING="1" CELLPADDING="1" >
                <tr>
                    <th><input id="add" class="center" type="submit" name="submit" value="add" style="background-color:green; width:100%" /></th>
                    <th><input id="edit" class="center" type="submit" name="submit" value="edit" style="background-color:yellow; width:100%" /></th>
                    <th><input id="delete" class="center" type="submit" name="submit" value="delete" style="background-color:red; width:100%" /></th>
                </tr>
                <tr>
                    <th class="center">Author ID</th>
                    <th class="center">Author Name</th>
                    <th class="center">Date Added</th>
                </tr>
                <c:forEach var="author" items="${authorList}">
                    <tr>
                        <td>
                            <input type="checkbox" name="authorId" value="${author.authorId}">
                            <%--<c:out value="${author.authorId}" />--%>
                        </td>
                        <td>
                            <c:out value="${author.authorName}" />
                        </td>
                        <td>
                           <%-- <fmt:formatDate value="${author.dateAdded}" pattern="MMM/dd/yy"/> --%>
                            <fmt:formatDate pattern="M/d/yyyy" value="${author.dateAdded}"></fmt:formatDate>

                        </td>
                    </tr>
                </c:forEach>
                <tr>
                    <th><input id="add" class="center" type="submit" name="submit" value="add" style="background-color:green; width:100%" /></th>
                    <th><input id="edit" class="center" type="submit" name="submit" value="edit" style="background-color:yellow; width:100%" /></th>
                    <th><input id="delete" class="center" type="submit" name="submit" value="delete" style="background-color:red; width:100%" /></th>
                </tr>
            </table>
        </form>
    </p>
</body>
<footer>
    <p>
        jWardell@my.wctc.edu
    </p>
    <p>
        Copyright &copy; 2016
    </p>
</footer>
</html>

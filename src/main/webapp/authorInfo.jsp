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
            <li><a href="AuthorController?action=AuthorController">View all Authors</a></li>
             <li><a href="AuthorController?action=AuthorController">View all Books</a></li>
        </ul>
        <p>
        <table id="authors" BORDER="3" CELLSPACING="1" CELLPADDING="1" >
            <tr>
                <th>Author ID</th>
                <th>Author Name</th>
                <th>Date Added</th>
            </tr>
            <c:forEach var="author" items="${authorList}">
                <tr>
                    <td>
                        <c:out value="${author.authorId}" />
                    </td>
                    <td>
                        <c:out value="${author.authorName}" />
                    </td>
                    <td>
                        <fmt:formatDate value="${author.dateAdded}" pattern="MMM/dd/yy"/>

                    </td>
                </tr>
            </c:forEach>
        </table>

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

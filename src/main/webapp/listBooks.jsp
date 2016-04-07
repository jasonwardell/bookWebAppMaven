<%-- 
    Document   : listAuthors
    Created on : Sep 21, 2015, 9:36:05 PM
    Author     : jlombardo
    Purpose    : display list of author records and (in the future) provide
                 a way to add/edit/delete records
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book List</title>
    </head>
    <body>
        <h1>Book List</h1>

        <form method="POST" action="BookController?action=addEditDelete">
            <input type="submit" value="Add/Edit" name="submit" />&nbsp;
            <input type="submit" value="Delete" name="submit" />
            <br><br>
            <table style="width: 75%;" border="1" cellspacing="0" cellpadding="4">
                <tr style="background-color: black;color:white;">
                    <th align="left" class="tableHead">ID</th>
                    <th align="left" class="tableHead">Title</th>
                    <th align="right" class="tableHead">ISBN</th>
                    <th align="right" class="tableHead">Author</th>
                </tr>
                <c:forEach var="b" items="${books}" varStatus="rowCount">
                    <c:choose>
                        <c:when test="${rowCount.count % 2 == 0}">
                            <tr style="background-color: white;">
                            </c:when>
                            <c:otherwise>
                            <tr style="background-color: #ccffff;">
                            </c:otherwise>
                        </c:choose>
                        <td><input type="checkbox" name="bookId" value="${b.bookId}" /></td>
                        <td align="left">${b.title}</td>
                        <td align="left">${b.isbn}</td>
                        <td align="left">
                            <c:choose>
                                <c:when test="${not empty b.authorId}">
                                    ${b.authorId.authorName}
                                </c:when>
                                <c:otherwise>
                                    None
                                </c:otherwise>
                            </c:choose>
                    </td>
                    </tr>
                </c:forEach>
            </table>
            <br>
            <input type="submit" value="Add/Edit" name="submit" />&nbsp;
            <input type="submit" value="Delete" name="submit" />
        </form>
        <p>Click here to go back to Home Page <a href="index.html">Home</a></p>

        <c:if test="${errMsg != null}">
            <p style="font-weight: bold;color: red;width:500px;">Sorry, data could not be retrieved:<br>
                ${errMsg}</p>
            </c:if>
    </body>
</html>

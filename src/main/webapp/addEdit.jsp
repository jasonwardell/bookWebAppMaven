<%-- 
    Document   : addEdit
    Created on : Feb 24, 2016, 6:37:52 PM
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
        <title>Author List</title>
    </head>
    <body>
        <header>
            <h1 class="center" >Author List</h1>
        </header>
        <div class="center" align="center">
            <form method="POST" action="AuthorController" class="center" >
                <table class="center" width="500" border="1" cellspacing="0" cellpadding="4">

                    <c:choose>
                        <c:when test="${not empty author}">
                            <tr class="center">
                                <td>ID</td>
                                <td><input type="text" value="${author.authorId}" id="authorId" name="authorId" readonly/></td>
                            </tr>         
                        </c:when>
                    </c:choose>

                    <tr class="center">
                        <td style="background-color: lightblue">Name</td>
                        <td><input type="text" value="${author.authorName}" id="authorName" name="authorName" /></td>
                    </tr>

                    <c:choose>
                        <c:when test="${not empty author}">
                            <tr class="center">
                                <td>Date Added</td>
                                <td><input type="text" value="${author.dateAdded}" name="dateAdded" readonly /></td>
                            </tr>         
                        </c:when>
                    </c:choose>

                        <tr class="center">
                        <input type="submit" value="Cancel" name="action" />&nbsp;
                        <input type="submit" value="Save" name="action" />
                        </tr>

                </table>
            </form>
        </div>
    </body>
</html>

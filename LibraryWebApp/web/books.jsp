<%@page import="java.util.List"%>
<%@page import="za.ac.tut.entity.Book"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Library Inventory</title></head>
<body>
<h2>Library Book Inventory</h2>

<!-- Form to add a new book -->
<form action="books" method="post">
    <input type="hidden" name="action" value="add" />
    Title: <input name="title" required />
    Author: <input name="author" required />
    <button type="submit">Add Book</button>
</form>

<hr/>
<!-- Table to display the list of books -->
<table border="1">
    <tr><th>Title</th><th>Author</th><th>Status</th><th>IssueDate</th><th>ReturnDate</th><th>Action</th></tr>
    <%
        List<Book> books = (List<Book>) request.getAttribute("books");
        
        for (Book book : books) { 
    %>
        <tr>
            <td><%= book.getTitle() %></td>
            <td><%= book.getAuthor() %></td>
            
            <td>
                <% if (book.isAvailable()) { %>
                    Available
                <% } else { %>
                    Borrowed
                <% } %>
            </td>
            <td><%= book.getIssueDate() %></td>
            <td><% if (book.isAvailable()) { %>
                    N/A
                <% } else { %>
                   <%= book.getReturnDate() %>
                <% } %></td>
            <td>
                <form action="books" method="post" style="display:inline">
                    <input type="hidden" name="id" value="<%= book.getId() %>" />
                    <% if (book.isAvailable()) { %>
                        <input type="hidden" name="action" value="issue" />
                        <button type="submit">Issue</button>
                    <% } else { %>
                        <input type="hidden" name="action" value="return" />
                        <button type="submit">Return</button>
                    <% } %>
                </form>
            </td>
        </tr>
    <% 
        }
    %>
</table>
</body>
</html>

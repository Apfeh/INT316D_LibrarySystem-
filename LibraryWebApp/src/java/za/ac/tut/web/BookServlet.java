package za.ac.tut.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import za.ac.tut.bl.BookFacadeLocal;
import za.ac.tut.entity.Book;

public class BookServlet extends HttpServlet {

    @EJB
    private BookFacadeLocal bfl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        List<Book> books = bfl.findAll();
        
        
        request.setAttribute("books", books);
        
        
        request.getRequestDispatcher("/books.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        Long id = request.getParameter("id") != null ? Long.parseLong(request.getParameter("id")) : null;

        switch (action) {
            case "add":
                
                String title = request.getParameter("title");
                String author = request.getParameter("author");
                
                
                Book book = new Book(title, author);
                book.setIssueDate(new Date());
                
                
                bfl.create(book);
                break;

            case "issue":
                
                book = bfl.find(id);
                Date issueDate = new Date();
                Date returnDate = bfl.findReturnDate(id);
                
                
                book.setIssueDate(issueDate);
                book.setReturnDate(returnDate);
                
                
                bfl.edit(book);
                bfl.issueBook(id);
                break;

            case "return":
                
                bfl.returnBook(id);
                break;
        }
        
        
        response.sendRedirect("books");
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.tut.bl;

import java.util.Calendar;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import za.ac.tut.entity.Book;

/**
 *
 * @author Student
 */
@Stateless
public class BookFacade extends AbstractFacade<Book> implements BookFacadeLocal {

    @PersistenceContext(unitName = "LibraryEJBModulePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookFacade() {
        super(Book.class);
    }

    @Override
    public void issueBook(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null && book.isAvailable()) {
            book.setAvailable(false);
            em.merge(book);
        }
    }

    @Override
    public void returnBook(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null && !book.isAvailable()) {
            book.setAvailable(true);
            em.merge(book);
        }
    }

    @Override
    public Date findReturnDate(Object id) {
        // Get the issueDate first
        Query query = em.createQuery("SELECT b.issueDate FROM Book b WHERE b.id = :id");
        query.setParameter("id", id);
        Date issueDate = (Date) query.getSingleResult();

        // Add 7 days to the issueDate in Java
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(issueDate);
        calendar.add(Calendar.DAY_OF_YEAR, 7); // Adds 7 days to the issue date

        return calendar.getTime(); // Return the new date (issueDate + 7 days)
    }

}

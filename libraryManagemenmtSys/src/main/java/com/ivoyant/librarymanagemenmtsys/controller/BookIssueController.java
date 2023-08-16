package com.ivoyant.librarymanagemenmtsys.controller;

import com.ivoyant.librarymanagemenmtsys.entity.BookIssue;
import com.ivoyant.librarymanagemenmtsys.entity.Books;
import com.ivoyant.librarymanagemenmtsys.entity.Status;
import com.ivoyant.librarymanagemenmtsys.entity.Student;
import com.ivoyant.librarymanagemenmtsys.repository.BookIssueRepository;
import com.ivoyant.librarymanagemenmtsys.repository.BookRepository;
import com.ivoyant.librarymanagemenmtsys.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/book-issues/")
public class BookIssueController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private BookIssueRepository bookIssueRepository;

    @Transactional
    @PostMapping("issue/{bookId}/{studentId}")
    public ResponseEntity<String> issueBook(@PathVariable Long bookId, @PathVariable Long studentId) {
        // Check if the student has issued more than 5 books
        Student student = studentRepository.findById(studentId).orElse(null);
        String name = student.getFirstName();
        String lname = student.getLastName();
        if (student != null && student.getBookIssues().size() >= 5) {
            return ResponseEntity.badRequest().body("Student has reached the maximum limit of issued books (5).");
        }

        // Check if the book is available
        Books book = bookRepository.findById(bookId).orElse(null);
        String bookName = book.getDescription();
        if (book == null || book.getQuantity() <= 0) {
            return ResponseEntity.badRequest().body("Book is not available for issuing.");
        }

        // Issue the book
        BookIssue bookIssue = new BookIssue();
        bookIssue.setBooks(book);
        bookIssue.setStudent(student);
        bookIssue.setStudentName(name+lname);
        bookIssue.setBookName(bookName);
        bookIssue.setIssueDate(LocalDate.now());
        bookIssue.setReturnDate(LocalDate.now().plusDays(15));

        // Update book quantity and student's book issues
        book.setQuantity(book.getQuantity() - 1);
        int quantity = book.getQuantity();
        if(quantity==0){
            book.setStatus(Status.AVAILABLE_ALLOCATED);
        }else {
            book.setStatus(Status.AVAILABLE);
        }
        student.getBookIssues().add(bookIssue);

        // Save changes to the database
        bookRepository.save(book);
        studentRepository.save(student);
        //bookIssueRepository.save(bookIssue);

        return ResponseEntity.ok("Book issued successfully.");
    }

    @Transactional
    @PostMapping("/return/{bookIssueId}")
    public ResponseEntity<String> returnBook(@PathVariable Long bookIssueId) {
        Optional<BookIssue> optionalBookIssue = bookIssueRepository.findById(bookIssueId);

        if (optionalBookIssue.isPresent() && !optionalBookIssue.isEmpty()) {
            BookIssue bookIssue = optionalBookIssue.get();
            Books book = bookIssue.getBooks();
            Student student = bookIssue.getStudent();


            // Update book quantity and remove book issue from student's list
            book.setQuantity(book.getQuantity() + 1);
            book.setStatus(Status.AVAILABLE);
            student.getBookIssues().remove(bookIssue);

            // Save changes to the database
            bookRepository.save(book);
            studentRepository.save(student);
            bookIssueRepository.deleteById(bookIssueId);

            return ResponseEntity.ok("Book returned successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("return-date-alert/today")
    public ResponseEntity<List<BookIssue>> getBookIssuesWithReturnDateToday() {
        LocalDate today = LocalDate.now();
        List<BookIssue> bookIssues = bookIssueRepository.findAll();
        List<BookIssue> bookIssues1= new ArrayList<>();
        for (BookIssue bookIssue:bookIssues) {
            if (bookIssue.getReturnDate().isEqual(today) ){
                bookIssue.setBooksId(bookIssue.getBooks().getId());
                bookIssue.setStudentsId(bookIssue.getStudent().getId());
                bookIssues1.add(bookIssue);
            }
        }


        return ResponseEntity.ok(bookIssues1);
    }

}

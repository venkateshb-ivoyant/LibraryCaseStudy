package com.ivoyant.librarymanagemenmtsys.controller;

import com.ivoyant.librarymanagemenmtsys.entity.Books;
import com.ivoyant.librarymanagemenmtsys.entity.Status;
import com.ivoyant.librarymanagemenmtsys.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/admin/book/")
public class AdminController {
    @Autowired
    private BookRepository bookRepository;
    @PostMapping("addBooks")
    public ResponseEntity<Books> addBooks(@RequestBody Books books) {
        int quantity = books.getQuantity();
        if (quantity <= 0) {
            books.setStatus(Status.UNAVAILABLE);
        }else {
            books.setStatus(Status.AVAILABLE);
        }
        Books books1 = bookRepository.save(books);
        return new ResponseEntity<Books>(books1, HttpStatus.CREATED);

    }

    @GetMapping("getAllBooks")
    public List<Books> getAllBooks(){
        return bookRepository.findAll();
    }
    @GetMapping("getBookById/{id}")
    public Optional<Books> getBookByID(@PathVariable Long id){
        return bookRepository.findById(id);
    }
    @DeleteMapping("deleteBookById/{id}")
    public void deleteBook(@PathVariable Long id){
        bookRepository.deleteById(id);
    }
    @PutMapping("updateBook/{id}")
    public ResponseEntity<String> updateBook(@RequestBody Books books, @PathVariable Long id){
        Optional<Books> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Books existingBook = optionalBook.get();

            existingBook.setDescription(books.getDescription());
            existingBook.setAuthor(books.getAuthor());
            existingBook.setIsbn(books.getIsbn());
            existingBook.setOriginalReleaseDate(books.getOriginalReleaseDate());
            existingBook.setUniqueBookId(books.getUniqueBookId());
            existingBook.setQuantity(books.getQuantity());
            int quantity = books.getQuantity();
            if (quantity <= 0) {
                existingBook.setStatus(Status.AVAILABLE_ALLOCATED);
            }else {
                existingBook.setStatus(Status.AVAILABLE);
            }

            bookRepository.save(existingBook);

            return ResponseEntity.ok("Book updated successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

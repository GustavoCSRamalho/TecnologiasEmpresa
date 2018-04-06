package com.example.controller;

import com.example.model.Book;
import com.example.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.internal.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;


    @RequestMapping(value = {"/books"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> create(@Valid @RequestBody String book) throws IOException {

        try {
            Book b = null;
            ObjectMapper obj = new ObjectMapper();
            b = obj.readValue(book, Book.class);
            bookService.create(b);
        }catch(Exception e){
            return new  ResponseEntity(book,HttpStatus.CONFLICT);
        }
        return new ResponseEntity(book,HttpStatus.OK);
    }

    @RequestMapping(value = {"/books"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> findAll() {
        return new ResponseEntity<List<Book>>(bookService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = {"/books/{id}"}, method = RequestMethod.GET)
    public ResponseEntity<Book> findById(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<Book>(bookService.findById(id), HttpStatus.OK);
    }

    @RequestMapping(value = {"/books"}, method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestParam(name = "id") long book ) {
        try {
            bookService.delete(book);
        } catch (Exception e) {
            return new ResponseEntity(book,HttpStatus.CONFLICT);
        }
        return new ResponseEntity(book, HttpStatus.OK);
    }


    @RequestMapping(value = {"/books"}, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@RequestBody String book) {
        try {
            Book b = null;
            ObjectMapper obj = new ObjectMapper();
            b = obj.readValue(book, Book.class);

            bookService.update(b);
        }catch(Exception e){
            return new ResponseEntity(book,HttpStatus.CONFLICT);
        }
        return new ResponseEntity(book,HttpStatus.OK);
    }

//    @RequestMapping(value = {"/books"}, method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> update(@RequestBody String book) {
//
//
//        try {
////            JSONWrappedObject ob = new JSONWrappedObject(book);
//            Book bookObj = new Book();
//            ObjectMapper objectMapper = new ObjectMapper();
//            bookObj = objectMapper.readValue(book, Book.class);
//
//            bookService.update(bookObj);
//
//        } catch (Exception e) {
//            return new ResponseEntity(book,HttpStatus.NO_CONTENT);
//        }
//        return new ResponseEntity(book,HttpStatus.OK);
//    }
}

package com.fh.shop.api.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;
@RestController
@RequestMapping("mongodb")
public class MongodbController {
    @Autowired
    private MongodbService mongodbService;

    @PostMapping("/save")
    public String saveObj(@RequestBody Book book) {
        return mongodbService.saveObj(book);
    }

    @GetMapping("/findAll")
    public List<Book> findAll() {
        return mongodbService.findAll();
    }

    @GetMapping("/findOne")
    public Book findOne(@RequestParam String id) {
        return mongodbService.getBookById(id);
    }

    @GetMapping("/findOneByName")
    public Book findOneByName(@RequestParam String name) {
        return mongodbService.getBookByName(name);
    }

    @PostMapping("/update")
    public String update(@RequestBody Book book) {
        return mongodbService.updateBook(book);
    }

    @PostMapping("/delOne")
    public String delOne(@RequestBody Book book) {
        return mongodbService.deleteBook(book);
    }

    @GetMapping("/delById")
    public String delById(@RequestParam String id) {
        return mongodbService.deleteBookById(id);
    }

    @GetMapping("/findlikes")
    public List<Book> findByLikes(@RequestParam String search) {
        return mongodbService.findByLikes(search);
    }

}

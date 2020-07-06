package com.fh.shop.api.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MongodbServiceImpl implements MongodbService {
    @Autowired
private MongoTemplate mongoTemplate;

    @Override
    public String saveObj(Book book) {
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        mongoTemplate.save(book);
        return "添加成功";
    }

    @Override
    public List<Book> findAll() {
        return mongoTemplate.findAll(Book.class);
    }

    @Override
    public Book getBookById(String id) {
        return null;
    }

    @Override
    public Book getBookByName(String name) {
        return null;
    }

    @Override
    public String updateBook(Book book) {
        return null;
    }

    @Override
    public String deleteBook(Book book) {
        return null;
    }

    @Override
    public String deleteBookById(String id) {
        Book book = getBookById(id);
        // delete
        deleteBook(book);
        return "success";
    }

    @Override
    public List<Book> findByLikes(String search) {
        return null;
    }

}

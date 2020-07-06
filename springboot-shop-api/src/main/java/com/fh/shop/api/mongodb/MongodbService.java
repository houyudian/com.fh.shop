package com.fh.shop.api.mongodb;


import java.util.List;

public interface MongodbService {
    String saveObj(Book book);

    List<Book> findAll();

    Book getBookById(String id);

    Book getBookByName(String name);

    String updateBook(Book book);

    String deleteBook(Book book);

    String deleteBookById(String id);

    List<Book> findByLikes(String search);

}

package com.fh.shop.api.exception;

public class StockLessException extends RuntimeException{
public StockLessException(String msg){
    super(msg);
}
}

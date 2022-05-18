package com.company.GameStore.exception;

public class NotEnoughInStockException extends RuntimeException {
    public NotEnoughInStockException(String message) { super (message); }
}

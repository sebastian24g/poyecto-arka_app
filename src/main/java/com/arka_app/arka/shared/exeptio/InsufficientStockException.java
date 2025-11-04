package com.arka_app.arka.shared.exeptio;


public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(String message) { super(message); }
}


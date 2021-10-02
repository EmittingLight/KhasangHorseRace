package com.petrovsergei1974;

final class HorseException extends RuntimeException {
    HorseException() {
        super("Неверный объект.");
    }

    HorseException(String message) {
        super(message);
    }
}

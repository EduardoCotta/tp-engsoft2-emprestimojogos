package br.ufmg.engsoft2.gameloan.exceptions;

public class NoUserFoundException extends RuntimeException {
    public NoUserFoundException(String message) {
        super(message);
    }
}

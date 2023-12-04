package br.ufmg.engsoft2.gameloan.helper;

import java.util.Date;

public class ValidatorHelper {
    private ValidatorHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static void isNullOrEmpty(String parameter, String name) {
        if (parameter == null || parameter.equals("")) {
            throw new IllegalArgumentException(name.concat(" não pode ser nulo ou vazio."));
        }
    }

    public static void isPositive(double number, String name) {
        if (number < 0) {
            throw new IllegalArgumentException(name.concat(" não pode ser negativo."));
        }
    }

    public static void isObjectNull(Object object, String name) {
    	if(object == null) {
    		throw new IllegalArgumentException(name.concat(" não pode ser nulo."));
    	}
    }

    public static void validateDeadline(Date deadline) {
        isObjectNull(deadline, "Data limite");
        if(!deadline.after(new Date())) {
            throw new IllegalArgumentException("A data limite deve ser posterior à data atual.");
        }
    }
}

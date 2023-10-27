package br.ufmg.engsoft2.gameloan.controller;

import br.ufmg.engsoft2.gameloan.exceptions.NoUserFoundException;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import br.ufmg.engsoft2.gameloan.view.View;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Controller {
    private static final FormController formController = new FormController();
    static Scanner scanner = new Scanner(System.in);

    private Controller() {
    }


    public static void handleHomeMenu() {
        int option = scanner.nextInt();

        View.showScreenSeparator();

        if (option == 1) {
            View.showSignUp();
        } else if (option == 2) {
            View.showLogin();
        } else {
            System.exit(0);
        }
    }

    public static void handleRegistration() {
        try {
            formController.formSignup();
        } catch (InputMismatchException ex) {
            View.showErrorNotLogged(ex.getMessage());
        }
        View.showLogin();
    }

    public static void handleLoggedHomeMenu() {
        int option = scanner.nextInt();

        if (option == 1) {
            View.showGameRegistration();
        } else if (option == 2) {
            View.showGameList();
        } else if (option == 3) {
            View.showLoanRegistration();
        } else if (option == 4) {
            View.showLoanList();
        } else if (option == 5) {
            View.showAvailableGames();
        } else if (option == 6) {
            View.searchAvailableGames();
        } else if (option == 7) {
            SessionManager.clean();
            View.showHome();
        } else if (option == 8) {
            System.exit(0);
        } else {
            View.showLoggedError("Opção não reconhecida!");
        }
    }

    public static void handleGameRegistration() {
        try {
            formController.formGameRegister();
        } catch (InputMismatchException ex) {
            View.showLoggedError(ex.getMessage());
        }
    }

    public static void handleLoanRegistration() {
        try {
            formController.formLoanRegister();
        } catch (InputMismatchException ex) {
            View.showLoggedError(ex.getMessage());
        }
    }

    public static void handleLogin() {
        try {
            formController.formLogin();
        } catch (NoUserFoundException ex) {
            View.showErrorNotLogged(ex.getMessage());
        }

        View.showLoggedHome();
    }
}

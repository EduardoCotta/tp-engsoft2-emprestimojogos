package br.ufmg.engsoft2.gameloan.view;

import java.util.List;

import br.ufmg.engsoft2.gameloan.constants.ConsoleColors;
import br.ufmg.engsoft2.gameloan.constants.MenuOptions;
import br.ufmg.engsoft2.gameloan.controller.Controller;
import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import br.ufmg.engsoft2.gameloan.helper.Helper;
import br.ufmg.engsoft2.gameloan.repository.GameDB;
import br.ufmg.engsoft2.gameloan.service.LoanService;
import br.ufmg.engsoft2.gameloan.service.GameService;
import br.ufmg.engsoft2.gameloan.domain.Loan;
import br.ufmg.engsoft2.gameloan.domain.User;

public class View {

    private static void printMenu(String[] options) {
        for (String option : options) {
            System.out.println(option);
        }
        System.out.print("Escolha sua opcão: ");
        System.out.println();
    }

    private static void showError(String error) {
        System.out.println(
                ConsoleColors.RED +
                        "Erro: " + error +
                        ConsoleColors.RESET);
    }

    public static void showLogin() {
        Controller.handleLogin();
    }

    public static void showSignUp() {
        showScreenSeparator();
        Controller.handleRegistration();
    }

    public static void showScreenSeparator() {
        System.out.println();
        System.out.println(
                ConsoleColors.CYAN_BOLD +
                        "---------------------------------------------------------------------------------------------"
                        +
                        ConsoleColors.RESET);
        System.out.println();
    }

    public static void showLoggedHome() {
        showScreenSeparator();
        String userName = SessionManager.getSession().getLoggedUser().getName();

        System.out.println(
                String.format(
                        ConsoleColors.PURPLE +
                                "-----------------  BEM-VINDO(A) %s AO SISTEMA DE EMPRESTIMO DE JOGOS -----------------"
                                +
                                ConsoleColors.RESET,
                        userName));

        printMenu(MenuOptions.LOGGED_HOME);
        Controller.handleLoggedHomeMenu();
    }

    public static void showHome() {
        showScreenSeparator();
        System.out.println(
                ConsoleColors.BLUE +
                        "-----------------  BEM-VINDO AO SISTEMA DE EMPRESTIMO DE JOGOS -----------------" +
                        ConsoleColors.RESET);
        System.out.println(
                ConsoleColors.BLUE +
                        "O que deseja fazer?" +
                        ConsoleColors.RESET);

        printMenu(MenuOptions.HOME);
        Controller.handleHomeMenu();
    }

    public static void showErrorNotLogged(String error) {
        showError(error);
        showHome();
    }

    public static void showLoggedError(String error) {
        showError(error);
        showLoggedHome();
    }

    public static void showGameRegistration() {
        showScreenSeparator();
        Controller.handleGameRegistration();
        System.out.println("Cadastro de jogo realizado com sucesso.");
        showLoggedHome();
    }

    public static void showGameList() {
        showScreenSeparator();
        showLoggedUserGames();
        showLoggedHome();
    }

    public static void showLoanRegistration() {
        showScreenSeparator();
        Controller.handleLoanRegistration();
        System.out.println("Cadastro de empréstimo realizado com sucesso.");
        showLoggedHome();
    }

    public static void showLoanList() {
        showScreenSeparator();
        showLoggedUserLoans();
        showLoggedHome();
    }

    public static void showAvailableGames() {
        showScreenSeparator();

        showLoggedUserAvailableGames();
        showLoggedHome();
    }

    public static void searchAvailableGames() {
        showScreenSeparator();
        searchLoggedUserAvailableGames();
        showLoggedHome();
    }

    private static void showLoggedUserGames() {

        GameService gameService = new GameService();

        User userLogado = SessionManager.getSession().getLoggedUser();

        List<Game> gamesByUser = gameService.listGamesByUser(userLogado);

        gameService.printGamesListByUser(userLogado, gamesByUser);
    }

    private static void showLoggedUserLoans() {

        LoanService loanService = new LoanService();

        User userLogado = SessionManager.getSession().getLoggedUser();

        List<Loan> emprestimosUsuario = loanService.listLoansByUser(userLogado);

        loanService.printLoansByUser(userLogado, emprestimosUsuario);
    }

    private static void showLoggedUserAvailableGames() {
        GameDB gameDB = GameDB.getInstance();
        gameDB.printAll();

    }

    private static void searchLoggedUserAvailableGames() {
        GameDB gameDB = GameDB.getInstance();
        gameDB.searchByKey(Helper.searchGameTerminal());
    }
}

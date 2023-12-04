package br.ufmg.engsoft2.gameloan.view;

import br.ufmg.engsoft2.gameloan.constants.ConsoleColors;
import br.ufmg.engsoft2.gameloan.constants.MenuOptions;
import br.ufmg.engsoft2.gameloan.controller.Controller;
import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import br.ufmg.engsoft2.gameloan.helper.Helper;
import br.ufmg.engsoft2.gameloan.service.LoanService;
import br.ufmg.engsoft2.gameloan.service.GameService;
import br.ufmg.engsoft2.gameloan.domain.User;

import java.util.List;

public class View {

    private View() {
        throw new IllegalStateException("Utility class");
    }

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

        System.out.printf(
                ConsoleColors.PURPLE +
                        "-----------------  BEM-VINDO(A) %s AO SISTEMA DE EMPRESTIMO DE JOGOS -----------------"
                        +
                        ConsoleColors.RESET + "%n",
                userName);

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

        gameService.printGamesListByUser(userLogado);
    }

    private static void showLoggedUserLoans() {

        LoanService loanService = new LoanService();

        User userLogado = SessionManager.getSession().getLoggedUser();

        loanService.printLoansByUser(userLogado);
    }

    private static void showLoggedUserAvailableGames() {
        GameService gameService = new GameService();
        List<Game> availableGames = gameService.getAll();
        gameService.printAll(availableGames);

    }

    private static void searchLoggedUserAvailableGames() {
        String searchKey = Helper.searchGameTerminal();
        GameService gameService = new GameService();
        List<Game> availableGames = gameService.searchByKeyword(searchKey);

       gameService.printAll(availableGames);
    }
}

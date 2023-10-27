package br.ufmg.engsoft2.gameloan.controller;

import br.ufmg.engsoft2.gameloan.constants.ConsoleColors;
import br.ufmg.engsoft2.gameloan.service.LoanService;
import br.ufmg.engsoft2.gameloan.service.GameService;
import br.ufmg.engsoft2.gameloan.service.UserService;

import java.util.Scanner;

public class FormController {

    private UserService userService;
    private GameService gameService;
    private LoanService loanService;
    
    public FormController() {
        userService = new UserService();
        gameService = new GameService();
        loanService = new LoanService();
    }

    static Scanner scanner = new Scanner(System.in);

    public void formSignup(){
        System.out.println(ConsoleColors.GREEN + "Informe os campos a seguir para cadastro: ");
        System.out.println("E-mail: ");
        String email = scanner.nextLine();
        System.out.println("Nome: ");
        String name = scanner.nextLine();
        System.out.println("Interesses: ");
        String interests = scanner.nextLine();
        System.out.println("Senha: " + ConsoleColors.RESET);
        String password = scanner.nextLine();

        userService.signUp(email, name, interests, password);
    }

    public void formGameRegister() {
        System.out.println(ConsoleColors.GREEN + "Informe os campos a seguir para cadastro do jogo: ");
        System.out.println("Nome do jogo: ");
        String gameName = scanner.nextLine();
        System.out.println("Descrição: ");
        String description = scanner.nextLine();
        System.out.println("Preco: ");
        double price = Double.parseDouble(scanner.nextLine());

        gameService.addGame(gameName, description, price);
    }

    public void formLogin(){
        System.out.println(ConsoleColors.GREEN + "Informe os campos a seguir para login: ");
        System.out.println("E-mail: ");
        String email = scanner.nextLine();
        System.out.println("Senha: " + ConsoleColors.RESET);
        String password = scanner.nextLine();

        userService.doLogin(email, password);
    }
    
    public void formLoanRegister() {
    	System.out.println(ConsoleColors.GREEN + "Informe os campos a seguir para cadastro do empréstimo: ");
    	System.out.println("Nome do jogo: ");
    	String gameName = scanner.nextLine();
    	System.out.println("Email do dono: ");
    	String ownerEmail = scanner.nextLine();
    	System.out.println("Data limite proposta: ");
    	String deadlineString = scanner.nextLine();

    	loanService.addLoan(ownerEmail, gameName, deadlineString);
    }
}

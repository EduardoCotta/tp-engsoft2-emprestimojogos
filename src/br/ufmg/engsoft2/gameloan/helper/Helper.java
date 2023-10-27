package br.ufmg.engsoft2.gameloan.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Helper {

	public static String formatDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(date);
	}

	public static Date getCurrentDateWithoutTime() {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.parse(sdf.format(new Date()));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date parseStringToDate(String dateString) {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date date = sdf.parse(dateString);
			return date;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date convertStringToDate(String dateString) {

		try {

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			Date deadline = formatter.parse(dateString);

			if (deadline == null) {
				throw new InputMismatchException("Data inválida");
			}

			return deadline;

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static void timer() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static String searchGameTerminal() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Digite a palavra-chave: ");
		String keyword = scanner.nextLine();
		return (keyword);

	}
}

package br.ufmg.engsoft2.gameloan.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Helper {

	private Helper() {
		throw new IllegalStateException("Utility class");
	}

	public static final String DD_MM_YYYY = "dd/MM/yyyy";

	public static String formatDateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
		return sdf.format(date);
	}

	public static Date getCurrentDateWithoutTime() {

		try {

			SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
			return sdf.parse(sdf.format(new Date()));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date parseStringToDate(String dateString) {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DD_MM_YYYY);
			return sdf.parse(dateString);

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	public static Date convertStringToDate(String dateString) {

		try {

			SimpleDateFormat formatter = new SimpleDateFormat(DD_MM_YYYY);
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

	public static String searchGameTerminal() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Digite a palavra-chave: ");
		String keyword = scanner.nextLine();
		return (keyword);

	}
}

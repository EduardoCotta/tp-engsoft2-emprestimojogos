package br.ufmg.engsoft2.gameloan.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import br.ufmg.engsoft2.gameloan.domain.Loan;
import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.helper.Helper;
import br.ufmg.engsoft2.gameloan.session.SessionManager;
import br.ufmg.engsoft2.gameloan.domain.User;

public class LoanDB {

	private static LoanDB instance;
	private final List<Loan> emprestimosDB = new ArrayList<>();

	private LoanDB() {

		List<Game> games = GameDB.getInstance().getAll();
		User owner = UserDB.getInstance().getAll().get(0);
		User requester = null;
		Date deadline = Helper.parseStringToDate("01/07/2023");
		User loggedUser = SessionManager.getSession().getLoggedUser();

		if (loggedUser != null) {
			requester = loggedUser;
		} else {
			requester = UserDB.getInstance().getAll().get(1);
		}

		this.emprestimosDB.add(
				new Loan(owner, requester, games.get(0), deadline));
	}

	public static LoanDB getInstance() {
		if (instance == null) {
			instance = new LoanDB();
		}
		return instance;
	}

	public void add(Loan loan) {
		emprestimosDB.add(loan);
	}

	public List<Loan> getAll() {
		return emprestimosDB;
	}

}

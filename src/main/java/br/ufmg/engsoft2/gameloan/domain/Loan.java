package br.ufmg.engsoft2.gameloan.domain;

import br.ufmg.engsoft2.gameloan.helper.Helper;

import java.util.Date;

public class Loan {
	
	private final User owner;
	private final User requester;
	private final Game requestedGame;
	private final Date deadline;
	
	public Loan(User owner, User requester, Game requestedGame, Date deadline) {
		
		this.owner = owner;
		this.requester = requester;
		this.requestedGame = requestedGame;
		this.deadline = deadline;
	}

	public User getOwner() {
		return owner;
	}

	public User getRequester() {
		return requester;
	}

	public Game getRequestedGame() {
		return requestedGame;
	}

	public Date getDeadline() {
		return deadline;
	}

	public String toString() {
		String formattedDeadline = Helper.formatDateToString(deadline);
		return String.format("| %-15s | %-15s | %-15s | %-15s |%n",
				this.requester.getName(),
				this.requestedGame.getName(),
				this.owner.getName(),
				formattedDeadline);
	}
}

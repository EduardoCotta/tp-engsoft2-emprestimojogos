package br.ufmg.engsoft2.gameloan.domain;

import java.util.Date;

public class Loan {
	
	private User owner;
	private User requester;
	private Game requestedGame;
	private Date deadline;
	
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
}

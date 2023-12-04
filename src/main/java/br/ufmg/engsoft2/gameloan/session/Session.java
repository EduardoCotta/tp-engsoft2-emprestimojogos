package br.ufmg.engsoft2.gameloan.session;

import br.ufmg.engsoft2.gameloan.domain.User;

public class Session {
    private User loggedUser;

    public User getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(User loggedUser) {
        this.loggedUser = loggedUser;
    }
}

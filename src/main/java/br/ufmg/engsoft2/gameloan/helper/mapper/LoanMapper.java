package br.ufmg.engsoft2.gameloan.helper.mapper;

import br.ufmg.engsoft2.gameloan.domain.Game;
import br.ufmg.engsoft2.gameloan.domain.Loan;
import br.ufmg.engsoft2.gameloan.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoanMapper {
    private LoanMapper() {
        throw new IllegalStateException("Utility class");
    }
    public static List<Loan> map(ResultSet resultSet) throws SQLException {
        List<Loan> loans = new ArrayList<>();
        while (resultSet.next()) {
            var deadline = resultSet.getDate("deadline");
            var ownerEmail = resultSet.getString("ownerEmail");
            var requestUserEmail = resultSet.getString("requestUserEmail");
            var gameId = resultSet.getObject("gameId", java.util.UUID.class);

            var owner = new User(ownerEmail);
            var requester = new User(requestUserEmail);
            var requestedGame = new Game(gameId);

            var loan = new Loan(owner, requester, requestedGame, deadline);

            loans.add(loan);
        }
        return loans;
    }
}

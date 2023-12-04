package br.ufmg.engsoft2.gameloan.repository;

import br.ufmg.engsoft2.gameloan.domain.Loan;
import br.ufmg.engsoft2.gameloan.infra.database.ConnectionManager;
import br.ufmg.engsoft2.gameloan.helper.mapper.LoanMapper;

import java.sql.Date;
import java.util.List;

public class LoanRepository {
    private final ConnectionManager connectionManager;

    public LoanRepository() {
        this.connectionManager = ConnectionManager.getInstance();
    }

    public boolean add(Loan loan){
        String sql = "INSERT INTO loan (deadline, ownerEmail, requestUserEmail, gameId) VALUES (?, ?, ?, ?)";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setDate(1, new Date(loan.getDeadline().getTime()));
            preparedStatement.setString(2, loan.getOwner().getEmail());
            preparedStatement.setString(3, loan.getRequester().getEmail());
            preparedStatement.setObject(4, loan.getRequestedGame().getId());

            return preparedStatement.execute();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public List<Loan> getAll(){
        String sql = "SELECT * FROM loan";

        try(var connection = connectionManager.getConnection();
            var preparedStatement = connection.prepareStatement(sql)) {

            var resultSet = preparedStatement.executeQuery();

            return LoanMapper.map(resultSet);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

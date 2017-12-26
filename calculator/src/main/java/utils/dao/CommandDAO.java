package utils.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Command;
import utils.DBConnectionManager;

public enum CommandDAO {

	INSTANCE;
	
	public void save(Command command) {
		PreparedStatement statement = null;
		Connection c = DBConnectionManager.INSTANCE.getConnection();
		try {
			statement = c.prepareStatement("INSERT INTO calculations VALUES "
					+ "( ? , ? );");
			statement.setDouble(1, command.getNumber());
			statement.setString(2, command.getMathFunction());
			statement.execute();
			statement.close();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public List<Command> select() {
		ArrayList<Command> r = new ArrayList<Command>();
		Connection c = DBConnectionManager.INSTANCE.getConnection();
		PreparedStatement stmt = null;
		try {
			stmt = c.prepareStatement("SELECT * FROM calculations;");
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Command cmd = new Command();
				cmd.setNumber(rs.getDouble(1));
				cmd.setMathFunction(rs.getString(2));
				r.add(cmd);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}
	
}

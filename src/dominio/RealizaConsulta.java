package dominio;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class RealizaConsulta {
	private Connection conexao;
	Statement stmt = null;

	public void preparaConexao() {
		try {
			stmt = conexao.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ResultSet RealizaConsulta(String query) {
		try {
			System.out.println("realizando consulta");
			ResultSet rs = stmt.executeQuery(query);
			return rs;
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

}

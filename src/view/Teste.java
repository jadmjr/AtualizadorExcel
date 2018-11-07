package view;

import java.sql.SQLException;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import dao.Conexao;
import dao.Host;
import dominio.ManipulaExcel;
import dominio.JulimarException;

public class Teste {

	public static void main(String[] args) {

		/*Conexao conexao = new Conexao();
		Host hosts = new Host();
		conexao.setUser("JULIMAR_KYROS");
		conexao.setPassword("ValarGot10");
		conexao.setStrHost(hosts.selecionarHost("FXPRD"));

		ManipulaExcel man = new ManipulaExcel();
		try {
			man.setConexao(conexao.conectar());
			XSSFSheet folha = man.abreArquivo("C:\\Users\\Julimar\\Documents\\testesAtualizador\\terminais.xlsx");
			String consulta = "SELECT C.BILL_LNAME, C.BILL_ADDRESS1, C.BILL_ZIP FROM EXTERNAL_ID_EQUIP_MAP E, CMF C WHERE C.ACCOUNT_NO = E.ACCOUNT_NO AND E.EXTERNAL_ID = ':A:'";

			try {
				man.montaConsulta(folha, consulta, "B,C");

			} catch (JulimarException e) {
				System.out.println("Voce é jo");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

}
}

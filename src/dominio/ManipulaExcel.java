package dominio;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ManipulaExcel {

	private Connection conexao;

	Pattern regex = Pattern.compile(":([A-Z]{1,2}):");
	List<String> celulasOrigem = new ArrayList<String>();
	CorrespondeLetra corresponde = new CorrespondeLetra();
	XSSFWorkbook workbook;
	File file;
	RealizaConsulta realizaConsulta = new RealizaConsulta();

	public XSSFSheet abreArquivo(String enderecoArq) {

		try {

			Runtime.getRuntime().exec("taskkill /IM EXCEL.exe");

			file = new File(enderecoArq);
			FileInputStream input = new FileInputStream(file);
			workbook = new XSSFWorkbook(input);
			XSSFSheet sheet = workbook.getSheetAt(0);
			input.close();

			return sheet;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public void montaConsulta(XSSFSheet folha, String query, String colunaDestino) {

		// jmjr tempo
		long start = System.currentTimeMillis();
		int contador = 0;

		List<String> colunasPesquisa = pegaColunas(query);

		String queryInic = query;

		Iterator<Row> rowIterator = folha.iterator();

		realizaConsulta.setConexao(conexao);
		realizaConsulta.preparaConexao();
		ResultSet rs = null;

		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();

			System.out.println("linhaaa" + row.getRowNum());

			// JMJR - LINHA INICIAL = 2
			if (row.getRowNum() >= 1) {

				for (int i = 0; i < colunasPesquisa.size(); i++) {
					int numeroColuna = corresponde.CorrespondeLetra(colunasPesquisa.get(i));

					System.out.println("numeorColuna" + numeroColuna);

					switch (row.getCell(numeroColuna - 1).getCellType()) {
					case Cell.CELL_TYPE_NUMERIC:
						Double valorCelula = row.getCell(numeroColuna - 1).getNumericCellValue();
						query = query.replace(":" + colunasPesquisa.get(i) + ":", valorCelula.toString());
						break;
					case Cell.CELL_TYPE_STRING:

						query = query.replace(":" + colunasPesquisa.get(i) + ":",
								row.getCell(numeroColuna - 1).getStringCellValue());
						break;
					}

				}

				System.out.println(query);
				rs = realizaConsulta.RealizaConsulta(query);
				System.out.println("consultou");
				editaLinha(rs, colunaDestino, row);
				query = queryInic;

			}

			// GRAVA E FECHA CANAL DE SAIDA
			System.out.println("leu");

		}

		try {

			System.out.println("PREPARANDO GRAVAÇÃO - JMJR");

			FileOutputStream outputStream = new FileOutputStream(file);
			workbook.write(outputStream);

			System.out.println("gravou");

			outputStream.close();
			outputStream.flush();
			long elapsed = (System.currentTimeMillis() - start) / 1000;
			Desktop.getDesktop().open(new File(file.getPath()));
			throw new JulimarException("Processamento de arquivo finalizado ! Tempo: " + elapsed + " segundo(s)");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void editaLinha(ResultSet rs, String colunaDestino, Row linha) {

		System.out.println("editalinha");

		String[] colunasSaida = colunaDestino.split(",");

		try {

			int colunas = rs.getMetaData().getColumnCount();
			System.out.println("Colunas" + colunas + "Saida" + colunasSaida.length);

			if (colunasSaida.length == colunas) {

				while (rs.next()) {
					System.out.println("percorrendoRS");
					for (int i = 0; i < colunas; i++) {

						Cell cell = linha.createCell(corresponde.CorrespondeLetra(colunasSaida[i]) - 1);
						System.out.println(rs.getString(i + 1));
						if (rs.getString(i + 1) == null) {
							System.out.println("NULOOOO");
							cell.setCellValue("");
						}
						if (rs.getMetaData().getColumnTypeName(i + 1) == "DATE" && rs.getString(i + 1) != null) {
							SimpleDateFormat data = new SimpleDateFormat("dd/MM/yyyy");
							cell.setCellValue(data.format(rs.getDate(i + 1)));

						} else
							cell.setCellValue(rs.getString(i + 1));

					}

				}

			} else {
				throw new JulimarException("Número de colunas informadas diferente de ResultSet");

			}

		} catch (SQLException e) {

			e.printStackTrace();
		}

	}

	public List<String> pegaColunas(String query) {
		
		Matcher regexMatcher = regex.matcher(query);
		
		while (regexMatcher.find()) {
			celulasOrigem.add(regexMatcher.group(1));
		}
		
		return celulasOrigem;
	}

	public Connection getConexao() {
		return conexao;
	}

	public void setConexao(Connection conexao) {
		this.conexao = conexao;
	}

}

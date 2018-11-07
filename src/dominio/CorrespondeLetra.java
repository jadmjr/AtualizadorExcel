package dominio;

public class CorrespondeLetra {

	// JMJR - APRIMORAR POSTERIORMENTE
	public Integer CorrespondeLetra(final String letraColuna) {
		Integer numero = null;
		char[] array = letraColuna.toCharArray();

		if (array.length == 1)
			numero = array[0] - 64;
		else if (array.length == 2) {
			int temp = array[0] - 64;
			switch (temp) {
			case 1:
				numero = (array[1] - 64) + 26;
				break;
			case 2:
				numero = (array[1] - 64) + 52;
				break;
			case 3:
				numero = (array[1] - 64) + 78;
				break;
			default:
				break;
			}

		}

		return numero;

	}

}

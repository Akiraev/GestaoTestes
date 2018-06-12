package enumeradores;

import java.util.ArrayList;
import java.util.List;

public enum TipoAcaoHistorico {
	EMAIL("E-mail");

	private String tipoAcao;

	TipoAcaoHistorico(String tipoAcao) {
		this.tipoAcao = tipoAcao;
	}

	public String getTipoAcao() {
		return this.tipoAcao;
	}
	
	public static List<String> getComboTipoAcaoHistorico() {
		List<String> lista = new ArrayList<>();
		for (TipoAcaoHistorico s : TipoAcaoHistorico.values()) {
			lista.add(s.getTipoAcao());
		}
		return lista;
	}
}

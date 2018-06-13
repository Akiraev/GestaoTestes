package enumeradores;

import java.util.ArrayList;
import java.util.List;

public enum StatusHistorioco {
	ABERTO("Aberto"), FECHADO("Fechado");

	private String statusHistorico;

	StatusHistorioco(String statusHistorico) {
		this.statusHistorico = statusHistorico;
	}
	
	public String getStatusHistorico() {
		return statusHistorico;
	}
	public void setStatusHistorico(String statusHistorico) {
		this.statusHistorico = statusHistorico;
	}

	public static List<String> getComboStatusHistorioco() {
		List<String> lista = new ArrayList<>();
		for (StatusHistorioco s : StatusHistorioco.values()) {
			lista.add(s.getStatusHistorico());
		}
		return lista;
	}
}

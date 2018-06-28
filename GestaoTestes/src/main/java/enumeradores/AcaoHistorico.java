package enumeradores;

import java.util.ArrayList;
import java.util.List;

public enum AcaoHistorico {
	EMAILENVIADO("E-mail enviado"), EMAIL_RECEBIDO("E-mail recebido"), TELEFONEMA_RECEBIDO(
			"Telefonema recebido"), TELEFONEMA_REALIZADO("Telefonema realizado"), REUNIAO_AGENDADA(
					"Reunião agendada"), REUNIAO_REALIZADA("Reunião realizada");

	private String acaoHistorico;

	AcaoHistorico(String acaoHistorico) {
		this.acaoHistorico = acaoHistorico;
	}

	public String getAcaoHistorico() {
		return this.acaoHistorico;
	}

	public static List<String> getComboAcaoHistorico() {
		List<String> lista = new ArrayList<>();
		for (AcaoHistorico s : AcaoHistorico.values()) {
			lista.add(s.getAcaoHistorico());
		}
		return lista;
	}
}

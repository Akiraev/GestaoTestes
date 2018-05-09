package enumeradores;

public enum NivelUsuario {
	JUNIOR("Júnior"), SENIOR("Sênior"), PLENO("Plêno");
	private String nivel;

	NivelUsuario(String nivel) {
		this.nivel = nivel;
	}

	public String getDireito() {
		return this.nivel;
	}
}

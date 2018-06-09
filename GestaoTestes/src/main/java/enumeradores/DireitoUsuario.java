package enumeradores;

public enum DireitoUsuario {
	ANALISTA("Analista"), GERENTE("Gerente");

	private String direito;

	DireitoUsuario(String direito) {
		this.direito = direito;
	}

	public String getDireito() {
		return this.direito;
	}
}

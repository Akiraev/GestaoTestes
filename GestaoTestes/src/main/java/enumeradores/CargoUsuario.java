package enumeradores;

public enum CargoUsuario {
	ANALISTA("Analista"), PROGRAMADOR("Programador"), GERENTE("Gerente"), ENGENHEIRO("Engenheiro");
	
	String cargo;
	
	private CargoUsuario(String cargo) {
		this.cargo = cargo;
	}
	
	public String getCargo() {
		return this.cargo;
	}
}

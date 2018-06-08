package enumeradores;

public enum CargoUsuario {
	TESTER("Tester"),PROGRAMADOR("Programador"), ENGENHEIRO("Engenheiro");
	
	String cargo;
	
	private CargoUsuario(String cargo) {
		this.cargo = cargo;
	}
	
	public String getCargo() {
		return this.cargo;
	}
}

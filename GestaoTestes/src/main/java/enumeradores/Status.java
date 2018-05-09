package enumeradores;

public enum Status {
	ATIVO("Ativo"), INATIVO("Inativo");
	
	private String status;
	
	Status(String status) {
		this.status = status;
	}
	
	public String geStatus() {
		return this.status;
	}
}

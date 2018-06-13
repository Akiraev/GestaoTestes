package util;

import java.util.Arrays;
import java.util.List;

public class teste {
	private List<String> nomes;

	public List<String> getNomes() {
		return nomes;
	}

	public void setNomes(List<String> nomes) {
		this.nomes = nomes;
	}

	public void setNomesAssAll(List<String> nomes) {
		this.nomes.addAll(nomes);
	}

	public void setNomesAsList(String...a) {
		setNomesAssAll(Arrays.asList(a));
	}

	public <T> void a(Class <? extends T> a) {
		
		
	}
	
}

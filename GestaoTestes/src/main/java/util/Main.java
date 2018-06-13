package util;

import enumeradores.Status;

public class Main {
	public static void main(String[] args) {
		for(Status a : Status.values()) {
			System.out.println(a.getStatus());
		}
		
		Long id = 1l;
		Long id2 = 1l;
		if(id == id2) {
			System.out.println("são iguais");
		}
	}
}

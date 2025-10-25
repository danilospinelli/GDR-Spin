package service.impl;

import datamodel.Guerriero;
import datamodel.ListaClassi;
import datamodel.Personaggio;
import datamodel.Stregone;

public class PersonaggioFactory {
	
	public static Personaggio getPersonaggio(ListaClassi classe) {
		if(classe == ListaClassi.GUERRIERO)
			return new Guerriero();
		if(classe == ListaClassi.STREGONE)
			return new Stregone();
		return null;
	}

}

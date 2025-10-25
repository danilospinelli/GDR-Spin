package service.impl;

import datamodel.Guerriero;
import datamodel.Personaggio;
import datamodel.Stregone;
import service.PersonaggioService;

public class PersonaggioServiceFactory {
	
	public static <T extends Personaggio> PersonaggioService getPersonaggioService(Class<T> c) {
		if(c == Guerriero.class)
			return new GuerrieroImpl();
		if(c == Stregone.class)
			return new StregoneImpl();
		return null;
	}

}

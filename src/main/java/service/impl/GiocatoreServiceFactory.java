package service.impl;

import datamodel.Giocatore;
import datamodel.Umano;
import datamodel.Virtuale;
import service.GiocatoreService;

public class GiocatoreServiceFactory {
	
	public static <T extends Giocatore> GiocatoreService getGiocatoreService(Class<T> c) {
		if(c == Virtuale.class)
			return new VirtualeImpl();
		if(c == Umano.class)
			return new UmanoImpl();
		return null;
	}

}

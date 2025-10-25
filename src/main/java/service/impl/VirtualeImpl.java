package service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import datamodel.Giocatore;
import datamodel.ListaClassi;
import datamodel.Personaggio;
import datamodel.Stregone;
import service.BuffException;
import service.PersonaggioService;
import service.StaminaInsufficienteException;

public class VirtualeImpl extends GiocatoreAbstractImpl {
	
	@Override
	public void turno(Giocatore gTurno, Giocatore gOdd) {
		Personaggio pgTurno = gTurno.getPg();
		//Personaggio pgOdd = gOdd.getPg();
		PersonaggioService pgimpl = PersonaggioServiceFactory.getPersonaggioService(pgTurno.getClass());
		
		// buff fa in modo che se pgTurno è uno Stregone già sotto effettu di buff, non può fare Attacco2
		boolean buff = false;
		if(pgTurno.getClasse() == ListaClassi.STREGONE && ((Stregone) pgTurno).getCountStatus() > 0)
			buff = true;
		
		// Logica:
		// 1. (se ha meno del 20% della vita AND può curarsi gli HP) OR (non può eseguire un attacco, si cura)
		// 2. se ha tutta la stamina, fa l'attacco peculiare (più costoso)
		// 3. in uno scenario normale, fa l'attacco base
		if((pgTurno.getHp() <= pgTurno.getFullHp() * 20/100 && pgTurno.getNumeroCure() > 0) || pgTurno.getStamina() < pgTurno.getCostoStamina1())
			pgimpl.cura(gTurno);
		else if(pgTurno.getStamina() == pgTurno.getFullStamina() && !buff) {
			try {
	        	pgimpl.attacco2(gTurno, gOdd);
	        }catch(StaminaInsufficienteException | BuffException e) {
	        	//GiocoImpl.getIstanza().inizioTurno();			non può succedere
	        }
		} else {
			try {
	        	pgimpl.attacco1(gTurno, gOdd);
	        }catch(StaminaInsufficienteException e) {
	        	//GiocoImpl.getIstanza().inizioTurno();			non può succedere
	        }
		}
	}
	
	// STREGONE:Esegue un buff casuale tra Atk, Def e Speed
	@Override
	public void sceltaBuff(Giocatore main) {
		StregoneImpl s = new StregoneImpl();
		List<Runnable> buffList = Arrays.asList(
		    () -> s.buffAtk(main),
		    () -> s.buffDef(main),
		    () -> s.buffSpeed(main)
		);

		buffList.get(new Random().nextInt(buffList.size())).run();
	}

}

package Domain.Hand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Domain.Card.*;

public class HandDetector {
	
	
	
	
	public Type detect(List<Card> cards) {
		Objects.requireNonNull(cards);
		if(estSuite(cards)) {
			if(estCouleur(cards))return Type.QUINTE_FLUSH;
			else return Type.SUITE;
		}
		else if(estCouleur(cards))return Type.COULEUR;
		else {
			Map<Rank, Integer> m = compterParRang(cards);
			Integer paires=0;
			boolean brelan = false;
			for(Integer v : m.values()) {
				if(v == 2)paires++;
				if(v == 3) {
					brelan=true;
				}
				if(v==4) {
					return Type.CARRE;
				}
			}
			if(paires == 1) {
				if(brelan == true ) return Type.FULL;
				else return Type.PAIRE;
			}
			if(paires == 2) {
				return Type.DOUBLE_PAIRE;
			}
			if(brelan)return Type.BRELAN;
		}
		return Type.CARTE_HAUTE;
	}
	
	
	private boolean estSuite(List<Card> cards) {
	    List<Card> t = new ArrayList<>(cards);
	    
	    t.sort((a,b)-> a.rank().Ordre() - b.rank().Ordre());
	    
	    if (t.get(0).rank() == Rank.DEUX
	            && t.get(1).rank() == Rank.TROIS
	            && t.get(2).rank() == Rank.QUATRE
	            && t.get(3).rank() == Rank.CINQ
	            && t.get(4).rank() == Rank.AS) {
	            return true;
	        }
		
		for (int i = 0; i < cards.size()-1; i++) {
			int c = t.get(i).rank().Ordre();
			int s = t.get(i+1).rank().Ordre();
			if(c+1 != s)return false;
		}
		return true;
	}
	
	private boolean estCouleur(List<Card> cards) {
		Couleur c = cards.getFirst().color();
		for (Card card : cards) {
			if(!card.color().equals(c)){
				return false;
			}
		}
		return true;
	}
	

	private Map<Rank, Integer> compterParRang(List<Card> cards){
		Map<Rank,Integer> map = new HashMap<>();
		for (Card card : cards) {
		    map.merge(card.rank(), 1, Integer::sum);
		}
		return map;
	}
}

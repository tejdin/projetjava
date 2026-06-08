package domain.hand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import domain.card.*;

public class HandDetector {
	
	
	
	
	public Type detect(List<Card> cards) {
		Objects.requireNonNull(cards);
		if(isStraight(cards)) {
			if(isFlush(cards))return Type.STRAIGHT_FLUSH;
			else return Type.STRAIGHT;
		}
		else if(isFlush(cards))return Type.FLUSH;
		else {
			Map<Rank, Integer> m = countByRank(cards);
			Integer pairs=0;
			boolean threeOfAKind = false;
			for(Integer v : m.values()) {
				if(v == 2)pairs++;
				if(v == 3) {
					threeOfAKind=true;
				}
				if(v==4) {
					return Type.FOUR_OF_A_KIND;
				}
			}
			if(pairs == 1) {
				if(threeOfAKind == true ) return Type.FULL_HOUSE;
				else return Type.PAIR;
			}
			if(pairs == 2) {
				return Type.TWO_PAIR;
			}
			if(threeOfAKind)return Type.THREE_OF_A_KIND;
		}
		return Type.HIGH_CARD;
	}
	
	
	private boolean isStraight(List<Card> cards) {
	    List<Card> t = new ArrayList<>(cards);
	    
	    t.sort((a, b) -> a.rank().order() - b.rank().order());
	    
	    if (t.get(0).rank() == Rank.TWO
	            && t.get(1).rank() == Rank.THREE
	            && t.get(2).rank() == Rank.FOUR
	            && t.get(3).rank() == Rank.FIVE
	            && t.get(4).rank() == Rank.ACE) {
	            return true;
	        }
		
		for (int i = 0; i < cards.size()-1; i++) {
			int c = t.get(i).rank().order();
			int s = t.get(i+1).rank().order();
			if(c+1 != s)return false;
		}
		return true;
	}
	
	private boolean isFlush(List<Card> cards) {
		Suit c = cards.getFirst().suit();
		for (Card card : cards) {
			if(!card.suit().equals(c)){
				return false;
			}
		}
		return true;
	}
	

	private Map<Rank, Integer> countByRank(List<Card> cards){
		Map<Rank,Integer> map = new HashMap<>();
		for (Card card : cards) {
		    map.merge(card.rank(), 1, Integer::sum);
		}
		return map;
	}
}

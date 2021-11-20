package local.pokertrainer;

import local.pokertrainer.models.Card;
import local.pokertrainer.models.CommunityCards;
import local.pokertrainer.models.Deck;
import local.pokertrainer.models.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class Evaluator {

    enum HANDS
    {
        royalFlush, straightFlush, fourOfAKind, fullHouse, flush, straight, threeOfAKind, twoPair, pair, highCard
    }

    public HANDS calculateCurrentBestHand(CommunityCards table, Hand hand) {
        // Calculate the current best 5-card hand out of cards on table and in hand.
        ArrayList<Card> allCards = new ArrayList<Card>();
        if (table != null) {
            allCards.addAll(table.getCardsOnTable());
        }
        if (hand != null) {
            allCards.addAll(hand.getCardsInHand());
        }

        HashMap<String, Integer> suitCount = getSuitsCount(allCards);
        for (String k : suitCount.keySet()) {
            System.out.println("Suit : "+k+" | Count : "+suitCount.get(k));
        }
        if (haveRoyalFlush(allCards)) {
            return HANDS.royalFlush;
        }

        return null;
    }

    public boolean haveRoyalFlush(ArrayList<Card> cards) {
        // TODO : REWRITE THIS FUNCTION

        HashMap<String, Integer> suitCount = getSuitsCount(cards);

        Integer maxCount = 0;
        String maxSuit = "";
        for (String k : suitCount.keySet()){
            if (suitCount.get(k) > maxCount) {
                maxCount = suitCount.get(k);
                maxSuit = k;
            }
        }
        if (maxCount < 5) {
            return false;
        }

        Collections.sort(cards, new SortByCardValue());
        for (Card c : cards) {
            System.out.println(c.getName());
        }

        if (cards.size() > 5) {
            // TODO : check this statement here
            String finalMaxSuit = maxSuit;
            cards.removeIf(card -> {
                return !card.getSuit().equals(finalMaxSuit);
            });
        }

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getValueInt() < 10) {
                return false;
            }
            if ((i != cards.size()-1) && (cards.get(i).getSuit() != cards.get(i+1).getSuit())) {
                return false;
            }
        }
        return true;
    }

    private HashMap<String, Integer> getSuitsCount (ArrayList<Card> cards) {
        // returns a hashmap with count of suits like {hearts: 1, spades: 3, clubs: 1, diamonds: 0}
        HashMap<String, Integer> suitsCount = new HashMap<String, Integer>();

        for (Card c : cards) {
            String suit = c.getSuit();
            if (suitsCount.containsKey(suit)) {
                suitsCount.put(suit, suitsCount.get(suit) + 1);
            } else {
                suitsCount.put(suit, 1);
            }
        }
        return suitsCount;
    }

    class SortByCardValue implements Comparator<Card> {
        public int compare(Card a, Card b) {
            return b.getValueInt() - a.getValueInt();
        }
    }
}

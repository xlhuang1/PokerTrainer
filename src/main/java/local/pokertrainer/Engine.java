package local.pokertrainer;

import local.pokertrainer.models.Card;
import local.pokertrainer.models.CommunityCards;
import local.pokertrainer.models.Deck;
import local.pokertrainer.models.Hand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Engine {

    enum HANDS
    {
        royalFlush, straightFlush, fourOfAKind, fullHouse, flush, straight, threeOfAKind, twoPair, pair, highCard
    }

    public static void main (String[] args) {

        Deck tableDeck = new Deck();
        //tableDeck.shuffleDeck();
        int x = tableDeck.getDeckSize();
        System.out.println("deck has "+x+" cards");
        Engine eng = new Engine();
        eng.run(args);

        Hand newHand = new Hand();
        newHand.drawCard(tableDeck.drawCard());
        newHand.drawCard(tableDeck.drawCard());
        newHand.lookAtHand();

        CommunityCards table = new CommunityCards();
        table.getFlop(tableDeck);
        System.out.println("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        table.getTurn(tableDeck);
        System.out.println("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        table.getRiver(tableDeck);
        System.out.println("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        table.lookAtCommunityCards();
    }

    public void run (String[] args) {
        Hand testHand = new Hand();
        testHand.drawCard(new Card("spade", "ace"));
        testHand.drawCard(new Card("spade", "ten"));
        testHand.drawCard(new Card("spade", "jack"));
        testHand.drawCard(new Card("spade", "three"));
        testHand.drawCard(new Card("spade", "king"));
        testHand.drawCard(new Card("spade", "queen"));
        //testHand.drawCard(new Card("heart", "king"));

        System.out.println("have royal flush "+haveRoyalFlush(testHand.getCardsInHand()));

    }

    public void calculateCurrentBestHand(CommunityCards table, Hand hand) {
        ArrayList<Card> allCards = new ArrayList<Card>();
        allCards.addAll(table.getCardsOnTable());
        allCards.addAll(hand.getCardsInHand());

    }

    private boolean haveRoyalFlush(ArrayList<Card> cards) {
        Collections.sort(cards, new SortByCardValue());
        for (Card c : cards) {
            System.out.println(c.getName());
        }

        if (cards.size() < 5) {
            return false;
        } else if (cards.size() > 5) {
            int size = cards.size();
            for (int i = size-1; i >= 5; i--) {
                cards.remove(i);
            }
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

    class SortByCardValue implements Comparator<Card> {
        public int compare(Card a, Card b) {
            return b.getValueInt() - a.getValueInt();
        }
    }

}

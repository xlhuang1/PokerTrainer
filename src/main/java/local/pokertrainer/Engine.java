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
        tableDeck.shuffleDeck();
        int x = tableDeck.getDeckSize();
        System.out.println("deck has "+x+" cards");
        Engine eng = new Engine();

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
        newHand.updateCardsInHandAndCommunityCards(table.getCardsOnTable());
        for (Card c : newHand.getCardsInHandAndCommunityCards()) {
            System.out.println("You have "+c.getName()+" in hand with value "+c.getValueInt());
        }
        eng.run(args);
    }

    public void run (String[] args) {
        Hand testHand = new Hand();
        testHand.drawCard(new Card("spade", "ace"));
        testHand.drawCard(new Card("spade", "ten"));
        testHand.drawCard(new Card("spade", "jack"));
        testHand.drawCard(new Card("spade", "three"));
        testHand.drawCard(new Card("spade", "king"));
        testHand.drawCard(new Card("spade", "queen"));
        testHand.drawCard(new Card("heart", "king"));

        System.out.println("have royal flush : "+haveRoyalFlush(testHand.getCardsInHand()));

    }

    public void calculateCurrentBestHand(CommunityCards table, Hand hand) {
        ArrayList<Card> allCards = new ArrayList<Card>();
        allCards.addAll(table.getCardsOnTable());
        allCards.addAll(hand.getCardsInHand());

    }

    private boolean haveRoyalFlush(ArrayList<Card> cards) {
        System.out.println("in haveRoyalFlush - cards below ");
        Collections.sort(cards, new SortByCardValue());
        for (Card c : cards) {
            System.out.println(c.getName());
        }

        if (cards.size() < 5) {
            return false;
        }

        int size = cards.size();

        if (!haveFlush(cards)) {
            return false;
        }

        for (int i = 0; i < size; i++) {

            if (cards.get(i).getValueInt() < 10) {
                cards.remove(i);
            }
        }

        if (cards.size() >= 5) {
            System.out.println("cards left : "+cards.size());
            int[]suitsCount = [4];
            int sp = 0;
            int dia = 0;
            int club = 0;
            int heart = 0;
            for (Card c : cards) {
                System.out.println(c.getName());
                switch (c.getSuit()) {
                    case ("spade"):
                        sp++;
                        break;
                    case ("dia"):
                        dia++;
                        break;
                    case ("club"):
                        club++;
                        break;
                    case ("heart"):
                        heart++;
                        break;
                }
            }
            System.out.println("counts: sp "+sp+" dia "+dia+" club "+club+" heart "+heart);

        }
        return true;
    }

    private boolean haveFlush(ArrayList<Card> cards) {
        Collections.sort(cards, new SortByCardValue());
//        for (Card c : cards) {
//            System.out.println(c.getName());
//        }

        if (cards.size() < 5) {
            return false;
        }

        int size = cards.size();
        ArrayList<String> suits = new ArrayList<>();

        for (Card c : cards) {
            if (!suits.contains(c.getSuit())) {
                suits.add(c.getSuit());
                if (suits.size() > 2){
                    return false;
                }
            }
        }
        return true;
    }

    private ArrayList<Card> filterBySuit(ArrayList<Card> cards, String suit) {
        cards.removeIf(c -> (!c.getSuit().equals(suit)));
        return cards;
    }

    class SortByCardValue implements Comparator<Card> {
        public int compare(Card a, Card b) {
            return b.getValueInt() - a.getValueInt();
        }
    }

}

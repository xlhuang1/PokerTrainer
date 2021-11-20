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

    private Evaluator evaluator;

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
        evaluator = new Evaluator();
        Hand testHand = new Hand();
        testHand.drawCard(new Card("spade", "ace"));
        testHand.drawCard(new Card("spade", "ten"));
        testHand.drawCard(new Card("spade", "jack"));
        testHand.drawCard(new Card("spade", "three"));
        testHand.drawCard(new Card("spade", "king"));
        testHand.drawCard(new Card("spade", "queen"));
        //testHand.drawCard(new Card("heart", "king"));

        System.out.println("have royal flush "+evaluator.haveRoyalFlush(testHand.getCardsInHand()));
        evaluator.calculateCurrentBestHand(null, testHand);

    }



}

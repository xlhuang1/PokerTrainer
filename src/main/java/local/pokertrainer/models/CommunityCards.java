package local.pokertrainer.models;

import local.pokertrainer.Debugger;

import java.util.ArrayList;

public class CommunityCards {

    private ArrayList<Card> cardsOnTable;

    public CommunityCards() {
        this.cardsOnTable = new ArrayList<Card>();
    }

    public Deck getFlop(Deck deck) {
        if (deck.getDeckSize() >= 3) {
            for (int i = 0; i < 3; i++) {
                Card c = deck.drawCard();
                this.addCommunityCard(c);
                Debugger.log("Flopped a "+c.getName());
                //System.out.println("Flopped a "+c.getName());
            }
            return deck;
        }
        return null;
    }

    public Deck getTurn(Deck deck) {
        if (deck.getDeckSize() >=2 ) {
            deck.drawCard(); // burn card
            Card c = deck.drawCard();
            this.addCommunityCard(c);
            Debugger.log("Turn card is "+c.getName());
            //System.out.println("Turn card is "+c.getName());
            return deck;
        }
        return null;
    }

    public Deck getRiver(Deck deck) {
        if (deck.getDeckSize() >=2 ) {
            deck.drawCard(); // burn card
            Card c = deck.drawCard();
            this.addCommunityCard(c);
            Debugger.log("River card is "+c.getName());
            //System.out.println("River card is "+c.getName());
            return deck;
        }
        return null;
    }

    public void addCommunityCard (Card c) {
        this.cardsOnTable.add(c);
    }

    public void lookAtCommunityCards() {
        for (Card c : cardsOnTable) {
            System.out.println("There is a "+c.getName()+" on the table");
        }
    }

    public ArrayList<Card> getCardsOnTable () {
        return cardsOnTable;
    }

}

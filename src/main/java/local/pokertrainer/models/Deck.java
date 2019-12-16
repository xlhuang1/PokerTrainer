package local.pokertrainer.models;

import java.util.ArrayList;
import java.util.Random;

public class Deck {

    private ArrayList<Card> CardsInDeck;

    enum SUITS
    {
        spade, heart, club, diamond
    }

    enum VALUES
    {
        two, three, four, five, six, seven, eight, nine, ten, jack, queen, king, ace
    }

    public Deck () {
        this.initializeDeck();
    }

    private void initializeDeck() {
        this.CardsInDeck = new ArrayList<Card>();
        for (SUITS s : SUITS.values()) {
            for (VALUES v : VALUES.values()) {
                Card c = new Card(s.name(),v.name());
                CardsInDeck.add(c);
            }
        }
    }

    public void addCard (Card card) {
        CardsInDeck.add(card);
    }

    public Card drawCard() {
        if (!CardsInDeck.isEmpty()) {
            return CardsInDeck.remove(0);
        }
        System.out.println("WARNING: in drawCard - DECK IS EMPTY");
        return null;
    }

    public void shuffleDeck() {
        Random rand = new Random();
        for (int i = 0; i < 200; i++) {
            Card out = CardsInDeck.remove(rand.nextInt(CardsInDeck.size()));
            CardsInDeck.add(out);
        }
    }

    public int getDeckSize() {
        return CardsInDeck.size();
    }

}

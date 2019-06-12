package local.pokertrainer.models;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cardsInHand;

    public Hand() {
        this.cardsInHand = new ArrayList<Card>();
    }

    public void drawCard(Card c) {
        this.cardsInHand.add(c);
    }

    public void discardCard(Card c) {
        if (cardsInHand.contains(c)) {
            cardsInHand.remove(c);
        }
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void lookAtHand() {
        for (int i = 0; i < cardsInHand.size(); i++) {
            System.out.println("You have "+cardsInHand.get(i).getName());
        }
    }

}

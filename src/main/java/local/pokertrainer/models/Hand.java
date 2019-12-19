package local.pokertrainer.models;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cardsInHand;
    private ArrayList<Card> cardsInHandAndCommunityCards;

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

    public ArrayList<Card> getCardsInHandAndCommunityCards() {
        if (cardsInHandAndCommunityCards == null) return cardsInHand;
        return cardsInHandAndCommunityCards;
    }

    public boolean updateCardsInHandAndCommunityCards(ArrayList<Card> cardsOnTable) {
        ArrayList<Card> cards = (ArrayList) cardsInHand.clone();
        cards.addAll(cardsOnTable);
        cardsInHandAndCommunityCards = cards;
        return true;
    }

    public void lookAtHand() {
        for (int i = 0; i < cardsInHand.size(); i++) {
            System.out.println("You have "+cardsInHand.get(i).getName());
        }
    }

    public void lookAtHandAndTable() {
        if (cardsInHandAndCommunityCards == null) {
            this.lookAtHand();
        } else {
            for (int i = 0; i < cardsInHandAndCommunityCards.size(); i++) {
                System.out.println("You have " + cardsInHandAndCommunityCards.get(i).getName());
            }
        }
    }

}

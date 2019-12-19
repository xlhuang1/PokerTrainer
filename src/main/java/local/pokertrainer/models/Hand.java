package local.pokertrainer.models;

import java.util.ArrayList;

public class Hand {

    private ArrayList<Card> cardsInHand;
    private ArrayList<Card> communityCards;

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

    public ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    public ArrayList<Card> getCardsInHandAndCommunityCards() {
        ArrayList<Card> cards = (ArrayList) cardsInHand.clone();
        if (communityCards != null) {
            cards.addAll(communityCards);
        }
        return cards;
    }

    public void updateCommunityCards(ArrayList<Card> cardsOnTable) {
        ArrayList<Card> cards = (ArrayList) cardsOnTable.clone();
        communityCards = cards;
    }

    public void lookAtHand() {
        for (int i = 0; i < cardsInHand.size(); i++) {
            System.out.println("You have in hand "+cardsInHand.get(i).getName());
        }
    }

    public void lookAtHandAndTable() {
        this.lookAtHand();
        if (communityCards != null) {
            for (int i = 0; i < communityCards.size(); i++) {
                System.out.println("Community Cards " + communityCards.get(i).getName());
            }
        }
    }
}

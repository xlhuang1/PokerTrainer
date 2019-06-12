package local.pokertrainer.models;

public class Player {

    private Hand hand;
    private double chips;


    public Player() {
        this.hand = new Hand();
    }

    public void drawCard (Card c) {
        this.hand.drawCard(c);
    }

    public void lookAtHand () {
        this.hand.lookAtHand();
    }
}

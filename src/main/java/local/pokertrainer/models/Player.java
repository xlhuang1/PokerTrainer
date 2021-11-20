package local.pokertrainer.models;

public class Player {

    private String playerName;
    private Hand hand;
    private double chips;
    private int handRank;
    private int handSubrank;


    public Player(String name) {
        this.hand = new Hand();
        this.playerName = name;
    }

    public void drawCard (Card c) {
        this.hand.drawCard(c);
    }

    public void lookAtHand () {
        this.hand.lookAtHand();
    }

    public Hand getHand() { return this.hand; }

    public void setHandRank (int r) {
        this.handRank = r;
    }

    public int getHandRank() { return this.handRank; }

    public void setHandSubrank (int s) {
        this.handSubrank = s;
    }

    public int getHandSubrank() { return this.handSubrank; }

    public String getPlayerName() { return playerName; }

    public void setPlayerName(String playerName) { this.playerName = playerName; }

}

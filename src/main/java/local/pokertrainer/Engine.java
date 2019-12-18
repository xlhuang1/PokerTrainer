package local.pokertrainer;

import local.pokertrainer.models.Card;
import local.pokertrainer.models.CommunityCards;
import local.pokertrainer.models.Deck;
import local.pokertrainer.models.Hand;

import java.text.DecimalFormat;
import java.util.*;

public class Engine {

    enum HANDS
    {
        royalFlush, straightFlush, fourOfAKind, fullHouse, flush, straight, threeOfAKind, twoPair, pair, highCard
    }

    private static String[] handNames = new String[] {"royalFlush", "straightFlush", "fourOfAKind", "fullHouse", "flush", "straight", "threeOfAKind", "twoPair", "pair", "highCard"};


    enum SUITS
    {
        spade, heart, club, diamond
    }

    private static String[] cardSuits = new String[] {"spade", "heart", "club", "diamond"};

    enum CARDVALUES
    {
        // 2,3,4,5,6,7,8,9,10,11,12,13,14
        two, three, four, five, six, seven, eight, nine, ten, jack, queen, king, ace
    }

    private static String[] cardValues = new String[] {"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};

    private static int[] statistics = new int[10];
    private static DecimalFormat df = new DecimalFormat("0.00");

    public static void main (String[] args) {

        Debugger Debugger = new Debugger();
        Engine eng = new Engine();
        //eng.run(args);
        System.out.println("Running simulation for 1,000,000 games");

        for (int i = 0 ; i < 1000000 ; i++) {
            eng.run(args);
        }
        System.out.println("SIMULATION END.................!");
        System.out.println(Arrays.toString(statistics));
        double[] percentages = new double[10];

        for (int i = 0; i < statistics.length; i++) {
            percentages[i] = Double.parseDouble(df.format((Double.valueOf(statistics[i])/4000000)*100));
        }
        System.out.println(Arrays.toString(percentages));
        //eng.runTest(args);
    }

    public void run (String[] args) {
        Deck tableDeck = new Deck();
        tableDeck.shuffleDeck();
        int x = tableDeck.getDeckSize();
        Debugger.log("deck has "+x+" cards");

        Hand newHand = new Hand();
        Hand newHand2 = new Hand();
        Hand newHand3 = new Hand();
        Hand newHand4 = new Hand();
        newHand.drawCard(tableDeck.drawCard());
        newHand2.drawCard(tableDeck.drawCard());
        newHand3.drawCard(tableDeck.drawCard());
        newHand4.drawCard(tableDeck.drawCard());
        newHand.drawCard(tableDeck.drawCard());
        newHand2.drawCard(tableDeck.drawCard());
        newHand3.drawCard(tableDeck.drawCard());
        newHand4.drawCard(tableDeck.drawCard());
        //newHand.lookAtHand();
        //newHand2.lookAtHand();

        CommunityCards table = new CommunityCards();
        table.getFlop(tableDeck);
        Debugger.log("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        table.getTurn(tableDeck);
        Debugger.log("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        table.getRiver(tableDeck);
        Debugger.log("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        //table.lookAtCommunityCards();
        //newHand.updateCardsInHandAndCommunityCards(table.getCardsOnTable());
        statistics[Math.abs(10-calculateCurrentBestHand(table, newHand))]++;
        statistics[Math.abs(10-calculateCurrentBestHand(table, newHand2))]++;
        statistics[Math.abs(10-calculateCurrentBestHand(table, newHand3))]++;
        statistics[Math.abs(10-calculateCurrentBestHand(table, newHand4))]++;
    }

    public void runTest (String[] args) {
        System.out.println("running test...");
        Hand testHand = new Hand();
        testHand.drawCard(new Card("spade", "ace"));
        testHand.drawCard(new Card("diamond", "ace"));
        testHand.drawCard(new Card("diamond", "six"));
        testHand.drawCard(new Card("club", "three"));
        testHand.drawCard(new Card("spade", "two"));
        testHand.drawCard(new Card("heart", "four"));
        testHand.drawCard(new Card("heart", "king"));

        for (Card c : testHand.getCardsInHand()) {
            System.out.println("You have "+c.getName()+" in hand with value "+c.getValueInt());
        }

//        boolean good = haveStraightFlush(testHand.getCardsInHand());
//        System.out.println("check for straight flush : "+good);
//        boolean haveStraight  = haveStraight(testHand.getCardsInHand());
//        System.out.println("check for straight : "+haveStraight);
//        System.out.println("check for flush : "+ haveFlush(testHand.getCardsInHand()));
//        System.out.println(haveFullHouse(testHand.getCardsInHand()));
//        System.out.println(havePair(testHand.getCardsInHand()));

        calculateCurrentBestHand(null, testHand);

    }

    public Integer calculateCurrentBestHand(CommunityCards table, Hand hand) {
        // Ranks the current best possible hand given the cards on the table and cards in hand
        // Ranks - royalFlush = 10, straightFlush = 9, fourOfAKind = 8, fullHouse = 7, flush = 6, straight = 5, threeOfAKind = 4, twoPair = 3, pair = 2, highCard = 1

        if (table != null) {
            hand.updateCardsInHandAndCommunityCards(table.getCardsOnTable());
        }

        ArrayList<Card> allCards = (ArrayList) hand.getCardsInHandAndCommunityCards().clone();

        int handRank = 0;

        if (haveRoyalFlush(allCards)) {
            handRank = 10;
        } else if (haveStraightFlush(allCards)) {
            handRank = 9;
        } else if (haveFourOfAKind(allCards)) {
            handRank = 8;
        } else if (haveFullHouse(allCards)) {
            handRank = 7;
        } else if (haveFlush(allCards)) {
            handRank = 6;
        } else if (haveStraight(allCards)) {
            handRank = 5;
        } else if (haveThreeOfAKind(allCards)) {
            handRank = 4;
        } else if (haveTwoPair(allCards)) {
            handRank = 3;
        } else if (havePair(allCards)) {
            handRank = 2;
        } else {
            // high card
            handRank = 1;
        }

        //System.out.println("Best hand is : "+handNames[Math.abs(10-handRank)]);
        return handRank;
    }

    public void compareBetterHand (CommunityCards table, Hand hand1, Hand hand2) {

    }

    private boolean haveRoyalFlush(ArrayList<Card> cards) {
        if (cards.size() < 5) {
            return false;
        }

        if (haveStraightFlush(cards)) {
            ArrayList<Card> cardsCopy = new ArrayList<>();
            cardsCopy = (ArrayList<Card>) cards.clone();
            for (int i = 0; i < cardsCopy.size(); i++) {
                if (cardsCopy.get(i).getValueInt() <= 9) {
                    //System.out.println("removing "+cardsCopy.get(i).getName());
                    cardsCopy.remove(i);
                }
            }

            String[] suitsName = new String[] {"spade", "heart", "club", "diamond"};
            int[]suitsCount = countSuits(cardsCopy);
            if (findLargest(suitsCount)[1]>=5) {
                String flushSuit = suitsName[findLargest(suitsCount)[0]];
                ArrayList<Card> filteredCards = filterBySuit(cardsCopy, flushSuit);
                if (haveStraightFlush(filteredCards)) {
//                    System.out.println("royal flush!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//                    for (Card c : filteredCards) {
//                        System.out.println("card : "+c.getName());
//                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean haveStraightFlush(ArrayList<Card> cards) {
        if (cards.size() < 5) {
            return false;
        }

        ArrayList<Card> cardsCopy = new ArrayList<>();
        cardsCopy = (ArrayList<Card>) cards.clone();
        Collections.sort(cardsCopy, new SortByCardValue());

        if (haveFlush(cardsCopy)) {
            String[] suitsName = new String[] {"spade", "heart", "club", "diamond"};
            int[]suitsCount = countSuits(cardsCopy);
            if (findLargest(suitsCount)[1]>=5) {
                String flushSuit = suitsName[findLargest(suitsCount)[0]];
                ArrayList<Card> filteredCards = filterBySuit(cardsCopy, flushSuit);
                //return haveStraight(filteredCards);
                if (haveStraight(filteredCards)) {
//                    System.out.println("****FOUND STRAIGHT FLUSH****");
//                    for (Card c : filteredCards) {
//                        System.out.println("card : "+c.getName());
//                    }
                    return true;
                }
            }
        }
        return false;
    }

    private boolean haveFourOfAKind(ArrayList<Card> cards) {
        int[] cardCount = countCards(cards);
        int[] largest = findLargest(cardCount);

        if (largest[1] == 4) {
            Debugger.log("Found four of a kind in hand : "+cardValues[largest[0]]);
            return true;
        }
        return false;
    }

    private boolean haveFullHouse(ArrayList<Card> cards) {
        int[] cardCount = countCards(cards);
        int[] largest = findLargest(cardCount);

        if (largest[1] == 3) {
            for (int i = 0 ; i < cardCount.length; i++) {
                if ((cardCount[i] >= 2) && (i != largest[0])) {
                    Debugger.log("Found full house in hand : "+cardValues[largest[0]]+"'s full of "+cardValues[i]+"s");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean haveThreeOfAKind(ArrayList<Card> cards) {
        int[] cardCount = countCards(cards);
        int[] largest = findLargest(cardCount);

        if (largest[1] == 3) {
            Debugger.log("Found three of a kind in hand : "+cardValues[largest[0]]);
            return true;
        }
        return false;
    }

    private boolean haveTwoPair(ArrayList<Card> cards) {
        int[] cardCount = countCards(cards);
        int[] largest = findLargest(cardCount);

        if (largest[1] == 2) {
            for (int i = 0 ; i < cardCount.length; i++) {
                if ((cardCount[i] == 2) && (i != largest[0])) {
                    Debugger.log("Found two pair in hand : " + cardValues[largest[0]] + "'s and " + cardValues[i] + "s");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean havePair(ArrayList<Card> cards) {
        int[] cardCount = countCards(cards);
        int[] largest = findLargest(cardCount);

        if (largest[1] == 2) {
            for (int i = 0 ; i < cardCount.length; i++) {
                if ((cardCount[i] >= 2) && (i != largest[0])) return false;
            }
            Debugger.log("Found pair : "+cardValues[largest[0]]+"s");
            return true;
        }
        return false;
    }


    private boolean haveFlush(ArrayList<Card> cards) {
        Collections.sort(cards, new SortByCardValue());

        if (cards.size() < 5) {
            return false;
        }

        int size = cards.size();
        ArrayList<String> suits = new ArrayList<>();

        int[]suitsCount = countSuits(cards);
        if (findLargest(suitsCount)[1]>=5) {
            return true;
        }
        return false;
    }

    private boolean haveStraight(ArrayList<Card> cards) {
        Collections.sort(cards, new SortByCardValue());

        if (cards.size() < 5) {
            return false;
        }

        int size = cards.size();
        ArrayList<Card> straightCards = findBestStraight(cards);
        return straightCards != null;
    }

    private ArrayList<Card> filterBySuit(ArrayList<Card> cards, String suit) {
        cards.removeIf(c -> (!c.getSuit().equals(suit)));
        return cards;
    }

    private static int[] findLargest(int[] numbers){
        // given array of int - returns pair (largest index, largest value)
        int largestIndex = 0;
        int largest = numbers[0];
        for(int i = 1; i < numbers.length; i++){
            if(numbers[i] > largest){
                largest = numbers[i];
                largestIndex = i;
            }
        }
        int[] result = new int[]{largestIndex, largest};
        return result;
    }


    private static int[] countSuits (ArrayList<Card> cards) {
        // counts up the number of each type of suit
        int[]suitsCount = new int[]{0,0,0,0};
        //String[] Suits = new String[] {"spade", "heart", "club", "diamond"};

        HashMap<String, Integer> suitsMap = new HashMap<>();
        suitsMap.put("spade", 0);
        suitsMap.put("heart", 0);
        suitsMap.put("club", 0);
        suitsMap.put("diamond", 0);

        for (Card c : cards) {
            Debugger.log(c.getName());
            switch (c.getSuit()) {
                case ("spade"):
                    suitsMap.put("spade", suitsMap.get("spade")+1);
                    suitsCount[0]++;
                    break;
                case ("heart"):
                    suitsMap.put("heart", suitsMap.get("heart")+1);
                    suitsCount[1]++;
                    break;
                case ("club"):
                    suitsMap.put("club", suitsMap.get("club")+1);
                    suitsCount[2]++;
                    break;
                case ("diamond"):
                    suitsMap.put("diamond", suitsMap.get("diamond")+1);
                    suitsCount[3]++;
                    break;
            }
        }
        Debugger.log("Results : ");
        Debugger.log("spade");
        Debugger.log("heart");
        Debugger.log("club");
        Debugger.log("diamond");
        Debugger.log("counts: "+ Arrays.toString(suitsCount));
        String largestSuit = cardSuits[findLargest(suitsCount)[0]];
        Debugger.log("largestSuit: "+largestSuit);
        return suitsCount;
    }

    private static int[] countCards (ArrayList<Card> cards) {
        int[]cardCount = new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0};
        //String[] cardNames = new String[] {"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};

        for (Card c : cards) {
            Debugger.log(c.getName());
            cardCount[c.getValueInt()-2]++;
        }
        Debugger.log("cardNames : "+Arrays.toString(cardValues));
        Debugger.log("cardCount : "+Arrays.toString(cardCount));
        return cardCount;
    }

    private ArrayList<Card> findBestStraight (ArrayList<Card> cards) {
        // NOTE: A straight is defined as exactly 5 cards that are in contiguous order (i.e. 2,3,4,5,6)
        // NOTE: This function only works for up to 7 cards... i.e. Texas Hold'em board
        // If there is less than 5 cards, return null
        // If there are more than 5 cards, return straight that has the highest value
        // NOTE: THIS SHOULD ONLY BE RUN AFTER FILTER FOR FLUSH TODO...

        if (cards.size() < 5) {
            Debugger.log("not enough cards to make straight - cards.size : "+cards.size());
            return null;
        }

        Collections.sort(cards, new SortByCardValueDesc());

        for (int i = 0; i < cards.size(); i++) {
            Debugger.log("in findBestStraight cards : "+cards.get(i).getName());
        }
        ArrayList<Card> cardsCopy = new ArrayList<>();
        cardsCopy = (ArrayList<Card>) cards.clone();

        if (cardsCopy.size() < 5) {
            return null;
        }

        int[] suitsArray = countSuits(cardsCopy);
        if (findLargest(suitsArray)[1] >=5) {
            Debugger.log("WARNING: possible straight flush - might supercede value");
        }

        ArrayList<Card> longestStraight = new ArrayList<Card>();
        ArrayList<Card> currentStraight = new ArrayList<Card>();
        // put first card as longest straight initially
        longestStraight.add(cardsCopy.get(0));
        currentStraight.add(cardsCopy.get(0));
        cardsCopy.remove(0);

        ArrayList<Card> aces = new ArrayList<Card>();
        while (!cardsCopy.isEmpty() && (cardsCopy.get(cardsCopy.size()-1).getValue().equals("ace"))) {
            aces.add(cardsCopy.get(cardsCopy.size()-1));
            cardsCopy.remove(cardsCopy.size()-1);
        }

        if ((cardsCopy.size() + aces.size()) < 4) {
            // not enough cards not counting ace to make a straight
            Debugger.log("after removing aces not enough cards to make straight - cards.size : "+cardsCopy.size());
            return null;
        }

        if ((longestStraight.get(0).getValueInt() == 2) && !aces.isEmpty()) {
            longestStraight.add(0,aces.get(0));
            currentStraight.add(0,aces.get(0));
        }

        for (Card c : cardsCopy) {
            if ((c.getValueInt() - currentStraight.get(currentStraight.size()-1).getValueInt()) == 1) {
                currentStraight.add(c);
            } else if ((c.getValueInt() - currentStraight.get(currentStraight.size()-1).getValueInt()) == 0) {
                // skip
            } else {
                if (currentStraight.size() >= longestStraight.size()) {
                    longestStraight = (ArrayList) currentStraight.clone();
                }
                currentStraight.clear();
                currentStraight.add(c);
            }
        }

        if (currentStraight.size() > longestStraight.size()) {
            longestStraight = currentStraight;
        }

        if ((longestStraight.get(0).getValueInt() == 10) && !aces.isEmpty()) {
            longestStraight.add(aces.get(0));
        }

        while (longestStraight.size() > 5) {
            longestStraight.remove(0);
        }

        for (int i = 0; i < longestStraight.size(); i++) {
            Debugger.log("Longest Straight : "+longestStraight.get(i).getName());
        }

        if (longestStraight.size() < 5) return null;
        return longestStraight;
    }

    class SortByCardValue implements Comparator<Card> {
        public int compare(Card a, Card b) {
            return b.getValueInt() - a.getValueInt();
        }
    }

    class SortByCardValueDesc implements Comparator<Card> {
        public int compare(Card a, Card b) {
            return a.getValueInt() - b.getValueInt();
        }
    }

}

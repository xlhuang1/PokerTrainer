package local.pokertrainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import local.pokertrainer.models.Card;
import local.pokertrainer.models.CommunityCards;
import local.pokertrainer.models.Deck;
import local.pokertrainer.models.Hand;

import java.util.*;

public class Engine {
    private static final Logger logger = LoggerFactory.getLogger(Engine.class);

    enum HANDS
    {
        royalFlush, straightFlush, fourOfAKind, fullHouse, flush, straight, threeOfAKind, twoPair, pair, highCard
    }

    public static void main (String[] args) {

        Deck tableDeck = new Deck();
        tableDeck.shuffleDeck();
        int x = tableDeck.getDeckSize();
        logger.debug("deck has "+x+" cards");
        Engine eng = new Engine();

        Hand newHand = new Hand();
        newHand.drawCard(tableDeck.drawCard());
        newHand.drawCard(tableDeck.drawCard());
        newHand.lookAtHand();

        CommunityCards table = new CommunityCards();
        table.getFlop(tableDeck);
        logger.debug("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        table.getTurn(tableDeck);
        logger.debug("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        table.getRiver(tableDeck);
        logger.debug("There are "+tableDeck.getDeckSize()+" cards left in the deck");
        table.lookAtCommunityCards();
        newHand.updateCardsInHandAndCommunityCards(table.getCardsOnTable());
        for (Card c : newHand.getCardsInHandAndCommunityCards()) {
            logger.debug("You have "+c.getName()+" in hand with value "+c.getValueInt());
        }
        eng.run(args);
    }

    public void run (String[] args) {
        logger.debug("running test...");
        Hand testHand = new Hand();
        testHand.drawCard(new Card("spade", "ace"));
        testHand.drawCard(new Card("spade", "ten"));
        testHand.drawCard(new Card("spade", "jack"));
        testHand.drawCard(new Card("spade", "three"));
        testHand.drawCard(new Card("spade", "king"));
        testHand.drawCard(new Card("spade", "queen"));
        testHand.drawCard(new Card("heart", "king"));

        boolean good = haveStraightFlush(testHand.getCardsInHand());
        logger.debug("check for straight flush : "+good);
        logger.debug("check for straight : "+haveStraight(testHand.getCardsInHand()));
        logger.debug("check for flush : "+ haveFlush(testHand.getCardsInHand()));

    }

    public void calculateCurrentBestHand(CommunityCards table, Hand hand) {
        ArrayList<Card> allCards = new ArrayList<Card>();
        allCards.addAll(table.getCardsOnTable());
        allCards.addAll(hand.getCardsInHand());

    }

    private boolean haveRoyalFlush(ArrayList<Card> cards) {
        logger.debug("in haveRoyalFlush - cards below ");
        Collections.sort(cards, new SortByCardValue());
        for (Card c : cards) {
            logger.debug(c.getName());
        }

        int size = cards.size();
        if ((!cards.get(size - 1).getValue().equals("ace")) || (cards.size() < 5) || (!haveFlush(cards))) {
            // Do not have royal flush if no Ace in hand, less than 5 cards, or no flush
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (cards.get(i).getValueInt() < 10) {
                cards.remove(i);
            }
        }

        if (cards.size() >= 5) {
            int[]suitsCount = countSuits(cards);
            if (findLargest(suitsCount)[1]>=5) {
                return true;
            }
        }
        return false;
    }

    private boolean haveStraightFlush(ArrayList<Card> cards) {
        ArrayList<Card> cardsCopy = new ArrayList<>();
        if (cards.size() < 5) {
            return false;
        }

        cardsCopy = (ArrayList<Card>) cards.clone();
        Collections.sort(cardsCopy, new SortByCardValue());

        if (haveFlush(cardsCopy)) {
            String[] suitsName = new String[] {"spade", "heart", "club", "diamond"};
            int[]suitsCount = countSuits(cardsCopy);
            if (findLargest(suitsCount)[1]>=5) {
                String flushSuit = suitsName[findLargest(suitsCount)[0]];
                ArrayList<Card> filteredCards = filterBySuit(cardsCopy, flushSuit);
                return haveStraight(filteredCards);
            }
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
        int[]suitsCount = new int[]{0,0,0,0};
        String[] Suits = new String[] {"spade", "heart", "club", "diamond"};

        HashMap<String, Integer> suitsMap = new HashMap<>();
        suitsMap.put("spade", 0);
        suitsMap.put("heart", 0);
        suitsMap.put("club", 0);
        suitsMap.put("diamond", 0);

        for (Card c : cards) {
            logger.debug(c.getName());
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
        logger.debug("Results : ");
        logger.debug("spade count : "+ suitsMap.get("spade"));
        logger.debug("heart count : "+ suitsMap.get("heart"));
        logger.debug("club count : "+ suitsMap.get("club"));
        logger.debug("diamond count : "+ suitsMap.get("diamond"));
        logger.debug("counts: "+ Arrays.toString(suitsCount));
        String largestSuit = Suits[findLargest(suitsCount)[0]];
        logger.debug("largestSuit: "+largestSuit);
        return suitsCount;
    }

    private ArrayList<Card> findBestStraight (ArrayList<Card> cards) {
        // NOTE: A straight is defined as exactly 5 cards that are in contiguous order (i.e. 2,3,4,5,6)
        // NOTE: This function only works for up to 7 cards... i.e. Texas Hold'em board
        // If there is less than 5 cards, return null
        // If there are more than 5 cards, return straight that has the highest value
        // NOTE: THIS SHOULD ONLY BE RUN AFTER FILTER FOR FLUSH TODO...

        if (cards.size() < 5) {
            logger.debug("not enough cards to make straight - cards.size : "+cards.size());
            return null;
        }

        Collections.sort(cards, new SortByCardValueDesc());

        for (int i = 0; i < cards.size(); i++) {
            logger.debug("in findBestStraight cards : "+cards.get(i).getName());
        }
        ArrayList<Card> cardsCopy = new ArrayList<>();
        cardsCopy = (ArrayList<Card>) cards.clone();

        if (cardsCopy.size() < 5) {
            return null;
        }

        int[] suitsArray = countSuits(cardsCopy);
        if (findLargest(suitsArray)[1] >=5) {
            logger.debug("WARNING: possible straight flush - might supercede value");
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
            logger.debug("after removing aces not enough cards to make straight - cards.size : "+cardsCopy.size());
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
                    longestStraight = currentStraight;
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

        for (int i = 0; i < longestStraight.size(); i++) {
            logger.debug("Best Straight : "+longestStraight.get(i).getName());
        }
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

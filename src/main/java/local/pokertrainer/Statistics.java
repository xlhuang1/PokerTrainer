package local.pokertrainer;

import local.pokertrainer.models.Card;

import java.text.DecimalFormat;
import java.util.*;

public class Statistics {
    private static int[] totalHandCount = new int[10];
    private HashMap<String, Integer> winningHandType = new HashMap<>();
    private static String[] handNames = new String[] { "highCard", "pair", "twoPair", "threeOfAKind", "straight",  "flush", "fullHouse", "fourOfAKind",  "straightFlush", "royalFlush"};
    // 0 = highCard, 1 = pair, ... 9 = royalFlush

    private static String[] cardSuits = new String[] {"spade", "heart", "club", "diamond"};
    private static String[] cardValues = new String[] {"two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "jack", "queen", "king", "ace"};
    private static int[] cardIntValues = new int[]{2,3,4,5,6,7,8,9,10,11,12,13,14};
    private String[] cardNames = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};

    private static DecimalFormat df = new DecimalFormat("0.00");

    public Statistics() {
        String[] cName = new String[] { "A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};

        for (int c = 0 ; c < cName.length ; c++) {
            for (int d = c ; d < cName.length ; d++) {
                for (String x : new String[] {"s", "u"}) {
                    winningHandType.put(cName[c]+cName[d]+x, 0);
                }
            }
        }

        for (String c : cName) {
            winningHandType.remove(c+c+"s");
        }

        Debugger.trace("Initialized "+winningHandType.size()+" hand types");

    }

    public void incrementHandCount (int handRank) {
        this.totalHandCount[handRank]++;
    }

    public void incrementWinningHandType (ArrayList<Card> cardsInHand) {
        if (cardsInHand.size() != 2) {
            Debugger.log("WARNING: wrong number of cards in hand!");
        }
        Collections.sort(cardsInHand, new SortByCardValue());

        String cardName = cardNames[cardsInHand.get(0).getValueInt()-2];
        cardName += cardNames[cardsInHand.get(1).getValueInt()-2];

        if (cardsInHand.get(0).isSameSuitAs(cardsInHand.get(1))) {
            cardName += "s";
        } else {
            cardName += "u";
        }

        winningHandType.put(cardName, winningHandType.get(cardName)+1);

        Debugger.log("winningHandType : "+cardName);
        Debugger.log("winningHandType : "+winningHandType.get(cardName));
    }

    public void printStatistics() {
        System.out.println("Printing Statistics...");
        System.out.println("======================================");

        System.out.println("Total Hand Counts : ");
        Double totalC = 0d;
        for ( int i = totalHandCount.length-1; i >= 0; i-- ) {
            totalC += totalHandCount[i];
        }
        for ( int i = totalHandCount.length-1; i >= 0; i-- ) {
            System.out.println(handNames[i]+" : "+df.format(totalHandCount[i]/totalC * 100)+" : "+totalHandCount[i]);
        }

        System.out.println("Winning Hand Types : ");
        Double total = 0d;
        for (String s : winningHandType.keySet()) {
            total += winningHandType.get(s);
        }
        ArrayList<String> sortedKeys = new ArrayList<String>(winningHandType.keySet());
        Collections.sort(sortedKeys);

        for (String s : sortedKeys) {
            System.out.println("Hand : "+s+" win percent : "+df.format(winningHandType.get(s)/total * 100)+" number of wins : "+winningHandType.get(s));
        }

        for (String s : sortedKeys) {
            Character c = s.charAt(2);

            if (c.equals('u')) {
                System.out.println("NORMALIZED Hand : " + s + " win percent : " + df.format((winningHandType.get(s) /(total * 3)) * 2 * 100) + " number of wins : " + winningHandType.get(s)*2/3);
            } else {
                System.out.println("NORMALIZED Hand : " + s + " win percent : " + df.format(winningHandType.get(s) / total * 2 * 100) + " number of wins : " + winningHandType.get(s)*2);
            }
        }


    }

    class SortByCardValue implements Comparator<Card> {
        //descending - (A is largest) 5, 4, 3, 2
        public int compare(Card a, Card b) {
            return b.getValueInt() - a.getValueInt();
        }
    }
}

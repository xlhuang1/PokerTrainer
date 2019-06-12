package local.pokertrainer.models;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Card {

    private String suit;
    private String value;
    private int valueInt;
    private String name;
    private static final Map<String, Integer> valuesMap = Collections.unmodifiableMap(initializeMapping());

    enum SUITS
    {
        spade, heart, club, diamond
    }

    enum VALUES
    {
        // 2,3,4,5,6,7,8,9,10,11,12,13,14
        two, three, four, five, six, seven, eight, nine, ten, jack, queen, king, ace
    }

    public Card(String suit, String value) {
        suit = suit;
        value = value;
        name = value+" of "+suit+"s";
        valueInt = valuesMap.get(value);
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public int getValueInt () {
        return valueInt;
    }

    private static Map<String, Integer> initializeMapping () {
        Map<String, Integer> valMap = new HashMap<String, Integer>();
        int i = 2;
        for (VALUES val : VALUES.values()) {
            //if (i > 10) { i = 10; }
            valMap.put(val.name(), i);
            i++;
        }
        return valMap;
    }

    public boolean isSameSuitAs(Card c) {
        return (this.getSuit() == c.getSuit());
    }


}

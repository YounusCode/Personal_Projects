import java.util.Random;

final class Card
{
    private static final String [] rankName =
    {
        "ace", // 0
        "two", // 1
        "three", // 2
        "four", // 3
        "five", // 4
        "six", // 5
        "seven", // 6
        "eight", // 7
        "nine", // 8
        "ten", // 9
        "jack", // 10
        "queen", // 11
        "king"  // 12
    };
    private static final String [] suitName =
    {
        "clubs", // 0
        "diamonds", // 1
        "hearts", // 2
        "spades" // 3
    };
    private int rank;
    private int suit;
    public Card(int rank, int suit)
    {
        //Constructor. Make a new instance of Card with a given rank and suit. The integer rank must be between 0 and 12. The integer suit must be between 0 and 3.
        if (rank < 0 || rank > 12)
        {
            throw new IllegalArgumentException("Invalid rank, must be between 0 and 12");
        }
        if (suit < 0 || suit > 3)
        {
            throw new IllegalArgumentException("Invalid suit, must be between 0 and 3");
        }
        this.rank = rank;
        this.suit = suit;
    }
    public int getRank()
    {
        //Return the rank of this Card, an int between 0 and 12.
        return rank;
    }
    public int getSuit()
    {
        //Return the suit of this Card, an int between 0 and 3.
        return suit;
    }
    public String toString()
    {
        //Return a String that describes this Card, such as "ace (0) of clubs", "two (1) of hearts", "king (12) of diamonds", etc. This String must be used only for printing.
        return rankName[rank] + " (" + rank + ") of " + suitName[suit];
    }
}
class Deck
{
    private Card[] arrCards;
    private int dealValue = -1;
    public Deck()
    {
        // Constructor. Make an array containing 52 different Card’s. You must use one or more loops: you will receive no points for this method if you write 52 assignment statements. The order of Card’s within the array does not matter.
        int count = 0;
        arrCards = new Card[52];
        for(int i = 0; i <= 3; i += 1)
        {
            for(int j = 0; j <= 12; j += 1)
            {
                arrCards[count] = new Card(j, i);
                count += 1;
            }
        }
    }
    public Card deal()
    {
        //Return the next Card from the array made by the constructor. You need not pick a Card at random, because the Card’s in the array must already be shuffled by the time you call deal. Throw an IllegalStateException if no cards remain to be dealt from the array. This must run in O(1) time.
        if (dealValue >= 52)
        {
            throw new IllegalStateException("There are no cards to be dealt with.");
        }
        dealValue += 1;
        return arrCards[dealValue];
    }
    public void shuffle()
    {
        //Shuffle the deck of Card’s that is represented by the array made in the constructor. The easiest way is the Durstenfeld-Fisher-Yates algorithm, named after its inventors. It works by exchanging randomly chosen pairs of array elements, and runs in O(n) time for an array of size n. You must use the following pseudocode for this algorithm.
        Random randomValue = new Random();
        if (dealValue > 0)
        {
            throw new IllegalStateException("You cannot shuffle after dealing.");
        }
        else
        {
            for(int i = arrCards.length - 1; i > 0; i -= 1)
            {
                int j = Math.abs(randomValue.nextInt()) % i;
                Card newTemp = arrCards[i];
                arrCards[i] = arrCards[j];
                arrCards[j] = newTemp;
            }
        }
    }
}
class Pile
{
    //
    private Layer topPile;
    private class Layer
    {
        private Card card;
        private Layer next;
        private Layer(Card card, Layer next)
        {
            //An instance of this class represents a layer in the Pile. It must have two slots. One must be called card, and it must point to an instance of the class Card. The other must be called next, and it must point to the next Layer in this Pile, or to null. The class Layer must also have a constructor that takes two arguments, called card and next, which determine the values of the two slots.
            this.card = card;
            this.next = next;
        }
    }
    public Pile()
    {
        // Constructor. Initialize a new empty Pile of Card’s.
        topPile = new Layer(null, null);
    }

    public void add(Card card)
    {
        //Put card on top of this Pile. This must run in O(1) time.
        topPile = new Layer(card, topPile);
    }

    public Card draw()
    {
        // Remove a Card from the top of this Pile. Return that Card. Throw an IllegalStateException if there are no Card’s in this Pile. This must run in O(1) time.
        if (topPile.card == null)
        {
            throw new IllegalStateException("There is no card found in the Pile.");
        }
        else
        {
            Card topCard = topPile.card;
            topPile = topPile.next;
            return topCard;
        }
    }
    public boolean isEmpty()
    {
        //Return true if this Pile has no cards left in it. Return false otherwise. This must run in O(1) time.
        return topPile.card == null;
    }
}
class Tableau
{
    private Pile[] pileOfCards;
    public Tableau()
    {
        //Constructor. Make an array with thirteen empty Pile’s in it (see below). Also make a new instance of Deck. Shuffle the Deck. Deal four Card’s from the shuffled Deck into each Pile.
        pileOfCards = new Pile[13];
        Deck deck = new Deck();
        deck.shuffle();
        for(int index = 0; index < pileOfCards.length; index += 1)
        {
            pileOfCards[index] = new Pile();
            for(int j = 0; j <= 3; j += 1)
            {
                Card c = deck.deal();
                pileOfCards[index].add(c);
            }
        }
    }
    public void play()
    {
        //Play a game of solitaire. Each time you get a Card from a Pile, you must print:
        // Got c from pile p.
        // Here c is a String describing the Card, and p is the number of the Pile you got it from. At the end of the game, if the program won, then print:
        // We won!
        // If the program lost, then print:
        // Pile p is empty. We lost!
        //Here p is the number of the empty Pile that the program tried to get a Card from, but could not. See the example for details.
        int p = 0;
        boolean hasWon = true;
        Card c1;
        c1 = pileOfCards[p].draw();
        System.out.println("Got " + c1.toString() + " from pile "+ p + ".");
        while (true)
        {
            if (pileOfCards[p].isEmpty())
            {
                for(int index = 0; index < pileOfCards.length; index += 1)
                {
                    if(!pileOfCards[index].isEmpty())
                    {
                        hasWon = false;
                        break;
                    }
                }
                if (hasWon == true)
                {
                    System.out.println("We won!");
                    break;
                }
                else
                {
                    System.out.println("Pile "+ p +" is Empty. We lost!");
                    break;
                }
            }
            Card c2;
            c2 = pileOfCards[p].draw();
            System.out.println("Got " + c2.toString() + " from pile "+ p + ".");
            if (c1.getSuit() == c2.getSuit())
            {
                p = c1.getRank();
            }
            else
            {
                p = c2.getRank();
                c1 = c2;
            }
        }
    }
}
class GameDriver
{
    //This is the driver class. It must contain only the main method. You must write Game yourself. Hint: make an instance of Tableau and let it do all the work for main.
    public static void main(String[] args)
    {
        Tableau t = new Tableau();
        t.play();
    }
}

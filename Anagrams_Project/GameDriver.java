// Author: Younus Ali



package Project1;



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
        return rank;
    }
  
    public int getSuit()
    {
        return suit;
    }
  
    public String toString()
    {
        return rankName[rank] + " (" + rank + ") of " + suitName[suit];
    }
}



class Deck
{
    private Card[] arrCards;
    private int dealValue = -1;
    public Deck()
    {
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
        if (dealValue >= 52)
        {
            throw new IllegalStateException("There are no cards to be dealt with.");
        }

      
        dealValue += 1;
      
        return arrCards[dealValue];
    }

  
    public void shuffle()
    {
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
    private Layer topPile;
    private class Layer
    {
        private Card card;
        private Layer next;
        private Layer(Card card, Layer next)
        {
            this.card = card;
            this.next = next;
        }
    }

  
    public Pile()
    {
        topPile = new Layer(null, null);
    }

    public void add(Card card)
    {
        topPile = new Layer(card, topPile);
    }

    public Card draw()
    {
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
        return topPile.card == null;
    }
}



class Tableau
{
    private Pile[] pileOfCards;
    public Tableau()
    {
        pileOfCards = new Pile[13];
        Deck deck = new Deck();
        deck.shuffle();
        for (int index = 0; index < pileOfCards.length; index += 1)
        {
            pileOfCards[index] = new Pile();
          
            for (int j = 0; j <= 3; j += 1)
            {
                Card c = deck.deal();
                pileOfCards[index].add(c);
            }
        }
    }
    public void play()
    {
        int p = 0;
        boolean hasWon = true;
        Card c1;
        c1 = pileOfCards[p].draw();
        System.out.println("Got " + c1.toString() + " from pile "+ p + ".");
        while (true)
        {
            if (pileOfCards[p].isEmpty())
            {
                for (int index = 0; index < pileOfCards.length; index += 1)
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
    public static void main(String[] args)
    {
        Tableau t = new Tableau();
        t.play();
    }
}

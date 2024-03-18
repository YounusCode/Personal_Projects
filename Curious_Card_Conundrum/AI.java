// Author: Younus Ali
// Date: October 25th, 2023

package Project_2.project2_testfiles;

public class AI {
    /**
     * Gets the card to play from the given hand on the given card pile.
     * @param hand The hand to search
     * @param cardPile The card pile to play on
     * @return The card to play, or null if no card can be played
     */
    public Card getPlay(Hand hand, CardPile cardPile) {
        for (int i = 0; i < hand.getSize(); i += 1) {
            Card card = hand.get(i);
            if (cardPile.canPlay(card)) {
                return card;
            }
        }
        return null;
    }

    /**
     * Gets the name of this AI.
     * @return The name of this AI
     */
    @Override
    public String toString() {
        return "Random Card AI";
    }
}

/*
 *@brief  Card class being used to represent the 51 cards being used in this game
 * 
 */

public class Card {
    private String suit;
    private String rank;

    Card(String suit, String rank){
        this.suit = suit; this.rank = rank;
    };
    public String getSuit(){return suit;}
    public String getRank(){return rank;}

    @Override
    /*
     * @brief Overriding the toString() method when printing the cards in string form
     * should print something like "9 of hearts" etc
     */
    public String toString(){
        return rank + " of " + suit;
    }
    
    @Override
    /*
     * @brief Overriding .equals() in order to find "pairs" between the cards
     * Pairs are when the ranks are the same and the suits (diamonds <-> hearts) && (clubs <-> spades) are present
     */
    public boolean equals(Object obj){
        Card card2 = (Card) obj;
        if(this.rank.equals(card2.rank) &&((this.suit.equals("Diamonds") && card2.suit.equals("Hearts")) || (this.suit.equals("Hearts") && card2.suit.equals("Diamonds")))){
            return true;
        } 
        else if(this.rank.equals(card2.rank) &&((this.suit.equals("Spades") && card2.suit.equals("Clubs")) || (this.suit.equals("Clubs") && card2.suit.equals("Spades")))){
            return true;
        }
        return false;
    }
}

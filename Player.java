import java.util.ArrayList;
import java.util.Scanner;
/*
 * @brief This is the main player class in this game.
 * @param playerNum     This implements a number to each player playing alongside the dealer, make's it easier to identify who is playing
 */
public class Player {
    protected ArrayList<Card> playerCards; //protected allows only the dealer class to have a copy of this so I don't have to make a manual replica of it
    protected String name; 
    protected String suits[];
    protected String ranks[];
    final private int playerNum; //final make sures the player num isn't accidentally modified by anything
    private int purchasesCommanded;
    private int bonds;
    Player(int playerNum){
        playerCards = new ArrayList<>();
        this.playerNum = playerNum;
        name = "Player #" + playerNum;
        bonds = 0;
        purchasesCommanded = 0;
        suits = new String[]{"Diamonds", "Hearts", "Spades", "Clubs"};
        ranks = new String[]{"Ace", "King", "Queen", "Jack", "10", "9", "8", "7", "6",
            "5", "4", "3", "2"};

    }

    //show player cards
    public void checkPlayerDeck(){
        System.out.println(this + "'s Updated Deck | Bond count: " + this.bonds + " Bonds | Eligible status: " + this.eligibilityPrint());
        for(Card card: this.playerCards){
            System.out.println(card);
        }
    }

    //getter to get the player's cardDeck, this is also accessible by the dealer
    public ArrayList<Card> getPlayerCards(){
        return playerCards;
    }


    //getter and setter for the piece system 
    public int getBondCount(){return bonds;}
    public int setBondCount(int piece){return this.bonds = piece;}


    //getter for player name
    public String getName(){return name;}



    //checks if the player is eligible for the ultimate pay to win.
    public boolean freeWinEligibility(){
        if(this.playerCards.size() <=3 && (this.purchasesCommanded >= 2 && this.purchasesCommanded <= 4) && bonds >= 4 && searchCard("Spades", "King") > -1){
                return true;
        }
        return false;
    }

    //print eligibility
    public String eligibilityPrint(){
        if(freeWinEligibility()){return "Yes";}
        return "No";
    }

    //================================Card Buying System================================//

    public void buyCard(Player adjacent){
        int cost = 2; //make a judgement on how much the cards should cost
        //Ask for payment
        Scanner processPay = new Scanner(System.in);
        adjacent.checkPlayerDeck();
        System.out.println(this + ", what card will you buy from " + adjacent + "? Note: be specific as this system is case-sensitive.");
        String desiredCard = processPay.nextLine();
        String[] messageArr = desiredCard.split(" ");
        
        if(!isSearchable(adjacent, messageArr)){ //check if it's searchable
            System.out.println("That card is not searchable unfortuantely, try again.\n");
            this.buyCard(adjacent);
        }
        if(messageArr[0].equals("King") && messageArr[2].equals("Spades")){ //checks if they are buying the king
            cost = 7;
        }
        if(this.bonds < cost){System.out.println(this + ", why are you trying? you can't even afford this... Unfortunately, you can't buy or grab for your poor decision.\n"); return;} //checks if they can even buy a card

        //if all previous conditions are passed, the payment process is good to go.
        this.playerCards.add(new Card(messageArr[2], messageArr[0]));
        adjacent.getPlayerCards().remove(adjacent.searchCard(messageArr[2], messageArr[0]));
        this.bonds = bonds - cost;
        adjacent.setBondCount(adjacent.getBondCount() + cost);
        this.purchasesCommanded++;

        System.out.println(this + " has successfully bought the " + desiredCard + " for " + cost + " bonds.\n");
    }

    /*
     * @brief checks if the card actually exists in the player's deck 
     * @param checkPlayer   the player the method's checking
     * @param messArr       The array cluster of the input the player sent in buying phase
     *                      ideally should be including the mentioned suit and rank
     */
    private boolean isSearchable(Player checkPlayer, String[] messArr){
        if(messArr.length != 3){return false;} // if the array doesn't work
        for(Card card : checkPlayer.getPlayerCards()){
            if(card.getRank().equals(messArr[0]) && card.getSuit().equals(messArr[2])){
                return true;
            }
        }
        return false;
    }

    public int searchCard(String suit, String rank){
        for(int i = 0; i < this.playerCards.size(); i++){
            if(this.playerCards.get(i).getRank().equals(rank) && this.playerCards.get(i).getSuit().equals(suit)){
                return i;
            }
        }
        return -1; //ideally this will probably never get reached
    }


    //giving classes actual names for when they are printed into the terminal
    @Override
    public String toString(){
        return name;
    }
}

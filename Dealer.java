import java.util.ArrayList;
/*
 * @brief This is the dealer, technically the main player of the game who starts out with all the cards in their possession
 */
public class Dealer extends Player{

    Dealer(){
        super(0);
        name = "Dealer";
        //Initially handing the full deck to the dealer
        
        for(String i : suits){
            for(String j: ranks){
                //we're trying to make an odd pair out, basically single out anyone who holds 
                //a queen of clubs since they have no mutual pair. 
                if(i.equals("Clubs") && j.equals("King")){continue;}

                playerCards.add(new Card(i, j));
            }
        }
    }

    //ideally the dealer will be the only person shuffling the FULL deck
    public ArrayList<Card> shuffleDeck(){
        ArrayList<Card> shuffleDeck = new ArrayList<>();
        for(int i = 0; i < 51; i++){
            int randomIndex = (int)(Math.random() * (playerCards.size()-1));
            shuffleDeck.add(playerCards.get(randomIndex));
            playerCards.remove(randomIndex);
        }
        playerCards = shuffleDeck;
        return playerCards;
    }
    //giving the class a name for when it's printed into the terminal
    @Override
    public String toString(){
        return name;
    }
}

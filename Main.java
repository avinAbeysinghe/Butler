
/*
 * @brief This is BUtler, my alternative to Old Maid, a classic and easy American Card Game.
 * However this game utilizes a currency system, which is slighlty more pay to win
 * Note: This game uses 51/52 of the standard deck
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    private static ArrayList<Player> playerList = new ArrayList<>();
    private static ArrayList<Card> placedCards = new ArrayList<>(); //the cards that have been rid of

    public static void main(String[] args){

        

        /*
         * @brief Introduction to the game
         */
        System.out.println("==================================================================");
        System.out.println("\t\tWill you become the ultimate Butler?");
        System.out.println("==================================================================\nSee README.txt for instructions on how to play this game\n");

        //selection phase: how many players will play alongside the dealer

        int playerCount = setPlayerCount(); 
        for(int i = 0; i < playerCount; i++){playerList.add(new Player(i+1));}
        Dealer dealer = new Dealer();
        System.out.println("Number of players joining in: " + playerList.size());
        
        //shuffle phase: dealer shuffles
        dealer.shuffleDeck();

        delay(500); //adding delays here and there so the game doesn't feel too rushed
        System.out.println("The deck has now been shuffled.");
        
        //Passing out the Cards

        int cardsPerPlayer = 51/(playerList.size() + 1); //players + the dealer
        for(Player player: playerList){
            for(int i =0; i < cardsPerPlayer; i++){
                player.getPlayerCards().add(dealer.getPlayerCards().get(0));
                dealer.getPlayerCards().remove(0);
            }
        }

        System.out.println("The cards have now been passed out. Automatically getting rid of initial card pairs...\n");
        delay(1000); //delay for some suspense

        /*
         * Phase 1(get rid of initial pairs) the dealer is now treated as player in this case
         * Automatically gets rid of the existing pairs that everyone has
         * It will also automatically update everyone about their card decks and the cards placed in the leftover deck(these will no longer be used in game)
         */
        playerList.add(0, dealer); //for the rest of the game, the dealer is now treated as a player(polymorphism)
        for(Player i: playerList){
            checkPairs(i);
        }
        System.out.println("\n");

        for(Player i: playerList){
            i.checkPlayerDeck();
            System.out.println("\n");
        }
        showPlacedCards();

        /*
         * Phase 2(checking phase)
         * Has each player enter an option phase to select what they want to do.
         * "grab"             -> if the player wants to grab a card from the adjacent player(is only allowed to do it once)
         * "buy"              -> allows the player to buy a card from the adjacent player
         * "eligible"         -> if the player is eligible for the free pay to win victory.
         * "view deck"        -> Can view their current deck
         * "view placed deck" -> Can view the cards that have been placed
         * "procede"          -> Can procede by checking for pairs and giving the next player this checking phase
         * 
         * Unlike Old Maid, if a player has no cards, they are FORCED to leave this game no matter what.
         */
        System.out.println("\nWelcome to the second phase of the game. Direct to the README.txt for input instruction!\n");

        //this double nested loop is very redundant, but this was the best way to guarantee no card-less player can play anymore. 
        while(playerList.size() > 1){
            for(int i = 0; i < playerList.size(); i++){
                if(playerList.size() > 1){ //when the loop doesn't automatically detect there is only 1 player left
                    //when a player no longer has gotten rid of all of there cards
                    for(int checkPlayer = i; checkPlayer < playerList.size(); checkPlayer++){
                        if(playerList.get(checkPlayer).getPlayerCards().isEmpty()){
                            tradeBonds(checkPlayer);
                            System.out.println(playerList.get(checkPlayer) + " has no cards left. They are no longer part of this game\n");
                            playerList.remove(i);
                            i--;
                            break;
                        }
                    }
                    options(i, 0);

                } else {
                    break;
                }
            }
        }
        
        //declare who is the ultimate butler
        System.out.println("\n===================================\n" + playerList.get(0) + " has the King of Spades, \ntherefore making them the ultimate Butler!!!\nFurthermore the game is over\n===================================");
    }    



   /*
    * @brief PHASE #2: gives players options on what they want to do in this phase until one player is left solely with the King Of Spades
    *  Or if one player buys is way to win.
    *  It function's recursively until the player want's to stop checking
    * @param pIndex         Index of the players in playerList
    * @param grabChances    Controls how many times the players can use "grab" or "buy", otherwise they will break the rules by grabbing more than once(resets each turn)
    */
    public static void options(int pIndex, int grabChances){
        String answer;
        //logging input
        if(playerList.size() > 1){ //incase the game doesn't check the player count properly on time. Remember they are forced to be eliminated if they have no card left.
            Scanner reader = new Scanner(System.in);
            System.out.println(playerList.get(pIndex) + ", what will you like to do?(Remember you can refer to the README if you're lost)");
            answer = reader.nextLine();
        } else {
            return;
        }
        
        
        //using a switch statement instead of an if statement jungle, should make this much more read-able
        //I also learned from the last game that java has a "rule" switch, where you don't have to worry too much about including breaks
        switch(answer){
            case "grab", "g" -> {
                if(grabChances == 0){
                    if(pIndex == playerList.size()-1){grabCard(pIndex, 0);}
                    else{grabCard(pIndex, pIndex + 1);}
                    options(pIndex, ++grabChances);
                } else {
                    System.out.println("Unfortunately, you are breaking rules, otherwise you will be running the other player dry and I can't allow that. Sorry...\n");
                    options(pIndex, grabChances);
                }
            }
            case "view deck", "v" ->{
                System.out.println("\n");
                playerList.get(pIndex).checkPlayerDeck();
                options(pIndex, grabChances);
            }
            case "view placed deck", "v placed" ->{
                showPlacedCards();
                System.out.println("\n");
                options(pIndex, grabChances);
            }
            case "procede", "p" ->{
                System.out.println("\n");
                checkPairs(playerList.get(pIndex));
            }
            case "eligible", "e" ->{
                if(!playerList.get(pIndex).freeWinEligibility()){
                    System.out.println("You are not eligible to get the free win unfortunately\n");
                    options(pIndex, grabChances);
                } else {
                    System.out.println("\n===================================\n" + playerList.get(pIndex) + " won by pay to win eligibility!!\ntherefore making them the ultimate Butler!!!\nFurthermore the game is over\n===================================");
                    System.exit(0); //exits after this free win.
                }
            }
            case "buy", "b" ->{
                System.out.println("Entering buy process...\n");
                delay(1100);

                if(grabChances == 0){
                    if(pIndex == playerList.size()-1){playerList.get(pIndex).buyCard(playerList.get(0));}
                    else{playerList.get(pIndex).buyCard(playerList.get(pIndex + 1));}
                    options(pIndex, ++grabChances);
                } else {
                    System.out.println("Unfortunately, you are breaking rules by grabbing or buying more than once, otherwise you will be running the other player dry and I can't allow that. Sorry...\n");
                    options(pIndex, grabChances);
                }
            }
            default ->{
                System.out.println("That's not a recognizable command, please check the README.txt for given commands and try again...\n");
                options(pIndex, grabChances);
            }
        }

    }

    //implementing any delays if it's necesary for the game
    public static void delay(int delayTime){
        try{
            Thread.sleep(delayTime);
        } catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    /*
     * @brief checks for any pairs in a player deck through a LINEAR Search. 
     */
    public static void checkPairs(Player player){
        for(int setIndex = 0; setIndex < player.getPlayerCards().size() -1; setIndex++){
            Card checkedCard = player.getPlayerCards().get(setIndex);
            for(int lcv = player.getPlayerCards().size() - 1; lcv >=1; lcv--){
                //if there is a pair
                if(player.getPlayerCards().get(lcv).equals(checkedCard)){
                    //adds the pairs to the placedCards deck while removing the cards from the current player
                    player.setBondCount(player.getBondCount()+1);
                    placedCards.add(player.getPlayerCards().get(lcv));
                    placedCards.add(checkedCard);
                    System.out.print(player + " has discovered a pair: " + player.getPlayerCards().get(lcv) + " && " + checkedCard + "!");
                    System.out.println(" " + player + "'s new Bond total: " + player.getBondCount() + " Bonds.");
                    player.getPlayerCards().remove(lcv);
                    player.getPlayerCards().remove(setIndex);
                    setIndex--; //resets the main forloop as this one gets smaller
                    break;
                } 
            }
        }
    }

    //sets a player limit of 50, otherwise everyone above that limit is not going to get a card to play with properly
    public static int setPlayerCount(){
        Scanner reader = new Scanner(System.in);
        System.out.println("As the dealer, how many other players will be playing with you today?");
        int count = reader.nextInt();

        if(count > 50){
            System.out.println("Sorry, with the number you're inviting, not everyone is going to be able to play properly");
            return setPlayerCount();
        } 
        else {
            return count;
        }
    }

    //shows the cards that have been placed
    public static void showPlacedCards(){
        System.out.println("Cards that have been placed...");
        for(Card card: placedCards){
            System.out.println(card);
        }
    }

    /*
     * Allows a player to grab a random card from the adjacent player's deckm. Announces what card they took as well
     * @param p1  index of the main player
     * @param p2  index of the adjacent player
     */
    public static void grabCard(int p1, int p2){
        int randomCardIndex = (int)(Math.random() * playerList.get(p2).getPlayerCards().size()-1);
        playerList.get(p1).getPlayerCards().add(playerList.get(p2).getPlayerCards().get(randomCardIndex));
        System.out.println(playerList.get(p1) + " grabbed the " + playerList.get(p2).getPlayerCards().get(randomCardIndex) + " from " + playerList.get(p2) + "'s deck!");
        playerList.get(p2).getPlayerCards().remove(randomCardIndex);
    }
    /*
     * @brief makes a transaction to trade all the bonds of the player leaving the game
     * @param playerIndex   This is the index of the player about to leave the game.
     */
    public static void tradeBonds(int playerIndex){
        String playerName;
        Scanner reader = new Scanner(System.in);
        System.out.println(playerList.get(playerIndex) + ", you can no longer play anymore without any cards. Who will you trade your " + playerList.get(playerIndex).getBondCount() + " Bonds to?");
        playerName = reader.nextLine();

        //check if name searchable
        if(!isNameSearchable(playerName)){
            System.out.println("This name isn't searchable, try again...\n");
            tradeBonds(playerIndex);
        }
        int trader = findPlayer(playerName);

        if(trader == -1){ //The program has a weird bug where it re-directs to initializing the trader if you give your bonds to the dealer, so this is required for the program to exit the method properly
            return;
        }
        playerList.get(trader).setBondCount(playerList.get(trader).getBondCount() + playerList.get(playerIndex).getBondCount());
        System.out.println(playerList.get(playerIndex) + " has successfully given all of their bonds to " + playerList.get(trader));
    }
    
    public static int findPlayer(String name){
        for(int i = 0; i < playerList.size(); i++){
            if(playerList.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1; // signify an error, which is intended if this somehow gets reached
    }

    //checks if the player's name is even searchle
    public static boolean isNameSearchable(String name){
        for(Player i: playerList){
            if(name.equals(i.getName())){
                return true;
            }
        }
        return false;
    }
}
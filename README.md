# Welcome to Butler
    a terminal card game heavily inspired by Old Maid, the popular American card game.

->To start, the dealer singles out the King Of Clubs as this game revolves around pairing and we want an odd-one-out type of outcome.

->Dealer then shuffles the deck and hands out the cards to other players and themself(ideally they'd likely have the most cards)

->First Phase: Players and the dealer will place down initial pairs that they have(pairs are cards with the similar color suit and the same rank). 
    The computer will automatically do this process
    A new feature is players get bonds, which is kind of the currency of the game. You gain bonds from discovering pairs

->Second Phase: Once all the inital pairs are placed, Dealer will start the CHECKING PHASE, they can either:
>    - *type "grab" to grab a card from the adjacent player
>    - *type "buy" to buy a card from the adjacent player with a bond. <br>NOTE: buying the special King is worth higher (Costing 7 Bonds)
>    - type "view deck" to view their own deck
>    - type "view placed deck" to view the cards that have been place
>    - type "eligible" If you are eligible to get a completely free win.(Noted further down)
>    - type "procede" to check for a pair and exit off the CHECKING PHASE

    (*can only be done once)

    (Fun fact: there are shorter keystrokes for these terms if you look at the code)
 
 CHECKING PHASE continues until once person is left with the King of Spades(the only card without a pair).

**Whoever has the only non-paired card is the winner and is declared the ultimate "Butler".**

---

### Terms:
 - **Pairs** == Cards with the similar color suit(like diamonds <-> hearts, clubs <-> spades) and the exact same rank


 - **Adjacent** == The player next to the main person in the checking phase
    Example with four players(including Dealer): Player #1 is adjacent to Dealer, Player #2 is Adjacent to Player #1, Player #3 is adjacent to Player #2, Dealer is adjacent to Player #3.

 - **Bonds** == Currency that can be earned through discovering bonds and from players buying cards from you. Obviously you can use this currency to buy cards from an adjacent player.
            Running out of cards forces you to be out, but you can choose who keeps your stack of bonds. 

 - **Eligibility** == Yes, there is a way a player can get a "pay-to-win" outcome. 
        <br>&nbsp;&nbsp;Requirements to be met:
            <br>&nbsp;&nbsp;&nbsp;&nbsp;- Got down to 3 or less cards
            <br>&nbsp;&nbsp;&nbsp;&nbsp;- Have ordered between 2-4 purchases (No less, No more)
            <br>&nbsp;&nbsp;&nbsp;&nbsp;- Have 4 or more Bonds
            <br>&nbsp;&nbsp;&nbsp;&nbsp;- Have to be currently holding the King Of Spades

---

This game isn't really gambling because of how there is inflation with players earning bonds through finding card pairs
and how there is a some sort of shopping system, meaning this game more than likely reflects monopoly than gambling.

Enjoy this java spagetti mess. ***As of now there are also memory leaks***


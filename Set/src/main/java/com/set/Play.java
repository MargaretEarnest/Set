package com.set;

import java.util.ArrayList;

public class Play {

    static ArrayList<Integer> selectedOld;
    static ArrayList<Integer> localSelected;//make sure every check on selected array is checking same array in any given loop

    public static void main(String[] args) throws InterruptedException {
        Deck deck = new Deck();
        deck.shuffle();
        deck.createTable();

        deck.populateTable();

        selectedOld = new ArrayList<Integer>();
        localSelected = new ArrayList<Integer>();

        while(Deck.cardDeck.size() >= 3) {
            int size = Card.selected.size();
            while(size < 3) {
                localSelected = Card.selected;
                // if(!selectedOld.equals(localSelected)) {
                //     deck.populateTable();
                // }
                selectedOld = new ArrayList<Integer>(localSelected);
                if(localSelected.contains(-1)) {
                    if(Deck.tableDeck.size() <= 12) {
                        deck.addThree();
                        // System.out.println("Three cards added!");
                        deck.populateTable();
                    }
                    Card.selected.clear();
                    try {
                        Thread.sleep(10);
                    } catch(InterruptedException ignored) {}
                    continue;
                }
                size = localSelected.size();
                try {
                    Thread.sleep(10);
                } catch(InterruptedException ignored) {}
            }
            int one = Card.selected.get(0);
            int two = Card.selected.get(1);
            int three = Card.selected.get(2);
            if(one == -1 || two == -1 || three == -1) {
                if(Deck.tableDeck.size() <= 12) {
                    deck.addThree();
                    // System.out.println("Three cards added!");
                    deck.populateTable();
                }
                Card.selected.clear();
                continue;
            }
            if(deck.isSet(one, two, three)) {
                // System.out.println("You got it!\n");
                Card.score++;
                deck.removeSet(one,two,three);
                if(Deck.tableDeck.size() < 9) {
                    deck.addThree();
                }
                deck.changeScore(Card.score);
                deck.populateTable();
            }
            Card.selected.clear();
        }
        System.out.println("Out of cards!\nYou win!");
    }
}
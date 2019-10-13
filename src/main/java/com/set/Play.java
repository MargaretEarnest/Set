package com.set;

import java.util.Scanner;


public class Play {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Deck deck = new Deck();
        deck.shuffle();
        deck.createTable();

        deck.populateTable();

        while(deck.cardDeck.size() >= 3) {
            // System.out.println(deck.toStringTable());
            // System.out.print("Which three cards are a set? (enter '-1' to add three if you're stuck) ");
            int size = Card.selected.size();
            while(size < 3) {
                if(Card.selected.contains(-1)) {
                    if(deck.tableDeck.size() <= 12) {
                        deck.addThree();
                        System.out.println("Three cards added!");
                        deck.populateTable();
                        // System.out.println("Cards in deck: " + deck.cardDeck.size() + "\n");
                        Card.selected.clear();
                        try {
                            Thread.sleep(10);
                        } catch(InterruptedException u) {}
                        continue;
                    } else {
                        System.out.println("Come on, there's a set in there somewhere! No more cards for you.\n");
                        Card.selected.clear();
                        try {
                            Thread.sleep(10);
                        } catch(InterruptedException u) {}
                        continue;
                    }
                }
                size = Card.selected.size();
                // System.out.println(size);
                try {
                    Thread.sleep(10);
                } catch(InterruptedException u) {}
            }
            // System.out.println("Out!");
            int one = Card.selected.get(0);
            int two = Card.selected.get(1);
            int three = Card.selected.get(2);
            if(one == -1 || two == -1 || three == -1) {
                if(deck.tableDeck.size() <= 12) {
                    deck.addThree();
                    System.out.println("Three cards added!");
                    deck.populateTable();
                    // System.out.println("Cards in deck: " + deck.cardDeck.size() + "\n");
                    Card.selected.clear();
                    continue;
                } else {
                    System.out.println("Come on, there's a set in there somewhere! No more cards for you.\n");
                    Card.selected.clear();
                    continue;
                }
            }
            if(deck.isSet(one, two, three)) {
                System.out.println("You got it!\n");
                Card.score++;
                deck.removeSet(one,two,three);
                if(deck.tableDeck.size() < 9) {
                    deck.addThree();
                }
                deck.changeScore(Card.score);
                deck.populateTable();
            } else {
                System.out.println("\nNope!\n");
            }
            Card.selected.clear();
        }
        System.out.println("Out of cards!\nYou win!");
        scanner.close();
    }
}
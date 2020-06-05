package com.set;

import java.util.*;

class Deck {
    public static ArrayList<Card> cardDeck = new ArrayList<Card>(81);
    public static ArrayList<Card> tableDeck = new ArrayList<Card>(9);
    public static int padding = 40;
    private static int cardWidth = 170;
    private static int cardHeight = 100;
    private static ShapeLibrary board;

    public Deck() {
        int number, style, color, shape;
        for(int i=0; i<81; i++) {
            int running = i;
            number = running / 27;
            running -= running / 27 * 27;
            style = running / 9;
            running -= running / 9 * 9;
            color = running / 3;
            running -= running / 3 * 3;
            shape = running;
            cardDeck.add(new Card(number + 1, style + 1, color + 1, shape + 1));
        }
        board = new ShapeLibrary();
    }

    public String toStringCards() {
        String result = "";
        for(int i = 0; i< cardDeck.size(); i++) {
            result += cardDeck.get(i).toString();
        }
        result += "\nThe deck has " + cardDeck.size() + " cards";
        return result;
    }

    public String toStringTable() {
        String result = "";
        for(int i = 0; i< tableDeck.size(); i++) {
            result += i + ": " + tableDeck.get(i).toString();
        }
        return result;
    }

    public void shuffle() {
        Collections.shuffle(cardDeck);
    }

    public boolean isSet(int card1, int card2, int card3) {
        boolean isNumber = ((tableDeck.get(card1).getNumber() == tableDeck.get(card2).getNumber() && tableDeck.get(card2).getNumber() == tableDeck.get(card3).getNumber()) || (tableDeck.get(card1).getNumber() != tableDeck.get(card2).getNumber() && tableDeck.get(card2).getNumber() != tableDeck.get(card3).getNumber() && tableDeck.get(card1).getNumber() != tableDeck.get(card3).getNumber()));
        boolean isStyle = ((tableDeck.get(card1).getStyle() == tableDeck.get(card2).getStyle() && tableDeck.get(card2).getStyle() == tableDeck.get(card3).getStyle()) || (tableDeck.get(card1).getStyle() != tableDeck.get(card2).getStyle() && tableDeck.get(card2).getStyle() != tableDeck.get(card3).getStyle() && tableDeck.get(card1).getStyle() != tableDeck.get(card3).getStyle()));
        boolean isColor = ((tableDeck.get(card1).getColor() == tableDeck.get(card2).getColor() && tableDeck.get(card2).getColor() == tableDeck.get(card3).getColor()) || (tableDeck.get(card1).getColor() != tableDeck.get(card2).getColor() && tableDeck.get(card2).getColor() != tableDeck.get(card3).getColor() && tableDeck.get(card1).getColor() != tableDeck.get(card3).getColor()));
        boolean isShape = ((tableDeck.get(card1).getShape() == tableDeck.get(card2).getShape() && tableDeck.get(card2).getShape() == tableDeck.get(card3).getShape()) || (tableDeck.get(card1).getShape() != tableDeck.get(card2).getShape() && tableDeck.get(card2).getShape() != tableDeck.get(card3).getShape() && tableDeck.get(card1).getShape() != tableDeck.get(card3).getShape()));
        return isNumber && isStyle && isColor && isShape && (card1 != card2);
    }

    public void createTable() {
        Random Random = new Random();
        for(int i=0; i<9; i++) {
            int random = Random.nextInt(cardDeck.size());
            tableDeck.add(cardDeck.get(random));
            cardDeck.remove(random);
        }
    }

    public void populateTable() {
        board.clearBoard();
        int row = 1;
        int column = 1;
        int perRow = tableDeck.size()/3;
        int i=0;
        for(Card card : tableDeck) {
            card.displayCard(padding+(cardWidth/2)+((column-1)*(padding+(cardWidth))), 100+((row-1)*(100+ cardHeight)), i, board, /*Card.selected.contains(i)*/false, card);
            column++;
            if(column > perRow) {
                column = 1;
                row++;
            }
            i++;
        }
        board.repaint();
        board.finishWindow();
    }

     public static void populateCard() {
         int row = 1;
         int column = 1;
         int perRow = Deck.tableDeck.size()/3;
         int i=0;
         for(Card card : Deck.tableDeck) {
             card.displayCard(padding+(cardWidth/2)+((column-1)*(padding+(cardWidth))), 100+((row-1)*(100+cardHeight)), i, board, Card.selected.contains(i), card);
             column++;
             if(column > perRow) {
                 column = 1;
                 row++;
             }
             i++;
         }
         board.repaint();
         board.finishWindow();
     }

    public void removeSet(int one, int two, int three) {
        ArrayList<Integer> indexes = new ArrayList<Integer>(3);
        indexes.add(one);
        indexes.add(two);
        indexes.add(three);
        Collections.sort(indexes);
        Collections.reverse(indexes);
        for(int index : indexes) { 		      
            tableDeck.remove(index);
        }
    }

    public void changeScore(int score) {
        board.changeScore(score);
    }

    public void addThree() {
        Random Random = new Random();
        for(int i=0; i<3; i++) {
            int random = Random.nextInt(cardDeck.size());
            tableDeck.add(cardDeck.get(random));
            cardDeck.remove(random);
        }
        board.repaint();
    }
}
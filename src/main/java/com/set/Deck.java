package com.set;

import java.util.*;

class Deck {
    public ArrayList<Card> cardDeck = new ArrayList<Card>(81);
    public ArrayList<Card> tableDeck = new ArrayList<Card>(9);
    public int padding = 40;
    private int cardWidth = 170;
    private int cardHeight = 100;
    private ShapeLibrary board;

    public Deck() {
        int number = 1;
        int style = 1;
        int color = 1;
        int shape = 1;
        for(int i=0; i<81; i++) {
            this.cardDeck.add(new Card(number,style,color,shape));
            if(shape < 3) {
                shape++;
            } else {
                shape = 1;
                if(color < 3) {
                    color++;
                } else {
                    color = 1;
                    if(style < 3) {
                        style++;
                    } else {
                        style = 1;
                        if(number < 3) {
                            number++;
                        }
                    }
                }
            }
        }
        this.board = new ShapeLibrary();
    }

    public String toStringCards() {
        String result = "";
        for(int i=0; i<this.cardDeck.size(); i++) {
            result += this.cardDeck.get(i).toString();
        }
        result += "\nThe deck has " + this.cardDeck.size() + " cards";
        return result;
    }

    public String toStringTable() {
        String result = "";
        for(int i=0; i<this.tableDeck.size(); i++) {
            result += i + ": " + this.tableDeck.get(i).toString();
        }
        return result;
    }

    public void shuffle() {
        Collections.shuffle(this.cardDeck);
    }

    public boolean isSet(int card1, int card2, int card3) {
        boolean isNumber = ((this.tableDeck.get(card1).getNumber() == this.tableDeck.get(card2).getNumber() && this.tableDeck.get(card2).getNumber() == this.tableDeck.get(card3).getNumber()) || (this.tableDeck.get(card1).getNumber() != this.tableDeck.get(card2).getNumber() && this.tableDeck.get(card2).getNumber() != this.tableDeck.get(card3).getNumber() && this.tableDeck.get(card1).getNumber() != this.tableDeck.get(card3).getNumber()));
        boolean isStyle = ((this.tableDeck.get(card1).getStyle() == this.tableDeck.get(card2).getStyle() && this.tableDeck.get(card2).getStyle() == this.tableDeck.get(card3).getStyle()) || (this.tableDeck.get(card1).getStyle() != this.tableDeck.get(card2).getStyle() && this.tableDeck.get(card2).getStyle() != this.tableDeck.get(card3).getStyle() && this.tableDeck.get(card1).getStyle() != this.tableDeck.get(card3).getStyle()));
        boolean isColor = ((this.tableDeck.get(card1).getColor() == this.tableDeck.get(card2).getColor() && this.tableDeck.get(card2).getColor() == this.tableDeck.get(card3).getColor()) || (this.tableDeck.get(card1).getColor() != this.tableDeck.get(card2).getColor() && this.tableDeck.get(card2).getColor() != this.tableDeck.get(card3).getColor() && this.tableDeck.get(card1).getColor() != this.tableDeck.get(card3).getColor()));
        boolean isShape = ((this.tableDeck.get(card1).getShape() == this.tableDeck.get(card2).getShape() && this.tableDeck.get(card2).getShape() == this.tableDeck.get(card3).getShape()) || (this.tableDeck.get(card1).getShape() != this.tableDeck.get(card2).getShape() && this.tableDeck.get(card2).getShape() != this.tableDeck.get(card3).getShape() && this.tableDeck.get(card1).getShape() != this.tableDeck.get(card3).getShape()));
        return isNumber && isStyle && isColor && isShape && (card1 != card2);
    }

    public void createTable() {
        Random Random = new Random();
        for(int i=0; i<9; i++) {
            int random = Random.nextInt(this.cardDeck.size());
            this.tableDeck.add(this.cardDeck.get(random));
            this.cardDeck.remove(random);
        }
    }

    public void populateTable() {
        this.board.clearBoard();
        int row = 1;
        int column = 1;
        int perRow = this.tableDeck.size()/3;
        int i=0;
        for(Card card : this.tableDeck) {
            card.displayCard(padding+(cardWidth/2)+((column-1)*(padding+(cardWidth))), 100+((row-1)*(100+this.cardHeight)), i, this.board);
            column++;
            if(column > perRow) {
                column = 1;
                row++;
            }
            i++;
        }
        this.board.repaint();
        this.board.finishWindow();
    }

    public void removeSet(int one, int two, int three) {
        ArrayList<Integer> indexes = new ArrayList<Integer>(3);
        indexes.add(one);
        indexes.add(two);
        indexes.add(three);
        Collections.sort(indexes);
        Collections.reverse(indexes);
        for(int index : indexes) { 		      
            this.tableDeck.remove(index); 		
        }
    }

    public void changeScore(int score) {
        this.board.changeScore(score);
    }

    public void addThree() {
        Random Random = new Random();
        for(int i=0; i<3; i++) {
            int random = Random.nextInt(this.cardDeck.size());
            this.tableDeck.add(this.cardDeck.get(random));
            this.cardDeck.remove(random);
        }
        this.board.repaint();
    }
}
package com.lennertsoffers.elementalstones.customClasses.models;

public class MoveController {

    // Moves
    private final Move move1;
    private final Move move2;
    private final Move move3;
    private final Move move4;
    private final Move move5;
    private final Move move6;
    private final Move move7;
    private final Move move8;


    // Constructor
    public MoveController() {
        this.move1 = new Move();
        this.move2 = new Move();
        this.move3 = new Move();
        this.move4 = new Move();
        this.move5 = new Move();
        this.move6 = new Move();
        this.move7 = new Move();
        this.move8 = new Move();
    }


    // Getters
    public Move getMove1() {
        return move1;
    }

    public Move getMove2() {
        return move2;
    }

    public Move getMove3() {
        return move3;
    }

    public Move getMove4() {
        return move4;
    }

    public Move getMove5() {
        return move5;
    }

    public Move getMove6() {
        return move6;
    }

    public Move getMove7() {
        return move7;
    }

    public Move getMove8() {
        return move8;
    }

    // Other methods
    public void clearMoves() {
        move1.clearMove();
        move2.clearMove();
        move3.clearMove();
        move4.clearMove();
        move5.clearMove();
        move6.clearMove();
        move7.clearMove();
        move8.clearMove();
    }
}

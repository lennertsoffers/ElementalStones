package com.lennertsoffers.elementalstones.moves;

import com.lennertsoffers.elementalstones.customClasses.models.ActivePlayer;

public class EmptyMove extends Move {

    public EmptyMove(ActivePlayer activePlayer) {
        super(activePlayer, "???????", "empty_stone", "default", 1);
    }

    @Override
    public void useMove() {
        System.out.println("Upgrade your stone to unlock this move");
    }
}

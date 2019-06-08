package game.button.states;

import game.button.ButtonPrototype;

import java.io.Serializable;

public abstract class ButtonState implements Serializable {
    ButtonPrototype context;

    public ButtonState(ButtonPrototype context) {
        this.context = context;
    }

    public abstract void nextState();
    public abstract void setButtonFunctionality();
}

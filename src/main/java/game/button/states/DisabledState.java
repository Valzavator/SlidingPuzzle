package game.button.states;

import game.button.ButtonPrototype;

public class DisabledState extends ButtonState {

    public DisabledState(ButtonPrototype context) {
        super(context);
    }

    @Override
    public void nextState() {
        this.context.setState(this.context.getActiveState());
    }

    @Override
    public void setButtonFunctionality() {
        this.context.setDisable(true);
    }
}

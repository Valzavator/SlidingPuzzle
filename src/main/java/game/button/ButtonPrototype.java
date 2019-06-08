package game.button;


import game.button.states.ActiveState;
import game.button.states.ButtonState;
import game.button.states.DisabledState;
import javafx.scene.control.Button;

import java.io.*;

public abstract class ButtonPrototype extends Button implements Serializable {

    private ButtonState buttonState;
    private ButtonState activeState = new ActiveState(this);
    private ButtonState disabledState = new DisabledState(this);

    private int gameIndex;

    public ButtonPrototype() {
        defaultInit();
    }

    public ButtonPrototype(int gameIndex) {
        this.gameIndex = gameIndex;
        defaultInit();
    }

    private void defaultInit() {
        this.buttonState = new ActiveState(this);
        this.setButtonFunctionality();
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public void setGameIndex(int gameIndex) {
        this.gameIndex = gameIndex;
    }

    public boolean compareGameIndex(int indexToCompare) {
        return this.gameIndex == indexToCompare;
    }

    public void setState(ButtonState newState) {
        this.buttonState = newState;
    }

    public void changeState() {
        this.buttonState.nextState();
    }

    public void setButtonFunctionality() {
        this.buttonState.setButtonFunctionality();
    }

    public void changeButtonFunctionality() {
        this.changeState();
        this.setButtonFunctionality();
    }

    public ButtonState getActiveState() {
        return activeState;
    }

    public ButtonState getDisabledState() {
        return disabledState;
    }

    public ButtonPrototype clone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            ButtonPrototype clone = (ButtonPrototype) ois.readObject();
            clone.setProperties(this);
            return clone;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void setProperties(ButtonPrototype button) {
        this.gameIndex = button.gameIndex;
        this.setStyle(button.getStyle());
        this.setButtonFunctionality();
        this.setFocusTraversable(false);
    }

}

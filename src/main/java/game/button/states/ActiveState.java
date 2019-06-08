package game.button.states;

import game.button.ButtonPrototype;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class ActiveState extends ButtonState {
    public ActiveState(ButtonPrototype context) {
        super(context);
    }

    @Override
    public void nextState() {
        this.context.setState(this.context.getDisabledState());
    }

    @Override
    public void setButtonFunctionality() {
        this.context.setOnMouseEntered(
                event -> context.setBorder(new Border(
                        new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))
                )));
        this.context.setOnMouseExited(event -> context.setBorder(Border.EMPTY));
    }
}

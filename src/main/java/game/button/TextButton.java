package game.button;

public class TextButton extends ButtonPrototype {

    public TextButton() {}

    public TextButton(String text, int countPosition) {
        super(countPosition);
        this.setString(text);
    }

    public void setString(String text) {
        this.setText(text);
    }

    @Override
    protected void setProperties(ButtonPrototype button) {
        super.setProperties(button);
        this.setString(button.getText());
        this.setFont(button.getFont());
    }
}

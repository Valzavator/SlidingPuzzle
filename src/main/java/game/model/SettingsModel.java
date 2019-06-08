package game.model;

import game.enumeration.BoardType;
import game.enumeration.Difficulty;
import game.helpers.Player;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@XmlRootElement(name = "settings")
public class SettingsModel extends Subject {

    private static SettingsModel settingsModel;

    private final static String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/settings/settings.xml";

    private SettingsModel() {
        loadDefaultSettings();
    }

    public static SettingsModel getInstance() {
        if (settingsModel == null)
            if (!loadFromFile())
                settingsModel = new SettingsModel();
        return settingsModel;
    }

    private Difficulty difficulty;

    private BoardType boardType;

    private boolean highlightButtons;
    private boolean multiSwitch;
    private boolean hardmode;
    private boolean playSounds;


    private void loadDefaultSettings() {
        difficulty = Difficulty.Easy;
        boardType = BoardType.ImageBoard;
        highlightButtons = true;
        multiSwitch = false;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public BoardType getBoardType() {
        return boardType;
    }

    public void setBoardType(BoardType boardType) {
        this.boardType = boardType;
    }

    public boolean getHighlightButtons() {
        return highlightButtons;
    }

    @XmlElement
    public void setHighlightButtons(boolean highlightButtons) {
        this.highlightButtons = highlightButtons;
    }

    public boolean getMultiSwitch() {
        return multiSwitch;
    }

    @XmlElement
    public void setMultiSwitch(boolean multiSwitch) {
        this.multiSwitch =  multiSwitch;
    }

    @XmlElement
    public void setPlaySounds(boolean playSounds) {
        this.playSounds = playSounds;
        Player.setPlaySound(playSounds);
    }

    public boolean getPlaySounds() {
        return playSounds;
    }

    @XmlElement
    public void setHardmode(boolean hardmode) {
        this.hardmode = hardmode;
    }

    public boolean getHardmode() {
        return hardmode;
    }

    public void saveToFile() {
        try {
            FileWriter fileWriter = new FileWriter(
                    FILE_PATH,
                    false
            );
            JAXBContext jaxbContext = JAXBContext.newInstance(SettingsModel.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(this, fileWriter);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean loadFromFile() {
        try {
            File file = new File(FILE_PATH);
            JAXBContext jaxbContext = JAXBContext.newInstance(SettingsModel.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            settingsModel = ((SettingsModel) jaxbUnmarshaller.unmarshal(file));
            checkSettings();
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void checkSettings() {
        if (settingsModel.boardType == null)
            settingsModel.boardType = BoardType.TextBoard;
        if (settingsModel.difficulty == null)
            settingsModel.difficulty = Difficulty.Easy;
    }
}

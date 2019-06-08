package game.model.highScores;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "scores")
public class HighScoresModel {
    private final static int MAX_SIZE = 30;
    private final static String FILE_PATH = System.getProperty("user.dir") + "/src/main/resources/high-scores/high-scores.xml";

    private static HighScoresModel highScoresModel;

    private ObservableList<HighScoreUnit> highScoresData;

    private HighScoresModel() {
        highScoresData = FXCollections.observableArrayList();
    }

    public static HighScoresModel getInstance() {
        if (highScoresModel == null) {
            if (!loadFromFile())
                highScoresModel = new HighScoresModel();
        }
        return highScoresModel;
    }

    @XmlElement(name = "score")
    public ObservableList<HighScoreUnit> getHighScoresData() {
        return highScoresData;
    }

    public void setHighScoresData(List<HighScoreUnit> highScoresData) {
        this.highScoresData.addAll(highScoresData);
    }

    public void addUnit(HighScoreUnit highScoreUnit) {
        if (highScoreUnit != null) {
            highScoresData.add(highScoreUnit);
            highScoresModel.controlListSize();
            saveToFile();
        }
    }

    public void reset() {
        highScoresData.removeIf(Objects::nonNull);
    }

    public void saveToFile() {
        try {
            FileWriter fileWriter = new FileWriter(
                    FILE_PATH,
                    false
            );
            JAXBContext jaxbContext = JAXBContext.newInstance(HighScoresModel.class);
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
            JAXBContext jaxbContext = JAXBContext.newInstance(HighScoresModel.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            highScoresModel = ((HighScoresModel) jaxbUnmarshaller.unmarshal(file));
            highScoresModel.controlListSize();
            return true;
        } catch (JAXBException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void controlListSize() {
        while (highScoresData.size() > MAX_SIZE) {
            removeWorstResult();
        }
    }

    private void removeWorstResult() {
        HighScoreUnit worst = highScoresData.get(0);
        for (HighScoreUnit unit : highScoresData) {
            if (Integer.parseInt(unit.getTime()) > Integer.parseInt(worst.getTime()))
                worst = unit;
        }
        highScoresData.remove(worst);
    }

}

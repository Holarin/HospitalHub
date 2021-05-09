package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.util.HashSet;
import java.util.Set;

public class AddController {

    public ToggleGroup time;
    @FXML
    private CheckBox day1;

    @FXML
    private CheckBox day2;

    @FXML
    private CheckBox day3;

    @FXML
    private CheckBox day4;

    @FXML
    private CheckBox day5;

    @FXML
    private RadioButton f1;

    @FXML
    private RadioButton f2;

    @FXML
    private TextField secondNameField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField fatherField;

    @FXML
    private ComboBox<Specialty> specBox;

    @FXML
    private TextField cabField;

    @FXML
    private Button add;

    @FXML
    private Button decline;

    static public Doctor transfer = null;
    @FXML
    void initialize() {
        if (transfer != null) {
            nameField.setText(transfer.getName());
            fatherField.setText(transfer.getFathersName());
            secondNameField.setText(transfer.getSecondName());
            cabField.setText(transfer.getCurrentCabinet());
            for (Days day : transfer.getAvailability().getDays()) {
                switch (day) {
                    case MONDAY: day1.setSelected(true); break;
                    case TUESDAY: day2.setSelected(true); break;
                    case WEDNESDAY: day3.setSelected(true); break;
                    case THURSDAY: day4.setSelected(true); break;
                    case FRIDAY: day5.setSelected(true); break;
                }
            }
            specBox.setValue(transfer.getSpecialty());
        }
        specBox.getItems().addAll(Specialty.values());

        decline.setOnAction(event -> {
            Stage stage = (Stage) decline.getScene().getWindow();
            stage.close();
        });

        add.setOnAction(event -> {
            if (transfer != null) {
                Doctor doc = editDoctor();
                if (doc != null) {
                    Controller.transfer = doc;
                    Stage stage = (Stage) add.getScene().getWindow();
                    stage.close();
                }
                return;
            }
            Doctor doc = getDoctor();
            if (doc == null) {
                return;
            }

            if (Controller.doctors.contains(doc)) {
                showAlert("");
            }
            Controller.transfer = doc;

            Stage stage = (Stage) add.getScene().getWindow();
            stage.close();
        });
    }

    public Doctor getDoctor() {
        Doctor doc = new Doctor();
        Availability availability = getAvailability();
        if (availability.getDays().size() == 0) {
            showAlert("Врач не может не работать");
            return null;
        }
        if (nameField.getText().equals("")) {
            showAlert("Имя не может быть пустым");
            return null;
        }
        doc.setName(nameField.getText());

        if (secondNameField.getText().equals("")) {
            showAlert("Фамилия не может быть пустой");
            return null;
        }
        if (fatherField.getText().equals("")) {
            doc.setFathersName(" ");
        } else {
            doc.setFathersName(fatherField.getText());
        }

        doc.setSecondName(secondNameField.getText());

        if (specBox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Специальность не может быть пустой");
            return null;
        }

        if (cabField.getText().equals("")) {
            showAlert("Кабинет не может отсутствовать");
            return null;
        }

        doc.setCurrentCabinet(cabField.getText());
        doc.setAvailability(availability);
        doc.setSpecialty(specBox.getSelectionModel().getSelectedItem());
        if (Controller.doctors.contains(doc)) {
            showAlert("График и кабинет врача пересекаются с другим врачом");
            return null;
        }

        return doc;
    }

    public Doctor editDoctor() {
        Doctor doc = new Doctor();
        Availability availability = getAvailability();
        if (availability.getDays().size() == 0) {
            showAlert("Врач не может не работать");
            return null;
        }
        if (nameField.getText().equals("")) {
            showAlert("Имя не может быть пустым");
            return null;
        }
        doc.setName(nameField.getText());

        if (secondNameField.getText().equals("")) {
            showAlert("Фамилия не может быть пустой");
            return null;
        }
        if (fatherField.getText().equals("")) {
            doc.setFathersName(" ");
        } else {
            doc.setFathersName(fatherField.getText());
        }

        doc.setSecondName(secondNameField.getText());

        if (specBox.getSelectionModel().getSelectedItem() == null) {
            showAlert("Специальность не может быть пустой");
            return null;
        }

        if (cabField.getText().equals("")) {
            showAlert("Кабинет не может отсутствовать");
            return null;
        }

        doc.setCurrentCabinet(cabField.getText());
        doc.setAvailability(availability);
        doc.setSpecialty(specBox.getSelectionModel().getSelectedItem());
        int counter = 0;
        counter += transfer.equals(doc) ? 0 : 1;
        for (Doctor d : Controller.doctors) {
            if (d.equals(doc)) {
                counter++;
            }
        }
        if (counter >= 2) {
            showAlert("График и кабинет врача пересекаются с другим врачом");
            return null;
        }
        return doc;
    }

    public Availability getAvailability() {
        Set<Days> daysSet = new HashSet<>();
        if (day1.isSelected()) {
            daysSet.add(Days.MONDAY);
        }

        if (day2.isSelected()) {
            daysSet.add(Days.THURSDAY);
        }

        if (day3.isSelected()) {
            daysSet.add(Days.WEDNESDAY);
        }

        if (day4.isSelected()) {
            daysSet.add(Days.THURSDAY);
        }

        if (day5.isSelected()) {
            daysSet.add(Days.FRIDAY);
        }

        Time t = null;

        if (f1.isSelected()) {
            t  = Time.TIME_8_13;
        }

        if (f2.isSelected()) {
            t = Time.TIME_14_19;
        }

        return new Availability(daysSet, t);
    }

    private void showAlert(String info) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(info);

        alert.showAndWait();
    }

}

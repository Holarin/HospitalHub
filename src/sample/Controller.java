package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Availability;
import model.Days;
import model.Doctor;
import model.Specialty;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private MenuItem load;

    @FXML
    private MenuItem save;

    @FXML
    private MenuItem day1;

    @FXML
    private MenuItem day2;

    @FXML
    private MenuItem day3;

    @FXML
    private MenuItem day4;

    @FXML
    private MenuItem day5;

    @FXML
    private MenuItem reset;

    @FXML
    private Button edit;

    @FXML
    private Button add;

    @FXML
    private ComboBox<Specialty> spec;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView<Doctor> table;

    public static Doctor transfer;
    public static List<Doctor> doctors = new ArrayList<>();
    public TableColumn<Doctor, String> name;
    public TableColumn<Doctor, String> secondName;
    public TableColumn<Doctor, Integer> fathersName;
    public TableColumn<Doctor, Specialty> specialty;
    public TableColumn<Doctor, Availability> doctorAvailability;
    public TableColumn<Doctor, String> cabinet;

    @FXML
    void initialize() {
        dataSetup();
        tableSetup();

        day1.setOnAction(event -> searchByDay(Days.MONDAY));

        day2.setOnAction(event -> searchByDay(Days.TUESDAY));

        day3.setOnAction(event -> searchByDay(Days.WEDNESDAY));

        day4.setOnAction(event -> searchByDay(Days.THURSDAY));

        day5.setOnAction(event -> searchByDay(Days.FRIDAY));

        add.setOnAction(event -> {
            openNewScene("add.fxml");
            if (transfer != null) {
                safeAdd(transfer);
                transfer = null;
            }
        });

        edit.setOnAction(event -> {
            if (table.getSelectionModel().getSelectedItem() != null) {
                AddController.transfer = table.getSelectionModel().getSelectedItem();
                transfer = null;
                openNewScene("add.fxml");
                if (transfer != null) {
                    doctors.remove(AddController.transfer);
                    table.getItems().remove(AddController.transfer);
                    AddController.transfer = null;
                    doctors.add(transfer);
                    table.getItems().add(transfer);
                    transfer = null;
                }
            }
        });

        searchBtn.setOnAction(event -> searchBySpecialty(spec.getSelectionModel().getSelectedItem()));

        load.setOnAction(event -> loadFromFile());

        save.setOnAction(event -> saveToFile());

        reset.setOnAction(event -> {
            table.getItems().clear();
            table.getItems().addAll(doctors);
        });
    }

    void searchBySpecialty(Specialty specialty) {
        if (specialty == null) {
            return;
        }

        table.getItems().clear();

        for (Doctor doctor : doctors) {
            if (doctor.getSpecialty().equals(specialty)) {
                table.getItems().add(doctor);
            }
        }
    }

    void safeAdd(Doctor doctor) {
        if (!doctors.contains(doctor)) {
            doctors.add(doctor);
            table.getItems().add(doctor);
        }
    }

    void dataSetup() {
        loadFromFile();
        spec.getItems().addAll(Specialty.values());
    }

    void tableSetup() {
        table.getColumns().clear();
        name = new TableColumn<>("Имя");
        name.setMinWidth(200.);
        name.setCellValueFactory(new PropertyValueFactory<>("Name"));

        secondName = new TableColumn<>("Фамилия");
        secondName.setMinWidth(200.);
        secondName.setCellValueFactory(new PropertyValueFactory<>("SecondName"));

        fathersName = new TableColumn<>("Отчество");
        fathersName.setMinWidth(200.);
        fathersName.setCellValueFactory(new PropertyValueFactory<>("FathersName"));

        specialty = new TableColumn<>("Специальность");
        specialty.setMinWidth(200.);
        specialty.setCellValueFactory(new PropertyValueFactory<>("Specialty"));

        doctorAvailability = new TableColumn<>("Время работы");
        doctorAvailability.setMinWidth(500.);
        doctorAvailability.setCellValueFactory(new PropertyValueFactory<>("Availability"));

        cabinet = new TableColumn<>("Кабинет");
        cabinet.setMinWidth(200.);
        cabinet.setCellValueFactory(new PropertyValueFactory<>("CurrentCabinet"));
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        boolean b = table.getColumns().addAll(name, secondName, fathersName, specialty, doctorAvailability, cabinet);

    }


    public void saveToFile() {
        List<String> csvList = new ArrayList<>();
        for (Doctor doc : doctors) {
            csvList.add(doc.toCsvString());
        }

        FileWriter writer;
        try {
            writer = new FileWriter("data.txt");
            for (String csv : csvList) {
                writer.write(csv + System.getProperty("line.separator"));
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void searchByDay(Days day) {
        table.getItems().clear();
        for (Doctor doc : doctors) {
            if (doc.getAvailability().getDays().contains(day)) {
                table.getItems().add(doc);
            }
        }
    }

    public void loadFromFile() {

        try {
            File file = new File("data.txt");
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            String line = reader.readLine();
            doctors.clear();
            table.getItems().clear();
            while (line != null) {
                safeAdd((Doctor.fromCsvString(line)));
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openNewScene(String window) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.showAndWait();
    }
}

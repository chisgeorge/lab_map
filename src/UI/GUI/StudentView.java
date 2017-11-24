package UI.GUI;

import Domain.Student;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class StudentView {
    private StudentController controller;

    TextField textField_ID = new TextField();
    TextField textField_Nume = new TextField();
    TextField textField_Grupa = new TextField();
    TextField textField_Email = new TextField();
    TextField textField_CadruDidactic = new TextField();

    private Button addStudent_Button = new Button("Adauga Student");
    private Button updateStudent_Button = new Button("Update Student");
    private Button deleteStudent_Button = new Button("Sterge Student");
    private Button clearFields_Button = new Button("Clear fields");

    public BorderPane getView() {
        return view;
    }

    private BorderPane view = new BorderPane();
    private TableView<Student> tableView = new TableView<>();

    public StudentView(StudentController controller) {
        this.controller = controller;
        setupView();
        connectButtons();
        createTableView();
    }

    private void connectButtons() {
        addStudent_Button.setOnAction(controller::handleAddStudent);
        updateStudent_Button.setOnAction(controller::handleUpdateStudent);
        deleteStudent_Button.setOnAction(controller::handleDeleteStudent);
        clearFields_Button.setOnAction(controller::handleClearFields);
    }

    private void setupView() {
        view.setTop(topSetup());
        view.setCenter(centerSetup());
        view.setBottom(BottomSetup());
    }

    private Node topSetup() {
        HBox hBox = new HBox(tableView);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }

    private Node centerSetup() {
        return createGridPane();
    }

    private Node BottomSetup() {
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().addAll(addStudent_Button, updateStudent_Button, deleteStudent_Button, clearFields_Button);
        return hBox;
    }
    private GridPane createGridPane() {
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.BOTTOM_CENTER);
        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(new Label("Nume:"), 0, 1);
        gridPane.add(new Label("Grupa:"), 0, 2);
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(new Label("Cadru Didactic:"), 0, 4);

        gridPane.add(textField_ID, 1, 0);
        gridPane.add(textField_Nume, 1, 1);
        gridPane.add(textField_Grupa, 1, 2);
        gridPane.add(textField_Email, 1, 3);
        gridPane.add(textField_CadruDidactic, 1, 4);

        ColumnConstraints first_columnConstraints = new ColumnConstraints(100d);
        first_columnConstraints.setHalignment(HPos.CENTER);
        ColumnConstraints secound_columnConstraints = new ColumnConstraints(200d);
        secound_columnConstraints.setHalignment(HPos.CENTER);
        gridPane.getColumnConstraints().addAll(first_columnConstraints,secound_columnConstraints);
        return gridPane;
    }
    private void createTableView() {
        TableColumn<Student, String> column_ID = new TableColumn<>("ID");
        TableColumn<Student, String> column_Nume = new TableColumn<>("Nume");
        TableColumn<Student, String> column_Grupa = new TableColumn<>("Grupa");
        TableColumn<Student, String> column_Email = new TableColumn<>("Email");
        TableColumn<Student, String> column_Cadru = new TableColumn<>("Cadru Didactic");

        tableView.getColumns().add(column_ID);
        tableView.getColumns().add(column_Nume);
        tableView.getColumns().add(column_Grupa);
        tableView.getColumns().add(column_Email);
        tableView.getColumns().add(column_Cadru);

        column_ID.setCellValueFactory(new PropertyValueFactory<>("id"));
        column_Nume.setCellValueFactory(new PropertyValueFactory<>("nume"));
        column_Grupa.setCellValueFactory(new PropertyValueFactory<>("grupa"));
        column_Email.setCellValueFactory(new PropertyValueFactory<>("email"));
        column_Cadru.setCellValueFactory(new PropertyValueFactory<>("cadruDidactic"));

        tableView.setItems(controller.getModel());

        tableView.getSelectionModel().selectedItemProperty().
                addListener((observable, oldValue, newValue) -> controller.showStudentDetails(newValue));
    }
}

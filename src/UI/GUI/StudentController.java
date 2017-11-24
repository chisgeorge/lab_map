package UI.GUI;

import Domain.Student;
import Service.StudentService;
import Validator.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import utils.ListEvent;
import utils.Observer;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController implements Observer<Student> {
    private StudentService service;
    private ObservableList<Student> model;

    private StudentView view;

    public StudentController(StudentService service) {
        this.service = service;
        this.model = FXCollections.observableArrayList(service.getAll());
    }

    @Override
    public void notifyEvent(ListEvent<Student> e) {
        model.setAll(StreamSupport.stream(e.getList().spliterator(),false).collect(Collectors.toList()));
    }

    ObservableList<Student> getModel() {
        return model;
    }

    public void setView(StudentView view) {
        this.view = view;
    }

    private Student getStudentFromFields () {
        String idText = view.textField_ID.getText();
        String numeText = view.textField_Nume.getText();
        String grupaText = view.textField_Grupa.getText();
        String emailText = view.textField_Email.getText();
        String cadruDidacticText = view.textField_CadruDidactic.getText();

        return new Student(Integer.valueOf(idText), numeText, Integer.valueOf(grupaText), emailText, cadruDidacticText);
    }

    void showStudentDetails(Student newValue) {
        if(newValue != null) {
            view.textField_ID.setText(String.valueOf(newValue.getId()));
            view.textField_ID.setDisable(true);
            view.textField_Nume.setText(newValue.getNume());
            view.textField_Grupa.setText(String.valueOf(newValue.getGrupa()));
            view.textField_Email.setText(newValue.getEmail());
            view.textField_CadruDidactic.setText(newValue.getCadruDidactic());
        }
    }

    void handleClearFields(ActionEvent actionEvent) {
        clearFields();
    }

    void clearFields()
    {
        view.textField_ID.clear();
        view.textField_ID.setDisable(false);
        view.textField_Nume.clear();
        view.textField_Grupa.clear();
        view.textField_Email.clear();
        view.textField_CadruDidactic.clear();
    }

    void handleAddStudent(ActionEvent actionEvent)
    {
        try {
            service.add(getStudentFromFields());
            showMessage(Alert.AlertType.INFORMATION, "Salvare", "Studentul a fost salvat cu succes!");
        }catch (ValidationException exception)
        {
            showErrorMessage(exception.getMessage());
        }

    }

    void handleUpdateStudent(ActionEvent actionEvent)
    {
        try {
            service.update(getStudentFromFields());
            showMessage(Alert.AlertType.INFORMATION, "Update", "Studentul a fost actualizat cu succes!");
        }catch (ValidationException exception)
        {
            showErrorMessage(exception.getMessage());
        }

    }

    void handleDeleteStudent(ActionEvent actionEvent)
    {
        try {
            service.delete(Integer.valueOf(view.textField_ID.getText()) );
            showMessage(Alert.AlertType.INFORMATION, "Stergere", "Studentul a fost sters cu succes!");
            clearFields();
        }catch (ValidationException exception)
        {
            showErrorMessage(exception.getMessage());
        }

    }

    private static void showMessage(Alert.AlertType type, String header, String text){
        Alert message=new Alert(type);
        message.setHeaderText(header);
        message.setContentText(text);
        message.showAndWait();
    }

    private static void showErrorMessage(String text){
        Alert message=new Alert(Alert.AlertType.ERROR);
        message.setTitle("Mesaj eroare");
        message.setContentText(text);
        message.showAndWait();
    }
}

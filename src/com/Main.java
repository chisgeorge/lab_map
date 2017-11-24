package com;

import Domain.Student;
import Service.StudentService;
import UI.GUI.StudentController;
import UI.GUI.StudentView;
import Validator.ValidatorStudent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import repository.FileRepository.StudentFileRepository;
import repository.Repository;

public class Main extends Application{
    private Repository<Student,Integer> repository_Student = new StudentFileRepository(new ValidatorStudent(),"Student.txt");
    private StudentService service = new StudentService(repository_Student);
    private StudentController controller= new StudentController(service);
    private StudentView view = new StudentView(controller);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Gestiune Note laborator MAP");
        service.addObserver(controller);
        controller.setView(view);
        BorderPane principal_node = view.getView();
        primaryStage.setScene(new Scene(principal_node,400,600));
        primaryStage.show();
        primaryStage.setMinHeight(primaryStage.getHeight());
        primaryStage.setMinWidth(primaryStage.getWidth());
    }
}

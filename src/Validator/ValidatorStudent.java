package Validator;

import Domain.Student;

public class ValidatorStudent implements Validator<Student> {

    String error = "";

    @Override
    public void validate(Student obj) throws ValidationException {

        if(obj.getNume() == "")
            error += "Numele nu poate fi vid \n";

        if(obj.getEmail() == "")
            error += "Emailul nu poate fi vid \n";

        if(obj.getCadruDidactic() == "")
            error += "Cadrul didactic nu poate fi vid \n";

        if(obj.getGrupa() < 220 || obj.getGrupa() > 227)
            error += "Grupa trebuie sa fie intre 220 si 227 \n";

        if(error != "")
            throw new ValidationException(error);
    }
}

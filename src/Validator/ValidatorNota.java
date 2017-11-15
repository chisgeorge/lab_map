package Validator;

import Domain.Nota;

public class ValidatorNota implements Validator<Nota>{

    String error = "";

    @Override
    public void validate(Nota obj) throws ValidationException {
        String error = "";
        if(obj.getNota() <0 || obj.getNota() > 10)
            error += "Nota trebuie sa  fie intre 1-10\n";

    }
}

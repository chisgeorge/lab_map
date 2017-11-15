package Validator;

import Domain.Tema;

public class ValidatorTeme implements Validator<Tema> {
    @Override
    public void validate(Tema obj) throws ValidationException {

        String error = "";

        if(obj.getCerinta().compareTo("") == 0)
            error = error.concat("Cerinta nu poate fi nula \n");


        if(error != "")
            throw new ValidationException(error);
    }
}

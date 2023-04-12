package org.example.validation;
import org.example.domain.Student;

public class StudentValidator implements Validator<Student> {
    public void validate(Student student) throws ValidationException {
        if (student.getID() == null || student.getID().equals("")) {
            throw new ValidationException("ID invalid! \n");
        }
        if (student.getNume() == null || student.getNume().equals("")) {
            throw new ValidationException("Nume invalid! \n");
        }
        if (student.getGrupa() <= 110 || student.getGrupa() >= 938) {
            throw new ValidationException("Grupa invalida! \n");
        }
        String[] digits=new String[]{"0","1","2","3","4","5","6","7","8","9"};
        for(String i:digits)
            if(student.getNume().contains(i)) throw new ValidationException("Nume invalid! \n");
    }
}


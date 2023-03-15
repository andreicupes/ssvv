import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentFileRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;

public class AppTest {
    private Validator<Student> studentValidator = new StudentValidator();
    private Validator<Tema> temaValidator = new TemaValidator();
    private Validator<Nota> notaValidator = new NotaValidator();

    private StudentXMLRepository fileRepository1 = new StudentXMLRepository(studentValidator, "studenti.xml");
    private TemaXMLRepository fileRepository2 = new TemaXMLRepository(temaValidator, "teme.xml");
    private NotaXMLRepository fileRepository3 = new NotaXMLRepository(notaValidator, "note.xml");

    private Service service = new Service(fileRepository1, fileRepository2, fileRepository3);
    @Test
    public void tc_1_saveStudent(){
        int result =service.saveStudent("5","Alex",932);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(4,students.spliterator().getExactSizeIfKnown());
        assertEquals(1,result);
    }

    @Test
    public void tc_2_saveExistingStudent(){
        int result = service.saveStudent("5","Alex",932);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(4,students.spliterator().getExactSizeIfKnown());
        assertEquals(0,result);
        service.deleteStudent("5");
    }

    //student.getGrupa() <= 110 || student.getGrupa() >= 938)
    @Test
    public void tc_3_saveInvalidGroupStudent(){
        int result = 0;
        try{
            result =service.saveStudent("6","Alex",100);
            assertEquals(1,result);
        }catch (ValidationException e){
            assertEquals(0,result);
        }

        try{
            result =service.saveStudent("7","Alex",1000);
            assertEquals(1,result);
        }catch (ValidationException e){
            assertEquals(0,result);
        }

    }

    @Test
    public void tc_4_saveValidGroupStudent(){
        int result = service.saveStudent("7","Alex",932);
        Iterable<Student> students = service.findAllStudents();
        assertEquals(5,students.spliterator().getExactSizeIfKnown());
        assertEquals(1,result);
        service.deleteStudent("7");
    }
}

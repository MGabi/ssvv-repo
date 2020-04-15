package main;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import domain.Nota;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.ValidationException;

public class TestAddGrade {

    public Service service;
    String filenameStudent = "fisiere/StudentiTest.xml";

    @Before
    public void setup() {
        StudentValidator studentValidator = new StudentValidator();
        TemaValidator temaValidator = new TemaValidator();
        String filenameStudent = "fisiere/StudentiTest.xml";
        String filenameTema = "fisiere/TemeTest.xml";
        String filenameNota = "fisiere/NoteTest.xml";

        //StudentFileRepository studentFileRepository = new StudentFileRepository(filenameStudent);
        //TemaFileRepository temaFileRepository = new TemaFileRepository(filenameTema);
        //NotaValidator notaValidator = new NotaValidator(studentFileRepository, temaFileRepository);
        //NotaFileRepository notaFileRepository = new NotaFileRepository(filenameNota);

        StudentXMLRepo studentXMLRepository = new StudentXMLRepo(filenameStudent);
        TemaXMLRepo temaXMLRepository = new TemaXMLRepo(filenameTema);
        NotaValidator notaValidator = new NotaValidator(studentXMLRepository, temaXMLRepository);
        NotaXMLRepo notaXMLRepository = new NotaXMLRepo(filenameNota);
        service = new Service(studentXMLRepository, studentValidator, temaXMLRepository, temaValidator, notaXMLRepository, notaValidator);
    }

    @Test
    public void addGradeSuccess() {
        Nota n = new Nota("123", "1", "1", 2.4, LocalDate.of(2018, 10, 15));
        service.addNota(n, "");
    }

    @Test(expected = ValidationException.class)
    public void addGradeStudentNullFail() {
        Nota n = new Nota("123", null, "1", 2.4, LocalDate.of(2018, 10, 15));
        service.addNota(n, "");
    }

    @Test(expected = ValidationException.class)
    public void addGradeTemaNullFail() {
        Nota n = new Nota("123", "1", null, 2.4, LocalDate.of(2018, 10, 15));
        service.addNota(n, "");
    }

    @Test(expected = ValidationException.class)
    public void addGradeInvalidFail() {
        Nota n = new Nota("123", "1", "1", 11, LocalDate.of(2018, 10, 15));
        service.addNota(n, "");
    }

    @After
    public void drop() {
        service = null;
    }
}

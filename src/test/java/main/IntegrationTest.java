package main;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import domain.Nota;
import domain.Student;
import domain.Tema;
import repository.NotaXMLRepo;
import repository.StudentXMLRepo;
import repository.TemaXMLRepo;
import service.Service;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;

import static org.junit.Assert.*;

public class IntegrationTest {

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
    public void testAddStudentIntegration() {
        Student s = new Student("123", "S1", 1, "email@email.com");
        service.addStudent(s);
    }

    @Test
    public void testAddStudentAssignmentIntegration() {
        testAddStudentIntegration();

        String nrTema = "9999";
        String descriere = "descrierea";
        int deadline = 4;
        int primire = 3;

        Tema tema = new Tema(nrTema, descriere, deadline, primire);
        service.addTema(tema);
    }

    @Test
    public void testAddStudentAssignmentGradeIntegration() {
        testAddStudentAssignmentIntegration();

        Nota n = new Nota("123", "1", "1", 2.4, LocalDate.of(2018, 10, 15));
        service.addNota(n, "");

        List<Nota> grades = StreamSupport.stream(service.getAllNote().spliterator(), true).collect(Collectors.toList());

        boolean result = grades.contains(n);
        assertTrue(result);
    }
}

package cs309.backend.controllers;

import cs309.backend.jpa.entity.user.StudentEntity;
import cs309.backend.DTOs.StudentData;
import cs309.backend.services.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.internalServerError;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Retrieves student information by student ID.
     *
     * @param id The ID of the student to retrieve.
     * @return ResponseEntity containing a StudentData object if successful, or an internal server error.
     */
    @Operation(description = "get student info by student id")
    @GetMapping("id/{id}")
    public ResponseEntity<StudentData> getStudentById(@PathVariable int id) {
        try {
            StudentEntity student = studentService.getStudentByUid(id);
            return ok(StudentData.fromEntity(student));
        } catch (RuntimeException e) {
            e.printStackTrace();
            return internalServerError().build();
        }
    }
}

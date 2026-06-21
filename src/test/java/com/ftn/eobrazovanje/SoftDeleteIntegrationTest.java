package com.ftn.eobrazovanje;

import com.ftn.eobrazovanje.dao.StudentDocumentRepository;
import com.ftn.eobrazovanje.dao.SubjectProfessorRepository;
import com.ftn.eobrazovanje.dao.SubjectStudentAttendanceRepository;
import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.Subject;
import com.ftn.eobrazovanje.domain.entity.user.Professor;
import com.ftn.eobrazovanje.domain.entity.user.Student;
import com.ftn.eobrazovanje.domain.entity.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * Verifies that soft-delete (@SQLDelete) executes for every soft-deletable entity.
 *
 * These entities extend BaseEntity, which is @Version-mapped, so Hibernate binds the
 * version column when issuing the delete. Each @SQLDelete statement must therefore
 * include the version predicate ("... WHERE id = ? and `version` = ?"); a single-parameter
 * statement throws "Parameter index out of range" at delete time (not at compile time).
 * The remove()+flush() here forces that statement to run; @Transactional rolls it back.
 */
@SpringBootTest
@Transactional
class SoftDeleteIntegrationTest {

    @Autowired
    EntityManager em;

    @Autowired
    SubjectStudentAttendanceRepository attendanceRepository;

    @Autowired
    StudentDocumentRepository studentDocumentRepository;

    @Autowired
    SubjectProfessorRepository subjectProfessorRepository;

    private User newUser(String email) {
        User user = new User();
        user.setEmail(email);
        user.setFirstName("probe");
        user.setLastName("probe");
        return user;
    }

    @Test
    void studyProgramSoftDeleteExecutes() {
        StudyProgram sp = new StudyProgram();
        sp.setName("probe");
        sp.setCode("PROBE");
        sp.setDescription("probe");
        em.persist(sp);
        em.flush();
        em.remove(sp);
        em.flush();
    }

    @Test
    void subjectSoftDeleteExecutes() {
        Subject s = new Subject("probe", "probe");
        em.persist(s);
        em.flush();
        em.remove(s);
        em.flush();
    }

    @Test
    void studentSoftDeleteCascadesToUser() {
        Student student = new Student();
        student.setUser(newUser("probe-student@example.com"));
        em.persist(student); // cascade ALL persists the user
        em.flush();
        em.remove(student); // soft-deletes student and cascade soft-deletes user
        em.flush();
    }

    @Test
    void professorSoftDeleteCascadesToUser() {
        Professor professor = new Professor(newUser("probe-professor@example.com"));
        em.persist(professor);
        em.flush();
        em.remove(professor);
        em.flush();
    }

    // The dependent-row cleanup on delete relies on Spring Data derived bulk-delete
    // queries. These confirm each one is a valid query that executes against the schema
    // (the SubjectProfessor one navigates a @MapsId composite key, the riskiest derivation).

    @Test
    void deleteAttendanceByStudentIdExecutes() {
        attendanceRepository.deleteAllByStudent_Id(-1L);
    }

    @Test
    void deleteDocumentsByStudentIdExecutes() {
        studentDocumentRepository.deleteAllByStudent_Id(-1L);
    }

    @Test
    void deleteSubjectProfessorByProfessorIdExecutes() {
        subjectProfessorRepository.deleteAllByProfessor_Id(-1L);
    }
}

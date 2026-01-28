package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.entity.StudyProgram;
import com.ftn.eobrazovanje.domain.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

    List<Subject> findAllByStudyProgram(StudyProgram studyProgram);

}

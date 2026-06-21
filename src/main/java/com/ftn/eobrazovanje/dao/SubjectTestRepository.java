package com.ftn.eobrazovanje.dao;

import com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto;
import com.ftn.eobrazovanje.domain.entity.relational.SubjectTest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubjectTestRepository extends JpaRepository<SubjectTest, Long> {


    @Query("""
    select new com.ftn.eobrazovanje.domain.dto.tests.StudentTestSimpleDto(st.id, st.title, st.dateTime)
    from SubjectTest st
    inner join Subject s on st.subject.id = s.id
    inner join SubjectStudentAttendance ssa on ssa.subject.id = s.id
    where ssa.student.id = :studentId
    and st.dateTime > :dateTime
    and not exists (
        select 1 from SubjectStudentTest sst
        where sst.subjectTest.id = st.id
        and sst.subjectStudentAttendance.id = ssa.id
    )
""")
    List<StudentTestSimpleDto> findAllStudentTestSimpleDtosByStudentId(Long studentId, LocalDateTime dateTime);

    List<SubjectTest> findAllBySubject_IdIn(List<Long> subjectIds);

}

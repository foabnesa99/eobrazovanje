# Admin Delete & Update Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a delete action to every admin list-card and an update (edit-in-card) flow for students and professors, across the Spring Boot backend and the Angular frontend.

**Architecture:** Deletes are soft-deletes for listed entities (reuse the existing `@SQLDelete` mechanism, add a `deleted = false` query filter) and physical removals/unassigns for association rows. Student/professor updates are orchestrated in `UserServiceImpl` (which already owns user creation) and exposed via new `PUT` endpoints. The frontend adds service methods, two update-request models, an inline edit form on the student card and professor list, and delete buttons on every card.

**Tech Stack:** Java 17 / Spring Boot 3 / Hibernate 6.5.2.Final / MySQL; Angular 17 standalone components + Bootstrap 5.

## Global Constraints

- Hibernate is **6.5.2.Final** → use `org.hibernate.annotations.SQLRestriction` (not the deprecated `@Where`) for the `deleted = false` filter.
- Entity-id contract: student/professor operations use the **Student.id / Professor.id** (entity id), never the User id. Dropdowns bind `[value]="x.id"` from `StudyProgramDto.id` / `ProfessorDto.id`.
- DB is MySQL with `ddl-auto=update` and snake_case physical naming. Raw `@SQLDelete` SQL must use the real table names: `user`, `subject`, `study_program`, `student`, `professor`.
- Follow the existing `@SQLDelete` style already in `User` (no `version` in the WHERE clause — known-working in this codebase).
- **No test harness exists** (only a default `@SpringBootTest` context-load). Each task's verification cycle is **compile/build + targeted manual check**, not new unit tests. Do not scaffold a new test framework — it is out of scope.
- Backend build/verify command (no DB needed): `./gradlew compileJava`
- Frontend build/verify command: run from `/home/nebojsa/faks/eobr/eobrazovanje-client/eobrazovanje-client` → `npx ng build --configuration development`
- Frontend admin base URL is `http://localhost:8080/api/admin`.

---

## Backend

### Task 1: Soft-delete annotations on listed entities

Make `Student`, `Professor`, `StudyProgram` soft-delete like `User`/`Subject` already do, and add a `deleted = false` filter to all five so removed rows vanish from every query. This also fixes a latent bug: `Subject`/`User` already soft-delete but were never filtered.

**Files:**
- Modify: `src/main/java/com/ftn/eobrazovanje/domain/entity/user/User.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/domain/entity/Subject.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/domain/entity/StudyProgram.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/domain/entity/user/Student.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/domain/entity/user/Professor.java`

**Interfaces:**
- Consumes: nothing.
- Produces: soft-delete behavior — `repository.delete*(...)` on these entities sets `deleted = true`; all reads exclude `deleted = true`.

- [ ] **Step 1: Add the filter import + annotation to `User`**

`User` already has `@SQLDelete(...)`. Add the import and the restriction annotation on the class (next to `@SQLDelete`):

```java
import org.hibernate.annotations.SQLRestriction;
```

```java
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class User extends BaseEntity{
```

- [ ] **Step 2: Add the filter to `Subject`**

`Subject` already has `@SQLDelete(...)`. Add the import and annotation:

```java
import org.hibernate.annotations.SQLRestriction;
```

```java
@SQLDelete(sql = "UPDATE subject SET deleted = true WHERE id = ? and `version` = ?")
@SQLRestriction("deleted = false")
public class Subject extends BaseEntity {
```

- [ ] **Step 3: Add soft-delete + filter to `StudyProgram`**

Add imports and annotations (table name is `study_program`):

```java
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
```

```java
@Entity
@Table
@SQLDelete(sql = "UPDATE study_program SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class StudyProgram extends BaseEntity {
```

- [ ] **Step 4: Add soft-delete + filter to `Student`**

Add the `SQLRestriction` import (`SQLDelete` is already imported in `Student`) and annotations:

```java
import org.hibernate.annotations.SQLRestriction;
```

```java
@Entity(name = "student")
@Table
@SQLDelete(sql = "UPDATE student SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Student extends BaseEntity{
```

- [ ] **Step 5: Add soft-delete + filter to `Professor`**

Add imports and annotations:

```java
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
```

```java
@Entity(name = "professor")
@Table
@SQLDelete(sql = "UPDATE professor SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Professor extends BaseEntity {
```

- [ ] **Step 6: Compile**

Run: `./gradlew compileJava`
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 7: Commit**

```bash
git add src/main/java/com/ftn/eobrazovanje/domain/entity/user/User.java \
        src/main/java/com/ftn/eobrazovanje/domain/entity/Subject.java \
        src/main/java/com/ftn/eobrazovanje/domain/entity/StudyProgram.java \
        src/main/java/com/ftn/eobrazovanje/domain/entity/user/Student.java \
        src/main/java/com/ftn/eobrazovanje/domain/entity/user/Professor.java
git commit -m "feat: soft-delete + deleted filter on listed entities"
```

---

### Task 2: Add IDs to pairing DTOs

The pairing cards need ids to issue deletes, but `SubjectProfessorPairingDto` and `StudyProgramSubjectPairingDto` currently carry only display strings. Add the ids and populate them in the builders.

**Files:**
- Modify: `src/main/java/com/ftn/eobrazovanje/domain/dto/subjectProfessor/SubjectProfessorPairingDto.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/domain/dto/studyProgramSubject/StudyProgramSubjectPairingDto.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/service/impl/SubjectProfessorServiceImpl.java` (method `findAllPairings`, ~line 92)
- Modify: `src/main/java/com/ftn/eobrazovanje/service/impl/SubjectServiceImpl.java` (method `findAllProgramPairings`, ~line 63)

**Interfaces:**
- Produces:
  - `SubjectProfessorPairingDto(Long subjectId, Long professorId, String subjectTitle, String professorName, ProfessorRole professorRole)`
  - `StudyProgramSubjectPairingDto(Long subjectId, String subjectTitle, String studyProgramCode, String studyProgramName)`

- [ ] **Step 1: Add ids to `SubjectProfessorPairingDto`**

Place the id fields first so the all-args constructor signature is `(subjectId, professorId, subjectTitle, professorName, professorRole)`:

```java
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubjectProfessorPairingDto {

    private Long subjectId;
    private Long professorId;
    private String subjectTitle;
    private String professorName;
    private ProfessorRole professorRole;

}
```

- [ ] **Step 2: Populate ids in `findAllPairings`**

In `SubjectProfessorServiceImpl.findAllPairings()`, update the mapping:

```java
public List<SubjectProfessorPairingDto> findAllPairings() {
    return subjectProfessorRepository.findAll().stream()
            .map(subjectProfessor -> new SubjectProfessorPairingDto(
                    subjectProfessor.getSubject().getId(),
                    subjectProfessor.getProfessor().getId(),
                    subjectProfessor.getSubject().getTitle(),
                    subjectProfessor.getProfessor().getUser().getFullName(),
                    subjectProfessor.getProfessorRole()))
            .toList();
}
```

(Keep the existing stream source — `subjectProfessorRepository.findAll()` — exactly as it is; only the `new SubjectProfessorPairingDto(...)` arguments change.)

- [ ] **Step 3: Add id to `StudyProgramSubjectPairingDto`**

```java
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudyProgramSubjectPairingDto {

    private Long subjectId;
    private String subjectTitle;
    private String studyProgramCode;
    private String studyProgramName;

}
```

- [ ] **Step 4: Populate id in `findAllProgramPairings`**

In `SubjectServiceImpl.findAllProgramPairings()`:

```java
public List<StudyProgramSubjectPairingDto> findAllProgramPairings() {
    return subjectRepository.findAll().stream()
            .filter(subject -> subject.getStudyProgram() != null)
            .map(subject -> new StudyProgramSubjectPairingDto(
                    subject.getId(),
                    subject.getTitle(),
                    subject.getStudyProgram().getCode(),
                    subject.getStudyProgram().getName()))
            .toList();
}
```

- [ ] **Step 5: Compile**

Run: `./gradlew compileJava`
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 6: Commit**

```bash
git add src/main/java/com/ftn/eobrazovanje/domain/dto/subjectProfessor/SubjectProfessorPairingDto.java \
        src/main/java/com/ftn/eobrazovanje/domain/dto/studyProgramSubject/StudyProgramSubjectPairingDto.java \
        src/main/java/com/ftn/eobrazovanje/service/impl/SubjectProfessorServiceImpl.java \
        src/main/java/com/ftn/eobrazovanje/service/impl/SubjectServiceImpl.java
git commit -m "feat: expose ids on pairing DTOs for delete"
```

---

### Task 3: Backend delete endpoints

Add the six delete endpoints to `AdminController`, plus the one new service method they need (`removeSubjectFromProgram`). All other backing methods already exist (`studentService.delete`, `professorService.delete`, `subjectService.delete`, `studyProgramService.delete`, `subjectProfessorService.delete`).

**Files:**
- Modify: `src/main/java/com/ftn/eobrazovanje/service/SubjectService.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/service/impl/SubjectServiceImpl.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/controller/AdminController.java`

**Interfaces:**
- Consumes: `studentService.delete(Long)`, `professorService.delete(Long)`, `subjectService.delete(Long)`, `studyProgramService.delete(Long)`, `subjectProfessorService.delete(Long subjectId, Long professorId)`.
- Produces: `subjectService.removeSubjectFromProgram(Long subjectId)`; HTTP routes documented below.

- [ ] **Step 1: Declare `removeSubjectFromProgram` in `SubjectService`**

Add to the interface:

```java
void removeSubjectFromProgram(Long subjectId);
```

- [ ] **Step 2: Implement `removeSubjectFromProgram` in `SubjectServiceImpl`**

```java
@Override
public void removeSubjectFromProgram(Long subjectId) {
    Subject subject = getById(subjectId);
    subject.setStudyProgram(null);
    update(subject);
}
```

- [ ] **Step 3: Inject the missing services into `AdminController`**

`AdminController` uses `@AllArgsConstructor`, so add two fields to the existing dependency list:

```java
private final StudentService studentService;
private final ProfessorService professorService;
```

Add the imports:

```java
import com.ftn.eobrazovanje.service.StudentService;
import com.ftn.eobrazovanje.service.ProfessorService;
```

(`StudentService`/`ProfessorService` are in `com.ftn.eobrazovanje.service`; the existing `import com.ftn.eobrazovanje.service.*;` already covers them — only add explicit imports if the wildcard is absent.)

- [ ] **Step 4: Add the six delete endpoints to `AdminController`**

Append these handlers inside the class:

```java
@CrossOrigin(origins = "*")
@DeleteMapping(value = "/student/{id}")
public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    studentService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
}

@CrossOrigin(origins = "*")
@DeleteMapping(value = "/professor/{id}")
public ResponseEntity<Void> deleteProfessor(@PathVariable Long id) {
    professorService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
}

@CrossOrigin(origins = "*")
@DeleteMapping(value = "/subject/{id}")
public ResponseEntity<Void> deleteSubject(@PathVariable Long id) {
    subjectService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
}

@CrossOrigin(origins = "*")
@DeleteMapping(value = "/study-program/{id}")
public ResponseEntity<Void> deleteStudyProgram(@PathVariable Long id) {
    studyProgramService.delete(id);
    return new ResponseEntity<>(HttpStatus.OK);
}

@CrossOrigin(origins = "*")
@DeleteMapping(value = "/subject-professor/{subjectId}/{professorId}")
public ResponseEntity<Void> deleteSubjectProfessor(@PathVariable Long subjectId, @PathVariable Long professorId) {
    subjectProfessorService.delete(subjectId, professorId);
    return new ResponseEntity<>(HttpStatus.OK);
}

@CrossOrigin(origins = "*")
@DeleteMapping(value = "/program-subject/{subjectId}")
public ResponseEntity<Void> deleteProgramSubject(@PathVariable Long subjectId) {
    subjectService.removeSubjectFromProgram(subjectId);
    return new ResponseEntity<>(HttpStatus.OK);
}
```

- [ ] **Step 5: Compile**

Run: `./gradlew compileJava`
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 6: Optional manual check (only if MySQL + app are running)**

Start the app (`./gradlew bootRun`), obtain a JWT via `POST /auth/authenticate`, then e.g.:

```bash
curl -i -X DELETE http://localhost:8080/api/admin/subject/1 -H "Authorization: Bearer <token>"
```

Expected: `200 OK`, and the subject no longer appears in `GET /api/subject`.

- [ ] **Step 7: Commit**

```bash
git add src/main/java/com/ftn/eobrazovanje/service/SubjectService.java \
        src/main/java/com/ftn/eobrazovanje/service/impl/SubjectServiceImpl.java \
        src/main/java/com/ftn/eobrazovanje/controller/AdminController.java
git commit -m "feat: admin delete endpoints for all entities and pairings"
```

---

### Task 4: Backend update endpoints (students & professors)

Add update-request DTOs, orchestrate the updates in `UserServiceImpl` (which already owns create), add a study-program re-point helper to `StudyProgramService`, and expose two `PUT` endpoints.

**Files:**
- Create: `src/main/java/com/ftn/eobrazovanje/domain/dto/student/StudentUpdateRequest.java`
- Create: `src/main/java/com/ftn/eobrazovanje/domain/dto/professor/ProfessorUpdateRequest.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/service/StudyProgramService.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/service/impl/StudyProgramServiceImpl.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/service/UserService.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/service/impl/UserServiceImpl.java`
- Modify: `src/main/java/com/ftn/eobrazovanje/controller/AdminController.java`

**Interfaces:**
- Consumes: `studentService.findById(Long)`, `studentService.update(Student)`, `professorService.getById(Long)`, `professorService.update(Professor)`, `studyProgramStudentService.getStudyProgramByStudent(Student)`, `studyProgramStudentService.delete(Long studyProgramId, Long studentId)`, `studyProgramService.addStudentToStudyProgram(Long, Student)`.
- Produces:
  - `StudentUpdateRequest { String firstName, lastName, email, phone; Long studyProgramId }`
  - `ProfessorUpdateRequest { String firstName, lastName, email, phone; ProfessorRole professorRole }`
  - `userService.updateStudent(Long studentId, StudentUpdateRequest req)`
  - `userService.updateProfessor(Long professorId, ProfessorUpdateRequest req)`
  - `studyProgramService.updateStudentProgram(Long studyProgramId, Student student)`

- [ ] **Step 1: Create `StudentUpdateRequest`**

```java
package com.ftn.eobrazovanje.domain.dto.student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Long studyProgramId;

}
```

- [ ] **Step 2: Create `ProfessorUpdateRequest`**

```java
package com.ftn.eobrazovanje.domain.dto.professor;

import com.ftn.eobrazovanje.domain.common.ProfessorRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfessorUpdateRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private ProfessorRole professorRole;

}
```

- [ ] **Step 3: Declare `updateStudentProgram` in `StudyProgramService`**

```java
void updateStudentProgram(Long studyProgramId, Student student);
```

- [ ] **Step 4: Implement `updateStudentProgram` in `StudyProgramServiceImpl`**

Add this method (it reuses the already-injected `studyProgramStudentService` and existing `addStudentToStudyProgram`):

```java
@Override
public void updateStudentProgram(Long studyProgramId, Student student) {
    StudyProgramStudent existing = studyProgramStudentService.getStudyProgramByStudent(student);
    if (existing != null) {
        if (existing.getStudyProgram().getId().equals(studyProgramId)) {
            return;
        }
        studyProgramStudentService.delete(existing.getStudyProgram().getId(), student.getId());
    }
    addStudentToStudyProgram(studyProgramId, student);
}
```

- [ ] **Step 5: Declare update methods in `UserService`**

Add the imports and two methods to the interface:

```java
import com.ftn.eobrazovanje.domain.dto.student.StudentUpdateRequest;
import com.ftn.eobrazovanje.domain.dto.professor.ProfessorUpdateRequest;
```

```java
void updateStudent(Long studentId, StudentUpdateRequest request);

void updateProfessor(Long professorId, ProfessorUpdateRequest request);
```

- [ ] **Step 6: Implement update methods in `UserServiceImpl`**

`UserServiceImpl` already injects `studentService`, `professorService`, and `studyProgramService`. Add the imports for the two request DTOs and implement:

```java
@Override
public void updateStudent(Long studentId, StudentUpdateRequest request) {
    Student student = studentService.findById(studentId);
    User user = student.getUser();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPhone(request.getPhone());
    studentService.update(student);
    studyProgramService.updateStudentProgram(request.getStudyProgramId(), student);
}

@Override
public void updateProfessor(Long professorId, ProfessorUpdateRequest request) {
    Professor professor = professorService.getById(professorId);
    User user = professor.getUser();
    user.setFirstName(request.getFirstName());
    user.setLastName(request.getLastName());
    user.setEmail(request.getEmail());
    user.setPhone(request.getPhone());
    professorService.update(professor);
}
```

(`professorRole` in `ProfessorUpdateRequest` is intentionally not persisted — the `Professor` entity has no role field, mirroring create. See spec "Known limitations.")

- [ ] **Step 7: Add `PUT` endpoints to `AdminController`**

Add the imports and handlers:

```java
import com.ftn.eobrazovanje.domain.dto.student.StudentUpdateRequest;
import com.ftn.eobrazovanje.domain.dto.professor.ProfessorUpdateRequest;
```

```java
@CrossOrigin(origins = "*")
@PutMapping(value = "/student/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Void> updateStudent(@PathVariable Long id, @RequestBody StudentUpdateRequest request) {
    userService.updateStudent(id, request);
    return new ResponseEntity<>(HttpStatus.OK);
}

@CrossOrigin(origins = "*")
@PutMapping(value = "/professor/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Void> updateProfessor(@PathVariable Long id, @RequestBody ProfessorUpdateRequest request) {
    userService.updateProfessor(id, request);
    return new ResponseEntity<>(HttpStatus.OK);
}
```

- [ ] **Step 8: Compile**

Run: `./gradlew compileJava`
Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 9: Optional manual check (only if MySQL + app are running)**

```bash
curl -i -X PUT http://localhost:8080/api/admin/student/1 \
  -H "Authorization: Bearer <token>" -H "Content-Type: application/json" \
  -d '{"firstName":"Ana","lastName":"Anic","email":"ana@x.com","phone":"123","studyProgramId":2}'
```

Expected: `200 OK`; `GET /api/user/students` reflects the new values.

- [ ] **Step 10: Commit**

```bash
git add src/main/java/com/ftn/eobrazovanje/domain/dto/student/StudentUpdateRequest.java \
        src/main/java/com/ftn/eobrazovanje/domain/dto/professor/ProfessorUpdateRequest.java \
        src/main/java/com/ftn/eobrazovanje/service/StudyProgramService.java \
        src/main/java/com/ftn/eobrazovanje/service/impl/StudyProgramServiceImpl.java \
        src/main/java/com/ftn/eobrazovanje/service/UserService.java \
        src/main/java/com/ftn/eobrazovanje/service/impl/UserServiceImpl.java \
        src/main/java/com/ftn/eobrazovanje/controller/AdminController.java
git commit -m "feat: admin update endpoints for students and professors"
```

---

## Frontend

> All frontend steps run from `/home/nebojsa/faks/eobr/eobrazovanje-client/eobrazovanje-client`.

### Task 5: Frontend models + service methods

Add the two update-request models, ids on the pairing models, and all delete/update service methods.

**Files:**
- Create: `src/app/services/models/student-update-request.ts`
- Create: `src/app/services/models/professor-update-request.ts`
- Modify: `src/app/services/models/subject-professor-pairing-dto.ts`
- Modify: `src/app/services/models/study-program-subject-pairing-dto.ts`
- Modify: `src/app/services/models.ts`
- Modify: `src/app/services/users.service.ts`
- Modify: `src/app/services/subjects.service.ts`
- Modify: `src/app/services/study-program.service.ts`

**Interfaces:**
- Produces (models): `StudentUpdateRequest`, `ProfessorUpdateRequest`; `SubjectProfessorPairingDto.subjectId/professorId`; `StudyProgramSubjectPairingDto.subjectId`.
- Produces (services):
  - `UsersService.deleteStudent(id: number): Observable<void>`, `deleteProfessor(id: number): Observable<void>`, `updateStudent(id: number, req: StudentUpdateRequest): Observable<void>`, `updateProfessor(id: number, req: ProfessorUpdateRequest): Observable<void>`
  - `SubjectsService.deleteSubject(id: number): Observable<void>`, `deleteSubjectProfessor(subjectId: number, professorId: number): Observable<void>`
  - `StudyProgramService.deleteProgram(id: number): Observable<void>`, `deleteProgramSubject(subjectId: number): Observable<void>`

- [ ] **Step 1: Create `student-update-request.ts`**

```typescript
export interface StudentUpdateRequest {
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  studyProgramId?: number;
}
```

- [ ] **Step 2: Create `professor-update-request.ts`**

```typescript
export interface ProfessorUpdateRequest {
  firstName?: string;
  lastName?: string;
  email?: string;
  phone?: string;
  professorRole?: 'PROFESSOR' | 'ASSISTANT' | 'DEMONSTRATOR' | 'LABORATORIAN';
}
```

- [ ] **Step 3: Add ids to the pairing models**

`src/app/services/models/subject-professor-pairing-dto.ts`:

```typescript
export interface SubjectProfessorPairingDto {
  subjectId?: number;
  professorId?: number;
  subjectTitle?: string;
  professorName?: string;
  professorRole?: 'PROFESSOR' | 'ASSISTANT' | 'DEMONSTRATOR' | 'LABORATORIAN';
}
```

`src/app/services/models/study-program-subject-pairing-dto.ts`:

```typescript
export interface StudyProgramSubjectPairingDto {
  subjectId?: number;
  subjectTitle?: string;
  studyProgramCode?: string;
  studyProgramName?: string;
}
```

- [ ] **Step 4: Export the two new models from the barrel**

Append to `src/app/services/models.ts`:

```typescript
export { StudentUpdateRequest } from './models/student-update-request';
export { ProfessorUpdateRequest } from './models/professor-update-request';
```

- [ ] **Step 5: Add delete/update methods to `UsersService`**

Update the import line to include the new models:

```typescript
import { ProfessorDto, StudentDto, UserCreateRequest, UserDto, StudentUpdateRequest, ProfessorUpdateRequest } from './models';
```

Add an admin base and the methods (inside the class, alongside the existing `createUser`/`createProfessor`):

```typescript
  private readonly ADMIN_URL = `${this.BASE_URL}/admin`;

  deleteStudent(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.ADMIN_URL}/student/${id}`)
      .pipe(catchError(this.handleError));
  }

  deleteProfessor(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.ADMIN_URL}/professor/${id}`)
      .pipe(catchError(this.handleError));
  }

  updateStudent(id: number, request: StudentUpdateRequest): Observable<void> {
    return this.http
      .put<void>(`${this.ADMIN_URL}/student/${id}`, request)
      .pipe(catchError(this.handleError));
  }

  updateProfessor(id: number, request: ProfessorUpdateRequest): Observable<void> {
    return this.http
      .put<void>(`${this.ADMIN_URL}/professor/${id}`, request)
      .pipe(catchError(this.handleError));
  }
```

- [ ] **Step 6: Add delete methods to `SubjectsService`**

Add URL constants near the other URLs and the two methods inside the class:

```typescript
  subjectDeleteBaseUrl = "http://localhost:8080/api/admin/subject";
  subjectProfessorDeleteBaseUrl = "http://localhost:8080/api/admin/subject-professor";

  deleteSubject(id: number): Observable<void> {
    return this.http.delete<void>(`${this.subjectDeleteBaseUrl}/${id}`).pipe(
      catchError(err => this.handleError(err))
    );
  }

  deleteSubjectProfessor(subjectId: number, professorId: number): Observable<void> {
    return this.http.delete<void>(`${this.subjectProfessorDeleteBaseUrl}/${subjectId}/${professorId}`).pipe(
      catchError(err => this.handleError(err))
    );
  }
```

- [ ] **Step 7: Add delete methods to `StudyProgramService`**

Add URL constants near the existing `private readonly` URLs and the two methods inside the class:

```typescript
  private readonly DELETE_PROGRAM = `${this.BASE_URL}/admin/study-program`;
  private readonly DELETE_PROGRAM_SUBJECT = `${this.BASE_URL}/admin/program-subject`;

  deleteProgram(id: number): Observable<void> {
    return this.http
      .delete<void>(`${this.DELETE_PROGRAM}/${id}`)
      .pipe(catchError(this.handleError));
  }

  deleteProgramSubject(subjectId: number): Observable<void> {
    return this.http
      .delete<void>(`${this.DELETE_PROGRAM_SUBJECT}/${subjectId}`)
      .pipe(catchError(this.handleError));
  }
```

- [ ] **Step 8: Build**

Run: `npx ng build --configuration development`
Expected: build succeeds (no TS errors).

- [ ] **Step 9: Commit**

```bash
git add src/app/services/models src/app/services/models.ts \
        src/app/services/users.service.ts src/app/services/subjects.service.ts \
        src/app/services/study-program.service.ts
git commit -m "feat: frontend models and service methods for admin delete/update"
```

---

### Task 6: Student card — delete + inline edit

Add a Delete button and an Edit toggle (inline pre-filled form) to `student-card`, and wire the events in `StudentComponent`.

**Files:**
- Modify: `src/app/student-card/student-card.component.ts`
- Modify: `src/app/student-card/student-card.component.html`
- Modify: `src/app/student/student.component.ts`
- Modify: `src/app/student/student.component.html`

**Interfaces:**
- Consumes: `StudentDto`, `StudyProgramDto`, `StudentUpdateRequest`, `UsersService.deleteStudent`, `UsersService.updateStudent`.
- Produces: `<app-student-card [student] [studyProgramList] (deleteStudent) (updateStudent)>`; events `deleteStudent: number`, `updateStudent: { id: number; request: StudentUpdateRequest }`.

- [ ] **Step 1: Rewrite `student-card.component.ts`**

```typescript
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { StudentDto, StudyProgramDto, StudentUpdateRequest } from '../services/models';

@Component({
  selector: 'app-student-card',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './student-card.component.html',
  styleUrl: './student-card.component.css'
})
export class StudentCardComponent implements OnInit {
  @Input() student!: StudentDto;
  @Input() studyProgramList: StudyProgramDto[] = [];
  @Output() deleteStudent = new EventEmitter<number>();
  @Output() updateStudent = new EventEmitter<{ id: number; request: StudentUpdateRequest }>();

  editing = false;

  editForm = new FormGroup({
    firstName: new FormControl('', { nonNullable: true }),
    lastName: new FormControl('', { nonNullable: true }),
    email: new FormControl('', { nonNullable: true }),
    phone: new FormControl('', { nonNullable: true }),
    studyProgramId: new FormControl(0, { nonNullable: true })
  });

  ngOnInit(): void {
    this.resetForm();
  }

  private resetForm(): void {
    this.editForm.reset({
      firstName: this.student.firstName ?? '',
      lastName: this.student.lastName ?? '',
      email: this.student.email ?? '',
      phone: '',
      studyProgramId: 0
    });
  }

  toggleEdit(): void {
    this.editing = !this.editing;
    if (this.editing) {
      this.resetForm();
    }
  }

  save(): void {
    const request: StudentUpdateRequest = {
      firstName: this.editForm.value.firstName!,
      lastName: this.editForm.value.lastName!,
      email: this.editForm.value.email!,
      phone: this.editForm.value.phone!,
      studyProgramId: this.editForm.value.studyProgramId!
    };
    this.updateStudent.emit({ id: this.student.id!, request });
    this.editing = false;
  }

  cancel(): void {
    this.editing = false;
  }

  onDelete(): void {
    if (confirm('Delete this student?')) {
      this.deleteStudent.emit(this.student.id!);
    }
  }
}
```

(Note: `StudentDto` exposes no phone/program, so those edit fields start blank/unselected; the admin re-enters them on edit. This mirrors the data the list actually carries.)

- [ ] **Step 2: Rewrite `student-card.component.html`**

```html
<div class="card shadow-sm">
  <div class="card-header bg-light d-flex justify-content-between align-items-center">
    <span class="fw-bold">{{student.firstName}} {{student.lastName}}</span>
    <span>
      <button type="button" class="btn btn-sm btn-outline-primary me-2" (click)="toggleEdit()">Edit</button>
      <button type="button" class="btn btn-sm btn-outline-danger" (click)="onDelete()">Delete</button>
    </span>
  </div>

  <ul class="list-group list-group-flush" *ngIf="!editing">
    <li class="list-group-item"><small class="text-muted">Email:</small> {{student.email}}</li>
  </ul>

  <div class="card-body" *ngIf="editing">
    <form [formGroup]="editForm" (submit)="save()">
      <div class="mb-2">
        <label class="form-label">First Name</label>
        <input type="text" class="form-control" formControlName="firstName">
      </div>
      <div class="mb-2">
        <label class="form-label">Last Name</label>
        <input type="text" class="form-control" formControlName="lastName">
      </div>
      <div class="mb-2">
        <label class="form-label">Email</label>
        <input type="email" class="form-control" formControlName="email">
      </div>
      <div class="mb-2">
        <label class="form-label">Phone number</label>
        <input type="text" class="form-control" formControlName="phone">
      </div>
      <div class="mb-2">
        <label class="form-label">Study Program</label>
        <select class="form-select" formControlName="studyProgramId">
          <option *ngFor="let studyProgram of studyProgramList" [value]="studyProgram.id">{{studyProgram.code}}</option>
        </select>
      </div>
      <button type="submit" class="btn btn-primary btn-sm me-2">Save</button>
      <button type="button" class="btn btn-secondary btn-sm" (click)="cancel()">Cancel</button>
    </form>
  </div>
</div>
```

- [ ] **Step 3: Add handlers to `StudentComponent`**

Add the `StudentUpdateRequest` import:

```typescript
import {
  StudentDto,
  StudyProgramDto,
  UserCreateRequest,
  StudentUpdateRequest
} from '../services/models';
```

Add these methods to the `StudentComponent` class (the `loadStudents` method already exists for the reload):

```typescript
  onDeleteStudent(id: number): void {
    this.usersService.deleteStudent(id).subscribe({
      next: () => this.loadStudents(),
      error: err => console.error('Failed to delete student', err)
    });
  }

  onUpdateStudent(event: { id: number; request: StudentUpdateRequest }): void {
    this.usersService.updateStudent(event.id, event.request).subscribe({
      next: () => this.loadStudents(),
      error: err => console.error('Failed to update student', err)
    });
  }
```

- [ ] **Step 4: Wire the card in `student.component.html`**

Replace the existing `<app-student-card ...>` line with:

```html
          <app-student-card
            [student]="student"
            [studyProgramList]="studyProgramList"
            (deleteStudent)="onDeleteStudent($event)"
            (updateStudent)="onUpdateStudent($event)"></app-student-card>
```

- [ ] **Step 5: Build**

Run: `npx ng build --configuration development`
Expected: build succeeds.

- [ ] **Step 6: Commit**

```bash
git add src/app/student-card src/app/student
git commit -m "feat: student card delete and inline edit"
```

---

### Task 7: Professor list — delete + inline edit

The professor list renders cards inline in `professor.component.html`. Add per-card Delete + Edit (inline form) handled directly in `ProfessorComponent`.

**Files:**
- Modify: `src/app/professor/professor.component.ts`
- Modify: `src/app/professor/professor.component.html`

**Interfaces:**
- Consumes: `ProfessorDto`, `ProfessorUpdateRequest`, `UsersService.deleteProfessor`, `UsersService.updateProfessor`.
- Produces: nothing for other tasks (self-contained component).

- [ ] **Step 1: Add edit state + handlers to `ProfessorComponent`**

Add the `ProfessorUpdateRequest` import:

```typescript
import { ProfessorDto, UserCreateRequest, ProfessorUpdateRequest } from '../services/models';
```

Add these fields and methods to the class (the existing `loadProfessors` handles reload):

```typescript
  editingProfessorId: number | null = null;

  editProfessorForm = new FormGroup({
    firstName: new FormControl('', { nonNullable: true }),
    lastName: new FormControl('', { nonNullable: true }),
    email: new FormControl('', { nonNullable: true }),
    phone: new FormControl('', { nonNullable: true }),
    professorRole: new FormControl('PROFESSOR', { nonNullable: true })
  });

  startEdit(professor: ProfessorDto): void {
    this.editingProfessorId = professor.id ?? null;
    this.editProfessorForm.reset({
      firstName: professor.firstName ?? '',
      lastName: professor.lastName ?? '',
      email: professor.email ?? '',
      phone: '',
      professorRole: 'PROFESSOR'
    });
  }

  cancelEdit(): void {
    this.editingProfessorId = null;
  }

  saveEdit(id: number): void {
    const request: ProfessorUpdateRequest = {
      firstName: this.editProfessorForm.value.firstName!,
      lastName: this.editProfessorForm.value.lastName!,
      email: this.editProfessorForm.value.email!,
      phone: this.editProfessorForm.value.phone!,
      professorRole: this.editProfessorForm.value.professorRole as 'PROFESSOR' | 'ASSISTANT' | 'DEMONSTRATOR' | 'LABORATORIAN'
    };
    this.usersService.updateProfessor(id, request).subscribe({
      next: () => {
        this.editingProfessorId = null;
        this.loadProfessors();
      },
      error: err => console.error('Failed to update professor', err)
    });
  }

  onDeleteProfessor(id: number): void {
    if (!confirm('Delete this professor?')) {
      return;
    }
    this.usersService.deleteProfessor(id).subscribe({
      next: () => this.loadProfessors(),
      error: err => console.error('Failed to delete professor', err)
    });
  }
```

- [ ] **Step 2: Update the inline card in `professor.component.html`**

Replace the existing professor card block (the `<div class="card shadow-sm">...</div>` inside the `*ngFor="let professor of filteredProfessorList"`) with:

```html
          <div class="card shadow-sm">
            <div class="card-header bg-light d-flex justify-content-between align-items-center">
              <span class="fw-bold">{{professor.firstName}} {{professor.lastName}}</span>
              <span>
                <button type="button" class="btn btn-sm btn-outline-primary me-2" (click)="startEdit(professor)">Edit</button>
                <button type="button" class="btn btn-sm btn-outline-danger" (click)="onDeleteProfessor(professor.id!)">Delete</button>
              </span>
            </div>

            <ul class="list-group list-group-flush" *ngIf="editingProfessorId !== professor.id">
              <li class="list-group-item"><small class="text-muted">Email:</small> {{professor.email}}</li>
            </ul>

            <div class="card-body" *ngIf="editingProfessorId === professor.id">
              <form [formGroup]="editProfessorForm" (submit)="saveEdit(professor.id!)">
                <div class="mb-2">
                  <label class="form-label">First Name</label>
                  <input type="text" class="form-control" formControlName="firstName">
                </div>
                <div class="mb-2">
                  <label class="form-label">Last Name</label>
                  <input type="text" class="form-control" formControlName="lastName">
                </div>
                <div class="mb-2">
                  <label class="form-label">Email</label>
                  <input type="email" class="form-control" formControlName="email">
                </div>
                <div class="mb-2">
                  <label class="form-label">Phone number</label>
                  <input type="text" class="form-control" formControlName="phone">
                </div>
                <div class="mb-2">
                  <label class="form-label">Role</label>
                  <select class="form-select" formControlName="professorRole">
                    <option value="PROFESSOR">Professor</option>
                    <option value="ASSISTANT">Assistant</option>
                    <option value="DEMONSTRATOR">Demonstrator</option>
                    <option value="LABORATORIAN">Laboratorian</option>
                  </select>
                </div>
                <button type="submit" class="btn btn-primary btn-sm me-2">Save</button>
                <button type="button" class="btn btn-secondary btn-sm" (click)="cancelEdit()">Cancel</button>
              </form>
            </div>
          </div>
```

- [ ] **Step 3: Build**

Run: `npx ng build --configuration development`
Expected: build succeeds.

- [ ] **Step 4: Commit**

```bash
git add src/app/professor
git commit -m "feat: professor delete and inline edit"
```

---

### Task 8: Delete buttons on remaining admin cards

Add Delete (with `confirm()`) to the subject card, study-program card, subject-professor pairing card, and program-subject pairing card, wiring each parent to call the service and reload.

**Files:**
- Modify: `src/app/subject-card/subject-card.component.ts`
- Modify: `src/app/subject-card/subject-card.component.html`
- Modify: `src/app/create-subject-form/create-subject-form.component.ts`
- Modify: `src/app/create-subject-form/create-subject-form.component.html`
- Modify: `src/app/study-program-card/study-program-card.component.ts`
- Modify: `src/app/study-program-card/study-program-card.component.html`
- Modify: `src/app/study-program/study-program.component.ts`
- Modify: `src/app/study-program/study-program.component.html`
- Modify: `src/app/subject-professor/subject-professor.component.ts`
- Modify: `src/app/subject-professor/subject-professor.component.html`
- Modify: `src/app/program-subject/program-subject.component.ts`
- Modify: `src/app/program-subject/program-subject.component.html`

**Interfaces:**
- Consumes: `SubjectsService.deleteSubject`, `SubjectsService.deleteSubjectProfessor`, `StudyProgramService.deleteProgram`, `StudyProgramService.deleteProgramSubject`.
- Produces: `<app-subject-card (deleteSubject)>`, `<app-study-program-card (deleteProgram)>`.

- [ ] **Step 1: Add delete output to `subject-card.component.ts`**

```typescript
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SubjectDto } from '../services/models';

@Component({
  selector: 'app-subject-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './subject-card.component.html',
  styleUrl: './subject-card.component.css'
})
export class SubjectCardComponent {
  @Input() subjectDto!: SubjectDto;
  @Output() deleteSubject = new EventEmitter<number>();

  onDelete(): void {
    if (confirm('Delete this subject?')) {
      this.deleteSubject.emit(this.subjectDto.id!);
    }
  }
}
```

- [ ] **Step 2: Add the button to `subject-card.component.html`**

```html
<div class="card shadow-sm">
  <div class="card-header bg-light d-flex justify-content-between align-items-center">
    <span class="fw-bold">{{subjectDto.title}}</span>
    <button type="button" class="btn btn-sm btn-outline-danger" (click)="onDelete()">Delete</button>
  </div>
  <ul class="list-group list-group-flush">
    <li class="list-group-item">{{subjectDto.description}}</li>
  </ul>
</div>
```

- [ ] **Step 3: Add reload + delete handler to `CreateSubjectFormComponent`**

The current component loads subjects once in the constructor and `createSubject()` does not refresh. Add a reload method, call it after create and delete, and add the delete handler. Replace the class body with:

```typescript
import { Component, inject } from '@angular/core';
import { SubjectsService } from '../services/subjects.service';
import { SubjectCreateRequest, SubjectDto } from '../services/models';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormControl } from '@angular/forms';
import { SubjectCardComponent } from "../subject-card/subject-card.component";

@Component({
  selector: 'app-create-subject-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, SubjectCardComponent],
  templateUrl: './create-subject-form.component.html',
  styleUrl: './create-subject-form.component.css'
})
export class CreateSubjectFormComponent {

  subjectService: SubjectsService = inject(SubjectsService);
  subjectList: SubjectDto[] = [];

  subjectCreateForm = new FormGroup({
    title: new FormControl(''),
    description: new FormControl(''),
  });

  constructor() {
    this.loadSubjects();
  }

  private loadSubjects(): void {
    this.subjectService.getAllSubjects().subscribe((subjectList: SubjectDto[]) => {
      this.subjectList = subjectList;
    });
  }

  createSubject() {
    const createRequest: SubjectCreateRequest = {
      title: this.subjectCreateForm.value.title ?? '',
      description: this.subjectCreateForm.value.description ?? '',
    };
    this.subjectService.createNewSubject(createRequest);
    this.subjectCreateForm.reset();
    setTimeout(() => this.loadSubjects(), 300);
  }

  onDeleteSubject(id: number): void {
    this.subjectService.deleteSubject(id).subscribe({
      next: () => this.loadSubjects(),
      error: err => console.error('Failed to delete subject', err)
    });
  }
}
```

(`createNewSubject` is fire-and-forget in the existing service — the `setTimeout` reload preserves current create behavior while showing new rows. The delete path uses a proper subscription.)

- [ ] **Step 4: Wire the card in `create-subject-form.component.html`**

Replace the `<app-subject-card ...>` line with:

```html
          <app-subject-card [subjectDto]="subject" (deleteSubject)="onDeleteSubject($event)"></app-subject-card>
```

- [ ] **Step 5: Add delete output to `study-program-card.component.ts`**

```typescript
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { StudyProgramDto } from '../services/models';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-study-program-card',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './study-program-card.component.html',
  styleUrl: './study-program-card.component.css'
})
export class StudyProgramCardComponent {
  @Input() programDto!: StudyProgramDto;
  @Output() deleteProgram = new EventEmitter<number>();

  onDelete(): void {
    if (confirm('Delete this study program?')) {
      this.deleteProgram.emit(this.programDto.id!);
    }
  }
}
```

- [ ] **Step 6: Add the button to `study-program-card.component.html`**

```html
<div class="card shadow-sm">
  <div class="card-header bg-light d-flex justify-content-between align-items-center">
    <span class="fw-bold">{{programDto.code}}</span>
    <button type="button" class="btn btn-sm btn-outline-danger" (click)="onDelete()">Delete</button>
  </div>
  <ul class="list-group list-group-flush">
    <li class="list-group-item"><small class="text-muted">Name:</small> {{programDto.name}}</li>
    <li class="list-group-item"><small class="text-muted">Description:</small> {{programDto.description}}</li>
  </ul>
</div>
```

- [ ] **Step 7: Add the delete handler to `StudyProgramComponent`**

Add this method (the existing `loadStudyPrograms()` handles reload):

```typescript
  onDeleteProgram(id: number): void {
    this.studyProgramService.deleteProgram(id).subscribe({
      next: () => this.loadStudyPrograms(),
      error: err => console.error('Failed to delete study program', err)
    });
  }
```

- [ ] **Step 8: Wire the card in `study-program.component.html`**

Replace the `<app-study-program-card ...>` line with:

```html
          <app-study-program-card [programDto]="program" (deleteProgram)="onDeleteProgram($event)"></app-study-program-card>
```

- [ ] **Step 9: Add the delete handler to `SubjectProfessorComponent`**

Add this method (the existing `loadPairings()` handles reload):

```typescript
  onDeletePairing(pairing: SubjectProfessorPairingDto): void {
    if (!confirm('Remove this professor from the subject?')) {
      return;
    }
    this.subjectService.deleteSubjectProfessor(pairing.subjectId!, pairing.professorId!).subscribe({
      next: () => this.loadPairings(),
      error: err => console.error('Failed to delete pairing', err)
    });
  }
```

- [ ] **Step 10: Add the button in `subject-professor.component.html`**

In the pairing card header (`<div class="card-header ...">` inside `*ngFor="let pairing of pairingList"`), add a Delete button after the role badge:

```html
            <div class="card-header bg-light d-flex justify-content-between align-items-center">
              <span class="fw-bold">{{pairing.subjectTitle}}</span>
              <span class="d-flex align-items-center gap-2">
                <span class="badge bg-secondary">{{pairing.professorRole}}</span>
                <button type="button" class="btn btn-sm btn-outline-danger" (click)="onDeletePairing(pairing)">Delete</button>
              </span>
            </div>
```

- [ ] **Step 11: Add the delete handler to `ProgramSubjectComponent`**

Add this method (the existing `loadPairings()` handles reload):

```typescript
  onDeletePairing(pairing: StudyProgramSubjectPairingDto): void {
    if (!confirm('Remove this subject from the study program?')) {
      return;
    }
    this.studyProgramService.deleteProgramSubject(pairing.subjectId!).subscribe({
      next: () => this.loadPairings(),
      error: err => console.error('Failed to delete pairing', err)
    });
  }
```

- [ ] **Step 12: Add the button in `program-subject.component.html`**

```html
            <div class="card-header bg-light d-flex justify-content-between align-items-center">
              <span class="fw-bold">{{pairing.subjectTitle}}</span>
              <span class="d-flex align-items-center gap-2">
                <span class="badge bg-secondary">{{pairing.studyProgramCode}}</span>
                <button type="button" class="btn btn-sm btn-outline-danger" (click)="onDeletePairing(pairing)">Delete</button>
              </span>
            </div>
```

- [ ] **Step 13: Build**

Run: `npx ng build --configuration development`
Expected: build succeeds.

- [ ] **Step 14: Commit**

```bash
git add src/app/subject-card src/app/create-subject-form \
        src/app/study-program-card src/app/study-program \
        src/app/subject-professor src/app/program-subject
git commit -m "feat: delete buttons on subject, study-program, and pairing cards"
```

---

## Final verification

- [ ] Backend compiles: `./gradlew compileJava` → `BUILD SUCCESSFUL`.
- [ ] Frontend builds: `npx ng build --configuration development` → success.
- [ ] (If MySQL + app available) Smoke test in the browser: on each admin page, delete a card and confirm it disappears after reload; on the student and professor pages, edit a record and confirm the list reflects the change.

## Notes / known limitations (from spec)

- Professor "role" is shown in the edit form for parity with create but is **not persisted** (the `Professor` entity has no role field).
- Email is editable and is the login identifier — changing it changes login identity (intended).
- `StudentDto`/`ProfessorDto` do not carry phone or current study program, so those edit fields open blank/unselected and the admin re-enters them on save.

# Admin Pages: Delete & Update — Design

**Date:** 2026-06-21
**Status:** Approved

## Goal

Admin pages currently support create + list only. Add:

1. A **Delete** action on the individual card (list item) of **every** admin page.
2. An **Update** flow for **students and professors** — an "Edit" button on the card that
   reveals an inline form pre-filled with current values (mirrors the create form). Saving
   issues an update; canceling reverts.

Covers backend (services, controllers, DTOs, soft-delete config) and the Angular frontend
(services, models, cards/list components).

## Decisions (confirmed with user)

- **Delete = soft delete + filtered lists.** User & Subject already use `@SQLDelete`;
  extend the same to the remaining listed entities and filter `deleted = false` everywhere.
  Join/association rows (pairings) have no soft-delete column and are removed/unassigned outright.
- **Update form = all create-form fields**, including email. Editing email changes the login
  identity — intended.
- **Native `confirm()`** before every delete.
- **Professor `role`** is included in the update form to mirror the create form, even though it
  is not persisted (see Known Limitations).

## Affected admin pages

| Page (route) | Component | List item | Delete | Update |
|---|---|---|---|---|
| `student` | `StudentComponent` | `app-student-card` | yes | yes |
| `professor` | `ProfessorComponent` | inline card | yes | yes |
| `subject` | `CreateSubjectFormComponent` | `subject-card` | yes | no |
| `study-program` | `StudyProgramComponent` | `study-program-card` | yes | no |
| `subject-professor` | `SubjectProfessorComponent` | inline pairing card | yes | no |
| `study-program-subject` | `ProgramSubjectComponent` | inline pairing card | yes | no |

## Backend

### 1. Soft-delete infrastructure

Entities extend `BaseEntity`, which already has a `deleted` boolean (default false) and `@Version`.

- `User` — `@SQLDelete` present. **Add** `deleted = false` query filter.
- `Subject` — `@SQLDelete` present (includes `version` in WHERE). **Add** filter.
- `StudyProgram` — **add** `@SQLDelete` + filter.
- `Student` — **add** `@SQLDelete` + filter.
- `Professor` — **add** `@SQLDelete` + filter.

Filter annotation: use `@Where(clause = "deleted = false")` if the resolved Hibernate version
still supports it; otherwise `@SQLRestriction("deleted = false")` (Hibernate 6.3+). Confirm
which compiles against the project's actual Hibernate version during implementation. Apply per
entity (mapped-superclass filters are not reliably applied by Hibernate).

`@SQLDelete` SQL mirrors the existing style, e.g. `UPDATE student SET deleted = true WHERE id = ?`
(table names: `student`, `professor`, `study_program`/`studyprogram` — match the actual mapped
table). Include `version` in the WHERE clause only if needed to match the existing `Subject` pattern.

**Latent bug fixed as a side effect:** `Subject`/`User` already soft-delete but are not filtered
today, so previously deleted rows still appear in lists. Adding the filter corrects this.

**Delete cascade note:** `Student`/`Professor` cascade `ALL` to `User` (and `Student` to
`StudentAccount`). With `@SQLDelete` on `Student`/`Professor`, deleting the entity soft-deletes
its row; the cascade still triggers `User`'s `@SQLDelete`, so the User row is soft-deleted too.
Verify this behaves as expected at implementation time.

### 2. Delete endpoints (`AdminController`)

| Page | Endpoint | Backing service method |
|---|---|---|
| Student | `DELETE /api/admin/student/{id}` | `studentService.delete(id)` — exists |
| Professor | `DELETE /api/admin/professor/{id}` | `professorService.delete(id)` — exists |
| Subject | `DELETE /api/admin/subject/{id}` | `subjectService.delete(id)` — add if missing |
| Study program | `DELETE /api/admin/study-program/{id}` | `studyProgramService.delete(id)` — add if missing |
| Subject–Professor | `DELETE /api/admin/subject-professor/{subjectId}/{professorId}` | `subjectProfessorService.delete(subjectId, professorId)` — exists |
| Program–Subject | `DELETE /api/admin/program-subject/{subjectId}` | new `removeSubjectFromProgram(subjectId)` |

- The program-subject "pairing" is modeled as `Subject.studyProgram` (a `@ManyToOne`), **not** a
  join entity. Removing the pairing sets `subject.studyProgram = null` and saves.
- `SubjectProfessor` is a real association entity with an embedded id and no `deleted` column;
  its delete is a physical row removal (method already exists).
- Each handler returns `200 OK` (or `204`) on success, consistent with the existing
  `deleteDocument` handler.

### 3. Update endpoints + DTOs (students & professors)

New DTOs:

- `StudentUpdateRequest` — `firstName`, `lastName`, `email`, `phone`, `studyProgramId`.
- `ProfessorUpdateRequest` — `firstName`, `lastName`, `email`, `phone`, `professorRole`.

Endpoints:

- `PUT /api/admin/student/{id}` →
  - Load `Student` by id (entity id, per the entity-id contract).
  - Update the backing `User`'s firstName/lastName/email/phone.
  - Re-point the study program: update the `StudyProgramStudent` association to the new
    `studyProgramId` (replace existing association, or update its program reference).
  - Save.
- `PUT /api/admin/professor/{id}` →
  - Load `Professor` by id.
  - Update the backing `User`'s firstName/lastName/email/phone.
  - `professorRole` is accepted but not persisted (no field on `Professor`).

Service methods (e.g. `studentService.updateFromRequest(id, req)`,
`professorService.updateFromRequest(id, req)`) encapsulate the entity loading + field copying so
the controller stays thin.

## Frontend

### Services & models

- Add models `StudentUpdateRequest`, `ProfessorUpdateRequest` mirroring the backend DTOs.
- `AdminService` (or `UsersService`, following existing placement): add
  - `deleteStudent(id)`, `deleteProfessor(id)`, `deleteSubject(id)`, `deleteStudyProgram(id)`,
    `deleteSubjectProfessor(subjectId, professorId)`, `deleteProgramSubject(subjectId)`.
  - `updateStudent(id, req)`, `updateProfessor(id, req)`.

### Cards / lists

- **`student-card`**: add a **Delete** button and an **Edit** toggle. Edit reveals an inline
  reactive form pre-filled with the student's current values (same fields as the create form,
  including the study-program select). Save emits an `update` event with the payload; Delete (after
  `confirm()`) emits a `delete` event. `StudentComponent` handles both — calls the service and
  reloads the list. The study-program option list is passed into the card as an `@Input`.
- **Professor list** (`ProfessorComponent`, inline card today): same — Delete + inline edit form
  (firstName, lastName, email, phone, role radios). Can stay inline or be extracted to a
  `professor-card` component; follow whichever keeps the code cleanest.
- **`subject-card`, `study-program-card`, subject-professor card, program-subject card**:
  add a **Delete** button only. On click → `confirm()` → emit `delete` (with the id or the
  pairing's id pair) → parent calls service and reloads.

### UX details

- Every delete: `confirm('Delete this <thing>?')` before firing the request.
- Inline edit: toggling Edit swaps the card's read view for the form; Cancel restores the read
  view without saving; Save persists then collapses back to the read view (parent reload refreshes
  displayed values).

## Known limitations / flags

- **Professor role not persisted.** The `Professor` entity stores only `user`; `professorRole`
  from create/update is dropped, and `ProfessorDto` does not return it. The update form shows role
  for parity with create, but it is a no-op. Persisting role would require a schema/entity change —
  out of scope here.
- **Email is the login username.** Editing it changes login identity — intended per user.
- Passwords, authentication, and role-guard behavior are unchanged.

## Out of scope

- Non-admin pages (student/professor self-service views).
- Password management.
- New authorization rules beyond the existing `authGuard`.
- Persisting professor role.

## Testing / verification

- Backend: project compiles (`./gradlew build` or equivalent); manually exercise each new
  endpoint (or via existing test patterns if present).
- Frontend: `npx ng build --configuration development` succeeds; manually verify delete removes
  a card and edit updates a student/professor.

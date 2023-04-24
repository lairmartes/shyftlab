# Shytflab - Student Manager Services
### Introduction
This project has been created by Lair Martes as Selection Process for Shyftlabs.  All data is saved in a Memory Database, so everytime the instance falls, all data is lost.
### Project Support Features
* Users can add and list Students;
* Users can add and list Courses;
* Users can add and list Score Results.
### Usage
* Install `docker-compose` (easier) or `docker`.
* _Easiest way_: 
  - just run `docker-compose up` in project's folder or.
* Run `docker run -p <local-port>:8080` in project's folder.
* Use address `http://localhost:8000` to access the services.
### API Endpoints
| HTTP Verbs | Endpoints     | Action                                                |
|------------|---------------|-------------------------------------------------------|
| POST       | /students/    | Add new student                                       |
| POST       | /courses/     | Add new course                                        |
| POST       | /results/     | Add new result                                        |
| GET        | /students/all | To retrieve all Students                              |
| GET        | /courses/all  | To retrieve all Courses                               |
| GET        | /results/all  | To retrieve all Results                               |
| DELETE     | /students/:id | To delete a student (will remove all results related) |
| DELETE     | /courses/:id  | To delete a course (will remove all results related)  |
### Payload examples:
#### Add Student
```json
{
  "firstName": "Shikamaru",
  "familyName": "Nara",
  "birthDate": "1993-08-23",
  "email": "shikamaru.nara@adm.konoha.gov.lv"
}
```

#### Add Course
```json
{
    "name":"Kage Bushin"
}
```

#### Add Result
```json
{
    "studentId":1,
    "courseId":1,
    "score":"C"
}
```

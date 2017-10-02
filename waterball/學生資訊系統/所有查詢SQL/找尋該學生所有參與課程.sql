select C.number as classNumber, C.courseNumber, CO.credit, CO.name, CO.content, P.name as professorName
from CourseClass as C 
inner join RegisterClass as R on C.number = R.classNumber and C.courseNumber = R.courseNumber
inner join Course as CO on C.courseNumber = CO.number
inner join Professor as P on C.professorId = P.id
where R.studentId = 1
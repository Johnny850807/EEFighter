select C.number as classNumber, CR.number as courseNumber, 
CR.name as courseName, CR.content as courseContent, CR.credit as courseCredit, P.id as professorId, P.name as professorName
from CourseClass as C
inner join Professor as P on C.professorId = P.id
inner join Course as CR on C.courseNumber = CR.number

Create Database StudentSystem
Go

Use StudentSystem 

Create Table CreditFeeType(
	[id]	int		Primary Key		Identity,
	[feePerCredit]	int,
	[name]		nvarchar(max)
)

Create Table Student(
	[id]		int		Primary Key		Identity,
	[account]	nvarchar(max),
	[password]	nvarchar(max),
	[name]		nvarchar(max),
	[creditFeeTypeId]	int,
	Foreign Key (creditFeeTypeId) References CreditFeeType(id)
)

Create Table Professor(
	[id]		int		Primary Key		Identity,
	[account]	nvarchar(max),
	[password]	nvarchar(max),
	[name]		nvarchar(max)
)

Create Table Course(
	[number]	nvarchar(5)		Primary Key,
	[name]		nvarchar(max),
	[content]	nvarchar(max),
	[credit]	int
)

Create Table CourseClass(
	[number]	nvarchar(5),
	[courseNumber]		nvarchar(5),
	[professorId]		int,
	Primary Key (number, courseNumber),
	Foreign Key (courseNumber) References Course(number) On Delete Cascade,
	Foreign Key (professorId) References Professor(id)
)

Create Table RegisterClass(
	[studentId]		int,
	[courseNumber]	nvarchar(5),
	[classNumber]	nvarchar(5),
	Primary Key (studentId, courseNumber, classNumber),
	Foreign Key (studentId)	References	Student(id),
	Foreign Key (classNumber, courseNumber)	References	CourseClass(number, courseNumber) On Delete Cascade,
)

Go

Insert Into Course values
('10248', N'物件導向', N'教大家如何 OOA/OOD/OOP', 3),
('70456', N'丟水球課程', N'水球很有趣', 50)

Go

Insert Into Professor values
('johnny', 'johnny', N'水球教授'),
('joanna', 'joanna', N'書瑄教授'),
('fungshu', 'fungshu', N'豐緒教授')

Go

Insert Into CourseClass values
('36102', '10248', 3), 
('36153', '70456', 1)

Go

insert into CreditFeeType values
(300, 'University'),
(500, 'Master')
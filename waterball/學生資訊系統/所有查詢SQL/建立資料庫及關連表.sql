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
('10248', N'����ɦV', N'�Фj�a�p�� OOA/OOD/OOP', 3),
('70456', N'����y�ҵ{', N'���y�ܦ���', 50)

Go

Insert Into Professor values
('johnny', 'johnny', N'���y�б�'),
('joanna', 'joanna', N'��ޱ�б�'),
('fungshu', 'fungshu', N'�׺��б�')

Go

Insert Into CourseClass values
('36102', '10248', 3), 
('36153', '70456', 1)

Go

insert into CreditFeeType values
(300, 'University'),
(500, 'Master')
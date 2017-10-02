select S.id, S.account, S.password, S.name, CF.id as feeType, CF.feePerCredit, CF.name
from Student as S inner join CreditFeeType as CF on S.creditFeeTypeId = CF.id
where account = 'waterball' and password = 'waterball'
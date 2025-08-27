---- 3개 전체 매핑 테이블 정보
SELECT * FROM
    (SELECT
         P.PROJECT_ID,
         P.PROJECT_KEY,
         P.TITLE,
         P.DESCRIPTION AS PROJECT_DESCRIPTION,
         B.BUILD_TITLE,
         M.BAMBOOKEY,
         M.BIZDIV,
         M.BIZMGR,
         M.CONFIG,
         M.DEV,
         M.OPER,
         M.STATUS,
         M.BITBUCKETNAME,
         M.BITBUCKETKEY,
         M.BITBUCKETDESC,
         M.BAMBOODESC,
         M.DEPLOYMGR
     FROM PROJECT P
              LEFT JOIN PROJMGMT M ON P.PROJECT_KEY = M.BAMBOOKEY
              LEFT JOIN BUILD B ON P.PROJECT_ID = B.PROJECT_ID
     ORDER BY P.TITLE ASC, B.BUILD_TITLE ASC) DASHBOARD;
---

SELECT
    BITBUCKETDESC,
    BITBUCKETKEY,
    BITBUCKETNAME,
    BIZDIV,
    BIZMGR,
    CONFIG,
FROM
    PROJMGMT
WHERE BAMBOOKEY = 'NULL';

---- 3개 전체 매핑 테이블 정보
SELECT * FROM
    (SELECT
         P.PROJECT_ID,
         P.PROJECT_KEY,
         P.TITLE,
         P.DESCRIPTION AS PROJECT_DESCRIPTION,
         B.BUILD_TITLE,
         M.BAMBOOKEY,
         M.BIZDIV,
         M.BIZMGR,
         M.CONFIG,
         M.DEV,
         M.OPER,
         M.STATUS,
         M.BITBUCKETNAME,
         M.BITBUCKETKEY,
         M.BITBUCKETDESC,
         M.BAMBOODESC,
         M.DEPLOYMGR
     FROM PROJECT P
              LEFT JOIN PROJMGMT M ON P.PROJECT_KEY = M.BAMBOOKEY
              LEFT JOIN BUILD B ON P.PROJECT_ID = B.PROJECT_ID
     ORDER BY P.TITLE ASC, B.BUILD_TITLE ASC) DASHBOARD WHERE BAMBOOKEY iS NULL;
---
SELECT * FROM
    (SELECT
         P.PROJECT_ID,
         P.PROJECT_KEY,
         P.TITLE,
         P.DESCRIPTION AS PROJECT_DESCRIPTION,
         B.BUILD_TITLE,
         M.BAMBOOKEY,
         M.BIZDIV,
         M.BIZMGR,
         M.CONFIG,
         M.DEV,
         M.OPER,
         M.STATUS,
         M.BITBUCKETNAME,
         M.BITBUCKETKEY,
         M.BITBUCKETDESC,
         M.BAMBOODESC,
         M.DEPLOYMGR
     FROM PROJECT P
              LEFT JOIN PROJMGMT M ON P.PROJECT_KEY = M.BAMBOOKEY
              LEFT JOIN BUILD B ON P.PROJECT_ID = B.PROJECT_ID
     ORDER BY P.TITLE ASC, B.BUILD_TITLE ASC) DASHBOARD WHERE BAMBOOKEY iS NOT NULL;

--- projmgmt 테이블 중복 bambookey 체크 로직
--- 이렇게하는이유는 projmgmt를 project
select bambookey, count(*) as duplicate_count
from projmgmt
group by bambookey
having count(*) > 1
order by duplicate_count desc

-- projmgmt 128데이터 중 bambookey null 45건 bamboo 83건
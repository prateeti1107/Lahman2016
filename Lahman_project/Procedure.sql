


#data table for decision tree construction and split
create table new select H.votedBy,M.birthCountry, M.bats, M.throws, H.inducted from HallOfFame as H, Master as M where H.playerID=M.playerID;
delete from new where bats="" or throws="";



#------------------------insert into itemset----------------------------------------------
create or replace procedure insert_into_itemset(IN year int)
begin
	truncate table itemset;
	SET @finalQuery = CONCAT("insert into itemSet (select teamID, DivWin, WcWin, LgWin, WsWin from Teams where yearID > ",year,");");
 PREPARE stmt FROM @finalQuery;
 EXECUTE stmt;
 DEALLOCATE PREPARE stmt;
END;
call insert_into_itemset
@;@


#----------------------------------create c1---------------------------------

create or replace procedure proc_create_c1()
begin
	create or replace table C1 AS
	SELECT ITEM ITEM_1, CNT, ALL_CNT FROM (
	    select 'DivWin' ITEM, SUM(CASE WHEN DivWin = 'Y' THEN 1 ELSE 0 END) CNT, COUNT(*) ALL_CNT 
	  from itemSet
	    UNION ALL
	    select 'WcWin' ITEM, SUM(CASE WHEN WcWin = 'Y' THEN 1 ELSE 0 END) CNT, COUNT(*) ALL_CNT 
	  from itemSet
	    UNION ALL
	    select 'LgWin' ITEM, SUM(CASE WHEN LgWin = 'Y' THEN 1 ELSE 0 END) CNT, COUNT(*) ALL_CNT 
	  from itemSet
	    UNION ALL
	    select 'WsWin' ITEM, SUM(CASE WHEN WsWin = 'Y' THEN 1 ELSE 0 END) CNT, COUNT(*) ALL_CNT 
	  from itemSet
	) D1;
END;
call proc_create_c1;
@@


#------------------------------- create f1---------------------------------------

create or replace procedure proc_create_f1()
begin
	create or replace table F1 AS
	SELECT ITEM_1, SUPPORT, CNT FROM (
	    SELECT ITEM_1, CNT / ALL_CNT SUPPORT, CNT FROM C1
	) D2;
END;
call proc_create_f1;
@@

#--------------------------------create apriori vertical------------------

create or replace procedure proc_create_apriori_vertical()
begin
	CREATE or replace TABLE APRIORI_VERTICAL AS
	SELECT TeamName, REPLACE(DivWin,'Y',"DivWin") AS item FROM itemSet A
	WHERE DivWin <> 'N'
	UNION ALL
	SELECT TeamName, REPLACE(WcWin,'Y',"WcWin") AS item FROM itemSet A
	WHERE WcWin <> 'N'
	UNION ALL
	SELECT TeamName, REPLACE(LgWin,'Y',"LgWin") FROM itemSet A
	WHERE LgWin <> 'N'
	UNION ALL
	SELECT TeamName, REPLACE(WsWin,'Y',"WsWin") FROM itemSet A
	WHERE WsWin <> 'N';
END;

call proc_create_apriori_vertical;
@@


#---------------------------create frequent iteamset 2----------------------------------------------
CREATE or replace PROCEDURE proc_create_freq_item_2()
begin
DECLARE row int;
SET @row=0;

	CREATE or replace TABLE FREQ_ITEM_2 AS 
	SELECT @row:=row+1 id, A.ITEM_1, B.ITEM_1 ITEM_2 
	FROM F1 A , F1 B
	WHERE A.ITEM_1 < B.ITEM_1;

END;
call proc_create_freq_item_2;
@@ 

#------------------------------------------create c2----------------------------------------------

CREATE or replace PROCEDURE proc_create_c2()
begin
	CREATE or replace TABLE C2 AS
	SELECT distinct Z.ITEM_1 ITEM_1, Z.ITEM_2 ITEM_2, CNT, REC_CNT ALL_CNT, FIRST_INSTANCE_COUNT
	FROM
	(
	    SELECT C.ITEM_1, C.ITEM_2, COUNT(DISTINCT C.TeamName) CNT
	    FROM
	    (
	        SELECT * FROM FREQ_ITEM_2
	    ) X
	    INNER JOIN 
	    (
	        SELECT A1.TeamName, A1.ITEM ITEM_1, A2.ITEM ITEM_2
	        FROM APRIORI_VERTICAL A1 CROSS JOIN APRIORI_VERTICAL A2 
	        WHERE A1.TeamName = A2.TeamName
	        AND A1.ITEM < A2.ITEM  
	    ) C 
	    ON X.ITEM_1 = C.ITEM_1 AND X.ITEM_2 = C.ITEM_2
	    GROUP BY C.ITEM_1, C.ITEM_2
	) Z
	CROSS JOIN 
	(
	    select count(*) REC_CNT from itemset   
	) D
	INNER JOIN 
	(
	    SELECT A.ITEM_1, COUNT(*) FIRST_INSTANCE_COUNT
	    FROM FREQ_ITEM_2 A , APRIORI_VERTICAL B
	    WHERE A.ITEM_1 = B.ITEM
	    GROUP BY A.id, A.ITEM_1
	    
	) E ON Z.ITEM_1 = e.item_1;
END;
@@
call proc_create_c2;
@@

#---------------------------------------create f2--------------------------------------------------------------

CREATE or replace PROCEDURE proc_create_f2()
begin
	CREATE or replace TABLE F2 AS
	SELECT ITEM_1, ITEM_2, SUPPORT, CONFIDENCE, CNT FROM (
	    SELECT ITEM_1, ITEM_2, TRUNCATE(CNT / ALL_CNT,2) SUPPORT, TRUNCATE(CNT / FIRST_INSTANCE_COUNT,2) CONFIDENCE, CNT FROM C2
	) D3;
END;

call proc_create_f2;



#--------------------------------------Decision Tree-------------------------------------------------------------

eDROP PROCEDURE IF EXISTS `CalcEntropy`;
CREATE PROCEDURE CalcEntropy (IN table_name VARCHAR(22), IN col VARCHAR(100), IN pred VARCHAR(100), OUT ans real)
BEGIN

DECLARE classCount int;
DECLARE inform int;
DECLARE i int;
DECLARE l int;
DECLARE len int;
DECLARE ltemp int;
DECLARE str real;
DECLARE ent real;
DECLARE tp real;

SET @i=0;
SET l=0;
SET @classCount = 0;
SET @str=0;
SET ent=0;
SET tp=0;
SET ans=0;
DROP TABLE IF EXISTS `category`;
create table category (cat varchar(25));
DROP TABLE IF EXISTS `category_pred`;
create table category_pred (cat varchar(25));
DROP TABLE IF EXISTS `category_entropy`;
create  table category_entropy (n varchar(10), entropy varchar(10));
DROP TABLE IF EXISTS `temp`;
create table temp (p real);
-- select concat(col,"col ");
-- select concat(pred,"pred ");

SET @s = concat('SELECT count(distinct ',col,' ) FROM ', table_name, ' into @classCount ' );
PREPARE st1 FROM @s;
EXECUTE st1;
DEALLOCATE PREPARE st1;	
-- select concat('c ',@classCount);
SET @s = concat('insert into category SELECT distinct ', col, ' as t FROM ', table_name);
PREPARE st2 FROM @s;
EXECUTE st2;
DEALLOCATE PREPARE st2;
SET @s = concat('insert into category_pred SELECT distinct ', pred, ' as t FROM ', table_name);
PREPARE st3 FROM @s;
EXECUTE st3;
DEALLOCATE PREPARE st3;

select count(*) into len FROM category;

SET @s = concat('select count(*)/(select count(*) from ', table_name, ' ) from ', table_name, ' where ',col,' =(select * from category limit ?,1 ) into @str');
PREPARE st1 FROM @s;
EXECUTE st1 using @i;
-- select concat('a ',@str);		
-- select concat('l ',len);	

IF col != pred THEN
	while @i < len DO
		delete from temp where 1=1;
		SET @s = concat('insert into temp select (count(*)/(select count(*) from ',table_name,' where ',col,' =(select * from category limit ?,1 ))) as p from ', table_name,' where ',col,' =(select * from category limit ?,1 ) group by ', pred);
		PREPARE st1 FROM @s;
		EXECUTE st1 using @i,@i;
		DEALLOCATE PREPARE st1;
		select count(*) FROM temp into ltemp;
		SET ent = 0;
		SET l=0;
		-- select * from temp;
		while l <ltemp DO
			SET tp = 0;
			select * from temp limit l,1 into tp;
			SET ent = ent + (-tp*LOG2(tp));
			-- select concat("tp: ",tp);
			SET l =l +1;
		END WHILE;
		-- select concat("e: ",ent);
		
		SET @s = concat('select count(*)/(select count(*) from ', table_name, ' ) from ', table_name, ' where ',col,' =(select * from category limit ?,1 ) into @str');
		PREPARE st2 FROM @s;
		EXECUTE st2 using @i;
		DEALLOCATE PREPARE st2;

		IF ent=0 THEN 
	   		SET ans = ans + 0;
			INSERT INTO category_entropy(n,entropy) values(TRUNCATE(@str,4),0);
		ELSE
			SET ans = ans + TRUNCATE(@str*ent,4);
			INSERT INTO category_entropy(n,entropy) values(TRUNCATE(@str,4),TRUNCATE(@str*ent,4));
		END IF;
		SET @i = @i+1;
	END WHILE;
ELSE
	-- select concat("hi");
	while @i < len DO
		SET @s = concat('select count(*)/(select count(*) from ', table_name, ' ) from ', table_name, ' where ',pred,' =(select * from category limit ?,1 ) into @str');
		PREPARE st1 FROM @s;
		EXECUTE st1 using @i;
		DEALLOCATE PREPARE st1;
		-- select concat('a ',@str);
		SET ans = ans + TRUNCATE(-@str*LOG2(@str),4);
		SET @i = @i+1;
	END WHILE;
		
END IF;
DROP TABLE IF EXISTS `category`;
DROP TABLE IF EXISTS `category_pred`;
DROP TABLE IF EXISTS `category_entropy`;
DROP TABLE IF EXISTS `temp`;
END;
Call CalcEntropy('interview','level','inter',@a);

DROP PROCEDURE IF EXISTS `TableEntropy`;
CREATE PROCEDURE TableEntropy (IN table_name VARCHAR(22), IN pred VARCHAR(22), out max_gain VARCHAR(22))
BEGIN

DECLARE j int;
DECLARE noa int;
DECLARE col_name varchar(50);
SET @noa=0;
SET @j=0;
SET @col_name="";

DROP TABLE IF EXISTS `info`;
DROP TABLE IF EXISTS `attributes`;
create table attributes (col varchar(100), entropy varchar(10));

SET @s = concat('SELECT count(*) FROM information_schema.columns WHERE table_name = "', table_name, '" into @noa');
PREPARE st1 FROM @s;
EXECUTE st1;
DEALLOCATE PREPARE st1;
-- select concat('columns',@noa);
-- call CalcEntropy(table_name,@col_name,@ans);
-- select concat('ans: ',@ans);

while @j < @noa DO
	-- select concat("j: ",@j);
	SET @s = concat('SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = "', table_name, '" limit ?,1 into @col_name');
	PREPARE st2 FROM @s;
	EXECUTE st2 using @j;
	DEALLOCATE PREPARE st2;
	call CalcEntropy(table_name,@col_name,pred,@ans);
	insert into attributes values(@col_name,@ans);	
	SET @j = @j+1;
	
END WHILE;
SET @s = concat('create table info as (select col,entropy,TRUNCATE((select entropy from attributes where col="',pred,'")-entropy,4) as info_gain from attributes)');
PREPARE st1 FROM @s;
EXECUTE st1;
DEALLOCATE PREPARE st1;
DROP TABLE IF EXISTS `attributes`;

select col from info order by info_gain desc limit 1 into max_gain;

END;
-- call TableEntropy('new_t','inducted',@name);
-- select @name; -- Man info attribute name
-- select * from info; -- infogain for all attributes
-- @@

create table new select H.votedBy,M.birthCountry, M.bats, M.throws, H.inducted from HallOfFame as H, Master as M where H.playerID=M.playerID;

delete from new where bats="" or throws="";

DROP PROCEDURE IF EXISTS `Validate`;
CREATE PROCEDURE Validate (IN table_name VARCHAR(22))
BEGIN

DECLARE l int;
DECLARE n real;
SET @s = concat('SELECT count(*) FROM ', table_name, ' into @l');
PREPARE st1 FROM @s;
EXECUTE st1;
DEALLOCATE PREPARE st1;
-- select concat("l",@l);
select @l*0.8 into @n;
DROP TABLE IF EXISTS `new_t`;
SET @s = concat('create table new_t SELECT votedBy,birthCountry, bats, throws, inducted FROM ', table_name, ' limit 0,? ');
PREPARE st1 FROM @s;
EXECUTE st1 using @n;
DEALLOCATE PREPARE st1;
DROP TABLE IF EXISTS `new_v`;
SET @s = concat('create table new_v SELECT votedBy,birthCountry, bats, throws, inducted FROM ', table_name, ' limit ?,?;');
PREPARE st1 FROM @s;
EXECUTE st1 using @n,@l;
DEALLOCATE PREPARE st1;

END;
call Validate("new");
call TableEntropy('new_t','inducted',@name);
select * from info;
call TableEntropy('new_v','inducted',@name);
select * from info;

@@
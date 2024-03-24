/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.10.123
 Source Server Type    : PostgreSQL
 Source Server Version : 140011
 Source Host           : 10.0.10.123:5432
 Source Catalog        : postgres
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140011
 File Encoding         : 65001

 Date: 24/03/2024 18:24:39
*/


-- ----------------------------
-- Table structure for t_log_info_03_00
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_log_info_03_00";
CREATE TABLE "public"."t_log_info_03_00" (
                                             "id" int8 NOT NULL,
                                             "log_info" varchar(255) COLLATE "pg_catalog"."default",
                                             "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for t_log_info_03_01
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_log_info_03_01";
CREATE TABLE "public"."t_log_info_03_01" (
                                             "id" int8 NOT NULL,
                                             "log_info" varchar(255) COLLATE "pg_catalog"."default",
                                             "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for t_log_info_03_02
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_log_info_03_02";
CREATE TABLE "public"."t_log_info_03_02" (
                                             "id" int8 NOT NULL,
                                             "log_info" varchar(255) COLLATE "pg_catalog"."default",
                                             "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for t_log_info_03_03
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_log_info_03_03";
CREATE TABLE "public"."t_log_info_03_03" (
                                             "id" int8 NOT NULL,
                                             "log_info" varchar(255) COLLATE "pg_catalog"."default",
                                             "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for t_order0
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_order0";
CREATE TABLE "public"."t_order0" (
                                     "id" int8 NOT NULL,
                                     "order_no" varchar COLLATE "pg_catalog"."default",
                                     "user_id" int8,
                                     "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for t_order1
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_order1";
CREATE TABLE "public"."t_order1" (
                                     "id" int8 NOT NULL,
                                     "order_no" varchar COLLATE "pg_catalog"."default",
                                     "user_id" int8,
                                     "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for t_order2
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_order2";
CREATE TABLE "public"."t_order2" (
                                     "id" int8 NOT NULL,
                                     "order_no" varchar COLLATE "pg_catalog"."default",
                                     "user_id" int8,
                                     "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for t_user0
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user0";
CREATE TABLE "public"."t_user0" (
                                    "id" int8 NOT NULL,
                                    "username" varchar(255) COLLATE "pg_catalog"."default",
                                    "create_time" timestamp(6)
)
;

-- ----------------------------
-- Table structure for t_user1
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user1";
CREATE TABLE "public"."t_user1" (
                                    "id" int8 NOT NULL,
                                    "username" varchar(255) COLLATE "pg_catalog"."default",
                                    "create_time" timestamp(6)
)
;

-- ----------------------------
-- Function structure for findattname
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."findattname"("namespace" varchar, "tablename" varchar, "ctype" varchar);
CREATE OR REPLACE FUNCTION "public"."findattname"("namespace" varchar, "tablename" varchar, "ctype" varchar)
  RETURNS "pg_catalog"."varchar" AS $BODY$

        declare
tt oid ;
        aname character varying default '';

begin
        tt := oid from pg_class where relname= tablename and relnamespace =(select oid from pg_namespace where
        nspname=namespace) ;
        aname:= array_to_string(
        array(
        select a.attname from pg_attribute a
        where a.attrelid=tt and a.attnum in (
        select unnest(conkey) from pg_constraint c where contype=ctype
        and conrelid=tt and array_to_string(conkey,',') is not null
        )
        ),',');

return aname;
end
        $BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Function structure for showcreatetable
-- ----------------------------
DROP FUNCTION IF EXISTS "public"."showcreatetable"("namespace" varchar, "tablename" varchar);
CREATE OR REPLACE FUNCTION "public"."showcreatetable"("namespace" varchar, "tablename" varchar)
  RETURNS "pg_catalog"."varchar" AS $BODY$
        declare
tableScript character varying default '';

begin
        -- columns
        tableScript:=tableScript || ' CREATE TABLE '|| tablename|| ' ( '|| chr(13)||chr(10) || array_to_string(
        array(
        select ' ' || concat_ws(' ',fieldName, fieldType, defaultValue, isNullStr ) as column_line
        from (
        select a.attname as fieldName,format_type(a.atttypid,a.atttypmod) as fieldType,        CASE WHEN
                (SELECT substring(pg_catalog.pg_get_expr(B.adbin, B.adrelid) for 128)
                 FROM pg_catalog.pg_attrdef B WHERE B.adrelid = A.attrelid AND B.adnum = A.attnum AND A.atthasdef) IS NOT NULL THEN
                'DEFAULT '|| (SELECT substring(pg_catalog.pg_get_expr(B.adbin, B.adrelid) for 128)
                              FROM pg_catalog.pg_attrdef B WHERE B.adrelid = A.attrelid AND B.adnum = A.attnum AND A.atthasdef)
            ELSE
                ''
            END as defaultValue,        (case when a.attnotnull=true then 'not null' else 'null' end) as isNullStr
        from pg_attribute a where attstattarget=-1 and attrelid = (select c.oid from pg_class c,pg_namespace n where
        c.relnamespace=n.oid and n.nspname =namespace and relname =tablename)

        ) as string_columns
        ),','||chr(13)||chr(10)) || ',';


        -- 约束
        tableScript:= tableScript || chr(13)||chr(10) || array_to_string(
        array(
        select concat(' CONSTRAINT ',conname ,c ,u,p,f) from (
        select conname,
        case when contype='c' then ' CHECK('|| ( select findattname(namespace,tablename,'c') ) ||')' end as c ,
        case when contype='u' then ' UNIQUE('|| ( select findattname(namespace,tablename,'u') ) ||')' end as u ,
        case when contype='p' then ' PRIMARY KEY ('|| ( select findattname(namespace,tablename,'p') ) ||')' end as p ,
        case when contype='f' then ' FOREIGN KEY('|| ( select findattname(namespace,tablename,'u') ) ||') REFERENCES '||
        (select p.relname from pg_class p where p.oid=c.confrelid ) || '('|| ( select
        findattname(namespace,tablename,'u') ) ||')' end as f
        from pg_constraint c
        where contype in('u','c','f','p') and conrelid=(
        select oid from pg_class where relname=tablename and relnamespace =(
        select oid from pg_namespace where nspname = namespace
        )
        )
        ) as t
        ) ,',' || chr(13)||chr(10) ) || chr(13)||chr(10) ||' ); ';

        -- indexs
        -- CREATE UNIQUE INDEX pg_language_oid_index ON pg_language USING btree (oid); -- table pg_language


        --
        /** **/
        --- 获取非约束索引 column
        -- CREATE UNIQUE INDEX pg_language_oid_index ON pg_language USING btree (oid); -- table pg_language
        tableScript:= tableScript || chr(13)||chr(10) || chr(13)||chr(10) || array_to_string(
        array(
        select 'CREATE INDEX ' || indexrelname || ' ON ' || tablename || ' USING btree '|| '(' || attname || ');' from (
        SELECT
        i.relname AS indexrelname , x.indkey,

        ( select array_to_string (
        array(
        select a.attname from pg_attribute a where attrelid=c.oid and a.attnum in ( select unnest(x.indkey) )

        )
        ,',' ) )as attname

        FROM pg_class c
        JOIN pg_index x ON c.oid = x.indrelid
        JOIN pg_class i ON i.oid = x.indexrelid
        LEFT JOIN pg_namespace n ON n.oid = c.relnamespace
        WHERE c.relname=tablename and i.relname not in
        ( select constraint_name from information_schema.key_column_usage where table_name=tablename )
        )as t
        ) ,','|| chr(13)||chr(10));


        -- COMMENT COMMENT ON COLUMN sys_activity.id IS '主键';
        tableScript:= tableScript || chr(13)||chr(10) || chr(13)||chr(10) || array_to_string(
        array(
        SELECT 'COMMENT ON COLUMN ' || 'namespace.tablename' || '.' || a.attname ||' IS '|| ''''|| d.description ||''''
        FROM pg_class c
        JOIN pg_description d ON c.oid=d.objoid
        JOIN pg_attribute a ON c.oid = a.attrelid
        WHERE c.relname=tablename
        AND a.attnum = d.objsubid),';'|| chr(13)||chr(10)) ;

return tableScript;

end
        $BODY$
LANGUAGE plpgsql VOLATILE
  COST 100;

-- ----------------------------
-- Primary Key structure for table t_log_info_03_00
-- ----------------------------
ALTER TABLE "public"."t_log_info_03_00" ADD CONSTRAINT "t_log_info_03_01_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_log_info_03_01
-- ----------------------------
ALTER TABLE "public"."t_log_info_03_01" ADD CONSTRAINT "t_log_info_03_00_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_log_info_03_02
-- ----------------------------
ALTER TABLE "public"."t_log_info_03_02" ADD CONSTRAINT "t_log_info_03_00_copy2_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_log_info_03_03
-- ----------------------------
ALTER TABLE "public"."t_log_info_03_03" ADD CONSTRAINT "t_log_info_03_00_copy3_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_order0
-- ----------------------------
ALTER TABLE "public"."t_order0" ADD CONSTRAINT "t_order_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_order1
-- ----------------------------
ALTER TABLE "public"."t_order1" ADD CONSTRAINT "t_order0_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_order2
-- ----------------------------
ALTER TABLE "public"."t_order2" ADD CONSTRAINT "t_order0_copy2_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_user0
-- ----------------------------
ALTER TABLE "public"."t_user0" ADD CONSTRAINT "t_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_user1
-- ----------------------------
ALTER TABLE "public"."t_user1" ADD CONSTRAINT "t_user_copy1_pkey" PRIMARY KEY ("id");

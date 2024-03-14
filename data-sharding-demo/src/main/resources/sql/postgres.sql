/*
 Navicat Premium Data Transfer

 Source Server         : 10.0.10.123-5430
 Source Server Type    : PostgreSQL
 Source Server Version : 140011
 Source Host           : 10.0.10.123:5430
 Source Catalog        : postgres
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140011
 File Encoding         : 65001

 Date: 14/03/2024 18:13:41
*/


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

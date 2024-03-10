/*
 Navicat Premium Data Transfer

 Source Server         : master
 Source Server Type    : PostgreSQL
 Source Server Version : 140011
 Source Host           : 10.0.10.123:5430
 Source Catalog        : postgres
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 140011
 File Encoding         : 65001

 Date: 10/03/2024 20:52:43
*/


-- ----------------------------
-- Table structure for t_user0
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user0";
CREATE TABLE "public"."t_user0" (
  "id" int8 NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for t_user1
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user1";
CREATE TABLE "public"."t_user1" (
  "id" int8 NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Table structure for t_user2
-- ----------------------------
DROP TABLE IF EXISTS "public"."t_user2";
CREATE TABLE "public"."t_user2" (
  "id" int8 NOT NULL,
  "username" varchar(255) COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Primary Key structure for table t_user0
-- ----------------------------
ALTER TABLE "public"."t_user0" ADD CONSTRAINT "t_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_user1
-- ----------------------------
ALTER TABLE "public"."t_user1" ADD CONSTRAINT "t_user_copy1_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table t_user2
-- ----------------------------
ALTER TABLE "public"."t_user2" ADD CONSTRAINT "t_user_copy2_pkey" PRIMARY KEY ("id");

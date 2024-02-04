/*
 Navicat Premium Data Transfer

 Source Server         : postgres
 Source Server Type    : PostgreSQL
 Source Server Version : 160001 (160001)
 Source Host           : localhost:5432
 Source Catalog        : postgres
 Source Schema         : public

 Target Server Type    : PostgreSQL
 Target Server Version : 160001 (160001)
 File Encoding         : 65001

 Date: 04/02/2024 17:20:01
*/


-- ----------------------------
-- Sequence structure for customers_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."customers_id_seq";
CREATE SEQUENCE "public"."customers_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for reservations_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."reservations_id_seq";
CREATE SEQUENCE "public"."reservations_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Sequence structure for tables_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "public"."tables_id_seq";
CREATE SEQUENCE "public"."tables_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;

-- ----------------------------
-- Table structure for customers
-- ----------------------------
DROP TABLE IF EXISTS "public"."customers";
CREATE TABLE "public"."customers" (
  "id" int4 NOT NULL GENERATED ALWAYS AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1
),
  "name" varchar COLLATE "pg_catalog"."default",
  "contact_info" varchar COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of customers
-- ----------------------------
INSERT INTO "public"."customers" OVERRIDING SYSTEM VALUE VALUES (19, 'Joanna Nowak', '123456789');
INSERT INTO "public"."customers" OVERRIDING SYSTEM VALUE VALUES (20, 'Jan Nowak', '987654321');
INSERT INTO "public"."customers" OVERRIDING SYSTEM VALUE VALUES (21, 'Antoni Kosiba', '111222444');
INSERT INTO "public"."customers" OVERRIDING SYSTEM VALUE VALUES (24, 'Hanna Mostowiak', '444666555');
INSERT INTO "public"."customers" OVERRIDING SYSTEM VALUE VALUES (26, 'Ala Nowak', '432567134');
INSERT INTO "public"."customers" OVERRIDING SYSTEM VALUE VALUES (27, 'Marian Kowalski', '823763134');
INSERT INTO "public"."customers" OVERRIDING SYSTEM VALUE VALUES (28, 'Ada Fijal', '876137413');

-- ----------------------------
-- Table structure for reservation_tables
-- ----------------------------
DROP TABLE IF EXISTS "public"."reservation_tables";
CREATE TABLE "public"."reservation_tables" (
  "reservation_id" int4 NOT NULL,
  "table_id" int4 NOT NULL
)
;

-- ----------------------------
-- Records of reservation_tables
-- ----------------------------
INSERT INTO "public"."reservation_tables" VALUES (21, 1);
INSERT INTO "public"."reservation_tables" VALUES (23, 12);
INSERT INTO "public"."reservation_tables" VALUES (23, 10);
INSERT INTO "public"."reservation_tables" VALUES (23, 8);
INSERT INTO "public"."reservation_tables" VALUES (25, 4);
INSERT INTO "public"."reservation_tables" VALUES (26, 7);
INSERT INTO "public"."reservation_tables" VALUES (26, 11);

-- ----------------------------
-- Table structure for reservations
-- ----------------------------
DROP TABLE IF EXISTS "public"."reservations";
CREATE TABLE "public"."reservations" (
  "id" int4 NOT NULL GENERATED ALWAYS AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1
),
  "customer_id" int4 NOT NULL,
  "start_time" timestamptz(6),
  "end_time" timestamptz(6),
  "special_setup" varchar COLLATE "pg_catalog"."default" DEFAULT '-'::character varying,
  "is_group" bool,
  "customer_name" varchar COLLATE "pg_catalog"."default"
)
;

-- ----------------------------
-- Records of reservations
-- ----------------------------
INSERT INTO "public"."reservations" OVERRIDING SYSTEM VALUE VALUES (23, 27, '2024-04-12 13:00:00+02', '2024-04-12 15:00:00+02', 'do not join tables', 't', 'Marian Kowalski');
INSERT INTO "public"."reservations" OVERRIDING SYSTEM VALUE VALUES (21, 27, '2024-04-12 10:52:00+02', '2024-04-12 12:00:00+02', NULL, 'f', 'Marian Kowalski');
INSERT INTO "public"."reservations" OVERRIDING SYSTEM VALUE VALUES (25, 24, '2024-06-13 13:00:00+02', '2024-06-13 16:30:00+02', NULL, 'f', 'Hanna Mostowiak');
INSERT INTO "public"."reservations" OVERRIDING SYSTEM VALUE VALUES (26, 26, '2023-12-31 11:00:00+01', '2023-12-31 22:00:00+01', 'none', 't', 'Ala Nowak');

-- ----------------------------
-- Table structure for tables
-- ----------------------------
DROP TABLE IF EXISTS "public"."tables";
CREATE TABLE "public"."tables" (
  "id" int4 NOT NULL GENERATED ALWAYS AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1
),
  "capacity" int4 NOT NULL,
  "available" bool NOT NULL DEFAULT true
)
;

-- ----------------------------
-- Records of tables
-- ----------------------------
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (5, 2, 't');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (1, 4, 'f');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (12, 9, 'f');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (10, 5, 'f');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (8, 4, 'f');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (4, 2, 'f');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (9, 3, 't');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (3, 2, 't');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (7, 4, 'f');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (11, 4, 'f');
INSERT INTO "public"."tables" OVERRIDING SYSTEM VALUE VALUES (2, 3, 't');

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."customers_id_seq"
OWNED BY "public"."customers"."id";
SELECT setval('"public"."customers_id_seq"', 28, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."reservations_id_seq"
OWNED BY "public"."reservations"."id";
SELECT setval('"public"."reservations_id_seq"', 27, true);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "public"."tables_id_seq"
OWNED BY "public"."tables"."id";
SELECT setval('"public"."tables_id_seq"', 13, true);

-- ----------------------------
-- Auto increment value for customers
-- ----------------------------
SELECT setval('"public"."customers_id_seq"', 28, true);

-- ----------------------------
-- Primary Key structure for table customers
-- ----------------------------
ALTER TABLE "public"."customers" ADD CONSTRAINT "customers_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table reservation_tables
-- ----------------------------
ALTER TABLE "public"."reservation_tables" ADD CONSTRAINT "reservation_tables_pkey" PRIMARY KEY ("reservation_id", "table_id");

-- ----------------------------
-- Auto increment value for reservations
-- ----------------------------
SELECT setval('"public"."reservations_id_seq"', 27, true);

-- ----------------------------
-- Primary Key structure for table reservations
-- ----------------------------
ALTER TABLE "public"."reservations" ADD CONSTRAINT "reservations_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for tables
-- ----------------------------
SELECT setval('"public"."tables_id_seq"', 13, true);

-- ----------------------------
-- Primary Key structure for table tables
-- ----------------------------
ALTER TABLE "public"."tables" ADD CONSTRAINT "tables_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table reservation_tables
-- ----------------------------
ALTER TABLE "public"."reservation_tables" ADD CONSTRAINT "fk_reservation" FOREIGN KEY ("reservation_id") REFERENCES "public"."reservations" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE "public"."reservation_tables" ADD CONSTRAINT "fk_table" FOREIGN KEY ("table_id") REFERENCES "public"."tables" ("id") ON DELETE CASCADE ON UPDATE NO ACTION;

-- ----------------------------
-- Foreign Keys structure for table reservations
-- ----------------------------
ALTER TABLE "public"."reservations" ADD CONSTRAINT "reservations_customer_id_fkey" FOREIGN KEY ("customer_id") REFERENCES "public"."customers" ("id") ON DELETE NO ACTION ON UPDATE NO ACTION;

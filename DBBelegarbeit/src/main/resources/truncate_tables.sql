START TRANSACTION;

--loeschen aller angelegten Datensaetze
--Tabelle written
TRUNCATE TABLE written CASCADE;
--Tabelle published
TRUNCATE TABLE published CASCADE;
--Tabelle commented
TRUNCATE TABLE commented CASCADE;
--Tabelle translated
TRUNCATE TABLE translated CASCADE;
-- Tabelle author
TRUNCATE TABLE author CASCADE;
--Tabelle publisher
TRUNCATE TABLE publisher CASCADE;
--Tabelle reader
TRUNCATE TABLE reader CASCADE;
--Tabelle book
TRUNCATE TABLE Book CASCADE;
--Tabelle category
TRUNCATE TABLE category CASCADE;
--Tabelle translator
TRUNCATE TABLE translator CASCADE;
--Tabelle language
TRUNCATE TABLE language CASCADE;
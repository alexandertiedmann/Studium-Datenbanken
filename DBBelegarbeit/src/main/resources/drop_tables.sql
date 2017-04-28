START TRANSACTION;

--loeschen aller angelegten Tabellen
--Tabelle written
DROP TABLE written CASCADE;
--Tabelle published
DROP TABLE published CASCADE;
--Tabelle commented
DROP TABLE commented CASCADE;
--Tabelle translated
DROP TABLE translated CASCADE;
-- Tabelle author
DROP TABLE author CASCADE;
--Tabelle publisher
DROP TABLE publisher CASCADE;
--Tabelle reader
DROP TABLE reader CASCADE;
--Tabelle book
DROP TABLE Book CASCADE;
--Tabelle category
DROP TABLE category CASCADE;
--Tabelle translator
DROP TABLE translator CASCADE;
--Tabelle language
DROP TABLE language CASCADE;
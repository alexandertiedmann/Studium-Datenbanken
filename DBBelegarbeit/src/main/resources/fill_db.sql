START TRANSACTION;

--einfuegen von Lesern
INSERT INTO reader (first_name, last_name) VALUES ('Heinz','Karl');
INSERT INTO reader (first_name, last_name) VALUES ('Mueller','Lotte');
INSERT INTO reader (first_name, last_name) VALUES ('Meier','Lisa');
INSERT INTO reader (first_name, last_name) VALUES ('Schulz','Otto');

--einfuegen von Kategorien
INSERT INTO category (name) VALUES ('Fantasy');
INSERT INTO category (name) VALUES ('Horror');
INSERT INTO category (name) VALUES ('Krimi');
INSERT INTO category (name) VALUES ('Komoedie');
INSERT INTO category (name) VALUES ('Fachliteratur');

--einfuegen von Autoren
INSERT INTO author (first_name, last_name, homeland, birth) VALUES ('Brent', 'Weeks', 'USA', '1980-03-31');
INSERT INTO author (first_name, last_name, homeland, birth) VALUES ('Sergej', 'Lukanjeko', 'Russland', '1976-02-13');
INSERT INTO author (first_name, last_name, homeland, birth) VALUES ('Trudi', 'Canavan', 'Italien', '1971-07-28');
INSERT INTO author (first_name, last_name, homeland, birth) VALUES ('Wolfgang', 'Hohlbein', 'Deutschland', '1954-09-17');

--einfuegen von Herausgebern
INSERT INTO publisher (name, headquarter) VALUES ('Blenvaled', 'Deutschland');
INSERT INTO publisher (name, headquarter) VALUES ('Piper', 'Deutschland');
INSERT INTO publisher (name, headquarter) VALUES ('Bastei Luebbe', 'Deutschland');
INSERT INTO publisher (name, headquarter) VALUES ('Langenscheidt', 'Deutschland');

--einfuegen von Uebersetzern
INSERT INTO translator (first_name, last_name) VALUES ('Wolfgang', 'Mueller');
INSERT INTO translator (first_name, last_name) VALUES ('Reinhardt', 'Stahl');
INSERT INTO translator (first_name, last_name) VALUES ('Test', 'Fall');
INSERT INTO translator (first_name, last_name) VALUES ('Reiner', 'Zufall');

--einfuegen von Sprachen
INSERT INTO language (name) VALUES ('deutsch');
INSERT INTO language (name) VALUES ('italienisch');
INSERT INTO language (name) VALUES ('russisch');
INSERT INTO language (name) VALUES ('englisch');

--einfuegen von buechern
INSERT INTO book (title, subtitle, category, price) VALUES ('Maerchenmond', 'Maerchenmonds Kinder', 1, 15.99);
INSERT INTO book (title, subtitle, category, price) VALUES ('Maerchenmond', 'Maerchenmonds Erben', 1, 14.99);
INSERT INTO book (title, subtitle, category, price) VALUES ('Maerchenmond', 'Maerchenmonds Zauberin', 1, 15.00);
INSERT INTO book (title, subtitle, category, price) VALUES ('Die Unsichtbare Bibliothek', '', 1, 19.99);
INSERT INTO book (title, subtitle, category, price) VALUES ('Die Waechter der Nacht', '', 2, 21.00);
INSERT INTO book (title, subtitle, category, price) VALUES ('Schlechtes Karma', '', 4,9.99);

--einfuegen von geschrieben-Werten
INSERT INTO written (author_id, book_id) VALUES (4,1);
INSERT INTO written (author_id, book_id) VALUES (4,2);
INSERT INTO written (author_id, book_id) VALUES (4,3);
INSERT INTO written (author_id, book_id) VALUES (3,4);
INSERT INTO written (author_id, book_id) VALUES (2,5);
INSERT INTO written (author_id, book_id) VALUES (3,6);

--einguegen von veroeffentlicht-Werten
INSERT INTO published (publisher_id, book_id, date) VALUES (1,1,'1992-05-06');
INSERT INTO published (publisher_id, book_id, date) VALUES (1,2,'1994-06-08');
INSERT INTO published (publisher_id, book_id, date) VALUES (1,3,'1996-02-10');
INSERT INTO published (publisher_id, book_id, date) VALUES (2,4,'2001-03-21');
INSERT INTO published (publisher_id, book_id, date) VALUES (3,5,'2016-12-20');
INSERT INTO published (publisher_id, book_id, date) VALUES (2,6,'2012-06-10');

--einfuegen von Kommentaren
INSERT INTO commented (reader_id, book_id, comment, date) VALUES (1,4,'Geiles Buch. Sehr Spannend','2016-12-24');
INSERT INTO commented (reader_id, book_id, comment, date) VALUES (3,5,'Sehr schoen geschrieben.','2017-01-10');
INSERT INTO commented (reader_id, book_id, comment, date) VALUES (3,6,'Nicht so interessant','2017-03-17');
INSERT INTO commented (reader_id, book_id, comment, date) VALUES (4,6,'Nicht mein Fall','2017-02-16');

--einfuegen von uebersetzungen
INSERT INTO translated (translator_id, book_id, language_id) VALUES (1,4,2);
INSERT INTO translated (translator_id, book_id, language_id) VALUES (1,4,3);
INSERT INTO translated (translator_id, book_id, language_id) VALUES (2,5,2);
INSERT INTO translated (translator_id, book_id, language_id) VALUES (3,4,1);
INSERT INTO translated (translator_id, book_id, language_id) VALUES (2,4,4);

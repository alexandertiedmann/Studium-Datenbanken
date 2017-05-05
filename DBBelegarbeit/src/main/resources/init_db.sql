START TRANSACTION;

--Anlegen der Tabellen (Entitaeten)
--Tabelle author
CREATE TABLE author(
  author_id SERIAL PRIMARY KEY NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20) NOT NULL,
  homeland VARCHAR(20),
  birth DATE
);
--Tabelle publisher
CREATE TABLE publisher(
  publisher_id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(20) NOT NULL,
  headquarter VARCHAR(20)
);
--Tabelle reader
CREATE TABLE reader(
  reader_id SERIAL PRIMARY KEY NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20)
);
--Tabelle category
CREATE TABLE category(
  category_id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(20) NOT NULL
);
--Tabelle translator
CREATE TABLE translator(
  translator_id SERIAL PRIMARY KEY NOT NULL,
  first_name VARCHAR(20) NOT NULL,
  last_name VARCHAR(20) NOT NULL
);
--Tabelle book
CREATE TABLE Book(
  book_id SERIAL PRIMARY KEY NOT NULL,
  title VARCHAR(40) NOT NULL,
  subtitle VARCHAR(40),
  category INTEGER REFERENCES category(category_id) NOT NULL,
  price FLOAT NOT NULL
);
--Tabelle language
CREATE TABLE language(
  language_id SERIAL PRIMARY KEY NOT NULL,
  name VARCHAR(15) NOT NULL
);

--Anlegen der Tabellen (Relationen)
--Tabelle written
CREATE TABLE written(
  author_id INTEGER REFERENCES author(author_id) NOT NULL,
  book_id INTEGER REFERENCES book(book_id) NOT NULL
);
--Tabelle published
CREATE TABLE published (
  publisher_id INTEGER REFERENCES publisher(publisher_id) NOT NULL,
  book_id INTEGER REFERENCES book(book_id) NOT NULL,
  date DATE
);
--Tabelle commented
CREATE TABLE commented(
  reader_id INTEGER REFERENCES reader(reader_id) NOT NULL,
  book_id INTEGER REFERENCES book(book_id) NOT NULL,
  comment TEXT NOT NULL,
  date DATE
);
--Tabelle translated
CREATE TABLE translated (
  translator_id INTEGER REFERENCES translator(translator_id) NOT NULL,
  book_id INTEGER REFERENCES book(book_id) NOT NULL,
  language_id INTEGER REFERENCES language(language_id) NOT NULL
);

GRANT INSERT,DELETE,UPDATE,SELECT ON book_book_id_seq TO _s0556127__belegarbeit_generic;
GRANT INSERT,DELETE ON book_book_id_seq TO _s0556127__belegarbeit_generic;
GRANT SELECT ON category TO _s0556127__belegarbeit_generic;
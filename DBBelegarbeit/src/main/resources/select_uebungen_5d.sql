-- Aufgabe 5d
--1. Abfrage
-- Anzahl der Buecher die jeder Autor geschrieben hat
SELECT
  a.last_name AS Nachname, count(*) AS Buecheranzahl
FROM
  author a, written w
WHERE
  a.author_id = w.author_id
GROUP BY
  a.last_name
;

--2. Abfrage
-- Anzahl der Kommentare fuer jeden Leser
SELECT
  r.first_name AS Vorname, count(*) AS Kommentaranzahl
FROM
  reader r, commented c
WHERE
  c.reader_id = r.reader_id
GROUP BY
  r.first_name
;

--3. Abfrage
-- Anzahl der Buecher die jeder Verlag herausgebracht hat
SELECT
  publisher.name AS Verlag, count(*) AS Buecheranzahl
FROM
  publisher, published
WHERE
  publisher.publisher_id = published.publisher_id
GROUP BY
  publisher.name
;

--4. Abfrage
-- Anzahl der Buecher in jeder Kategorie
SELECT
  c.name AS Kategorie, count(*) AS Buecheranzahl
FROM
  category c, book b
WHERE
  c.category_id = b.category
GROUP BY
  c.name
;
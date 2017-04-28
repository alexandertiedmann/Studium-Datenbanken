--Aufgabe 5e
--Abfrage 1
-- Anzahl der Buecher in Kategorien die mehr als 2 Buecher haben
SELECT
  c.name AS Kategorie, count(*) AS Buecheranzahl
FROM
  category c, book b
WHERE
  c.category_id = b.category
GROUP BY
  c.name
HAVING
  count(*) > 2
;

--Abfrage 2
--Summe der Buecherpreise fuer die Autoren bei denen der Preis kleiner als 30€ betraegt
SELECT
  a.last_name AS Autor, sum(b.price) AS Preis
FROM
  author a, book b, written w
WHERE
  w.book_id = b.book_id
AND
  w.author_id = a.author_id
GROUP BY
  a.last_name
HAVING
  sum(b.price) < 30
;
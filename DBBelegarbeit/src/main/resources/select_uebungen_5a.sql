--Aufgabe 5a
--Alle Buecher mit Titel, Untertitel und dem Autor
SELECT
  b.title,b.subtitle,a.first_name,a.last_name
FROM
  book b, author a, written w
WHERE
  b.book_id = w.book_id
AND
  a.author_id = w.author_id
ORDER BY
  1
;

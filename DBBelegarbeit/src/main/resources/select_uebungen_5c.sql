-- Aufgabe 5c
-- 1. Abfrage
-- Alle nicht kommentierten Buecher
SELECT
  b.title, b.subtitle
FROM
  book b
WHERE
  b.book_id NOT IN
  (
    SELECT
      book_id
    FROM
      commented
    JOIN reader ON
    commented.reader_id = reader.reader_id
  )
ORDER BY
1;

--2. Abfrage
-- Der Name des Juengsten Autors
SELECT
  first_name, last_name
FROM
  author
WHERE
  birth >= all (SELECT birth from author)
;

-- Augabe 5b
-- 1. Abfrage
--Alle nicht kommentierten Buecher
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
-- Alle Buecher die von Autoren mit dem Vornamen 'Test' uebersetzt wurden
SELECT
  b.title,b.subtitle
FROM
  book b
WHERE
  b.book_id in
  (
    SELECT
      translated.book_id
    FROM
      translated
    JOIN translator ON
      translated.translator_id = translator.translator_id
    WHERE
      translator.first_name='Test'
  )
ORDER BY 1
;
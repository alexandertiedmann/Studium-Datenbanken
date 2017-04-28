--Aufgabe 5a
--Alle Namen der Autoren und Uebersetzer (keine Doppelten)
SELECT a.first_name, a.last_name
FROM author a
UNION
SELECT t.first_name, t.last_name
FROM translator t
ORDER BY 1;

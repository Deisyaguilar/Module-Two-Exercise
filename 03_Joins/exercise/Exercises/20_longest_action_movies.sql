-- 20. The titles, lengths, and release dates of the 5 longest movies in the "Action" genre.
-- Order the movies by length (highest first), then by release date (latest first).
-- (5 rows, expected lengths around 180 - 200)
select m.title, m.length_minutes, m.release_date
from movie m
join movie_genre mg on m.movie_id = mg.movie_id
join genre g on mg.genre_id = g.genre_id
where g.genre_name like 'Action'
order by m.length_minutes desc, m.release_date desc
limit 5;
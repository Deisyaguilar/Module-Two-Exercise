-- 8. The genres of movies that Robert De Niro has appeared in that were released in 2010 or later, sorted alphabetically.
-- (6 rows)
select distinct g.genre_name
from genre g
join movie_genre mg on mg.genre_id = g.genre_id
join movie m on m.movie_id = mg.movie_id
join movie_actor ma on ma.movie_id = m.movie_id
join person p on p.person_id = ma.actor_id
where p.person_name = 'Robert De Niro' and m.release_date >= '2010-01-01'
order by g.genre_name
limit 6;

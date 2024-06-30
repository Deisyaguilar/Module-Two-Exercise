-- 7. The genres of movies that Christopher Lloyd has appeared in, sorted alphabetically.
-- (8 rows) Hint: DISTINCT will prevent duplicate values in your query results.
select distinct g.genre_name
from genre g
join movie_genre mg on mg.genre_id = g.genre_id
join movie m on m.movie_id = mg.movie_id
join movie_actor ma on ma.movie_id = m.movie_id
join person p on p.person_id = ma.actor_id
where person_name like 'Christopher Lloyd'
order by genre_name
limit 8;

-- 15. The title of the movie and the name of director for movies where the director was also an actor in the same movie.
-- Order the results by movie title (A-Z)
-- (73 rows)
select m.title, p.person_name
from movie m
join movie_actor ma on ma.movie_id = m.movie_id
join person p on p.person_id = m.director_id
where ma.actor_id = m.director_id
order by m.title
limit 73;
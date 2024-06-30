-- 1. The titles and release dates of movies that Tom Hanks has appeared in.
-- Order the results by release date, newest to oldest.
-- (47 rows)
select m.title, m.release_date 
from movie m
join movie_actor on m.movie_id = movie_actor.movie_id
join person on person_id = movie_actor.actor_id
where person_name = 'Tom Hanks'
order by m.release_date desc;


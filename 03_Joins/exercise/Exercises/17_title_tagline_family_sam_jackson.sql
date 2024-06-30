-- 17. The titles and taglines of movies that are in the "Family" genre that Samuel L. Jackson has acted in.
-- Order the results alphabetically by movie title.
-- (4 rows)
select m.title, m.tagline
from movie m
join movie_genre mg on m.movie_id = mg.movie_id
join genre g on mg.genre_id = g.genre_id
join movie_actor ma on m.movie_id = ma.movie_id
join person p on ma.actor_id = p.person_id
where genre_name like 'Family' and p.person_name like 'Samuel L. Jackson'
order by m.title
limit 4;

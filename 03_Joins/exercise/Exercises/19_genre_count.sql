-- 19. The genre name and the number of movies in each genre. Name the count column 'num_of_movies'.
-- Order the results from the highest movie count to the lowest.
-- (19 rows, the highest expected count is around 400).
select genre_name, count(m.movie_id) as num_of_movies
from movie m 
join movie_genre using(movie_id)
join genre g using(genre_id)
group by g.genre_name
order by num_of_movies desc
limit 19;
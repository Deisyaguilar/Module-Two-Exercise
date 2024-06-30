 -- 18. The average length of movies in the "Science Fiction" genre. Name the column 'average_length'.
-- (1 row, expected result between 110-120)
select avg(m.length_minutes) as average_length
from movie m
join movie_genre mg on m.movie_id = mg.movie_id
join genre g on mg.genre_id = g.genre_id
where g.genre_name like 'Science Fiction' 


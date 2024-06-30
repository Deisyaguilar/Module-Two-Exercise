-- 6. The genres of "The Wizard of Oz" sorted in alphabetical order (A-Z).
-- (3 rows)
select g.genre_name
from genre g
join movie_genre mg on mg.genre_id = g.genre_id
join movie m on m.movie_id = mg.movie_id
where m.title like 'The Wizard of Oz'
order by g.genre_name;

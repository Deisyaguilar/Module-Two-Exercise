-- 9. The titles of movies directed by James Cameron, sorted alphabetically.
-- (6 rows)
select m.title
from movie m
join person p on p.person_id = m.director_id
where person_name like 'James Cameron'
order by title;

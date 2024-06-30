-- 2. The names and birthdays of actors in "The Fifth Element"
-- Order the results alphabetically (A-Z) by name.
-- (15 rows)
select person_name, birthday
from movie a
join movie_actor using (movie_id)
join person on actor_id = person_id
where title = 'The Fifth Element'
order by person_name,  birthday
limit 15;



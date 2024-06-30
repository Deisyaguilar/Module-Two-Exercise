-- 21. For every person in the person table with the first name of "George", list the number of movies they've been in. Name the count column 'num_of_movies'.
-- Include all Georges, even those that have not appeared in any movies. Display the names in alphabetical order.
-- (59 rows)
-- TIP: This one can be a little tricky. If you're off by one, look closer at each clause of your statement. There's something you can change to get a different result set.
select p.person_name, count(m.title) as num_of_movies
from person p
left join movie_actor ma on p.person_id = ma.actor_id
left join movie m on ma.movie_id = m.movie_id
where p.person_name like 'George %'
group by p.person_id
order by p.person_name

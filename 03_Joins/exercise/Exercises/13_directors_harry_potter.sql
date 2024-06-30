-- 13. The directors of the movies in the "Harry Potter Collection", sorted alphabetically.
-- (4 rows)
select distinct p.person_name
from person p
join movie m on m.director_id = p.person_id
join collection c on c.collection_id = m.collection_id
where collection_name = 'Harry Potter Collection'
order by p.person_name
limit 4;

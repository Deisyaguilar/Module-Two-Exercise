-- 11. The titles of the movies in the "Star Wars Collection" ordered by release date, most recent first.
-- (9 rows)
select m.title
from movie m
join collection c on c.collection_id = m.collection_id
where collection_name = 'Star Wars Collection'
order by release_date desc;

-- 10. The names of directors who have directed a movie over 3 hours [180 minutes], sorted alphabetically.
-- (15 rows)
select distinct p.person_name
from person p
join movie m on m.director_id = p.person_id
where m.length_minutes > '180'
order by p.person_name
limit 15;

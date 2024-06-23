-- 13. The name, abbreviation, and population of all records in the state table with no official nickname (NULL).
-- Order the results by state name alphabetically.
-- (5 rows)
select state_name, state_abbreviation, population
from state
where state_nickname is null
order by state_name 
limit 5;

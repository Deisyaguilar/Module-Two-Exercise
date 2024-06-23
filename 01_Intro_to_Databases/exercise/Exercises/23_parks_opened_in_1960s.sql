-- 23. The name and date established of parks opened in the 1960s.
-- Order the results by date established, oldest first.
-- (6 rows)
select park_name, date_established
from park 
where date_established >= '1960-01-01' and date_established < '1970-01-01'
order by date_established 
limit 6;

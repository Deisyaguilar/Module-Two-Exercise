-- 21. The name and nickname for the largest state.
-- (1 row)
select state_name, state_nickname
from state
where area = (select max(area)from state)
limit 1;
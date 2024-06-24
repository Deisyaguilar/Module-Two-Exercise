-- 21. The census region, and the average, minimum, and maximum population of states and districts in each census region. Exclude ones that don't have a census region.
-- Name the population columns 'average_population, 'min_population', and 'max_population'.
-- Order the results from lowest to highest average population.
-- (4 rows)
select census_region, avg(population) AS average_population, min(population) AS min_population, max(population) AS max_population
from state
where census_region is not null
group by census_region
order by average_population
limit 4;

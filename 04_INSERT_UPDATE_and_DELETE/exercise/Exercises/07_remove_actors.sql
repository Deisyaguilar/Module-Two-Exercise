-- 7. Remove the actor appearances in "Avengers: Infinity War" (14 rows)
-- Note: Don't remove the actors themeselves, just make it so it seems no one appeared in the movie.
--select * from movie where title = 'Avengers: Infinity War' 299536
--select * from movie_actor where movie_id = 299536
delete from movie_actor where movie_id = (select movie_id from movie where title = 'Avengers: Infinity War')

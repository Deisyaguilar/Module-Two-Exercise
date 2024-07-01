-- 4. Add a "Sports" genre to the genre table. Add the movie "Coach Carter" to the newly created genre. (1 row each)
insert into genre(genre_name)
values ('Sports');
--select * from genre
--select * from movie where title like 'Coach Carter'
insert into movie_genre(movie_id, genre_id)
values (7214, 11771);
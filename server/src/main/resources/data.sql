/*INSERT INTO subscriber (password, username, first_name, last_name)
VALUES ('pwd', 'usr', 'root', 'root')
  ,('pwd2', 'usr2', 'root2', 'root2');

INSERT INTO tv_show (description, name, tvdb_id, airs_day_of_week, airs_time,
                     network, post_url, status_id)
VALUES('', 'test_show', 2, 'sunday', '12:30', 'amc', 'url', 1),
  ('', 'test_show2', 1, 'sunday', '12:30', 'amc', 'url', 1);

INSERT INTO episode (description, name, tvdb_id, air_date, episode_number,
                     season_number, tv_show_id)
VALUES ('', 'ep1', 1, '2018-08-24', 1, 1, 1),
  ('', 'ep2', 2, '2018-08-23', 2, 1, 1);

INSERT INTO subscription (tv_show_id, user_id)
VALUES (1, 1),
  (1, 2),
  (2, 2);

INSERT INTO watched_episode (watched, episode_id, user_id)
VALUES (true, 1, 1), -- u1 e1
  (false, 2, 1), -- u1 e2
  (false, 1, 2),
  (FALSE, 2, 2);
*/

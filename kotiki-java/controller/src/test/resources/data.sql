Set search_path to kotikisecurity;

INSERT INTO owners(username, password)
VALUES
('admin', '$2a$10$apONicSdW6t8gfxElHBOGeOy6DTyWicfAkMOWF0ktgTmxuF3p3V2i'),
('user1', '$2a$10$Uqxntuu62VkeeZlSAQuKJOGnYur8SZ0oJeg7f0smEcVSVjQjrXEce'),
('user2', '$2a$10$eAPj3CnzTaR1iwecRhUaZuOayObRb3azj.PrlbojkbEJ76HLGVTHO');

INSERT INTO user_authority (user_id, roles)
VALUES (1, 'ADMIN'),
       (2, 'USER'),
       (3, 'USER');


INSERT INTO cats(name, birth_date, breed, color, owner_id)
VALUES
('Sonya', '2017-02-12', 'without', 'BLACK', 2),
('Mysya', '2019-03-11', 'without', 'BLACK', 2),
('Cat1', '2022-10-11', 'without', 'WHITE', 2),
('Cat2', '2021-10-11', 'without', 'WHITE', 2),
('Cat3', '2011-10-11', 'without', 'RED', 2),
('Cat4', '2022-10-11', 'without', 'RED', 2),
('Carrot', '2010-12-22', 'without', 'RED', 3),
('Archi', '2011-12-22', 'without', 'WHITE', 3);


INSERT INTO friendship
VALUES
(1, 2),
(1, 3);

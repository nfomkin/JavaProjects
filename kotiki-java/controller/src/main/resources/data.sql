Delete from kotiki.cats;
Delete from kotiki.owners;
Delete from kotiki.friendship;


INSERT INTO kotiki.owners (id, name, birth_date)
VALUES
(1, 'Nikita', '2002-10-12'),
(2, 'Renat', '2002-06-09'),
(3, 'Sanya', '2002-04-22'),
(4, 'Lev', '2002-01-14');

INSERT INTO kotiki.cats (id, name, birth_date, breed, color, owner_id)
VALUES
(1, 'Sonya', '2017-02-12', 'without', 'BLACK', 1),
(2, 'Mysya', '2019-03-11', 'without', 'BLACK', 1),
(3, 'Carrot', '2010-12-22', 'without', 'BLACK', 2),
(4, 'Archi', '2011-12-22', 'without', 'BLACK', 2);

INSERT INTO kotiki.friendship
VALUES
(1, 2),
(1, 3);

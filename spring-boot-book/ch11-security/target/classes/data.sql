INSERT INTO idol(name, birthday, blood_type)
VALUES ('ふじの　もも', '2001-01-01', 'A'),
       ('とうの　りんか', '2002-02-02', 'B'),
       ('ささもと　みく', '2003-03-03', 'O'),
       ('うえだ　ひなこ', '2004-04-05', 'AB'),
       ('しょうじ　いおり', '2005-05-05', 'UNKNOWN');

-- passwordは"operator"
INSERT INTO system_operator(email, name, role, password)
VALUES ('operator@idol.com', 'オペレーター', 'ROLE_OPERATOR', '$2a$10$wjCrj14XAZ6qze0rFAGCZO16WXOXE55anHjVEvDfwS9rd0aEA.81.');
-- passwordは"manager"
INSERT INTO system_operator(email, name, role, password)
VALUES ('manager@idol.com', 'マネージャー', 'ROLE_MANAGER', '$2a$10$gykzTwR9vEqV9BjnRXPUe.aQnMsodtmd1ZCu2MIbxsgFU8sUu9Pxa');

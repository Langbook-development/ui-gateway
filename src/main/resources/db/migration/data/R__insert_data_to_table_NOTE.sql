delete from NOTE;
insert into NOTE(id, title, content, sort_idx, parent_id, category_id)
values (1000, 'Basic grammar', 'This is some placeholder content!', '0', NULL, 1000),
       (1001, 'Present simple', 'Try to add some more text if you want to play with it', '0', 1000, 1000),
       (1002, 'Present continuous', 'Content for present continuous', '1', 1000 , 1000),
       (1003, 'Vocabulary', 'This is vocabulary', '1', NULL, 1000),
       (1004, 'Russian basic grammar', 'There is no basic grammar in russian', '2', NULL, 1000),
       (1005, 'Examples', 'Example number one', '0', 1001 , 1000),
       (1006, 'Exercises', 'Some Exercise', '1', 1001 , 1000),
       (1007, 'Spelling', 'Spelling', '2', 1001, 1000),
       (1008, 'Examples', 'Some more examples', '2', 1002 , 1000),
       (1009, 'Exercises', 'Some more exercise', '1', 1002, 1000);
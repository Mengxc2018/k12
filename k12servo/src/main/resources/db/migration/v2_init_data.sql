INSERT INTO `school` (`id`, `name`, `desc`)
VALUES (1, '博顿幼儿园', '');
INSERT INTO `grade` (`id`, `name`, `desc`, `school_id`)
VALUES (1, '大班', '', 1),
  (2, '中班', '', 1),
  (3, '小班', '', 1);
INSERT INTO `course` (`id`, `name`, `desc`, `school_id`)
VALUES (1, '课程', '', 1);
INSERT INTO `user` (`id`, `mobile`, `username`, `password`, `first_name`, `last_name`, `gender`, `state`, `created_at`)
VALUES
  (1, '13716149443', 'fenghua.wang', '', 'fenghua', 'wang', 'MALE', 'ACTIVE', CURRENT_TIMESTAMP);
INSERT INTO `school_users` (`school_id`, `user_id`) VALUES (1, 1);
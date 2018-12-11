/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table course
# ------------------------------------------------------------
CREATE TABLE `course` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `desc` varchar(512) DEFAULT NULL,
  `school_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `course_school_fk` (`school_id`),
  CONSTRAINT `course_school_fk` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table grade
# ------------------------------------------------------------
CREATE TABLE `grade` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `desc` varchar(512) DEFAULT NULL,
  `school_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `grade_school_fk` (`school_id`),
  CONSTRAINT `grade_school_fk` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table guardian
# ------------------------------------------------------------
CREATE TABLE `guardian` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `student_id` int(11) unsigned NOT NULL,
  `relation_type` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `guardian_student_fk` (`student_id`),
  KEY `guardian_user_fk` (`user_id`),
  CONSTRAINT `guardian_student_fk` FOREIGN KEY (`student_id`) REFERENCES `student` (`id`) ON DELETE CASCADE,
  CONSTRAINT `guardian_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table klass
# ------------------------------------------------------------
CREATE TABLE `klass` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL DEFAULT '',
  `desc` varchar(128) DEFAULT NULL,
  `grade_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `klass_grade_fk` (`grade_id`),
  CONSTRAINT `klass_grade_fk` FOREIGN KEY (`grade_id`) REFERENCES `grade` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table klass_feed
# ------------------------------------------------------------
CREATE TABLE `klass_feed` (
  `id` bigint(21) unsigned NOT NULL AUTO_INCREMENT,
  `klass_id` int(10) unsigned NOT NULL,
  `content` varchar(128) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `klass_feed_klass_fk` (`klass_id`),
  KEY `klass_feed_creator_fk` (`created_by`),
  CONSTRAINT `klass_feed_creator_fk` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `klass_feed_klass_fk` FOREIGN KEY (`klass_id`) REFERENCES `klass` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table klass_feed_comment
# ------------------------------------------------------------
CREATE TABLE `klass_feed_comment` (
  `id` bigint(21) unsigned NOT NULL AUTO_INCREMENT,
  `feed_id` bigint(21) unsigned NOT NULL,
  `content` varchar(64) NOT NULL DEFAULT '',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int(11) unsigned DEFAULT NULL,
  `reply_to` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `klass_feed_comment_feed_fk` (`feed_id`),
  KEY `klass_feed_comment_creator_fk` (`created_by`),
  KEY `klass_feed_comment_repliee_fk` (`reply_to`),
  CONSTRAINT `klass_feed_comment_creator_fk` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `klass_feed_comment_feed_fk` FOREIGN KEY (`feed_id`) REFERENCES `klass_feed` (`id`) ON DELETE CASCADE,
  CONSTRAINT `klass_feed_comment_repliee_fk` FOREIGN KEY (`reply_to`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table klass_feed_images
# ------------------------------------------------------------
CREATE TABLE `klass_feed_images` (
  `feed_id` bigint(21) unsigned NOT NULL,
  `url` varchar(256) NOT NULL DEFAULT '',
  KEY `klass_feed_images_feed_fk` (`feed_id`),
  CONSTRAINT `klass_feed_images_feed_fk` FOREIGN KEY (`feed_id`) REFERENCES `klass_feed` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table klass_plan
# ------------------------------------------------------------
CREATE TABLE `klass_plan` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `klass_id` int(10) unsigned NOT NULL,
  `plan_type` varchar(16) NOT NULL DEFAULT '',
  `content` varchar(512) NOT NULL DEFAULT '',
  `created_at` timestamp NULL DEFAULT NULL,
  `created_by` int(11) unsigned DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL,
  `updated_by` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `klass_plan_creator_fk` (`created_by`),
  KEY `klass_plan_updater_fk` (`updated_by`),
  CONSTRAINT `klass_plan_creator_fk` FOREIGN KEY (`created_by`) REFERENCES `user` (`id`) ON DELETE SET NULL,
  CONSTRAINT `klass_plan_klass_fk` FOREIGN KEY (`id`) REFERENCES `klass` (`id`) ON DELETE CASCADE,
  CONSTRAINT `klass_plan_updater_fk` FOREIGN KEY (`updated_by`) REFERENCES `user` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table role
# ------------------------------------------------------------
CREATE TABLE `role` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `desc` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table role_permissions
# ------------------------------------------------------------
CREATE TABLE `role_permissions` (
  `role_id` int(11) unsigned NOT NULL,
  `permission` varchar(128) NOT NULL DEFAULT '',
  PRIMARY KEY (`role_id`,`permission`),
  KEY `permission` (`permission`),
  CONSTRAINT `role_permission_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table school
# ------------------------------------------------------------
CREATE TABLE `school` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(128) DEFAULT NULL,
  `desc` varchar(512) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table school_users
# ------------------------------------------------------------
CREATE TABLE `school_users` (
  `school_id` int(11) unsigned NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`school_id`,`user_id`),
  KEY `school_user_user_fk` (`user_id`),
  CONSTRAINT `school_user_school_fk` FOREIGN KEY (`school_id`) REFERENCES `school` (`id`) ON DELETE CASCADE,
  CONSTRAINT `school_user_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table student
# ------------------------------------------------------------
CREATE TABLE `student` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(16) NOT NULL DEFAULT '',
  `last_name` varchar(16) NOT NULL DEFAULT '',
  `gender` varchar(16) NOT NULL DEFAULT '',
  `birthday` timestamp NULL DEFAULT NULL,
  `avatar` varchar(512) DEFAULT NULL,
  `joined_at` timestamp NULL DEFAULT NULL,
  `klass_id` int(11) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_klass_fk` (`klass_id`),
  CONSTRAINT `student_klass_fk` FOREIGN KEY (`klass_id`) REFERENCES `klass` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table teacher
# ------------------------------------------------------------

CREATE TABLE `teacher` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(11) unsigned NOT NULL,
  `joined_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `teacher_user_fk` (`user_id`),
  CONSTRAINT `teacher_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table teaching
# ------------------------------------------------------------
CREATE TABLE `teaching` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `teacher_id` int(11) unsigned NOT NULL,
  `course_id` int(11) unsigned NOT NULL,
  `klass_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `teaching_teacher_fk` (`teacher_id`),
  KEY `teaching_course_fk` (`course_id`),
  KEY `teaching_klass_fk` (`klass_id`),
  CONSTRAINT `teaching_course_fk` FOREIGN KEY (`course_id`) REFERENCES `course` (`id`) ON DELETE CASCADE,
  CONSTRAINT `teaching_klass_fk` FOREIGN KEY (`klass_id`) REFERENCES `klass` (`id`) ON DELETE CASCADE,
  CONSTRAINT `teaching_teacher_fk` FOREIGN KEY (`teacher_id`) REFERENCES `teacher` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user
# ------------------------------------------------------------
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `mobile` varchar(16) NOT NULL DEFAULT '',
  `username` varchar(64) DEFAULT '',
  `password` varchar(256) DEFAULT '',
  `first_name` varchar(16) DEFAULT NULL,
  `last_name` varchar(16) DEFAULT NULL,
  `gender` varchar(16) DEFAULT NULL,
  `avatar` varchar(256) DEFAULT '',
  `state` varchar(16) NOT NULL DEFAULT '',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobile` (`mobile`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table user_roles
# ------------------------------------------------------------
CREATE TABLE `user_roles` (
  `user_id` int(11) unsigned NOT NULL,
  `role_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`),
  KEY `user_role_role_fk` (`role_id`),
  CONSTRAINT `user_role_role_fk` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`) ON DELETE CASCADE,
  CONSTRAINT `user_role_user_fk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
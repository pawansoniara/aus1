-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.5.22


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema pspdb
--

CREATE DATABASE IF NOT EXISTS pspdb;
USE pspdb;

--
-- Definition of table `access_level`
--

DROP TABLE IF EXISTS `access_level`;
CREATE TABLE `access_level` (
  `access_id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `access_level` varchar(100) DEFAULT NULL,
  `acess_description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`access_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `access_level`
--

/*!40000 ALTER TABLE `access_level` DISABLE KEYS */;
INSERT INTO `access_level` (`access_id`,`access_level`,`acess_description`) VALUES 
 (1,'level of access 1','administrator'),
 (2,'level of access 2','supervisor');
/*!40000 ALTER TABLE `access_level` ENABLE KEYS */;


--
-- Definition of table `assessment`
--

DROP TABLE IF EXISTS `assessment`;
CREATE TABLE `assessment` (
  `AID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SID` int(10) unsigned DEFAULT NULL,
  `EID` int(10) unsigned DEFAULT NULL,
  `A1` mediumint(5) unsigned DEFAULT NULL,
  `A2` mediumint(5) unsigned DEFAULT NULL,
  PRIMARY KEY (`AID`),
  KEY `Index_2` (`SID`) USING BTREE,
  KEY `Index_3` (`EID`) USING BTREE,
  CONSTRAINT `FK_assessment_1` FOREIGN KEY (`SID`) REFERENCES `student` (`SID`),
  CONSTRAINT `FK_assessment_2` FOREIGN KEY (`EID`) REFERENCES `enrollment` (`EID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `assessment`
--

/*!40000 ALTER TABLE `assessment` DISABLE KEYS */;
/*!40000 ALTER TABLE `assessment` ENABLE KEYS */;


--
-- Definition of table `course`
--

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `CID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DESCRIPTION` varchar(500) NOT NULL,
  PRIMARY KEY (`CID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course`
--

/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`CID`,`DESCRIPTION`) VALUES 
 (6,'ABC'),
 (7,'ASSSS');
/*!40000 ALTER TABLE `course` ENABLE KEYS */;


--
-- Definition of table `course_assessment`
--

DROP TABLE IF EXISTS `course_assessment`;
CREATE TABLE `course_assessment` (
  `CAID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CID` int(10) unsigned NOT NULL,
  `AID` int(10) unsigned NOT NULL,
  `semester` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CAID`),
  KEY `Index_2` (`CID`) USING BTREE,
  KEY `Index_3` (`AID`) USING BTREE,
  CONSTRAINT `FK_course_assessment_1` FOREIGN KEY (`CID`) REFERENCES `course` (`CID`),
  CONSTRAINT `FK_course_assessment_2` FOREIGN KEY (`AID`) REFERENCES `assessment` (`AID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course_assessment`
--

/*!40000 ALTER TABLE `course_assessment` DISABLE KEYS */;
/*!40000 ALTER TABLE `course_assessment` ENABLE KEYS */;


--
-- Definition of table `course_conduction`
--

DROP TABLE IF EXISTS `course_conduction`;
CREATE TABLE `course_conduction` (
  `CCID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `TID` int(10) unsigned NOT NULL,
  `CID` int(10) unsigned NOT NULL,
  `semester` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`CCID`),
  KEY `Index_2` (`TID`) USING BTREE,
  KEY `Index_3` (`CID`) USING BTREE,
  CONSTRAINT `FK_course_conduction_2` FOREIGN KEY (`CID`) REFERENCES `course` (`CID`),
  CONSTRAINT `FK_course_conduction_1` FOREIGN KEY (`TID`) REFERENCES `teacher` (`TID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `course_conduction`
--

/*!40000 ALTER TABLE `course_conduction` DISABLE KEYS */;
INSERT INTO `course_conduction` (`CCID`,`TID`,`CID`,`semester`) VALUES 
 (3,2,6,'4'),
 (4,2,7,'4'),
 (5,3,6,'9');
/*!40000 ALTER TABLE `course_conduction` ENABLE KEYS */;


--
-- Definition of table `enrollment`
--

DROP TABLE IF EXISTS `enrollment`;
CREATE TABLE `enrollment` (
  `EID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `SID` int(10) unsigned NOT NULL,
  `CID` int(10) unsigned NOT NULL,
  `semester` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`EID`),
  KEY `Index_2` (`SID`) USING BTREE,
  KEY `Index_3` (`CID`) USING BTREE,
  CONSTRAINT `FK_enrollment_2` FOREIGN KEY (`CID`) REFERENCES `course` (`CID`),
  CONSTRAINT `FK_enrollment_1` FOREIGN KEY (`SID`) REFERENCES `student` (`SID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `enrollment`
--

/*!40000 ALTER TABLE `enrollment` DISABLE KEYS */;
/*!40000 ALTER TABLE `enrollment` ENABLE KEYS */;


--
-- Definition of table `lu_thesis_status`
--

DROP TABLE IF EXISTS `lu_thesis_status`;
CREATE TABLE `lu_thesis_status` (
  `thesis_status_id` tinyint(2) NOT NULL AUTO_INCREMENT,
  `status` varchar(100) DEFAULT NULL,
  `display_order` tinyint(2) DEFAULT '0',
  PRIMARY KEY (`thesis_status_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `lu_thesis_status`
--

/*!40000 ALTER TABLE `lu_thesis_status` DISABLE KEYS */;
INSERT INTO `lu_thesis_status` (`thesis_status_id`,`status`,`display_order`) VALUES 
 (1,'Started',2),
 (2,'Assigned',1),
 (3,'Completed',3),
 (4,'Submitted',4),
 (5,'Accepted',5);
/*!40000 ALTER TABLE `lu_thesis_status` ENABLE KEYS */;


--
-- Definition of table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `SID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) DEFAULT NULL,
  `surname` varchar(200) DEFAULT NULL,
  `email` varchar(150) DEFAULT NULL,
  `passwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`SID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student`
--

/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`SID`,`fname`,`surname`,`email`,`passwd`) VALUES 
 (1,'James','','30132345@gmail.com','james100'),
 (2,'aa','aa','bb@bb.com','test');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;


--
-- Definition of table `student_comment`
--

DROP TABLE IF EXISTS `student_comment`;
CREATE TABLE `student_comment` (
  `student_comment_id` int(8) NOT NULL AUTO_INCREMENT,
  `staff_id` tinyint(2) NOT NULL,
  `student_degree_id` int(10) NOT NULL,
  `cdate` datetime DEFAULT NULL,
  `acomment` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`student_comment_id`),
  KEY `fk_staff_id` (`staff_id`),
  KEY `fk_student_degree_id2` (`student_degree_id`),
  CONSTRAINT `fk_staff_id` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_degree_id2` FOREIGN KEY (`student_degree_id`) REFERENCES `student_degree` (`student_degree_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_comment`
--

/*!40000 ALTER TABLE `student_comment` DISABLE KEYS */;
INSERT INTO `student_comment` (`student_comment_id`,`staff_id`,`student_degree_id`,`cdate`,`acomment`) VALUES 
 (13,1,26,'2015-10-03 22:38:43','sadasdasdas');
/*!40000 ALTER TABLE `student_comment` ENABLE KEYS */;


--
-- Definition of table `student_degree`
--

DROP TABLE IF EXISTS `student_degree`;
CREATE TABLE `student_degree` (
  `student_degree_id` int(10) NOT NULL AUTO_INCREMENT,
  `student_id` mediumint(7) NOT NULL,
  `date_enrolled` date DEFAULT NULL,
  `date_completed` date DEFAULT NULL,
  `scholarship` varchar(100) DEFAULT NULL,
  `date_thesis_intend_submit` date DEFAULT NULL,
  `date_thesis_submit` date DEFAULT NULL,
  `thesis_title` varchar(50) DEFAULT NULL,
  `degree_type_id` tinyint(2) DEFAULT NULL,
  `date_confirmation_intended` date DEFAULT NULL,
  `date_confirmation_completed` date DEFAULT NULL,
  `thesis_status_id` tinyint(2) NOT NULL,
  PRIMARY KEY (`student_degree_id`),
  KEY `fk_student_id` (`student_id`),
  KEY `fk_degree_type_id` (`degree_type_id`),
  KEY `fk_thesis_status_id` (`thesis_status_id`),
  CONSTRAINT `fk_degree_type_id` FOREIGN KEY (`degree_type_id`) REFERENCES `lu_degree_type` (`degree_type_Id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_thesis_status_id` FOREIGN KEY (`thesis_status_id`) REFERENCES `lu_thesis_status` (`thesis_status_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_degree`
--

/*!40000 ALTER TABLE `student_degree` DISABLE KEYS */;
INSERT INTO `student_degree` (`student_degree_id`,`student_id`,`date_enrolled`,`date_completed`,`scholarship`,`date_thesis_intend_submit`,`date_thesis_submit`,`thesis_title`,`degree_type_id`,`date_confirmation_intended`,`date_confirmation_completed`,`thesis_status_id`) VALUES 
 (26,1,'2014-03-10','2014-02-10','adadad','2014-05-10','2014-05-11','asdasd',1,'2014-12-12','2014-01-12',1);
/*!40000 ALTER TABLE `student_degree` ENABLE KEYS */;


--
-- Definition of table `student_supervisor`
--

DROP TABLE IF EXISTS `student_supervisor`;
CREATE TABLE `student_supervisor` (
  `supervisor_id` mediumint(8) NOT NULL AUTO_INCREMENT,
  `staff_id` tinyint(2) NOT NULL,
  `student_degree_id` int(10) NOT NULL,
  `supervisor_type` enum('P','A') DEFAULT NULL,
  PRIMARY KEY (`supervisor_id`),
  KEY `fk_student_degree_id` (`student_degree_id`),
  KEY `fk_staff_supervisor_id` (`staff_id`),
  CONSTRAINT `fk_staff_supervisor_id` FOREIGN KEY (`staff_id`) REFERENCES `staff` (`staff_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_degree_id` FOREIGN KEY (`student_degree_id`) REFERENCES `student_degree` (`student_degree_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `student_supervisor`
--

/*!40000 ALTER TABLE `student_supervisor` DISABLE KEYS */;
INSERT INTO `student_supervisor` (`supervisor_id`,`staff_id`,`student_degree_id`,`supervisor_type`) VALUES 
 (14,2,26,'P');
/*!40000 ALTER TABLE `student_supervisor` ENABLE KEYS */;


--
-- Definition of table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `TID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `fname` varchar(50) DEFAULT NULL,
  `surname` varchar(200) DEFAULT NULL,
  `access_id` tinyint(2) DEFAULT '0',
  `email` varchar(50) DEFAULT NULL,
  `passwd` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`TID`) USING BTREE,
  KEY `fk_access_level_id` (`access_id`),
  CONSTRAINT `FK_teacher_access` FOREIGN KEY (`access_id`) REFERENCES `access_level` (`access_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Dumping data for table `teacher`
--

/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` (`TID`,`fname`,`surname`,`access_id`,`email`,`passwd`) VALUES 
 (1,'Phil','Smith',1,'admin','test'),
 (2,'John','Yearwood',2,'jyearwood','john100'),
 (3,'Andrew','Stranieri',2,'astranie','andrew100'),
 (4,'atul','aggarwal',2,'aa@aa.com','test');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

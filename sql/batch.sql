# drop table if exists `member`;
# create table `member`
# (
#     `id`     bigint not null comment 'id',
#     `mobile` varchar(11) comment '手机号',
#     primary key (`id`),
#     unique key `mobile_unique` (`mobile`)
# ) engine = innodb
#   default charset = utf8mb4 comment ='会员';

#
# Quartz seems to work best with the driver mm.mysql-2.0.7-bin.jar
#
# PLEASE consider using mysql with innodb tables to avoid locking issues
#
# In your Quartz properties file, you'll need to set
# org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate
#
DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;
CREATE TABLE QRTZ_JOB_DETAILS
(
    SCHED_NAME        VARCHAR(120) NOT NULL comment '定时任务名称',
    JOB_NAME          VARCHAR(200) NOT NULL comment 'job名称',
    JOB_GROUP         VARCHAR(200) NOT NULL comment 'job组',
    DESCRIPTION       VARCHAR(250) NULL comment '描述',
    JOB_CLASS_NAME    VARCHAR(250) NOT NULL comment 'job类名',
    IS_DURABLE        VARCHAR(1)   NOT NULL comment '是否持久化',
    IS_NONCONCURRENT  VARCHAR(1)   NOT NULL comment '是否非同步',
    IS_UPDATE_DATA    VARCHAR(1)   NOT NULL comment '是否更新数据',
    REQUESTS_RECOVERY VARCHAR(1)   NOT NULL comment '请求是否覆盖',
    JOB_DATA          BLOB         NULL comment 'job数据',
    PRIMARY KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
);
CREATE TABLE QRTZ_TRIGGERS
(
    SCHED_NAME     VARCHAR(120) NOT NULL comment '定时任务名称',
    TRIGGER_NAME   VARCHAR(200) NOT NULL comment '触发器名称',
    TRIGGER_GROUP  VARCHAR(200) NOT NULL comment '触发器组',
    JOB_NAME       VARCHAR(200) NOT NULL comment 'job名称',
    JOB_GROUP      VARCHAR(200) NOT NULL comment 'job组',
    DESCRIPTION    VARCHAR(250) NULL comment '描述',
    NEXT_FIRE_TIME BIGINT(13)   NULL comment '下一次触发时间',
    PREV_FIRE_TIME BIGINT(13)   NULL comment '前一次触发时间',
    PRIORITY       INTEGER      NULL comment '等级',
    TRIGGER_STATE  VARCHAR(16)  NOT NULL comment '触发状态',
    TRIGGER_TYPE   VARCHAR(8)   NOT NULL comment '触发类型',
    START_TIME     BIGINT(13)   NOT NULL comment '开始时间',
    END_TIME       BIGINT(13)   NULL comment '结束时间',
    CALENDAR_NAME  VARCHAR(200) NULL comment '日程名称',
    MISFIRE_INSTR  SMALLINT(2)  NULL comment '未触发实例',
    JOB_DATA       BLOB         NULL comment 'job数据',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, JOB_NAME, JOB_GROUP)
        REFERENCES QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
);
CREATE TABLE QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL comment '定时任务名称',
    TRIGGER_NAME    VARCHAR(200) NOT NULL comment '触发器名称',
    TRIGGER_GROUP   VARCHAR(200) NOT NULL comment '触发器组',
    REPEAT_COUNT    BIGINT(7)    NOT NULL comment '重复执行次数',
    REPEAT_INTERVAL BIGINT(12)   NOT NULL comment '重复执行间隔',
    TIMES_TRIGGERED BIGINT(10)   NOT NULL comment '已经触发次数',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);
CREATE TABLE QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      VARCHAR(120) NOT NULL comment '定时任务名称',
    TRIGGER_NAME    VARCHAR(200) NOT NULL comment '触发器名称',
    TRIGGER_GROUP   VARCHAR(200) NOT NULL comment '触发器组',
    CRON_EXPRESSION VARCHAR(200) NOT NULL comment 'cron表达式',
    TIME_ZONE_ID    VARCHAR(80) comment '时区',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);
CREATE TABLE QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME    VARCHAR(120)   NOT NULL comment '定时任务名称',
    TRIGGER_NAME  VARCHAR(200)   NOT NULL comment '触发器名称',
    TRIGGER_GROUP VARCHAR(200)   NOT NULL comment '触发器组',
    STR_PROP_1    VARCHAR(512)   NULL comment '开始配置1',
    STR_PROP_2    VARCHAR(512)   NULL comment '开始配置2',
    STR_PROP_3    VARCHAR(512)   NULL comment '开始配置3',
    INT_PROP_1    INT            NULL comment 'int配置1',
    INT_PROP_2    INT            NULL comment 'int配置2',
    LONG_PROP_1   BIGINT         NULL comment 'long配置1',
    LONG_PROP_2   BIGINT         NULL comment 'long配置2',
    DEC_PROP_1    NUMERIC(13, 4) NULL comment '配置描述1',
    DEC_PROP_2    NUMERIC(13, 4) NULL comment '配置描述2',
    BOOL_PROP_1   VARCHAR(1)     NULL comment 'bool配置1',
    BOOL_PROP_2   VARCHAR(1)     NULL comment 'bool配置2',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);
CREATE TABLE QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME    VARCHAR(120) NOT NULL comment '定时任务名称',
    TRIGGER_NAME  VARCHAR(200) NOT NULL comment '触发器名称',
    TRIGGER_GROUP VARCHAR(200) NOT NULL comment '触发器组',
    BLOB_DATA     BLOB         NULL comment '数据',
    PRIMARY KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    FOREIGN KEY (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
        REFERENCES QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
);
CREATE TABLE QRTZ_CALENDARS
(
    SCHED_NAME    VARCHAR(120) NOT NULL comment '定时任务名称',
    CALENDAR_NAME VARCHAR(200) NOT NULL comment '日程名称',
    CALENDAR      BLOB         NOT NULL comment '日程数据',
    PRIMARY KEY (SCHED_NAME, CALENDAR_NAME)
);
CREATE TABLE QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME    VARCHAR(120) NOT NULL comment '定时任务名称',
    TRIGGER_GROUP VARCHAR(200) NOT NULL comment '触发器组',
    PRIMARY KEY (SCHED_NAME, TRIGGER_GROUP)
);
CREATE TABLE QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME        VARCHAR(120) NOT NULL comment '定时任务名称',
    ENTRY_ID          VARCHAR(95)  NOT NULL comment 'entryId',
    TRIGGER_NAME      VARCHAR(200) NOT NULL comment '触发器名称',
    TRIGGER_GROUP     VARCHAR(200) NOT NULL comment '触发器组',
    INSTANCE_NAME     VARCHAR(200) NOT NULL comment '实例名称',
    FIRED_TIME        BIGINT(13)   NOT NULL comment '执行时间',
    SCHED_TIME        BIGINT(13)   NOT NULL comment '定时任务时间',
    PRIORITY          INTEGER      NOT NULL comment '等级',
    STATE             VARCHAR(16)  NOT NULL comment '状态',
    JOB_NAME          VARCHAR(200) NULL comment 'job名称',
    JOB_GROUP         VARCHAR(200) NULL comment 'job组',
    IS_NONCONCURRENT  VARCHAR(1)   NULL comment '是否异步',
    REQUESTS_RECOVERY VARCHAR(1)   NULL comment '是否请求覆盖',
    PRIMARY KEY (SCHED_NAME, ENTRY_ID)
);
CREATE TABLE QRTZ_SCHEDULER_STATE
(
    SCHED_NAME        VARCHAR(120) NOT NULL comment '定时任务名称',
    INSTANCE_NAME     VARCHAR(200) NOT NULL comment '实例名称',
    LAST_CHECKIN_TIME BIGINT(13)   NOT NULL comment '最近检入时间',
    CHECKIN_INTERVAL  BIGINT(13)   NOT NULL comment '检入间隔',
    PRIMARY KEY (SCHED_NAME, INSTANCE_NAME)
);
CREATE TABLE QRTZ_LOCKS
(
    SCHED_NAME VARCHAR(120) NOT NULL comment '定时任务名称',
    LOCK_NAME  VARCHAR(40)  NOT NULL comment 'lock名称',
    PRIMARY KEY (SCHED_NAME, LOCK_NAME)
);
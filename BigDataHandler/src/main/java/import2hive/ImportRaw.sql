-- 启用本地模式，不会启动yarn，速度会更快
set hive.exec.mode.local.auto=true;
-- 打开强制分桶
set hive.enforce.bucketing=true;
-- 非严格模式
set hive.exec.dynamic.partition.mode=nonstrict;
-- 打开Map-Join
set hive.auto.convert.join=true;

create database if not exists graduation;

use graduation;

create table if not exists raw
(
    category     string,
    title        string,
    salary       string,
    place        string,
    experience   string,
    education    string,
    need_persons string,
    publish_date string,
    url          string,
    need_skill   string
) comment '原始数据'
    partitioned by (cate string)
    row format delimited fields terminated by '\t';

-- 导入Java数据
load data local inpath '/opt/data/graduation/2020-03-02_51Job_java.txt' into table raw partition(cate='Java');
load data local inpath '/opt/data/graduation/2020-04-10_Lagou_Java.txt' into table raw partition(cate='Java');
-- 导入大数据数据
load data local inpath '/opt/data/graduation/2020-03-02_51Job_BigData.txt' into table raw partition(cate='BigData');
load data local inpath '/opt/data/graduation/2020-04-10_Lagou_BigData.txt' into table raw partition(cate='BigData');
-- 导入Python数据
load data local inpath '/opt/data/graduation/2020-03-02_51Job_python.txt' into table raw partition(cate='Python');

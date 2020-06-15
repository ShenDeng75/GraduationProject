-- 启用本地模式，不会启动yarn，速度会更快
set hive.exec.mode.local.auto=true;
-- 打开强制分桶
set hive.enforce.bucketing=true;
-- 非严格模式
set hive.exec.dynamic.partition.mode=nonstrict;
-- 打开Map-Join
set hive.auto.convert.join=true;

use graduation;

-- 清洗后的表
create external table if not exists clean
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
) comment '清洗后的数据'
    row format delimited fields terminated by '\t';

-- 导入数据
load data inpath '/graduation/clean/clean_data.txt' overwrite into table clean;

select *
from clean;

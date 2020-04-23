-- 启用本地模式，不会启动yarn，速度会更快
set hive.exec.mode.local.auto=true;
-- 打开强制分桶
set hive.enforce.bucketing=true;
-- 非严格模式
set hive.exec.dynamic.partition.mode=nonstrict;
-- 打开Map-Join
set hive.auto.convert.join=true;

use graduation;

-- 查看数据总数
select count(*)
from clean;

-- 查看是否有重复数据
select url, count(url) as counts
from clean
group by url
having counts >= 2
order by counts desc;

-- 查看education字段清洗效果
select education, count(education) as counts
from clean
group by education;
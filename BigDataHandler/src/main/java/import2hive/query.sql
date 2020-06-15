-- 启用本地模式，不会启动yarn，速度会更快
set hive.exec.mode.local.auto=true;
-- 打开强制分桶
set hive.enforce.bucketing=true;
-- 非严格模式
set hive.exec.dynamic.partition.mode=nonstrict;
-- 打开Map-Join
set hive.auto.convert.join=true;

use graduation;

-- 查看原始数据大类分布
select category, count(*)
from raw
group by category;

-- 查看原始数据的地区分布
select place, count(place) as cnt
from raw
group by place
order by cnt desc
limit 10;


-- 查看数据总数
select count(*)
from raw;

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
from raw
group by education;

select education, count(education) as counts
from clean
group by education;
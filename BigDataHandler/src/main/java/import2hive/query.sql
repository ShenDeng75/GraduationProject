-- 启用本地模式，不会启动yarn，速度会更快
set hive.exec.mode.local.auto=true;
-- 打开强制分桶
set hive.enforce.bucketing=true;
-- 非严格模式
set hive.exec.dynamic.partition.mode=nonstrict;
-- 打开Map-Join
set hive.auto.convert.join=true;

use graduation;

select url, count(url) as counts
from raw
where cate='Python'
group by url
having counts >= 2
order by counts desc;

select count(*)
from raw;

# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

import datetime
import re

from job51.items import Job51Item


class Job51Pipeline(object):

    def process_item(self, item, spider):
        data = Job51Item(item)
        # 去除每个属性数据中的 \t字符，再用 \t字符作为分隔符
        # 在hadoop中就以 \t为分隔符来切割记录
        # 由于dict中各个字段的顺序不固定，所以采用这种方式
        output_data = (data["title"] + "\t" +
                       data["salary"] + "\t" +
                       data["place"] + "\t" +
                       data["experience"] + "\t" +
                       data["education"] + "\t" +
                       data["need_persons"] + "\t" +
                       data["publish_date"] + "\t" +
                       re.sub(r"\s+", " ", data["need_skill"]).strip()) + "\n"

        # 通过title属性，把记录写入相应的文件
        now_day = str(datetime.datetime.now().date())
        file_path = "D:/Code/GraduationProject/files/" + now_day + "_" + str(data["title"]) + ".txt"
        with open(file_path, "a", encoding="utf-8") as f:
            f.write(output_data)

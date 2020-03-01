# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: https://doc.scrapy.org/en/latest/topics/item-pipeline.html

# import pymongo
# from Tools import Const
import json
import re

from job51.items import Job51Item


class Job51Pipeline(object):
    # def __init__(self):
    #     self.connection = pymongo.MongoClient()
    #     self.db = self.connection['51job岗位信息']
    #     self.collection = self.db["python岗位技能点"]

    def process_item(self, item, spider):
        data = Job51Item(item)
        # 去除每个属性数据中的 \t字符，再用 \t字符作为分隔符
        # 在hadoop中就以 \t为分隔符来切割记录
        # 由于dict中各个字段的顺序不一致，所以采用这种方式
        output_data = (data["title"] + "\t" +
                       data["salary"] + "\t" +
                       data["place"] + "\t" +
                       data["experience"] + "\t" +
                       data["education"] + "\t" +
                       data["need_persons"] + "\t" +
                       data["publish_date"] + "\t" +
                       re.sub(r"\s+", " ", data["need_skill"]).strip()) + "\n"
        with open("./test.txt", "a", encoding="utf-8") as f:
            f.write(output_data)

        # self.collection.insert(data)

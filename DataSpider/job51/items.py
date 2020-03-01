# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# https://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class Job51Item(scrapy.Item):
    # define the fields for your item here like:
    # name = scrapy.Field()
    title = scrapy.Field()   # 岗位名
    salary = scrapy.Field()   # 工资
    place = scrapy.Field()   # 地点
    experience = scrapy.Field()   # 经验要求
    education = scrapy.Field()   # 学历要求
    need_persons = scrapy.Field()   # 招聘的人数
    publish_date = scrapy.Field()   # 发布日期
    need_skill = scrapy.Field()   # 需要的技能

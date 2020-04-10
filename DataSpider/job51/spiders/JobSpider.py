#!/usr/bin/env python
# -*- coding: utf-8 -*-
import scrapy
from scrapy.selector import Selector
import re
from scrapy.utils.project import get_project_settings
from scrapy.crawler import CrawlerProcess
from job51.items import Job51Item
from Tools import Parse_ele


# 51job(前程无忧)的解析类
class JobSpider(scrapy.Spider):
    name = "JobSpider"
    allowed_domains = ['search.51job.com', 'jobs.51job.com']

    # 根据关键字构造初始url
    def start_requests(self):
        murl = "https://search.51job.com/list/000000,000000,0000,00,9,99,{0},2,1.html"
        # 构建不同岗位的初始url
        # 一共114695条数据(大数据41491、python29609、java43595)
        key_word = ["python"]
        for key in key_word:
            url = murl.format(key)
            meta = {"search_key": key}
            yield scrapy.Request(url, callback=self.parse_page, meta=meta)

    # 解析所有页面，并返回岗位URL
    def parse_page(self, responses):
        response = Selector(responses)
        # 获得岗位url
        urls = response.xpath(r'.//div[@class="el"]/p/span/a/@href').extract()
        meta = responses.meta
        for url in urls:
            yield scrapy.Request(url, callback=self.parse_url, meta=meta)
        # 获得下一页url（如果存在）
        next_page = response.xpath(r'.//li[@class="bk"]/a/@href').extract()
        if next_page:
            next_url = next_page[-1]
            yield scrapy.Request(next_url, callback=self.parse_page, meta=meta)

    # 解析岗位url，并返回结构化数据
    def parse_url(self, responses):
        response = Selector(responses)
        head = response.xpath(r'.//div[@class="cn"]')
        no = Parse_ele(head)

        category = responses.meta["search_key"]
        title = no.xpath_no(r'./h1/@title')
        salary = no.xpath_no(r'./strong/text()')
        salary = self.changeSalary(salary)
        url = responses.request.url
        need = no.xpath_no(r'./p[contains(class, msg)]/@title')
        needs = str(need).split('|')
        try:
            place = needs[0].split('-')[0].strip()
            education = "缺失"
            experience = "缺失"
            need_persons = "缺失"
            publish_date = "缺失"
            for n in needs[1:]:
                if "经验" in n:
                    experience = n.strip()
                elif "人" in n:
                    need_persons = n.strip()
                elif "发布" in n:
                    publish_date = n.strip()
                else:
                    education = n.strip()
            need_skill = response.xpath(r'.//div[@class="bmsg job_msg inbox"]//text()').extract()
        except Exception as e:
            print("信息获取有误!", e, response.response.url, sep="，")
        needs_skill = "".join([x for x in need_skill if x.strip() != ''])
        item = Job51Item(category=category, title=title, salary=salary, place=place, experience=experience, education=education,
                         need_persons=need_persons, publish_date=publish_date, url=url, need_skill=needs_skill)
        yield item

    # 统一工资的单位
    def changeSalary(self, salary):
        sala = re.split('[-/]', salary)
        if len(sala) != 3:  # 如果为缺失
            return salary
        if sala[1][-1] == "万":
            b = 10
        else:
            b = 1
        ll = float(sala[0]) * b
        u = float(sala[1][:-1]) * b
        if sala[2] == "年":
            ll /= 12
            u /= 12
        ans = "{0}-{1}".format(round(ll, 1), round(u, 1))
        return ans


if __name__ == "__main__":
    process = CrawlerProcess(get_project_settings())
    process.crawl('JobSpider')
    process.start()

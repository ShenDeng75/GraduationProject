#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import json
import random
import re
import time
from lxml import html

from selenium import webdriver


# 加载并配置驱动
def get_driver():
    option = webdriver.ChromeOptions()  # 获取参数对象。
    prefs = {"profile.managed_default_content_settings.images": 1}  # 不加载图片2
    option.add_experimental_option("prefs", prefs)
    # 使用自己的配置文件，如果没有则会创建文件;
    # 之后在chromedriver上的所有修改配置都会保存。
    option.add_argument(r'--user-data-dir=D:\必备工具\Google\自动化测试配置文件')
    option.binary_location = r'D:\必备工具\Google\Chrome\Application\chrome.exe'
    driver_path = r'D:\必备工具\Google\Chrome\Application\chromedriver.exe'
    driver = webdriver.Chrome(driver_path, chrome_options=option)
    return driver


# 获取url数据
def get_url_data():
    filepath = r"D:\Code\GraduationProject\files\拉勾网URL数据\大数据\2020-04-09_lagou_BigData_35.json"
    with open(filepath, "r", encoding="utf-8") as f:
        datas = json.load(f)
    return datas


driver = get_driver()
success = 0
error = 0


# 解析岗位需求
def get_needs(url):
    driver.get(url)
    page = driver.page_source
    tree = html.fromstring(page)
    needs = tree.xpath(r'.//div[@class="job-detail"]//text()')
    if not needs:
        global error
        error += 1
        print(url)
        p = input("输入指令：")
        return p

    needs = " ".join([x for x in needs if x.strip() != ""])
    needs = re.sub(r"\s+", " ", needs).strip()

    return needs


# 返回最终结果
def get_item(datas: list):
    for item in datas[205:]:
        url = item["url"]
        needs = get_needs(url)
        if needs == "c":
            continue
        result = (item["category"] + "\t" +
                  item["positionName"] + "\t" +
                  item["salary"] + "\t" +
                  item["city"] + "\t" +
                  item["workYear"] + "\t" +
                  item["education"] + "\t" +
                  item["need_persons"] + "\t" +
                  item["createTime"] + "\t" +
                  item["url"] + "\t" +
                  needs) + "\n"
        now_day = str(datetime.datetime.now().date())
        filepath = r"D:\Code\GraduationProject\files\{0}_{1}_{2}.txt" \
            .format(now_day, "Lagou", "BigData")
        with open(filepath, "a", encoding="utf-8") as f:
            f.write(result)

        global success
        success += 1
        print(success)

        stime = random.random() + 5
        time.sleep(stime)


if __name__ == "__main__":
    data = get_url_data()
    get_item(data)

#!/usr/bin/env python
# -*- coding: utf-8 -*-


# 处理输出
class Str:
    green = "green"
    yellow = "yellow"
    red = "red"

    @staticmethod
    def cstr(str, color):   # 在控制台输出有颜色的字
        colorMap = {"green": 32, "yellow": 33, "red": 31}
        cs = "\033[{0}m{1}\033[0m".format(colorMap[color], str)
        return cs


# 处理list
class List:
    @staticmethod
    def GetOne(ls):   # 获得list中的唯一元素
        if len(ls) < 1:
            raise Exception(Str.cstr("---列表为空！---", Str.red))
        if len(ls) > 1:
            raise Exception(Str.cstr("---列表有多个元素！---", Str.red))
        return ls[0]

    @staticmethod
    def GetOneStrip(ls):   # 去list中的空格，并拼接成一个str
        result = ""
        for i in ls:
            result += str(i).strip()
        return result


# 处理缺省值
class Parse_ele:
    def __init__(self, response):
        self.response = response
    def xpath_no(self, patt, response=None):
        if not response:
           response = self.response
        try:
           return response.xpath(patt).extract_first().strip()
        except:
            return "缺失"


# 全局静态变量
class Const:
    jobName = ""
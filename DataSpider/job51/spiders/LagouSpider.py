#!/usr/bin/env python
# -*- coding: utf-8 -*-
import datetime
import json
import re
import requests

from job51.spiders.Tools import cookies2dict

sid = "3ee59ed360bc49ddb059dc41dd30c69c"
cookies = "JSESSIONID=ABAAAECABBJAAGICCFBC56D6CBF4679EFD6F4115B1D5119; WEBTJ-ID=20200409152811-1715dd6d1ed4a3-0c73b657cc0f7f-b383f66-1327104-1715dd6d1ee85e; _ga=GA1.2.1395121368.1586417292; _gid=GA1.2.429351118.1586417292; user_trace_token=20200409152812-829cfc29-9a5d-4157-9f49-9cb61fd7f6ab; LGUID=20200409152812-b5e98bcb-36c6-4ac9-a5ff-fffa0b986cc5; index_location_city=%E5%85%A8%E5%9B%BD; sajssdk_2015_cross_new_user=1; sensorsdata2015jssdkcross=%7B%22distinct_id%22%3A%221715dd702ea6c-0c3567683f59ec-b383f66-1327104-1715dd702ebbaf%22%2C%22%24device_id%22%3A%221715dd702ea6c-0c3567683f59ec-b383f66-1327104-1715dd702ebbaf%22%7D; X_MIDDLE_TOKEN=495d8b2085c5ed346febd8487fc7369d; Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1586417292,1586420948,1586441253; LGSID=20200409220733-04341a41-7511-4976-93d7-b39d95447e25; gate_login_token=0c7a0a068260eacf4cf5310f6a64626c287887f43a4cf0fa415302bf6bcd1106; _putrc=4F585A7CA7C02082123F89F2B170EADC; login=true; unick=%E8%82%96%E6%8B%89%E9%92%A9; privacyPolicyPopup=false; showExpriedIndex=1; showExpriedCompanyHome=1; showExpriedMyPublish=1; hasDeliver=0; TG-TRACK-CODE=search_code; _gat=1; SEARCH_ID=6d60c40ebc2a4396b9178ec28fc61cd0; X_HTTP_TOKEN=deb2a99082f4f0971734446851b0d2bdac05e0ae1a; Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1586444372; LGRID=20200409225932-67062092-2559-48a8-9d42-5d49eb8a7240"

headers = {
    "Referer": "https://www.lagou.com/jobs/list_%E5%A4%A7%E6%95%B0%E6%8D%AE/p-city_0?&cl=false&fromSearch=true&labelWords=&suginput=",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36"
}

cookies = cookies2dict(cookies)


# 统一工资的单位
def format_salary(sal):
    sala = re.split('[·]', sal)
    return sala[0][:-1]


# 保存到磁盘
def save(category, results, no):
    now_day = str(datetime.datetime.now().date())
    path = "D:/Code/GraduationProject/files/{0}_{1}_{2}_{3}.json". \
        format(now_day, "lagou", category, no)
    with open(path, "w", encoding="utf-8") as f:
        json.dump(results, f, indent=4, ensure_ascii=False)


# 获取岗位列表
def get_job_list():
    url = "https://www.lagou.com/jobs/positionAjax.json?needAddtionalResult=false"
    base_detail_url = "https://www.lagou.com/jobs/{0}.html?show={1}"
    category = "大数据"
    results = []
    sess = requests.session()
    counter = 0
    for i in range(179, 200):
        data = {
            "first": "false",
            "pn": str(i),
            "kd": category,
            "sid": sid
        }
        page = sess.post(url, data=data, cookies=cookies, headers=headers)
        text = page.text
        json_data = json.loads(text)
        try:
            ass = json_data["content"]
        except:
            save("BigData", results, i)
            print(i)

        result_size = json_data["content"]["positionResult"]["resultSize"]
        if result_size == 0:
            break
        show_id = json_data["content"]["showId"]
        result = json_data["content"]["positionResult"]["result"]
        for item in result:
            ans = dict()
            position_id = item["positionId"]
            detail_url = base_detail_url.format(position_id, show_id)
            ans["category"] = category
            ans["positionName"] = item["positionName"]
            ans["salary"] = format_salary(item["salary"])
            ans["city"] = item["city"]
            ans["workYear"] = item["workYear"]
            ans["education"] = item["education"]
            ans["need_persons"] = "缺失"
            ans["createTime"] = str(item["createTime"]).split(" ")[0]
            ans["url"] = detail_url

            results.append(ans)
            counter += 1
            print(counter)

    save(category, results, 200)


if __name__ == "__main__":
    get_job_list()

#!/usr/bin/env python
# -*- coding: utf-8 -*-
import requests
from lxml import html
from datetime import datetime
import json
from multiprocessing.pool import ThreadPool

# 获取 西刺代理的代理IP
def Xici(url):
    headers = {'User-Agent':"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0"}
    response = requests.get(url, headers=headers)
    tree = html.fromstring(response.content)
    trs = tree.xpath(r'.//table[@id="ip_list"]/tr')[1:]
    proxyIPs = []
    for tr in trs:
        tds = tr.xpath(r'./td')
        ip = tds[1].text
        port = tds[2].text
        protocol = str(tds[5].text).lower()
        proxy = (ip, port, protocol)
        proxyIPs.append(proxy)
    return proxyIPs

# 测试可用性，以及速度
def request(proxies):
    url = "https://search.51job.com/list/000000,000000,0000,00,9,99,python,2,11.html"
    times = 0
    for i in range(3):
        try:
            start = datetime.now()
            response = requests.get(url, proxies=proxies, timeout=5)
            end = datetime.now()
            times += (end-start).total_seconds()
        except :
            return -1
        if response.status_code != 200:
            return -2
    return times/3

# 多线程验证
def Verify():
    print("获取代理IP中...")
    proxyIPs = Xici("http://www.xicidaili.com/nn/1")
    proxyIPs2 = Xici("http://www.xicidaili.com/nn/2")
    proxyIPs.extend(proxyIPs2)
    pool = ThreadPool(100)
    result = pool.map(m, proxyIPs)
    pool.close()
    pool.join()
    IPs = ["{0}://{1}:{2}".format(ip[2], ip[0], ip[1]) for ip in result if ip!=None]
    with open(r'proxies.txt', 'w', encoding='utf-8') as f:
        json.dump(IPs, f)
    print("完成！已获取 %d 个可用代理IP" % len(IPs))
def m(proxyIPs):
    ip, port, pro = proxyIPs
    addr = "{0}://{1}:{2}".format(pro, ip, port)
    proxies = {pro: addr}
    time = request(proxies)
    if time > 0:
        return proxyIPs

if __name__ == "__main__":
    Verify()

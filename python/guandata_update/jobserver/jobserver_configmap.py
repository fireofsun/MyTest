#!/usr/bin/env python
#encoding: utf-8
import collections
import yaml
import json


# 将configmap中data的部分内容替换为新的内容
# part为需要替换的部分， content为替换的内容
def relace_data_part(part, replace_content):
    f_read = open('/Users/mc/codeTest/pytest/Development/spark-jobserver/spark-jobserver-configmap.yaml', 'r+')
    data_map = yaml.load(f_read)
    f_read.seek(0)
    origin_text = f_read.read()
    f_read.close()
    #获得configmap中configuration的部分，是一个string
    origin_part = ''
    for line in data_map.get('data').get(part).splitlines():
        origin_part += ('    ' + line + '\n')

    # 在参数的replace_content前加入适量的空格，保证yaml格式
    replace_part = ''
    for line in replace_content.splitlines():
        replace_part += ('    ' + line + '\n')

    if origin_text.__contains__(origin_part):
        f_write = open('/Users/mc/codeTest/pytest/Development/spark-jobserver/spark-jobserver-configmap.yaml', 'w+')
        f_write.write(origin_text.replace(origin_part, replace_part))
        f_write.close()
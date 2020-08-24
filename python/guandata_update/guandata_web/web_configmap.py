#!/usr/bin/env python
#encoding: utf-8
import collections
import yaml
import json


# 修改 configuration.json 中的内容（增加或者修改，就是用addon_map里的内容替换掉对应的部分，目前不支持删除）
def change_configuration_json(addon_map):
    f_read = open('/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-configmap.yaml', 'r+')
    data_map = yaml.load(f_read)
    f_read.seek(0)
    origin_text = f_read.read()
    f_read.close()
    #获得configmap中configuration的部分，是一个string
    configuration = data_map.get('data').get('configuration.json')
    origin_config = ''
    for line in configuration.splitlines():
        origin_config += ('    ' + line + '\n')
    # 从configuration的string中提取成config_json
    config_json = json.loads(configuration, object_pairs_hook=collections.OrderedDict)
    config_json.update(addon_map)
    replace_json_str = json.dumps(config_json, indent=4)
    replace_config = ''
    for line in replace_json_str.splitlines():
        replace_config += ('    ' + line + '\n')
    if origin_text.__contains__(origin_config):
        f_write = open('/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-configmap.yaml', 'w+')
        f_write.write(origin_text.replace(origin_config, replace_config))
        f_write.close()
#!/usr/bin/env python
#encoding: utf-8
import yaml
import re


# 从dust中读取对应版本的service_pagepdf_export的configmap，更换其中的的ip部分，写成新的
def exchange_configMap(version):
    template_file_path = './dust/service-pagepdf-export-configmap.yaml_' + version
    f1 = open(template_file_path, 'r+')
    config_map = f1.read()
    f1.close()
    f_read = open('/Users/mc/codeTest/pytest/Development/service-pagepdf-export/service-pagepdf-export-configmap.yaml', 'r')
    #### 更换镜像号###
    # 从原始文件中提取出minioHost的内容，替换掉
    orign_content = f_read.read()
    orign_host = re.search('const minioHost = \'.*\'', orign_content).group(0)
    changed_content = config_map.replace("const minioHost = '192.168.186.188'", orign_host)
    f_read.close()
    # 必须是w+ 覆盖写入
    f_write = open('/Users/mc/codeTest/pytest/Development/service-pagepdf-export/service-pagepdf-export-configmap.yaml', 'w+')
    f_write.write(changed_content)
    f_write.close()
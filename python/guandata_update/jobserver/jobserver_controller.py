#!/usr/bin/env python
#encoding: utf-8
import yaml
import re


# 更换guandata-server-controller.yaml中的版本号
def exchange_version(version):
    f_read = open('/Users/mc/codeTest/pytest/Development/spark-jobserver/spark-jobserver-controller.yaml', 'r')
    #### 更换镜像号###
    pattern = re.compile(r'registry.cn-hangzhou.aliyuncs.com/guandata/spark-jobserver:.*')
    orign_content = f_read.read()
    changed_content = re.sub(pattern, 'registry.cn-hangzhou.aliyuncs.com/guandata/spark-jobserver:'+version, orign_content)
    f_read.close()
    # 必须是w+ 覆盖写入
    f_write = open('/Users/mc/codeTest/pytest/Development/spark-jobserver/spark-jobserver-controller.yaml', 'w+')
    f_write.write(changed_content)
    f_write.close()
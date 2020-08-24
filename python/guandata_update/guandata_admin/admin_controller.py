#!/usr/bin/env python
#encoding: utf-8
import yaml
import re
from general_utils import file_utils

# 更换guandata-server-controller.yaml中的版本号
def exchange_version(version):
    f_read = open('/Users/mc/codeTest/pytest/Development/guandata-admin/guandata-admin-controller.yaml', 'r')
    #### 更换镜像号###
    pattern = re.compile(r'registry.cn-hangzhou.aliyuncs.com/guandata/guandata-admin:.*')
    orign_content = f_read.read()
    changed_content = re.sub(pattern, 'registry.cn-hangzhou.aliyuncs.com/guandata/guandata-admin:'+version, orign_content)
    f_read.close()
    # 必须是w+ 覆盖写入
    f_write = open('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-admin.yaml', 'w+')
    f_write.write(changed_content)
    f_write.close()


def readArg(key):
    f_read = open('/Users/mc/codeTest/pytest/Development/guandata-admin/guandata-admin-controller.yaml', 'r')
    controller_map = yaml.load(f_read)
    f_read.close()
    # 获得yaml中arg部分的list
    arg_list = controller_map.get('spec').get('template').get('spec').get('containers')[0].get('args')
    # 如果元素已经存在，则不重复添加
    for arg in arg_list:
        if arg.__contains__(key):
            return arg

    return ""


def add_arg_if_not_exist(arg):
    controller_map = file_utils.read_yaml_content('/Users/mc/codeTest/pytest/Development/guandata-admin/guandata-admin-controller.yaml')
    controller_txt = file_utils.read_file_content('/Users/mc/codeTest/pytest/Development/guandata-admin/guandata-admin-controller.yaml')

    # 获得yaml中arg部分的list
    arg_list = controller_map.get('spec').get('template').get('spec').get('containers')[0].get('args')
    # 如果元素已经存在，则不重复添加
    if arg in arg_list:
        return
    # 从arg list中拼接处匹配用的文本
    origin_args_txt = ''
    for line in arg_list:
        origin_args_txt += ('            - \'' + line + '\'\n')
    changed_args_txt = origin_args_txt + ('            - \'' + arg + '\'\n')
    # 用修改后的内容替换之前的内容
    if controller_txt.__contains__(origin_args_txt):
        changed_controller_txt = controller_txt.replace(origin_args_txt, changed_args_txt)
        file_utils.rewrite_file('/Users/mc/codeTest/pytest/Development/guandata-admin/guandata-admin-controller.yaml', changed_controller_txt)


if __name__ == '__main__':
    print readArg("Dguandata.okLogServer")
    # print(__name__)

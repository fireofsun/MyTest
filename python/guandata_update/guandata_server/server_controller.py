#!/usr/bin/env python
#encoding: utf-8
import re
from general_utils import file_utils as file_utils
# 从guandata-server-controller.yaml中获得版本号，不包含hf内容，默认为x.x.x-hfx格式中的x.x.x部分
def extract_version():
    controller_map = file_utils.read_file_content('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml')
    full_version = re.search(r'registry.cn-hangzhou.aliyuncs.com/guandata/guandata-server:(.*)', controller_map).group(1)
    return full_version.split('-')[0]


# 更换guandata-server-controller.yaml中的版本号
def exchange_version(version):
    orign_content = file_utils.read_file_content('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml')
    #### 更换镜像号###
    pattern = re.compile(r'registry.cn-hangzhou.aliyuncs.com/guandata/guandata-server:.*')
    changed_content = re.sub(pattern, 'registry.cn-hangzhou.aliyuncs.com/guandata/guandata-server:'+version, orign_content)
    # 必须是w+ 覆盖写入
    file_utils.rewrite_file('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml', changed_content)


# 增加controller的arg列表中的内容
def add_args(arg):
    # load获得yaml结构
    controller_map = file_utils.read_yaml_content('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml')
    # read 获得yaml原来的文本
    controller_txt = file_utils.read_file_content('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml')

    # 获得yaml中arg部分的list
    arg_list = controller_map.get('spec').get('template').get('spec').get('containers')[0].get('args')

    # 从arg list中拼接处匹配用的文本
    origin_args_txt = ''
    for line in arg_list:
        origin_args_txt += ('            - \'' + line + '\'\n')

    changed_args_txt = origin_args_txt + ('            - \'' + arg + '\'\n')
    # 用修改后的内容替换之前的内容
    if controller_txt.__contains__(origin_args_txt):
        changed_controller_txt = controller_txt.replace(origin_args_txt, changed_args_txt)
        file_utils.rewrite_file('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml', changed_controller_txt)


def add_arg_if_not_exist(arg):
    controller_map = file_utils.read_yaml_content('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml')
    controller_txt = file_utils.read_file_content('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml')

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
        file_utils.rewrite_file('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml', changed_controller_txt)

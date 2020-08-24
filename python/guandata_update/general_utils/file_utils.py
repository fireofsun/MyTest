#!/usr/bin/env python
#encoding: utf-8
import yaml

# 工具类，因为直接使用yaml.dump，dump出来的yaml格式，在 - 前是没有两个缩进的，所以在dump的时候要用这个Dumper来格式化
class MyDumper(yaml.Dumper):
    def increase_indent(self, flow=False, indentless=False):
        return super(MyDumper, self).increase_indent(flow, False)


# 读取文件内容为一个string
def read_file_content(file_path):
    f_read = open(file_path, 'r+')
    content = f_read.read()
    f_read.close()
    return content


# 读取一个yaml文件，返回map
def read_yaml_content(file_path):
    f_read = open(file_path, 'r+')
    content = yaml.load(f_read)
    f_read.close()
    return content


def rewrite_file(file_path, new_content):
    f_write = open(file_path, 'w+')
    f_write.write(new_content)
    f_write.close()


# 将一个文件中的orign内容替换为changed内容，并写回原文件
def replace_content_in_file(file_path, orign_content, changed_content):
    content = read_file_content(file_path)
    content.replace(orign_content, changed_content)
    rewrite_file(file_path, content.replace(orign_content, changed_content))


# 这个工具函数主要是针对内容为yaml的部分内容，且结构较为复杂的部分，不适合直接解析的青黄
# 直接将传入的content拼接到原来yaml 结构的后半部分，从而方便直接进行字符串替换
# yaml_part为用户读取的部分内容，addon_str为需要增加的内容，indent为这部分在yaml中是缩进数量
# 并且这个函数会根据你是否曾经添加过addon_str来决定是否修改
def add_str_after_list_content(yaml_part, addon_str, indent):
    part_txt = yaml.dump(yaml_part, Dumper=MyDumper, default_flow_style=False)
    origin_part_txt = ''
    for line in part_txt.split('\n'):
        origin_part_txt += (' '*indent + line + '\n')

    origin_part_txt = origin_part_txt.rstrip()

    addon_part_txt = ''
    for line in addon_str.lstrip().rstrip().split('\n'):
        addon_part_txt += (' ' * indent + line + '\n')
    addon_part_txt = addon_part_txt.rstrip()

    # 如果说要增加的内容是已经在这个部分里面了，那就不管他，如果不存在，那就吧origin_part_txt和addon_part_txt组合起来返回出去
    if origin_part_txt.__contains__(addon_part_txt):
        return (origin_part_txt, origin_part_txt)
    else:
        return (origin_part_txt, origin_part_txt + '\n' + addon_part_txt + '\n')
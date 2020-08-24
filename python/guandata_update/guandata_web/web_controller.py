#!/usr/bin/env python
#encoding: utf-8
import re
from general_utils import file_utils as file_utils


# 更换guandata-web-controller.yaml中的版本号
def exchange_version(version):
    f_read = open('/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml', 'r+')
    #### 更换镜像号###
    pattern = re.compile(r'registry.cn-hangzhou.aliyuncs.com/guandata/guandata-web:.*')
    orign_content = f_read.read()
    changed_content = re.sub(pattern, 'registry.cn-hangzhou.aliyuncs.com/guandata/guandata-web:'+version, orign_content)
    f_read.close()
    f_write = open('/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml', 'w+')
    f_write.write(changed_content)
    f_write.close()


def add_volume(addon_volume_str):
    controller_txt = file_utils.read_file_content(
        '/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml')
    controller_yaml = file_utils.read_yaml_content(
        '/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml')
    # 这里做slice的原因是，volumes里面有个 medium: ""，虽然也对，但正规写法应该是medium: ''，所以dump出来的就跟实际的有差距，所以排除掉了这个东西
    volumes = controller_yaml.get('spec').get('template').get('spec').get('volumes')[1:]

    (origin_volume_txt, changed_volume_txt) = file_utils.add_str_after_list_content(volumes, addon_volume_str, 8)
    if controller_txt.__contains__(origin_volume_txt):
        file_utils.replace_content_in_file(
            '/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml',
            origin_volume_txt,
            changed_volume_txt)


# 注意，guandata-web内包含两个container，这个只是修改web的volume mount
def add_volume_mounts_in_web(addon_volume_mounts):
    controller_txt = file_utils.read_file_content(
        '/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml')
    controller_yaml = file_utils.read_yaml_content(
        '/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml')
    volume_mounts = controller_yaml.get('spec').get('template').get('spec').get('containers')[0].get('volumeMounts')
    (origin_volume_mounts_txt, changed_volume_mounts_txt) = file_utils.add_str_after_list_content(volume_mounts, addon_volume_mounts, 12)
    if controller_txt.__contains__(origin_volume_mounts_txt):
        file_utils.replace_content_in_file(
            '/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml',
            origin_volume_mounts_txt,
            changed_volume_mounts_txt)
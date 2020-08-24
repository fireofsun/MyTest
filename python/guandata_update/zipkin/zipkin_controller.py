#!/usr/bin/env python
#encoding: utf-8
from update.general_utils import file_utils as file_utils


def add_env(addon_env_str):
    controller_txt = file_utils.read_file_content('/Users/mc/codeTest/pytest/Development/zipkin/zipkin-server-controller.yaml')
    controller_yaml = file_utils.read_yaml_content('/Users/mc/codeTest/pytest/Development/zipkin/zipkin-server-controller.yaml')
    env = controller_yaml.get('spec').get('template').get('spec').get('containers')[0].get('env')
    (origin_env_txt, changed_env_txt) = file_utils.add_str_after_list_content(env, addon_env_str, 12)
    if controller_txt.__contains__(origin_env_txt):
        changed_controller_txt = controller_txt.replace(origin_env_txt, changed_env_txt)
        file_utils.rewrite_file('/Users/mc/codeTest/pytest/Development/zipkin/zipkin-server-controller.yaml',
                                changed_controller_txt)
#!/usr/bin/env python
#encoding: utf-8
from guandata_server import server_controller as server_controller
from guandata_web import web_controller as web_controller
from guandata_web import web_configmap as web_configmap
from guandata_admin import admin_controller as admin_controller

def update():
    server_controller.add_arg_if_not_exist('habeafewqdasdfasdfadfasdfa')
    server_controller.exchange_version('2.6.6')
    web_controller.exchange_version('2.6.3')
    admin_controller.exchange_version('20200303_266883837')
    tmp = {'CUSTOM_VENDOR_NAME':'', 'CUSTOM_VENDOR_LOGO':''}
    web_configmap.change_configuration_json(tmp)
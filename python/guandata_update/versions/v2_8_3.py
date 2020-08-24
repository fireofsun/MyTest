#!/usr/bin/env python
#encoding: utf-8
from guandata_server import server_controller as server_controller
from guandata_web import web_controller as web_controller
from jobserver import jobserver_controller as jobserver
from guandata_admin import admin_controller as admin_controller

server_controller.exchange_version('2.8.3')
web_controller.exchange_version('2.8.3')
admin_controller.exchange_version('20200428_0ec4c89d2')
jobserver.exchange_version('20200415_b72b64e_sparkPool')

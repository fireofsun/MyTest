#!/usr/bin/env python
#encoding: utf-8
from guandata_server import server_controller as server_controller
from guandata_web import web_controller as web_controller
from jobserver import jobserver_controller as jobserver, jobserver_configmap as jobserver_configmap
from guandata_admin import admin_controller as admin_controller

server_controller.exchange_version('2.7.3')
web_controller.exchange_version('2.7.3-hf2')
admin_controller.exchange_version('20200428_0ec4c89d2')
jobserver.exchange_version('20200415_b72b64e_sparkPool')

job_pool_config='''<?xml version="1.0"?>
<allocations>
  <pool name="default">
    <schedulingMode>FAIR</schedulingMode>
    <weight>100</weight>
    <minShare>100</minShare>
  </pool>
  <pool name="etl">
    <schedulingMode>FAIR</schedulingMode>
    <weight>1</weight>
    <minShare>0</minShare>
  </pool>
</allocations>
'''
jobserver_configmap.relace_data_part('job-pool-config.xml', job_pool_config)
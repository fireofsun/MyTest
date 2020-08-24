#!/usr/bin/env python
#encoding: utf-8
from guandata_server import server_controller as server_controller
from guandata_web import web_controller as web_controller
from jobserver import jobserver_controller as jobserver
from guandata_admin import admin_controller as admin_controller
from service_pagepdf_export import configMap as service_pdf_export_configmap

def update():
    server_controller.exchange_version('2.7.0-hf8')
    web_controller.exchange_version('2.7.0-hf3')
    admin_controller.exchange_version('20200401_2bf65befd')
    jobserver.exchange_version('20200424_5997a3c')
    service_pdf_export_configmap.exchange_configMap('1.0.1')

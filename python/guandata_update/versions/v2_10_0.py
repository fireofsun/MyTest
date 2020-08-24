#!/usr/bin/env python
#encoding: utf-8
from guandata_server import server_controller as server_controller
from guandata_web import web_controller as web_controller
from jobserver import jobserver_controller as jobserver
from guandata_admin import admin_controller as admin_controller
from general_utils import file_utils
from zipkin import zipkin_controller

server_controller.exchange_version('2.10.0-hf9')
web_controller.exchange_version('2.10.0-hf12')
admin_controller.exchange_version('20200618_6cfb10653_2')
jobserver.exchange_version('20200513_b301781')
# 读取admin中的oklog的内容，并增加到server controller中
oklog_in_admin = admin_controller.readArg("Dguandata.okLogServer")
server_controller.add_arg_if_not_exist(oklog_in_admin)
# pdf configmap的配置略过

web_controller_content = file_utils.read_file_content('/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml')
if not web_controller_content.__contains__('name: guandata-shared-data'):
    #注意，这里的字符串是用三引号括起来的，传入的内容就直接传递内容，最后不能有回车a
    new_volumes = '''- name: guandata-shared-data
  hostPath:
    path: /home/guandata/data'''
    web_controller.add_volume(new_volumes)

if not web_controller_content.__contains__('mountPath: /tmp/gd-plugin'):
    new_volume_mounts = '''- name: guandata-shared-data
  mountPath: /tmp/gd-plugin
  subPath: web/gd-plugin'''
    web_controller.add_volume_mounts_in_web(new_volume_mounts)

file_utils.replace_content_in_file(
    '/Users/mc/codeTest/pytest/Development/guandata-web/guandata-web-controller.yaml',
    'rm -f /etc/localtime; ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime; mkdir -p /guandata-web; cp -r /tmp/guandata-web/* /guandata-web/; cd /guandata-web/; pm2-docker /conf/docker-pm2.yaml',
    'rm -f /etc/localtime; ln -s /usr/share/zoneinfo/Asia/Shanghai /etc/localtime; mkdir -p /guandata-web; cp -r /tmp/guandata-web/* /guandata-web/; cd /guandata-web/; node server.js')

server_controller.add_arg_if_not_exist('-Dctx.session.credentials.0=guandata')
server_controller.add_arg_if_not_exist('-Dctx.session.credentials.1=k$R!3!6$')

admin_controller.add_arg_if_not_exist('-Dctx.session.credentials.0=guandata')
admin_controller.add_arg_if_not_exist('-Dctx.session.credentials.1=k$R!3!6$')

orign_part = '''          #spark.cassandra.connection.compression = "LZ4"
          spark.cassandra.output.ignoreNulls = true'''
new_part = '''          #spark.cassandra.connection.compression = "LZ4"
          spark.cassandra.auth.username = "guandata"
          spark.cassandra.auth.password = "k$R!3!6$"
          spark.cassandra.output.ignoreNulls = true'''

file_utils.replace_content_in_file(
    '/Users/mc/codeTest/pytest/Development/spark-jobserver/spark-jobserver-configmap.yaml',
    orign_part,
    new_part)


addon_env = '''- name: CASSANDRA_USERNAME
  value: "guandata"
- name: CASSANDRA_PASSWORD
  value: "k$R!3!6$"'''
zipkin_controller.add_env(addon_env)
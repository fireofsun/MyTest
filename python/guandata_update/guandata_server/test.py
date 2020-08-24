#!/usr/bin/env python
#encoding: utf-8
import yaml
import re
def addArgIfNotExist(arg):
    f1 = open('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml', 'r+')
    # load获得yaml结构

    controllerMap = yaml.load(f1)
    f1.seek(0)
    controllerTxt = f1.read()
    # read 获得yaml原来的文本
    print controllerMap
    print controllerTxt
    f1.close()




addArgIfNotExist('ffffffefwefwe')
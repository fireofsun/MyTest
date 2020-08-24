#!/usr/bin/env python
# encoding: utf-8
import os


# 执行单行shell命令
def execute(cmd):
    result = os.system(cmd)
    if result == 0:
        print "execute : " + cmd + " successfully"
    else:
        print "failed to execute : " + cmd


# 执行一个路径下的脚本
def execute_shell(shell_path):
    result = os.system(shell_path)
    print result


# 执行单行shell并获得这个shell的返回值
def execute_with_result(cmd):
    result = os.popen(cmd).read()
    print "execute result : " + result
    return result


if __name__ == '__main__':
    execute_shell('/Users/mc/codeTest/pytest/update/dust/test.sh')
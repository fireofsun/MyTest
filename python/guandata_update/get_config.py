import os
result = os.system("cat ~/.kube/config")
print result
print '============================================================'
result = os.read('pwd')
print result
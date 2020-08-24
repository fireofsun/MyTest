import versions.v2_6_3 as v2_6_3
if __name__ == '__main__':
    development_dir = raw_input("please enter the path to the Development folder(with Development), default is ../Development")
    if len(development_dir) == 0:
        development_dir = "../Development"
    v2_6_3.update()
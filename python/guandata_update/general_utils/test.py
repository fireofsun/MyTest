import oyaml as yaml
import file_utils as file_utils


class MyDumper(yaml.Dumper):

    def increase_indent(self, flow=False, indentless=False):
        return super(MyDumper, self).increase_indent(flow, False)

def add_volume(volume_str):
    controller_txt = file_utils.read_file_content('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml')
    controller_yaml = file_utils.read_yaml_content('/Users/mc/codeTest/pytest/Development/guandata-server/guandata-server-controller.yaml')
    volumes = controller_yaml.get('spec').get('template').get('spec').get('volumes')
    origin_args_txt = ''

#object_pairs_hook=collections.OrderedDict
    with open('/Users/mc/codeTest/pytest/Development/guandata-server/test.yaml', "w") as f:
        yaml.safe_dump(volumes,f,  indent=2,  default_flow_style=False)

        #print(yaml.dump(volumes, indent=4, default_flow_style=False).replace('- ', '  - '))
        #print yaml.safe_dump(volumes, indent=2, default_flow_style=False)
        print yaml.dump(volumes, Dumper=MyDumper, default_flow_style=False)
        print "========="
        print(yaml.dump(volumes, indent=4, default_flow_style=False).replace('-   ', '  - '))
        #yaml.dump()
        #yaml.dump(controller_yaml, f)
    print origin_args_txt


if __name__ == '__main__':
    add_volume("Dguandata.okLogServer")
    # print(__name__)
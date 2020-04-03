import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ConfigCompare {
    public static void main(String[] args) throws IOException {
        String pathname = "/Users/mc/kube_ins/weekly_update/bk_shops.config_bk"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        File filename = new File(pathname); // 要读取以上路径的input。txt文件
        InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename)); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        String line = "";
        line = br.readLine();
        Set<String> set1 = new HashSet<String>();
        set1.addAll(Arrays.asList(line.split(",")));
        //-------------------------------------------
        pathname = "/Users/mc/kube_ins/weekly_update/shops.config"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        filename = new File(pathname); // 要读取以上路径的input。txt文件
        reader = new InputStreamReader(
                new FileInputStream(filename)); // 建立一个输入流对象reader
        br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        line = "";
        line = br.readLine();
        Set<String> set2 = new HashSet<String>();
        set2.addAll(Arrays.asList(line.split(",")));

       set2.removeAll(set1);
       for(String store : set2){
           System.out.println(store);
       }
       System.out.println(set2.size());
    }
}

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CsvReadWrite {

    public static List<Map<String, String>> parseJSONWithJSONObject(String jsonData) {
        List<Map<String, String>> result = new LinkedList<Map<String, String>>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(jsonData);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, String> row = new HashMap<String,String>();
                row.put("dsId", jsonObject.getString("dsId"));
                row.put("cascadeUpdateEnabled", jsonObject.getString("cascadeUpdateEnabled"));
                result.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public static List<Map<String, String>> parseJSONWithJSONObject2(String jsonData) {
        List<Map<String, String>> result = new LinkedList<Map<String, String>>();
        try {
            JSONArray jsonArray = JSONArray.fromObject(jsonData);

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, String> row = new HashMap<String,String>();
                row.put("dsId", jsonObject.getString("dsId"));
                result.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void read(){

        String filePath = "/Users/mc/Downloads/guandata_data_flow.csv";
        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath,',',Charset.forName("UTF-8"));

            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                System.out.println(csvReader.getRawRecord());
                // 读这行的某一列
                //System.out.println(csvReader.get("inputs"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void write(){

        String filePath = "/Users/mc/Downloads/etl输入基础信息.csv";

        try {
            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(filePath,',', Charset.forName("UTF-8"));
            //CsvWriter csvWriter = new CsvWriter(filePath);

            // 写表头
            String[] headers = {"编号","姓名","年龄"};
            String[] content = {"12365","张山","34"};
            csvWriter.writeRecord(headers);
            csvWriter.writeRecord(content);
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCronType(String trigger_type) {
        if(trigger_type.equals("0"))
            return "级联触发";
        else if(trigger_type.equals("1"))
            return "手动触发";
        else
            return "定时触发";
    }

    public static void main(String[] args) {
        String filePath = "/Users/mc/Downloads/guandata_data_flow.csv";
        String inputInfoPath = "/Users/mc/Downloads/etl输入基础信息.csv";
        String outputInfoPath = "/Users/mc/Downloads/etl输出基础信息.csv";
        try {
            CsvReader csvReader = new CsvReader(filePath,',',Charset.forName("UTF-8"));
            CsvWriter inputInfoWriter = new CsvWriter(inputInfoPath,',', Charset.forName("UTF-8"));
            CsvWriter outputInfoWriter = new CsvWriter(outputInfoPath,',', Charset.forName("UTF-8"));
            String[] inputInfoHeaders = {"ETL id","ETL名称","ETL 更新方式","cron","输入数据集id","是否触发更新","创建时间","最近更新时间"};
            String[] outputInfoHeaders = {"ETL id","ETL名称","输出数据集id","创建时间","最近更新时间"};
            inputInfoWriter.writeRecord(inputInfoHeaders);
            outputInfoWriter.writeRecord(outputInfoHeaders);
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                //System.out.println(csvReader.getRawRecord());
                String etlId = csvReader.get(1);
                String name = csvReader.get("name");
                String inputs = csvReader.get("inputs");
                String outputs = csvReader.get("outputs");
                String ctime = csvReader.get("ctime");
                String utime = csvReader.get("utime");
                String cron = csvReader.get("cron");
                String triggerType = csvReader.get("trigger_type");
                List<Map<String, String>> inputRows = parseJSONWithJSONObject(inputs);
                for(Map<String, String> inputRow:inputRows){
                    String[] content = {etlId,name,getCronType(triggerType),cron,inputRow.get("dsId"),inputRow.get("cascadeUpdateEnabled"),ctime,utime};
                    inputInfoWriter.writeRecord(content);
                }

                List<Map<String, String>> outputRows = parseJSONWithJSONObject2(outputs);
                for(Map<String, String> outputRow:outputRows){
                    String[] content = {etlId,name,outputRow.get("dsId"),ctime,utime};
                    outputInfoWriter.writeRecord(content);
                }

                // 读这行的某一列
                //System.out.println(csvReader.get("inputs"));
            }

            csvReader.close();
            inputInfoWriter.close();
            outputInfoWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

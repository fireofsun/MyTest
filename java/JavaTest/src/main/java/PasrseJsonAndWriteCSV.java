import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
public class PasrseJsonAndWriteCSV {

    public static List<Map<String, String>> parseJSONWithJSONObject(String jsonData) {
        List<Map<String, String>> result = new LinkedList<Map<String, String>>();
        try {

            JSONArray jsonArray = JSONObject.fromObject(jsonData).getJSONArray("response");

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Map<String, String> row = new HashMap<String,String>();
                row.put("desc", jsonObject.getString("desc"));
                row.put("duration", jsonObject.getString("duration"));
                row.put("objectName", jsonObject.getString("objectName"));
                row.put("startTime", jsonObject.getString("startTime"));
                row.put("supportCancel", jsonObject.getString("supportCancel"));
                row.put("taskId", jsonObject.getString("taskId"));
                row.put("taskStatus", jsonObject.getString("taskStatus"));
                row.put("type", jsonObject.getString("type"));
                row.put("userName", jsonObject.getString("userName"));

                result.add(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String filePath = "/Users/mc/kube_ins/pindaotask.yaml";
        String outputInfoPath = "/Users/mc/Downloads/品道运行时长.csv";
        try {
            InputStream is = new FileInputStream(filePath);
            CsvWriter output = new CsvWriter(outputInfoPath,',', Charset.forName("UTF-8"));
            String[] outputHeader = {"desc","duration","objectName","startTime","supportCancel","taskId","taskStatus","type","userName"};
            output.writeRecord(outputHeader);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while (true) {
                str = reader.readLine();
                //List<Map<String, String>> oneDayDatas = parseJSONWithJSONObject(str);
                if(str!=null) {
                    List<Map<String, String>> oneDayDatas = parseJSONWithJSONObject(str);
                    for (Map<String, String> oneRow : oneDayDatas) {
                        String[] content = {oneRow.get("desc"), oneRow.get("duration"), oneRow.get("objectName"), oneRow.get("startTime"), oneRow.get("supportCancel"), oneRow.get("taskId"), oneRow.get("taskStatus"), oneRow.get("type"), oneRow.get("userName")};
                        output.writeRecord(content);
                    }
                }
                else
                    break;
            }

            output.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package cn.learning;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 联通大王卡找号
 */
public class FindTelephoneNumber {
    public static void main(String[] args) throws Exception{
        String prefix = "1766512";
        Set<String> sets = new HashSet<>();
        while (sets.size()<5){
            List<String> telList = getAllTel();
            Set<String> telSet = new HashSet(telList);
            telSet.remove("0");
            telSet.remove("1");
            for (String aTel : telSet) {
                if (aTel.contains(prefix)) {
                    sets.add(aTel);
                    System.out.println(aTel);
                }
            }
            System.out.println("查找中"+sets);
        }
        System.out.println(sets);
    }

    private static List<String> getAllTel() throws IOException {
        URL rul = new URL("https://m.10010.com/NumApp/NumberCenter/qryNum?callback=jsonp_queryMoreNums&provinceCode=51&cityCode=510&monthFeeLimit=0&groupKey=21236872&searchCategory=3&net=01&amounts=200&codeTypeCode=&searchValue=&qryType=02&goodsNet=4&_=1551230053900");
        HttpURLConnection httpURLConnection = (HttpsURLConnection)rul.openConnection();
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        InputStream inputStream = httpURLConnection.getInputStream();
        byte[] b = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len = 0;
        while (true) {
            len = inputStream.read(b);
            if (len == -1) {
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        String response = byteArrayOutputStream.toString();
        response = response.replaceAll("jsonp_queryMoreNums","").replaceAll("\\(","").replaceAll("\\)","");
        JSONArray jsonArray =  ((JSONObject) JSONObject.parse(response)).getJSONArray("numArray");
        return jsonArray.toJavaList(String.class);
    }
}

package ctt.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

@Slf4j
/**
 * Created by theo on 25-06-2017.
 * Utility class for http request
 */
public class GsonUtil {
    /**
     * execute http request against a REST Api
     * @param url
     * @param jsonBody
     * @return http response as {@link java.lang.String}
     */
    public static String http(String url, String jsonBody) {

        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            HttpPost request = new HttpPost(url);
            StringEntity params = new StringEntity(jsonBody);
            request.addHeader("content-type", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
        return null;
    }

    /**
     *
     * @param responseText
     * @return json for creating corresponding java Objects
     */
    public static String CSVResponse2Json(String responseText) {
        log.debug("json encoding CSV formatted response");
        //replace inconvenient field name
        String text = responseText.replace("GPS_YYYY-MM-DD_HH:MM:SS","gps_datetime");
        String lines[] = text.split("\r?\n");
        String headers[] = lines[0].split(",");
        StringBuilder jSonObject = new StringBuilder();
        jSonObject.append("[");
        for (int j = 1; j < lines.length; j++) {
            String values[] = lines[j].split(",");
            jSonObject.append("{");
            for (int i = 0; i < headers.length; i++) {
                if (values[i].length() > 0) {
                    jSonObject.append("\"");
                    jSonObject.append(headers[i]);
                    jSonObject.append("\":\"");
                    jSonObject.append(values[i]);
                    jSonObject.append("\"");
                    if (i < headers.length - 1) {
                        jSonObject.append(",");
                    }
                }
            }
            jSonObject.append("}");
            if(j<lines.length-1){
                jSonObject.append(",");
            }
        }
        jSonObject.append("]");
        return jSonObject.toString();
    }


}
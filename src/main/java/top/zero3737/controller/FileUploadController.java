package top.zero3737.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Controller
public class FileUploadController {

    @RequestMapping(value= {"/fileupload"})
    @ResponseBody
    public String addContent(@RequestParam Map<String, String> params, @RequestParam("file") MultipartFile[] files) {

        String url = "http://192.168.1.3:1122/Home/Media/importMusic";
        String url2 = "http://localhost:8080/test_war_exploded/testfileupload";
        RestTemplate restTemplate = new RestTemplate();

        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("multipart/form-data");
        headers.setContentType(type);
        headers.set("Cookie", "classify=9; PHPSESSID=isnbovacds33r40b8fmmmqt6v3; theme=red; user_id=4; language=0; user_type=admin");

        //设置请求体，注意是LinkedMultiValueMap
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        for(String key : params.keySet()){

            form.add(key, params.get(key));

        }
        for (final MultipartFile file : files) {

            try {

                ByteArrayResource fileAsResource = new ByteArrayResource(file.getBytes()) {

                    @Override
                    public String getFilename() {

                     return  file.getOriginalFilename();

                    }

                    @Override
                    public long contentLength() {
                        return file.getSize();
                    }

                };

                form.add("file", fileAsResource);

            } catch (Exception e) {

                e.printStackTrace();

            }

        }

        //用HttpEntity封装整个请求报文
        HttpEntity<MultiValueMap<String, Object>> my_file = new HttpEntity<>(form, headers);
        String s = restTemplate.postForObject(url, my_file, String.class);

        System.out.println(s);

        return null;

    }

    @RequestMapping(value= {"/testfileupload"})
    @ResponseBody
    public String testFileUpload(@RequestParam Map<String, String> params, @RequestParam("file") MultipartFile[] files) {

        System.out.println(params);

        return null;

    }

}

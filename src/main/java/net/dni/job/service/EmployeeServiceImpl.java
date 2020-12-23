package net.dni.job.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;

@Log4j2
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    RestTemplate restTemplate;

    @Override
    public void downloadEmployeeCsv(Resource resource) {
        File file = restTemplate.execute("http://localhost:8080/sample", HttpMethod.GET, null, clientHttpResponse -> {
            File ret = resource.getFile();
            /*todo:manipulate stream*/
            StreamUtils.copy(clientHttpResponse.getBody(), new FileOutputStream(ret));
            return ret;
        });
        log.info("download - [{}]", file);
    }
}

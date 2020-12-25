package net.dni.job.rest;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class SampleRestController {

    @GetMapping(path = "/sample", produces = "text/csv")
    public ResponseEntity<Resource> sampleCsv(HttpServletResponse response) {
        Resource resource = new ClassPathResource("sample.csv");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);

    }
}

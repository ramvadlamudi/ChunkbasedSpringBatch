package com.springbatch.springbatch.Writer;

import com.springbatch.springbatch.dto.MemberDto;
import org.springframework.batch.item.ItemWriter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class RestApiItemWriter implements ItemWriter<MemberDto> {

    private final RestTemplate restTemplate;

    public RestApiItemWriter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void write(List<? extends MemberDto> items) throws Exception {
        for (MemberDto item : items) {
            String url = "http://localhost:9999/api/insertMemberDetails"; // Replace with the actual third-party URL
            try {
                System.out.println("========Print the Item details======"+item.toString());
                restTemplate.postForEntity(url, item, MemberDto.class); // Send each record as a POST request
            } catch (Exception e) {
                // Handle failure, logging, retries, etc.
                e.printStackTrace();
            }
        }
    }


}

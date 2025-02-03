package it.cgmconsulting.mspost.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Configuration
@Slf4j
public class PostsConfiguration {

    @Bean("getWriters")
    public Map<Integer, String> getWriters(){

        RestTemplate restTemplate = new RestTemplate();

        /* http://localhost:9090/ms-auth/users-by-role?role=WRITER */
        String url = Constants.GATEWAY+Constants.MS_AUTH+"users-by-role?role=WRITER";

        try{
            Map<Integer, String> map = restTemplate.getForObject(url, Map.class);
            return map;
        } catch (RestClientException ex){
            log.error("## "+ex.getMessage());
            return null;
        }
    }
}

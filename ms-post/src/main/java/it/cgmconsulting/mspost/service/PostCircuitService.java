package it.cgmconsulting.mspost.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import it.cgmconsulting.mspost.configuration.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class PostCircuitService {

    @CircuitBreaker(name="a-tentativi", fallbackMethod = "getAvgByPostFallBackMethod")
    public Double getAvgByPost(int postId){
        RestTemplate restTemplate = new RestTemplate();
        String url = Constants.GATEWAY+Constants.MS_RATING+"v0/"+postId;
        return restTemplate.getForObject(url, Double.class);
    }
    public Double getAvgByPostFallBackMethod(Exception e){
        log.info("SONO NEL METODO DI FALLBACK");
        return 0d;
    }

}

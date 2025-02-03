package it.cgmconsulting.gateway.configuration;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

    // v0 -> non serve token
    // v1 -> role ADMIN
    // v2 -> role WRITER
    // v3 -> role MEMBER
    // v4 -> role MODERATOR
    // etc ...

    public boolean isOpenEndpoint(ServerHttpRequest request){
        return request.getURI().getPath().contains("v0") ||
                request.getURI().getPath().contains("ms-auth") ||
                request.getURI().getPath().contains("actuator");
    }

}

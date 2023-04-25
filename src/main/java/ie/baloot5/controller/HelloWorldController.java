package ie.baloot5.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:9090")
@RestController
public class HelloWorldController {

    @CrossOrigin(origins = "http://localhost:9090")
    @GetMapping("/hello")
    public String sayHello() {
        return "adfasdfasdfsdf asdfasdf ddddddd";
    }
}

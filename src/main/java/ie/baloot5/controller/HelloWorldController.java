package ie.baloot5.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @GetMapping("/api/hello")
    public String sayHello() {
        return "adfasdfasdfsdf asdfasdf ddddddd";
    }
}

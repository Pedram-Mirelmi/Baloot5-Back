package ie.baloot5.controller;

import ie.baloot5.data.IRepository;
import ie.baloot5.data.ISessionManager;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PaymentController {
    final IRepository repository;
    final ISessionManager sessionManager;

    public PaymentController(IRepository repository, ISessionManager sessionManager) {
        this.repository = repository;
        this.sessionManager = sessionManager;
    }
}

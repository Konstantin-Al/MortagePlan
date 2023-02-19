package ax.k.controller;
import ax.k.domain.Customer;
import ax.k.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addCustomer(Customer customer, Model model) {

        if (!userService.addCustomer(customer)) {
            model.addAttribute("message", "User exists!");
            return "registration";
        }
        return "redirect:/customer";
    }
}
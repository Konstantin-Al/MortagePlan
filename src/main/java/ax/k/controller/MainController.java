package ax.k.controller;
import ax.k.domain.Customer;
import ax.k.repos.LoanCalRepo;
import ax.k.service.MainService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@org.springframework.stereotype.Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    @Autowired
    private LoanCalRepo loanCalRepo;
    @Autowired
    private MainService mainService;

    @GetMapping("/login")
    private String login(){
        logger.info("getMapping login");

        return "login";
    }

    @GetMapping("/")
    private String index(   @AuthenticationPrincipal Customer customer, Model model ){
        logger.info("getMapping main");
        logger.info(String.valueOf(customer.getRoles()));

        if (customer.isAdmin())  {
            logger.info("admin");

            model.addAttribute("loanCals", loanCalRepo.findAll(
                    Sort.by(Sort.Direction.DESC, "CD")
                            .and(Sort.by(Sort.Direction.ASC, "customer"))));
        }
        else{
            logger.info("user");
            model.addAttribute("loanCals", loanCalRepo.findLoanCalByCustomerOrderByCDDesc(customer));
        }

        return "main";
    }

    @PostMapping (params = "action=calculate")
    public String add(  @AuthenticationPrincipal Customer customer,
                        @RequestParam String total_loan,
                        @RequestParam String interest,
                        @RequestParam String years
                    ){
        logger.info("getMapping action=calculate");

        int myYears = 2; // TODO

        mainService.calBtnClicked(customer, total_loan, interest, myYears);

        return "redirect:/";
    }

    @PostMapping (params = "action=import")
    public String importCSV(@RequestParam MultipartFile importedFile) throws IOException {
        logger.info("getMapping action=import");
        logger.info("File name: " + importedFile.getOriginalFilename());
        logger.info("File size: " + importedFile.getSize());

        mainService.importBtnClicked(importedFile);

        return "redirect:/";
    }
}
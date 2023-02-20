package ax.k.controller;
import ax.k.domain.Customer;
import ax.k.service.MainService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;



@Controller
public class MainController {

    Logger logger = LoggerFactory.getLogger(MainController.class);

    String RESULT_STR = null;


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
            model.addAttribute("loanCals", mainService.mainLoadAllLoanCal());
        }
        else{
            logger.info("user");
            model.addAttribute("loanCals", mainService.mainLoadLoanCalByCustomer(customer));

            if (RESULT_STR != null){
                model.addAttribute("result_str", RESULT_STR);
                RESULT_STR = null;
            }
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

        boolean isTotal_loanNum = isNum(total_loan);
        boolean isInterestNum = isNum(interest);
        boolean isYearsNum = isNum(years);

        if (isTotal_loanNum) {
            if(isInterestNum) {
                if (isYearsNum) {
                    RESULT_STR = mainService.calBtnClicked(customer, total_loan, interest, years);
                } else {
                    RESULT_STR = "Years is not numeric";
                }
            }
            else {
                RESULT_STR = "Interest is not numeric";
            }
        }
        else {
            RESULT_STR = "Total loan is not numeric";
        }

        return "redirect:/";
    }

    @PostMapping (params = "action=import")
    public String importCSV(@RequestParam MultipartFile importedFile) {
        logger.info("getMapping action=import");
        logger.info("File name: " + importedFile.getOriginalFilename());
        logger.info("File size: " + importedFile.getSize());

        mainService.importBtnClicked(importedFile);

        return "redirect:/";
    }

    private boolean isNum (String str){
        return str.chars().allMatch( Character::isDigit );
    }


}
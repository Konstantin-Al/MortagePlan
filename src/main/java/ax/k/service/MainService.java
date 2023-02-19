package ax.k.service;


import ax.k.controller.MainController;
import ax.k.domain.Customer;
import ax.k.domain.LoanCal;
import ax.k.repos.LoanCalRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


@Service
public class MainService  {


    Logger logger = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private ReadService readService;

    @Autowired
    private CalService calService;


    @Autowired
    private LoanCalRepo loanCalRepo;


    public void importBtnClicked(MultipartFile importedFile) throws IOException {
        logger.info("importBtnClicked");

        // read and clean
        ArrayList<String[]> listOfLines = readService.readFile(importedFile);

        // Send ArrayList to a method for calculation
        ArrayList<String[]> calListOfLines = calService.calculateArray(listOfLines);

        logger.info(Arrays.toString(calListOfLines.get(1)));
        logger.info(String.valueOf(calListOfLines.size()));
    }

    public void calBtnClicked(Customer customer, String total_loan, String interest, int years){
        logger.info("addBtnClicked");

        LocalDateTime CD = LocalDateTime.now();

        LoanCal loanCal = new LoanCal(CD, customer, total_loan, interest, years);
        loanCalRepo.save(loanCal);

    }

}
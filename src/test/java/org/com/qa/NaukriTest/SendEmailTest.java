package org.com.qa.NaukriTest;

import static Utils.Constant.*;
import static Utils.EmailData.*;

import Utils.EmailUtils;
import Utils.ExtractHREmailsFromPDF;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.ArrayList;


public class SendEmailTest {

    ArrayList<String> filesPaths = new ArrayList<>(
            Arrays.asList(
                    WFH_HR_EMAIL_PATH,
                    PUNE_HR_EMAIL_PATH,
                    HYD_HR_EMAIL_PATH,
                    BANG_HR_EMAIL_PATH,
                    TestingJobs_HR_EMAIL_PATH,
                    OTHER_HR_EMAIL_PATH
            )
    );


    private String rajuEmailPassKey = "lzcovpirefbjajlm";
    private String sushantEmailPassKey = "khejraffnvojznnb";
    private String priyaEmailPassKey = "vivukgxopmphpour";
    private String aniketaEmailPassKey = "lczwyxxuckyspyao";
    private String anchalEmailPassKey = "vfehlvcsciwiwfdz";

    @Test
    public void sendEmail() {

        for (String filePath : filesPaths) {
            Set<String> HR_Emails = ExtractHREmailsFromPDF.extractEmails(filePath);
            EmailUtils emailUtils = new EmailUtils();
            emailUtils.sendEmailToBCC("rajugodamwar@gmail.com", rajuEmailPassKey, HR_Emails, RAJU_EMAIL_SUBJECT, RAJU_EMAIL_BODY, RAJU_RESUME_PATH);
            emailUtils.sendEmailToBCC("yadavpriya73028@gmail.com", priyaEmailPassKey, HR_Emails, PRIYA_EMAIL_SUBJECT, PRIYA_EMAIL_BODY, PRIYA_RESUME_PATH);
            emailUtils.sendEmailToBCC("sushj2405@gmail.com", sushantEmailPassKey, HR_Emails, SUSHANT_EMAIL_SUBJECT, SUSHANT_EMAIL_BODY, SUSHANT_RESUME_PATH);
            emailUtils.sendEmailToBCC("aniketpotdar88@gmail.com", aniketaEmailPassKey, HR_Emails, ANIKET_EMAIL_SUBJECT, ANIKET_EMAIL_BODY, ANIKET_RESUME_PATH);
            emailUtils.sendEmailToBCC("anchalsingh1029@gmail.com", anchalEmailPassKey, HR_Emails, ANCHAL_EMAIL_SUBJECT, ANCHAL_EMAIL_BODY, ANCHAL_RESUME_PATH);

            System.out.println("Total Email Sent to  HR is :" + HR_Emails.size());
        }
    }


    @Test
    public void sendEmailSushant() {
        for (String file : filesPaths) {
            Set<String> emails = ExtractHREmailsFromPDF.extractEmails(file);
            EmailUtils emailUtils = new EmailUtils();
            for (String email : emails) {
                emailUtils.sendEmailTo("sushj2405@gmail.com", sushantEmailPassKey, email, SUSHANT_EMAIL_SUBJECT, SUSHANT_EMAIL_BODY, SUSHANT_RESUME_PATH);
            }
            System.out.println("Total Email Sent to HR is :" + emails.size());
        }
    }
}
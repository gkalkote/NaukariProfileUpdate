package org.com.qa.NaukriTest;

import static Utils.Constant.*;
import static Utils.EmailData.*;

import Utils.EmailUtils;
import Utils.ExtractHREmailsFromPDF;
import org.com.qa.NaukriBase.PlaywrightFactoryPage;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.ArrayList;


public class SendEmailTest extends PlaywrightFactoryPage {

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

    private final String rajuEmailPassKey = "lzcovpirefbjajlm";
    private final String sushantEmailPassKey = "khejraffnvojznnb";
    private final String priyaEmailPassKey = "vivukgxopmphpour";
    private final String aniketaEmailPassKey = "lczwyxxuckyspyao";
    private final String anchalEmailPassKey = "vfehlvcsciwiwfdz";
    private final String truptiEmailPassKey = "trnlezudgfqkdxcb";
    private final String ganeshEmailPassKey = "adrctxykgpumbyjb";
    private final String nikhilEmailPassKey= "rmunucldudesopqo";
    private final String krishnaEmailPasskey = "mkkyctaiesuwrvag";

    @Test
    public void sendEmail() {

    	    int totalHRMailCount=0;

        for (String filePath : filesPaths) {
            Set<String> HR_Emails =  ExtractHREmailsFromPDF.limitEmails(ExtractHREmailsFromPDF.extractEmails(filePath));
            totalHRMailCount+=HR_Emails.size();
            EmailUtils emailUtils = new EmailUtils();
//            emailUtils.sendEmailToBCC("rajugodamwar@gmail.com", rajuEmailPassKey, HR_Emails, RAJU_EMAIL_SUBJECT, RAJU_EMAIL_BODY, RAJU_RESUME_PATH);
            emailUtils.sendEmailToBCC("yadavpriya73028@gmail.com", priyaEmailPassKey, HR_Emails, PRIYA_EMAIL_SUBJECT, PRIYA_EMAIL_BODY, PRIYA_RESUME_PATH);
            emailUtils.sendEmailToBCC("sushj2405@gmail.com", sushantEmailPassKey, HR_Emails, SUSHANT_EMAIL_SUBJECT, SUSHANT_EMAIL_BODY, SUSHANT_RESUME_PATH);
            emailUtils.sendEmailToBCC("aniketpotdar88@gmail.com", aniketaEmailPassKey, HR_Emails, ANIKET_EMAIL_SUBJECT, ANIKET_EMAIL_BODY, ANIKET_RESUME_PATH);
            emailUtils.sendEmailToBCC("anchalsingh1029@gmail.com", anchalEmailPassKey, HR_Emails, ANCHAL_EMAIL_SUBJECT, ANCHAL_EMAIL_BODY, ANCHAL_RESUME_PATH);
            emailUtils.sendEmailToBCC("truptirahir26@gmail.com", truptiEmailPassKey, HR_Emails, TRUPTI_EMAIL_SUBJECT, TRUPTI_EMAIL_BODY, TRUPTI_RESUME_PATH);
            emailUtils.sendEmailToBCC("gkalkote2026@gmail.com", ganeshEmailPassKey, HR_Emails, GANESH_EMAIL_SUBJECT, GANESH_EMAIL_BODY, GANESH_RESUME_PATH);
            emailUtils.sendEmailToBCC("em.nikhilmali@gmail.com", nikhilEmailPassKey, HR_Emails, NIKHIL_EMAIL_SUBJECT, NIKHIL_EMAIL_BODY, NIKHIL_RESUME_PATH);
            emailUtils.sendEmailToBCC("krishnaindrale19@gmail.com", krishnaEmailPasskey, HR_Emails, KRISHNA_EMAIL_SUBJECT, KRISHNA_EMAIL_BODY, KRISHNA_RESUME_PATH);
        }
        System.out.println("Total Email send to HR's are : "+ totalHRMailCount);
    }

    /*
    @Test(enabled = false)
    public void devBccSendEmail(){
        int totalHRMailCount=0;

        for (String filePath : filesPaths) {
            Set<String> HR_Emails =  ExtractHREmailsFromPDF.limitEmails(ExtractHREmailsFromPDF.extractEmails(filePath));
            totalHRMailCount+=HR_Emails.size();
            EmailUtils emailUtils = new EmailUtils();
            if(filePath.contains(NameOfpdf_file)){
               emailUtils.sendEmailToBCC("yadavpriya73028@gmail.com", priyaEmailPassKey, HR_Emails, PRIYA_EMAIL_SUBJECT, PRIYA_EMAIL_BODY, PRIYA_RESUME_PATH);
               emailUtils.sendEmailToBCC("anchalsingh1029@gmail.com", anchalEmailPassKey, HR_Emails, ANCHAL_EMAIL_SUBJECT, ANCHAL_EMAIL_BODY, ANCHAL_RESUME_PATH);
               emailUtils.sendEmailToBCC("em.nikhilmali@gmail.com", nikhilEmailPassKey, HR_Emails, NIKHIL_EMAIL_SUBJECT, NIKHIL_EMAIL_BODY, NIKHIL_RESUME_PATH);
            }
        }
        System.out.println("Total Email send to HR's are : "+ totalHRMailCount);
    }
    */

}

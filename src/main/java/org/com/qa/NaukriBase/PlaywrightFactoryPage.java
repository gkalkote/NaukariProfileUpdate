package org.com.qa.NaukriBase;

import Utils.EmailUtils;
import com.microsoft.playwright.*;
import org.com.qa.NaukaruPages.LoginPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

import static Utils.Constant.HR_EMAIL_PATH;

public class PlaywrightFactoryPage {

    // Thread-safe storage for Playwright objects
    private static final ThreadLocal<Playwright> threadLocalPlaywright = new ThreadLocal<>();
    private static final ThreadLocal<Browser> threadLocalBrowser = new ThreadLocal<>();
    private static final ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private static final ThreadLocal<LoginPage> threadLocalLoginPage = new ThreadLocal<>();

    @BeforeMethod
    public void setupBrowser() {
        Playwright playwright = Playwright.create();
        threadLocalPlaywright.set(playwright);

        Browser browser = playwright.chromium()
                .launch(new BrowserType.LaunchOptions().setHeadless(true));
        threadLocalBrowser.set(browser);

        Page page = browser.newPage();
        threadLocalPage.set(page);

        // Navigate to login page
        page.navigate("https://www.naukri.com/nlogin/login");


        // Initialize LoginPage
        threadLocalLoginPage.set(new LoginPage(page));
    }
    public static String getTodayDateString() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd - yyyy");
        Date today = new Date();
        return sdf.format(today);
    }

    @BeforeSuite
    public void emailPdfCleanUpAndDownloadLatest(){
        String sushantEmailPassKey = "khejraffnvojznnb";
        EmailUtils emailUtils = new EmailUtils();
        emailUtils.deleteExistingPdfs(HR_EMAIL_PATH);
        String subject= "Today Testing Jobs Across India - "+getTodayDateString();
        emailUtils.downloadPdfAttachments("sushj2405@gmail.com", sushantEmailPassKey,subject,HR_EMAIL_PATH);
    }

    @AfterMethod
    public void tearDown() {
        if (threadLocalPage.get() != null) {
            threadLocalPage.get().close();
            threadLocalPage.remove();
        }

        if (threadLocalBrowser.get() != null) {
            threadLocalBrowser.get().close();
            threadLocalBrowser.remove();
        }

        if (threadLocalPlaywright.get() != null) {
            threadLocalPlaywright.get().close();
            threadLocalPlaywright.remove();
        }

        threadLocalLoginPage.remove();
    }

    // Getter methods
    public static Page getPage() {
        return threadLocalPage.get();
    }

    public static LoginPage getLoginPage() {
        return threadLocalLoginPage.get();
    }

    public static Browser getBrowser() {
        return threadLocalBrowser.get();
    }
}

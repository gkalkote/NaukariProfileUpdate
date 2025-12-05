package org.com.qa.NaukriTest;

import org.com.qa.NaukaruPages.HomePage;
import org.com.qa.NaukaruPages.LoginPage;
import org.com.qa.NaukriBase.PlaywrightFactoryPage;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NaukriTest extends PlaywrightFactoryPage {

    @DataProvider(name = "naukriUsers")
    public Object[][] getNaukriUsers() {
        return new Object[][] { {"rajugodamwar@gmail.com", "Raju1998@"},{"gskalkote@gmail.com","Ganesh@123"},{"krishnaindrale19@gmail.com","Krishna@123"},{"yadavpriya73028@gmail.com","Priya@2023"} };
    }
    @Test(dataProvider = "naukriUsers")
    public void naukariProfileUpdate(String userName, String password) {
        LoginPage loginPage = getLoginPage();
        HomePage homePage = loginPage.login(userName, password);
        assert homePage.isHomePageDisplayed() : "Home page did not load correctly!"; // Verify home page loaded
        homePage.clickOnViewProfile();
        homePage.addAndDeleteSkill("Java");  // you can pass a skill name
    }
}
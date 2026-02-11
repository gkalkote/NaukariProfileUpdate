package Utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashSet;
import java.util.Set;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class ExtractHREmailsFromPDF {

    private static final int GMAIL_BCC_LIMIT = 499;

    public static void main(String[] args) {
        extractEmails("C:\\Users\\HP\\OneDrive\\Pictures\\Documents\\GitHub\\NaukariProfileUpdate\\src\\test\\resources\\Emailpdf\\Hyderabad.pdf");
    }
    // Method to extract unique email IDs from a PDF file
    public static Set<String> extractEmails(String filePath) {
        Set<String> uniqueEmails = null;
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            // Extract text from PDF
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            // Regex to match emails ending with .com or .in
            Pattern emailPattern = Pattern.compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.(com|in)");
            Matcher matcher = emailPattern.matcher(text);
            uniqueEmails = new HashSet<>();
            while (matcher.find()) {
                uniqueEmails.add(matcher.group());
            }
            System.out.println("Extracted Email IDs:");
            uniqueEmails.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return uniqueEmails;
    }

    public static Set<String> limitEmails(Set<String> extractedEmails) {

        // If already within limit, return as-is
        if (extractedEmails == null || extractedEmails.size() <= GMAIL_BCC_LIMIT) {
            return extractedEmails;
        }
        Set<String> limitedEmails = new LinkedHashSet<>();
        int count = 0;
        for (String email : extractedEmails) {
            limitedEmails.add(email);
            count++;
            if (count == GMAIL_BCC_LIMIT) {
                break;
            }
        }
        return limitedEmails;
    }
}

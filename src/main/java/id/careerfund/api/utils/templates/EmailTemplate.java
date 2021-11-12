package id.careerfund.api.utils.templates;

import org.springframework.stereotype.Component;

@Component("emailTemplate")
public class EmailTemplate {
    public String getResetPassword() {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "\t.email-container {\n" +
                "\t\tpadding-top: 10px;\n" +
                "\t}\n" +
                "\tp {\n" +
                "\t\ttext-align: left;\n" +
                "\t}\n" +
                "\n" +
                "\ta.btn {\n" +
                "\t\tdisplay: block;\n" +
                "\t\tmargin: 30px auto;\n" +
                "\t\tbackground-color: #01c853;\n" +
                "\t\tpadding: 10px 20px;\n" +
                "\t\tcolor: #fff;\n" +
                "\t\ttext-decoration: none;\n" +
                "\t\twidth: 30%;\n" +
                "\t\ttext-align: center;\n" +
                "\t\tborder: 1px solid #01c853;\n" +
                "\t\ttext-transform: uppercase;\n" +
                "\t}\n" +
                "\ta.btn:hover,\n" +
                "\ta.btn:focus {\n" +
                "\t\tcolor: #01c853;\n" +
                "\t\tbackground-color: #fff;\n" +
                "\t\tborder: 1px solid #01c853;\n" +
                "\t}\n" +
                "\t.user-name {\n" +
                "\t\ttext-transform: uppercase;\n" +
                "\t}\n" +
                "\t.manual-link,\n" +
                "\t.manual-link:hover,\n" +
                "\t.manual-link:focus {\n" +
                "\t\tdisplay: block;\n" +
                "\t\tcolor: #396fad;\n" +
                "\t\tfont-weight: bold;\n" +
                "\t\tmargin-top: -15px;\n" +
                "\t}\n" +
                "\t.mt--15 {\n" +
                "\t\tmargin-top: -15px;\n" +
                "\t}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div class=\"email-container\">\n" +
                "\t\t<p><strong>Dear customer!</strong></p>\n" +
                "\t\t\n" +
                "\t\t<p>Click link below to reset the password</p>\n" +
                "\t\t\n" +
                "\t\t<a href='{{RESET_LINK}}' >Reset Password</a>\n" +
                "\t\t\n" +
                "\t\t<p class=\"mt--15\">Valid for only 5 minutes</p>\n" +
                "\t\t\n" +
                "\t\t<p><strong>CareerFund Team</strong></p>\n" +
                "\t\t<p class=\"mt--15\".....</p>\n" +
                "\n" +
                "\t\t\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>";
    }
}


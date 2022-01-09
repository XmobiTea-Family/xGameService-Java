package com.xmobitea.mail;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.xmobitea.debug.xDebug;
import lombok.NonNull;

import java.io.IOException;
import java.util.regex.Pattern;

public final class xMail {
    private final static String EMAIL_PATTERN = "[a-zA-Z._-]+@(([a-z]+\\.)+[a-zA-Z]{2,})$";

    private static SendGrid _sendGrid;
    private static String _fromEmail;

    public static void init(@NonNull String apiKey, @NonNull String fromEmail) {
        _sendGrid = new SendGrid(apiKey);

        if (!isValidEmail(_fromEmail)) {
            xDebug.logError("From email invalid " + fromEmail);
            return;
        }

        _fromEmail = fromEmail;
    }

    public static boolean isValidEmail(@NonNull String email) {
        return Pattern.matches(EMAIL_PATTERN, email);
    }

    public static void sendMail(@NonNull String toEmail, @NonNull String subject, @NonNull String contentHTML) {
        if (_sendGrid == null) {
            xDebug.logError("xMail does not init");
            return;
        }
        if (!isValidEmail(toEmail)) {
            xDebug.logError("To email invalid " + toEmail);
            return;
        }

        var mail = new Mail(new Email(_fromEmail), subject, new Email(toEmail), new Content("text/html", contentHTML));

        var request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            var response = _sendGrid.api(request);

            var statusCode = response.getStatusCode();
            if (statusCode > 399) {
                xDebug.logError("Mail send not success " + response.toString());
            }
        } catch (IOException ex) {
            xDebug.logException(ex);
        }
    }
}

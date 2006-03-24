package org.dotplot.util;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.dotplot.plugin.DotplotPlugin;

public class MailUtil
{
   public static void postMailBySMTP(
         String smtpHost, String smtpUser, String smtpPass, String from, String[] recipients, String subject,
         String message)
         throws MessagingException
   {
      postMailBySMTP(smtpHost, smtpUser, smtpPass, from, recipients, subject, message, null, null);
   }

   public static void postMailBySMTP(
         String smtpHost, String smtpUser, String smtpPass, String from, String[] recipients, String subject,
         String message, File[] attachments, String[] filenames)
         throws MessagingException
   {
      boolean debug = false;
      if (debug)
      {
         System.out.println("using the following settings:\n"
               + "host: " + smtpHost + "\n"
               + "user: " + smtpUser + "\n"
               + "pass: " + "*----*" + "\n"
               + "from: " + from + "\n"
               + "to:   " + recipients[0] + "\n"
               + "subj: " + subject + "\n"
               + "msg:  " + message);
      }

      //Set the host smtp address
      Properties props = new Properties();
      props.put("mail.smtp.host", smtpHost);
      props.put("mail.smtp.auth", "true");

      Authenticator auth = new SMTPAuthenticator(smtpUser, smtpPass);
      Session session = Session.getInstance(props, auth);

      session.setDebug(debug);

      // create a message
      Message msg = new MimeMessage(session);

      InternetAddress[] addressTo = new InternetAddress[recipients.length];
      for (int i = 0; i < recipients.length; i++)
      {
//         System.out.println(recipients[i]);
         addressTo[i] = new InternetAddress(recipients[i].trim());
      }

      msg.setFrom(new InternetAddress(from));
      msg.setRecipients(Message.RecipientType.TO, addressTo);
      msg.setSubject(subject);
      msg.setSentDate(new Date());
      msg.setHeader("X-Mailer", DotplotPlugin.getVersionInfo());

      if (attachments != null)
      {
         Multipart multipart = new MimeMultipart();

         BodyPart messageBodyPart;

         messageBodyPart = new MimeBodyPart();
         messageBodyPart.setText(message + "\n\n-- \n" + DotplotPlugin.getVersionInfo());

         multipart.addBodyPart(messageBodyPart);

         for (int i = 0; i < attachments.length; i++)
         {
            File attachment = attachments[i];

            messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachment);
            messageBodyPart.setDataHandler(new DataHandler(source));

            if (filenames != null && filenames.length == attachments.length)
            {
               messageBodyPart.setFileName(filenames[i]);
            }
            else
            {
               messageBodyPart.setFileName(attachment.getName());
            }

            multipart.addBodyPart(messageBodyPart);
         }

         msg.setContent(multipart);
      }
      else
      {
         msg.setContent(message + "\n\n-- \n" + DotplotPlugin.getVersionInfo(), "text/plain");
      }

      Transport.send(msg);
   }

   /**
    * SimpleAuthenticator is used to do simple authentication when the SMTP server requires it.
    */
   private static class SMTPAuthenticator extends Authenticator
   {
      private String m_user;
      private String m_pass;

      SMTPAuthenticator(String user, String pass)
      {
         m_user = user;
         m_pass = pass;
      }

      public PasswordAuthentication getPasswordAuthentication()
      {
         return new PasswordAuthentication(m_user, m_pass);
      }
   }
}

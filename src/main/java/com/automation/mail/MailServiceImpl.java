package com.automation.mail;


import com.automation.beanclass.ExtentReportBean;
import com.automation.configuration.MailConfiguration;
import freemarker.template.Configuration;
import java.util.HashMap;
import java.util.Map;
import javax.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;


@Component
@Log4j2
public class MailServiceImpl {
  
  @Autowired
  private JavaMailSender mailSender;

  @Autowired
  private Configuration freemarkerConfiguration;
  
  @Autowired
  private MailConfiguration mailconfig;
  

  /**
   * This method is used to send email.
   * @param object object.
   */
  public void sendEmail(Object object) {

    ExtentReportBean report = (ExtentReportBean) object;
    MimeMessagePreparator preparator = getMessagePreparator(report);

    try {
      mailSender.send(preparator);
      log.debug("Message has been sent.............................");
      
    } catch (MailException ex) {
      log.error(ex.getMessage());
      
    }
  }

  private MimeMessagePreparator getMessagePreparator(final ExtentReportBean report) {

    MimeMessagePreparator preparator = new MimeMessagePreparator() {

      public void prepare(MimeMessage mimeMessage) throws Exception {
        
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject(mailconfig.getEmailSubject());
        helper.setFrom(mailconfig.getFromEmailId());
        helper.setTo(mailconfig.getToEmailid());
        

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("report", report);

        String text = geFreeMarkerTemplateContent(model);
        //System.out.println("Template content : " + text);
        log.debug("Template content : " + text);

        /*
         * use the true flag to indicate you need a multipart message
         */
        helper.setText(text, true);

        /*
         * Additionally, let's add a resource as an attachment as well.
         */
        //helper.addAttachment("cutie.png", new ClassPathResource("linux-icon.png"));
        helper.addAttachment(report.getAutomationresult().getName(), report.getAutomationresult());

      }
    };
    return preparator;
  }

  /**
   * This method is used to convert the html email template.
   * @param model is of type object
   * @return Mail body content after replace the object 
   */
  public String geFreeMarkerTemplateContent(Map<String, Object> model) {
    StringBuffer content = new StringBuffer();
    try {
      log.debug("debugging");
      log.debug(freemarkerConfiguration.getTemplateLoader().toString());
      content.append(FreeMarkerTemplateUtils.processTemplateIntoString(
                      freemarkerConfiguration.getTemplate("mailTemplate.txt"), model));
      return content.toString();
    } catch (Exception e) {
      log.error(e.getMessage());
      e.printStackTrace();
    }
    log.debug("Email message body content is empty due to some error");
    return "";
  }

}

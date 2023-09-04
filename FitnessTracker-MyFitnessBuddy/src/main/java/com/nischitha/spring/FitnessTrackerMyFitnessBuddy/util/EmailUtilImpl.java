package com.nischitha.spring.FitnessTrackerMyFitnessBuddy.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.nischitha.spring.FitnessTrackerMyFitnessBuddy.services.FitnessTrackerServiceImpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtilImpl implements EmailUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailUtilImpl.class);

	@Autowired
	private JavaMailSender sender;

	@Override
	public void sendEmail(String toAddress, String body, String subject) {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper;
		String fromAddress = "noreply@myfitnessbuddy.com";
		String fromName = "MyFITNESSBUDDY";

		try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(toAddress);
			helper.setSubject(subject);

			String htmlBody = "<html><head><style>#socialMedia{text-align:center;}#socialMedia ul{list-style: none;} #socialMedia ul li{display:inline-block;"
					+ "            margin: 25px 15px;} #socialMedia img{width: 40px;" + "            height: 40px;"
					+ "            cursor: pointer;}#mainHeader{text-align:center;}"
					+ "              #imgBlock {height: 400px;width: 100%;position: relative; padding-top: 10px;} "
					+ "               #logInBtn { margin: 4px auto;display: block;height: 33px;width: 128px;background: #fe7250;color: #fff;border-radius: 25px;font-size: 17px;padding: 9px 6px; border: 0;outline: 0;text-decoration: none;cursor: pointer;transition: all ease-in-out 1s;}"
					+ "              </style></head>" + "             <body>" + "                 <div id='mainHeader'>"
					+ "                      <h3>Thank you for signing up with MYFITNESSBUDDY!</h3>"
					+ "                       <p>Our goal is to help you track your fitness journey</p>"
					+ "                       <h2>Lets get strarted!</h2>"
					+ "                     <div id='btnBlock'><a href='http://localhost:8080/fitnesstrackermyfitnessbuddy/SignInPage\' id='logInBtn'>Sign In</a></div> "
					+ "                        <div id='imgBlock'>"
					+ "                         <img style='height:400px;width:100%;object-fit:cover;' src='cid:BackgroundImage' />"
					+ "                        </div>" + "               <div id='socialMedia'> <ul>"
					+ "                    <li>" + "                        <img src='cid:facebookImage'  />"
					+ "                    </li>" + "                    <li>"
					+ "                        <img src='cid:instagramImage'  />" + "                    </li>"
					+ "                    <li>" + "                        <img src='cid:twitterImage' />"
					+ "                    </li>" + "                    <li>"
					+ "                        <img src='cid:linkedinImage'  />" + "                    </li>"
					+ "                </ul></div>" + "            </body></html>";

			helper.setText(htmlBody, true);
			helper.addInline("BackgroundImage", new ClassPathResource("/static/FitnessTrackerTest-Images/e11.jpg"));
			helper.addInline("facebookImage", new ClassPathResource("/static/FitnessTrackerTest-Images/facebook.png"));
			helper.addInline("instagramImage",
					new ClassPathResource("/static/FitnessTrackerTest-Images/instagram.png"));
			helper.addInline("twitterImage", new ClassPathResource("/static/FitnessTrackerTest-Images/twitter.png"));
			helper.addInline("linkedinImage", new ClassPathResource("/static/FitnessTrackerTest-Images/linkedin.png"));

			InternetAddress from = new InternetAddress(fromAddress, fromName);
			helper.setFrom(from);

		} catch (MessagingException e1) {
			LOGGER.error("Exception(MessagingException) inside sendEmail is:" + e1);
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Exception(UnsupportedEncodingException) inside sendEmail is:" + e);
		}
		sender.send(message);

	}
}

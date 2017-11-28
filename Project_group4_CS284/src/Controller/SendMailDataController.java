package Controller;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

import model.Course;
import model.Member;

public class SendMailDataController {
	private Properties properties;
	private Session session;
	private Member member;
	private Course course;

	public SendMailDataController(Member member , Course course) {
		properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.socketFactory.port", "465");
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.port", "465");
		this.member = member;
		this.course = course;
	}

	public void initSender(String email, String password) {
		session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(email, password);
			}
		});
	}

	public void send(String sender, String email, String subject, String detail, OnSuccessListener listener) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(sender));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
			message.setSubject(subject);
			message.setText(detail);

			MimeBodyPart messageBodyPart = new MimeBodyPart();

			File file = new File(member.getUsername()+"_"+course.getCourseName()+"TotalGradeList.txt");
			String attach = file.getAbsolutePath();

			// fill message
			messageBodyPart.setText("Text file for all grades of students in the course "+course.getCourseName());

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();

			DataSource source = new FileDataSource(attach);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(member.getUsername()+"_"+course.getCourseName()+"TotalGradeList.txt");
			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);
			Transport.send(message);

			System.out.println("Mail Send Successfully.");
			listener.OnSuccess();
		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}
	}

}

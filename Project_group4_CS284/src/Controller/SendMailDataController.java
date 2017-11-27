package Controller;

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

import View.ProgressBarFrame;
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

			String attach = "C:\\Users\\Asus\\git\\Project_group4_CS2842\\Project_group4_CS284\\"+member.getUsername()+"_"+course.getCourseName()+"TotalGradeList.txt";
			// fill message
			//System.out.println(attach);
			messageBodyPart.setText("Text file for all grades of students in the course "+course.getCourseName());

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			//System.out.println(111);

			DataSource source = new FileDataSource(attach);
			//System.out.println(2222);
			messageBodyPart.setDataHandler(new DataHandler(source));
			//System.out.println(3333);
			messageBodyPart.setFileName(member.getUsername()+"_"+course.getCourseName()+"TotalGradeList.txt");
			//System.out.println(44444);
			multipart.addBodyPart(messageBodyPart);
			//System.out.println(55555);
			message.setContent(multipart);
			//System.out.println(6666);
			Transport.send(message);

			System.out.println("Mail Send Successfully.");
			listener.OnSuccess();
		} catch (MessagingException e) {

			throw new RuntimeException(e);

		}
	}

}

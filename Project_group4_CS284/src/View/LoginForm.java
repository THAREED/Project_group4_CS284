package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Controller.LoginController;

public class LoginForm extends JFrame implements ActionListener {
	private JLabel logo;
	private JTextField username;
	private JPasswordField password;
	private JLabel userLabel, passLabel;
	private JButton login, cancel;
	private JPanel mainPanel;
	private Color backgroundColor = new Color(179, 235, 255);

	public LoginForm() throws IOException {
		mainPanel = new JPanel(new BorderLayout());
		setLogo();
		setLoginControl();
		mainPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), "Login"));
		mainPanel.setBackground(backgroundColor);

		add(mainPanel);
		setTitle("Grading System");
		setSize(new Dimension(450, 350));
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void setLogo() {
		logo = new JLabel(new ImageIcon(this.getClass().getResource("/TU-logo.png")));
		JPanel logoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		logoPanel.add(logo);
		logo.setText("Thammasat University.");
		logo.setHorizontalTextPosition(SwingConstants.CENTER);
		logo.setVerticalTextPosition(SwingConstants.BOTTOM);
		mainPanel.add(logoPanel, BorderLayout.NORTH);
		logoPanel.setBackground(new Color(179, 235, 255));

	}

	public void setLoginControl() {
		username = new JTextField(15);
		username.setText("USERNAME");
		username.setForeground(Color.LIGHT_GRAY);
		username.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		username.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (username.getForeground().equals(Color.LIGHT_GRAY)) {
					username.setText("");
					username.setForeground(Color.DARK_GRAY);
					username.setHorizontalAlignment(SwingConstants.LEFT);
				}
			}
		});
		password = new JPasswordField(15);
		password.setText("********");
		password.setForeground(Color.LIGHT_GRAY);
		password.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		userLabel = new JLabel(new ImageIcon(this.getClass().getResource("/userIcon.png")));
		passLabel = new JLabel(new ImageIcon(this.getClass().getResource("/passwordIcon.png")));
		password.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (password.getForeground().equals(Color.LIGHT_GRAY)) {
					password.setText("");
					password.setForeground(Color.DARK_GRAY);
					password.setHorizontalAlignment(SwingConstants.LEFT);
				}
			}
		});
		password.addActionListener(this);
		password.setActionCommand("password");

		JPanel userPanel = new JPanel(new FlowLayout());
		userPanel.add(userLabel);
		userPanel.add(username);

		JPanel passPanel = new JPanel(new FlowLayout());
		passPanel.add(passLabel);
		passPanel.add(password);

		login = new JButton("Login");
		cancel = new JButton("Cancel");
		JPanel loginPanel = new JPanel(new FlowLayout());
		loginPanel.add(login);
		loginPanel.add(cancel);
		login.setBackground(Color.WHITE);
		cancel.setBackground(Color.WHITE);
		login.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		cancel.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		login.addActionListener(this);
		cancel.addActionListener(this);

		JPanel controlPanel = new JPanel(new GridLayout(3, 1));
		controlPanel.add(userPanel);
		controlPanel.add(passPanel);
		controlPanel.add(loginPanel);
		mainPanel.add(controlPanel);
		userPanel.setBackground(backgroundColor);
		loginPanel.setBackground(backgroundColor);
		passPanel.setBackground(backgroundColor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		if (str.equalsIgnoreCase("Cancel")) {
			username.setText("USERNAME");
			password.setText("********");
			username.setForeground(Color.LIGHT_GRAY);
			password.setForeground(Color.LIGHT_GRAY);
		}
		if (str.equalsIgnoreCase("Login") || str.equalsIgnoreCase("password")) {
			try {
				if (username.getText().equals("") && password.getPassword().length == 0) {
					throw new LoginException();
				} else if (username.getForeground().equals(Color.LIGHT_GRAY)
						|| password.getForeground().equals(Color.LIGHT_GRAY)) {
					throw new LoginException();
				} else {
					LoginController controller = new LoginController(username.getText(), password.getPassword());
					boolean check = controller.disposeLoginForm();
					if (check) {
						dispose();
					}
				}
			} catch (IOException | LoginException ex) {
				username.setText("USERNAME");
				password.setText("********");
				username.setForeground(Color.LIGHT_GRAY);
				password.setForeground(Color.LIGHT_GRAY);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		new LoginForm();
	}

}
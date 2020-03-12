package view;

import controller.ViewController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends AppView {

	private ViewController vc;
	private String defaultUserName;

	public LoginView(ViewController vc, String defaultUserName){
		this.vc = vc;
		this.defaultUserName = defaultUserName;
	}

	@Override
	public JPanel makePanel(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(200, 200, 200, 200));
		JButton loginButton = new JButton("Login as " + defaultUserName);
		loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent){
				vc.loginButtonPressed();
			}
		});
		panel.add(loginButton, BorderLayout.CENTER);
		return panel;

	}
}

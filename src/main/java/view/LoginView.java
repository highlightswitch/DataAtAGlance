package view;

import controller.ViewController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Vector;

public class LoginView extends AppView {

	private ViewController vc;
	private List<String> users;

	public LoginView(ViewController vc, List<String> users){
		this.vc = vc;
		this.users = users;
	}

	@Override
	public JPanel makePanel(){
		JPanel panel = new JPanel(new BorderLayout());
		panel.setLayout(new BorderLayout());
		panel.setBorder(new EmptyBorder(100, 40, 50, 40));

		JPanel buttons = new JPanel(new GridLayout(2,1));

		JComboBox<String> userComboBox = new JComboBox<>((Vector<String>) users);
		buttons.add(userComboBox);

		JButton loginButton = new JButton("Login");
		loginButton.addActionListener(e -> vc.loginButtonPressed((String) userComboBox.getSelectedItem()));
		buttons.add(loginButton);

		panel.add(buttons, BorderLayout.PAGE_START);
		return panel;
	}
}

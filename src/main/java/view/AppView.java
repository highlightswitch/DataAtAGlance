package view;

import javax.swing.*;

public abstract class AppView {

	private JPanel panel;

	public JPanel getPanel(){
		if(this.panel == null)
			this.panel = makePanel();
		return this.panel;
	}

	protected abstract JPanel makePanel();

}

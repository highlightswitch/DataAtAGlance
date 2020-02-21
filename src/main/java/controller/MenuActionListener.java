package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuActionListener implements ActionListener {


    MenuActionListener(){

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "Log Out":
                break;
            case "Quit":
                break;
        }
    }
}

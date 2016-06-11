package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui implements ActionListener{


    public void test() {
        JPanel panel = new JPanel(new BorderLayout(50, 50));
        panel.setBackground(Color.white);

//        JLabel label = new JLabel("Hello World", Label.CENTER);
//        panel.add(label);
        panel.setVisible(true);


    }

    public static void main(String [] args) {
//        Gui gui = new Gui();
//        /gui.test();
        String [] values = new String[] {"3", "4", "5", "6"};
        JComboBox comboBox = new JComboBox(values);
//        comboBox.setSelectedIndex(0);
//        comboBox.addActionListener(this);
        Action action = new ButtonAction("Ok");
        JPanel panel = new JPanel();
        JButton ok = new JButton("Ok");
        JButton cancel = new JButton("Cancel");
        JFrame frame = new JFrame("Example");
        panel.add(ok);
        panel.add(cancel);
        ok.setAction(action);

        ok.setText("Okay");
        cancel.setText("Cancel");


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }

    static class ButtonAction extends AbstractAction {

        String names;
        public ButtonAction(String name) {
            names = name;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
//                displayResult("Result", e);

            System.out.format("Hello %s ", e.getActionCommand());
        }
    }

}

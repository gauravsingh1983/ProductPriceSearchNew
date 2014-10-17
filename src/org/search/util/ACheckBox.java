package org.search.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ACheckBox {
	static JCheckBox check = null;
	static JPanel panel = null;
	static JCheckBox[] checkBoxes = {new JCheckBox("FK"),new JCheckBox("SD"),new JCheckBox("JB"),new JCheckBox("AZ")} ;
	
	static void init(){

	    //String title = (args.length == 0 ? "CheckBox Sample" : args[0]);
	    JFrame frame = new JFrame("title");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    panel = new JPanel(new GridLayout(0, 1));
	    Border border = BorderFactory.createTitledBorder("Pizza Toppings");
	    panel.setBorder(border);
	    for(JCheckBox check: checkBoxes)
	    {
	    	panel.add(check);
	    }

	    JButton button = new JButton("Submit");
	    Container contentPane = frame.getContentPane();
	    contentPane.add(panel, BorderLayout.CENTER);
	    contentPane.add(button, BorderLayout.SOUTH);
	    frame.setSize(300, 200);
	    frame.setVisible(true);
	    button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
			    
				System.out.println(panel.getComponents());
				for(Component comp : panel.getComponents())
				{
					if(comp instanceof JCheckBox)
					{
						check = (JCheckBox)comp;
						System.out.println(check.getText());
					}
				}
				
			}
		});
	  
	}
  public static void main(String args[]) {
	  
init();
  }
}

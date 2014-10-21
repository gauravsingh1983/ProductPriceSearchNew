package org.akka.actors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.akka.messages.Download;
import org.akka.messages.ProcessingCompleted;
import org.akka.messages.Start;
import org.search.entity.ProductDetails;
import org.search.util.WriteExcelDemo;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.RoundRobinPool;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Master extends UntypedActor
{

	private static JCheckBox							check				= null;
	private static File									productFile			= null;
	private static final JCheckBox[]					checkBoxes			= { new JCheckBox("FK"), new JCheckBox("SD"), new JCheckBox("JB"),
			new JCheckBox("AZ")	, new JCheckBox("SS")											};
	private static List<ActorRef>						actorList			= null;
	private static String[]								urls				= null;
	private static JRadioButton[]						radioButtons		= { new JRadioButton("2 GB"), new JRadioButton("4 GB"),
			new JRadioButton("8 GB")										};
	private static JFrame								frame				= new JFrame("BANG BANG");
	private static JTextField							txtField			= null;
	private static JPanel								portalPanel			= null;
	private static ButtonGroup							group				= null;
	private static Map<String, List<ProductDetails>>	allProductsPrices	= null;
	private static String[]								products			= null;					// WriteExcelDemo.readXLS(productFile);
	private long										startTime;
	private long										endTime;

	public Master()
	{

	}

	private void start()
	{
		for (String product : products)
		{
			for (ActorRef actor : actorList)
			{
				actor.tell(new Download(product), getSelf());
			}
		}
	}

	private void shutdown()
	{
		getContext().system().shutdown();
	}

	private static List<ActorRef> getActors(String[] urls, int workers)
	{
		Config config = ConfigFactory.load();
		ActorSystem system = ActorSystem.create("TestSystem", config);
		ActorRef downloaderRouter = null;
		List<ActorRef> actorList = new ArrayList<ActorRef>();
		for (String url : urls)
		{
			if ("FK".equals(url))
				downloaderRouter = system.actorOf(new RoundRobinPool(workers).props(Props.create(FlipkartProductPriceSearch.class)),
						"downloadRouterFK");
			if ("SD".equals(url))
				downloaderRouter = system.actorOf(new RoundRobinPool(workers).props(Props.create(SnapdealProductPriceSearch.class)),
						"downloadRouterSD");
			if ("JB".equals(url))
				downloaderRouter = system
						.actorOf(new RoundRobinPool(workers).props(Props.create(JabongProductPriceSearch.class)), "downloadRouterJB");
			if("SS".equals(url))
				downloaderRouter = system
				.actorOf(new RoundRobinPool(workers).props(Props.create(ShoppersStopProductPriceSearch.class)), "downloadRouterSS");

			actorList.add(downloaderRouter);
		}

		return actorList;
	}

	public static void main(String[] args)
	{
		init();
	}

	@Override
	public void onReceive(Object message) throws Exception
	{
		if (message instanceof Start)
		{
			startTime = System.currentTimeMillis();
			start();

		}
		else if (message instanceof ProcessingCompleted)
		{
			int cntr = ((ProcessingCompleted) message).getCount();
			System.out.println("Counter val :" + cntr);

			if (cntr == products.length * actorList.size())
			{
				allProductsPrices = ((ProcessingCompleted) message).getAllProductsPrices();
				System.out.println("Total items: "+getTotalProductListSize(allProductsPrices));
				
				if(getTotalProductListSize(allProductsPrices)>100)
				{
					WriteExcelDemo.writeToXLS(allProductsPrices);
					shutdown();
				}
				else
				{
					Object rowData[][] = WriteExcelDemo.allProductPricesToArray(allProductsPrices);
					Object columnNames[] = { "TITLE", "PRICE", "MRP", "DISCOUNT" };
					JTable table = new JTable(rowData, columnNames);
					JScrollPane scrollPane = new JScrollPane(table);
					frame.add(scrollPane, BorderLayout.CENTER);
					frame.setVisible(true);
					
				}
				shutdown();
				endTime = System.currentTimeMillis();
				System.out.println("Total time taken to extract data:" + (endTime - startTime));
				System.out.println("\nTotal time taken : " + TimeUnit.MILLISECONDS.toMinutes(endTime - startTime) + " minutes");
			}

		}

	}

	public static JRadioButton getSelection(ButtonGroup group)
	{
		for (Enumeration<AbstractButton> e = group.getElements(); e.hasMoreElements();)
		{
			JRadioButton b = (JRadioButton) e.nextElement();
			if (b.getModel() == group.getSelection())
			{
				return b;
			}
		}
		return null;
	}
	
	
	private static int getTotalProductListSize(Map<String, List<ProductDetails>> allProductsPrices)
	{
		//allProductsPrices = getSampleData();
		List<ProductDetails> allProductDetails = new ArrayList<ProductDetails>();
		for(Map.Entry<String, List<ProductDetails>> e: allProductsPrices.entrySet())
		{
			
			allProductDetails.addAll((List<ProductDetails>)e.getValue());
		}
		
		return allProductDetails.size();
	}

	private static void init()
	{

		// create checkbox panel for selectin portals to search
		portalPanel = new JPanel(new GridLayout(0, 1));
		Border border = BorderFactory.createTitledBorder("Portal to Search");
		portalPanel.setBorder(border);

		// create radiobutton panel for selecting RAM on your machine
		final JPanel ramPanel = new JPanel(new GridLayout(0, 1));
		Border border1 = BorderFactory.createTitledBorder("RAM on machine");
		ramPanel.setBorder(border1);

		// radio and checkboxes panel
		final JPanel panel = new JPanel(new GridLayout(1, 2));
		// add checkboxes
		for (JCheckBox check : checkBoxes)
		{
			portalPanel.add(check);
		}
		// add radio buttons
		group = new ButtonGroup();
		radioButtons[0].setSelected(true);
		for (JRadioButton radio : radioButtons)
		{
			ramPanel.add(radio);
			group.add(radio);
		}

		panel.add(portalPanel);
		panel.add(ramPanel);

		// contentField.add(filenameTxtfield);

		final JLabel fileLabel = new JLabel("Select a file for search: ");
		final JLabel txtFieldLabel = new JLabel("Type a product for search: ");
		txtField = new JTextField(1);
		JButton btnOpen = new JButton("Open File");

		btnOpen.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				JFileChooser fileopen = new JFileChooser();
				int ret = fileopen.showDialog(null, "Open file");

				if (ret == JFileChooser.APPROVE_OPTION)
				{
					productFile = fileopen.getSelectedFile();
					fileLabel.setText("Product list from file " + productFile.getName() + " Loaded for search !!");
				}

			}
		});

		JPanel contentPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(contentPanel, BoxLayout.Y_AXIS);
		contentPanel.setLayout(boxLayout);
		JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
		JPanel buttonPanel1 = new JPanel(new GridLayout(1, 1));
		JPanel buttonPanel2 = new JPanel(new GridLayout(1, 2));
		Border border2 = BorderFactory.createTitledBorder("Button Panel");
		contentPanel.setBorder(border2);
		// BoxLayout layout2 = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
		// buttonPanel.setLayout(layout2);

		buttonPanel.add(fileLabel);
		buttonPanel.add(btnOpen);
		buttonPanel1.add(new JLabel("OR"));
		buttonPanel2.add(txtFieldLabel);
		buttonPanel2.add(txtField);

		contentPanel.add(buttonPanel);
		contentPanel.add(buttonPanel1);
		contentPanel.add(buttonPanel2);
		// buttonPanel.add(filenameTxtfield);

		JPanel allContent = new JPanel(new GridLayout(2, 1));
		allContent.add(contentPanel);
		allContent.add(panel);

		JButton button = new JButton("Start Search!");

		// Make the table vertically scrollable
		Container contentPane = frame.getContentPane();
		contentPane.add(allContent, BorderLayout.NORTH);
		// contentPane.add(tablePane, BorderLayout.CENTER);
		contentPane.add(button, BorderLayout.SOUTH);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocation(200, 100);
		frame.setVisible(true);

		button.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{

				if (txtField.getText() != null)
				{
					products = txtField.getText().split(",");
				}
				else
				{
					products = WriteExcelDemo.readXLS(productFile);
				}

				if (products != null)
				{
					List<String> portalList = new ArrayList<String>();
					for (Component comp : portalPanel.getComponents())
					{
						if (comp instanceof JCheckBox)
						{

							check = (JCheckBox) comp;
							if (check.isSelected())
							{
								portalList.add(check.getText());
							}
						}
					}

					int workerCount = 1;
					if (getSelection(group).getText().contains("4"))
					{
						workerCount = 2;
					}
					else if (getSelection(group).getText().contains("8"))
					{
						workerCount = 3;
					}

					urls = new String[portalList.size()];
					urls = portalList.toArray(urls);
					actorList = getActors(urls, workerCount);
					Config config = ConfigFactory.load();
					ActorSystem system = ActorSystem.create("TestSystem", config);
					ActorRef master = system.actorOf(Props.create(Master.class), "master");
					master.tell(new Start(), master);
				}
				else
				{
					try
					{
						throw new Exception("NO PRODUCTS FOUND FOR SEARCH!!!");
					}
					catch (Exception e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

	}

}
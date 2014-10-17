package org.akka.actors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	private static JCheckBox check = null;
	private static JPanel panel = null;
	private static File productFile = null;
	private static final JCheckBox[] checkBoxes = {new JCheckBox("FK"),new JCheckBox("SD"),new JCheckBox("JB"),new JCheckBox("AZ")} ;

	private ActorRef			downloaderRouterAZ	= null;
	private ActorRef			downloaderRouterFK	= null;
	private ActorRef			downloaderRouterSD	= null;
	private ActorRef			downloaderRouterJB	= null;
	//String[]					products = { "moto g", "moto x", "moto e", "lg g3", "sony xperia z3", "xiomi mi3" };
	private static String[]					urls = {"http://www.flipkart.com/", "http://www.jabong.com/"};
	//private static String[]					products = {"BURBERRY TOUCH EDP 100ML","NINA RICCI NINA EDT 80ML","NINA RICCI NINA 50ML edt","Ricci Ricci by Nina Ricci Edp 50 ml. Women"};
	//private static String[]					products = {"NINA RICCI","BURBERRY TOUCH","Carolina Herrera","Azzaro Visit"};
	//private static String[]					products = {"canon 1200D"};
	String[] products = WriteExcelDemo.readXLS(productFile);
	private long startTime;
	private long endTime;
	

	public Master(ActorRef downloaderRouterFK,ActorRef downloaderRouterSD,ActorRef downloaderRouterJB)
	{
		this.downloaderRouterFK = downloaderRouterFK;
		this.downloaderRouterJB = downloaderRouterJB;
		this.downloaderRouterSD = downloaderRouterSD;
		//this.downloaderRouterAZ = downloaderRouterAZ;
		
	}
	
	public Master(ActorRef downloaderRouterSD/*ActorRef downloaderRouterAZ,ActorRef downloaderRouterSD*/)
	{
		//this.downloaderRouterFK = downloaderRouterFK;
		//this.downloaderRouterJB = downloaderRouterJB;
		//this.downloaderRouterAZ = downloaderRouterAZ;
		this.downloaderRouterSD = downloaderRouterSD;
	}
	

	private void start()
	{
		for (String product : products)
		{
			downloaderRouterFK.tell(new Download(product), getSelf());
			downloaderRouterJB.tell(new Download(product), getSelf());
			downloaderRouterSD.tell(new Download(product), getSelf());
		}
	}
	
	private void shutdown()
	{
		getContext().system().shutdown();
	}

	public static void main(String[] args)
	{

		init();
		//Monitor mon = MonitorFactory.start("This is gaurav Test!!");
		/*
		int downloadWorkersCount = 2;//products.length;

		Config config = ConfigFactory.load();
		ActorSystem system = ActorSystem.create("TestSystem", config);

		//ActorRef downloaderRouterAZ = system.actorOf(new RoundRobinPool(downloadWorkersCount).props(Props.create(AmazonProductPriceSearch.class)), "downloadRouterAZ");
		ActorRef downloaderRouterFK = system.actorOf(new RoundRobinPool(downloadWorkersCount).props(Props.create(FlipkartProductPriceSearch.class)), "downloadRouterFK");
		ActorRef downloaderRouterJB = system.actorOf(new RoundRobinPool(downloadWorkersCount).props(Props.create(JabongProductPriceSearch.class)), "downloadRouterJB");
		ActorRef downloaderRouterSD = system.actorOf(new RoundRobinPool(downloadWorkersCount).props(Props.create(SnapdealProductPriceSearch.class)), "downloadRouterSD");

		//ActorRef master = system.actorOf(Props.create(Master.class, downloaderRouterSD), "master");
		//ActorRef master = system.actorOf(Props.create(Master.class, downloaderRouterJB), "master");
		//ActorRef master = system.actorOf(Props.create(Master.class, downloaderRouterFK), "master");
		ActorRef master = system.actorOf(Props.create(Master.class, downloaderRouterFK,downloaderRouterJB, downloaderRouterSD), "master");
		master.tell(new Start(), master);
		 */
		//mon.stop();

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
			int cntr = ((ProcessingCompleted)message).getCount();
			System.out.println("Counter val :"+cntr);
			
			if(cntr==products.length*3)
			{
				Map<String, List<ProductDetails>>	allProductsPrices = ((ProcessingCompleted)message).getAllProductsPrices();
				System.out.println(allProductsPrices);
				//WriteExcelDemo excelDemo = new WriteExcelDemo();
				WriteExcelDemo.writeToXLS(allProductsPrices);
				shutdown();
				endTime = System.currentTimeMillis();
				System.out.println("Total time taken to extract data:"+ (endTime-startTime));
				System.out.println("\nTotal time taken : " + TimeUnit.MILLISECONDS.toMinutes(endTime-startTime) +" minutes");
			}
			
			
		}

	}
	
	
	private static void init()
	{
	    JFrame frame = new JFrame("BANG BANG");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    panel = new JPanel(new GridLayout(0, 1));
	    Border border = BorderFactory.createTitledBorder("Portal to Search");
	    panel.setBorder(border);
	    for(JCheckBox check: checkBoxes)
	    {
	    	panel.add(check);
	    }

	    JButton button = new JButton("Submit");
	    JButton btnOpen = new JButton("OpenFile");
	    final JLabel  fileLabel = new JLabel();
	    Container contentPane = frame.getContentPane();
	   
		JPanel buttons = new JPanel();
		BoxLayout layout2 = new BoxLayout(buttons, BoxLayout.Y_AXIS);
		buttons.setLayout(layout2);
		buttons.add(btnOpen);
		buttons.add(fileLabel);
	    btnOpen.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{

			    JFileChooser fileopen = new JFileChooser();
			    int ret = fileopen.showDialog(null, "Open file");

			    if (ret == JFileChooser.APPROVE_OPTION) {
			      productFile = fileopen.getSelectedFile();
			      fileLabel.setText("Product list from file "+productFile.getName()+ " Loaded for search !!");
			    }
				
			}
		});
	    
	    
	    JPanel allContent = new JPanel(new GridLayout(2, 1));
	    allContent.add(buttons);
	    allContent.add(panel);
	    
	    contentPane.add(allContent, BorderLayout.CENTER);
	    contentPane.add(button, BorderLayout.SOUTH);
	    
	    frame.setSize(300, 200);
	    frame.setVisible(true);
	    button.addActionListener(new ActionListener()
		{
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				for(Component comp : panel.getComponents())
				{
					if(comp instanceof JCheckBox)
					{
						check = (JCheckBox)comp;
						System.out.println(check.isSelected()?check.getText():"");
					}
				}
				
			}
		});
	  
	}


}
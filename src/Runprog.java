import java.util.concurrent.Executors;

import javax.swing.JFrame;

public class Runprog {
	private static JFrame mainFrame = new JFrame();
	
	public static void main(String[]args) throws Exception {
		
		System.out.println("Application is launching...");
		
        ProgressBar progressBar = new ProgressBar(mainFrame, true);
        progressBar.showProgressBar("Collecting data...");
        
        UI ui = new UI();
        Executors.newCachedThreadPool().submit(()->{
            try {
                ui.initUI();
            } 
            finally {
                progressBar.setVisible(false);
                mainFrame.dispose();
            }
        });

	}
	
	

}

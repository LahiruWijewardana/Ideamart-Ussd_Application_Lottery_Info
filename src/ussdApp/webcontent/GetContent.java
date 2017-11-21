package ussdApp.webcontent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class GetContent implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Hello World");
		
		Runnable command = new Runnable() {
			public void run() {
			      
				GetDLBWebContent getDLBWebContent = new GetDLBWebContent();
				try {
					getDLBWebContent.getWebContent();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				GetNLBWebContent getNLBWebContent = new GetNLBWebContent();
				try {
					getNLBWebContent.getWebContent();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		
		ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
		service.scheduleAtFixedRate(command, 0, 12, TimeUnit.HOURS);
	}

}

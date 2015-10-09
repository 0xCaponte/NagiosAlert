import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
 
public class NagiosContextListener
               implements ServletContextListener{
 
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("ServletContextListener destroyed");
	}
 
    //Esto se ejecuta antes que el servlet inicie
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		System.out.println("Iniciando Servlet e Hilo");
		Novedades obj = new Novedades("nagios");
		Thread tobj = new Thread(obj);
		tobj.start();
	}
}


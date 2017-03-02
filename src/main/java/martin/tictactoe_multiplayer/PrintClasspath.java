package martin.tictactoe_multiplayer;

import java.net.URL;
import java.net.URLClassLoader;

public class PrintClasspath {

	   public static void main (String args[]) {
		   System.out.println(PrintClasspath.class.getClassLoader().getResource("O.png"));
	        ClassLoader cl = ClassLoader.getSystemClassLoader();

	        URL[] urls = ((URLClassLoader)cl).getURLs();

	        for(URL url: urls){
	        	System.out.println(url.getFile());
	        }

	   }
}

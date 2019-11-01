package eu.cessda.cafe.waiter.resource;

import javax.servlet.http.HttpServlet;

import com.wordnik.swagger.config.ConfigFactory;
import com.wordnik.swagger.model.ApiInfo;

@SuppressWarnings("serial")
public class Bootstrap extends HttpServlet {
	
	static {
	        ApiInfo info = new ApiInfo(
	          "Swagger Sample App",                             /* title */
	          "This is a sample server Petstore server.  You can find out more about Swagger " + 
	          "at <a href=\"http://swagger.wordnik.com\">http://swagger.wordnik.com</a> or on irc.freenode.net, #swagger.  For this sample, " + 
	          "you can use the api key \"special-key\" to test the authorization filters", 
	          "http://helloreverb.com/terms/",                  /* TOS URL */
	          "apiteam@wordnik.com",                            /* Contact */
	          "Apache 2.0",                                     /* license */
	          "http://www.apache.org/licenses/LICENSE-2.0.html" /* license URL */
	        );
	        
	        ConfigFactory.config().setApiInfo(info);
	    }
	

}

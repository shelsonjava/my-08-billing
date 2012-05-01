package com.info08.billing.callcenterbk.server.ws;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.isomorphic.jpa.EMF;
import com.sun.jersey.spi.resource.Singleton;

@Produces("application/xml")
@Path("/customerservices")
@Singleton
public class InfoCenter08WS {

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes("application/xml")
	public String addCustomer(@PathParam("name") String name,
			@PathParam("lastName") String lastName) {
		EntityManager oracleManager = null;
		Object transaction = null;
		try {
			oracleManager = EMF.getEntityManager();
			System.out.println("oracleManager = " + oracleManager);
			if (oracleManager != null) {
				transaction = EMF.getTransaction(oracleManager);
				System.out.println("transaction = " + transaction);
			}
			EMF.commitTransaction(transaction);
			return "Hello " + name + " " + lastName;
		} catch (Exception e) {
			EMF.rollbackTransaction(transaction);
			e.printStackTrace();
			return "Exception";
		} finally {
			if (oracleManager != null) {
				EMF.returnEntityManager(oracleManager);
			}
		}
	}

	// This method is called if XML is request
	// @GET
	// @Produces(MediaType.TEXT_XML)
	// public String sayXMLHello() {
	// return "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey" + "</hello>";
	// }
	//
	// // This method is called if HTML is request
	// @GET
	// @Produces(MediaType.TEXT_HTML)
	// public String sayHtmlHello() {
	// return "<html> " + "<title>" + "Hello Jersey" + "</title>"
	// + "<body><h1>" + "Hello Jersey" + "</body></h1>" + "</html> ";
	// }
}

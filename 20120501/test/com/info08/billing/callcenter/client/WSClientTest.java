package com.info08.billing.callcenter.client;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.representation.Form;

public class WSClientTest {

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8888/").build();
	}

	public static void main(String[] args) {
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource(getBaseURI());
			// Fluent interfaces
			System.out.println(service.path("services")
					.path("customerservices").accept(MediaType.TEXT_PLAIN)
					.get(ClientResponse.class).toString());
			// Get plain text

			Form form = new Form();
			form.add("name", "Paata");
			form.add("lastName", "Lominadze");
			
			ClientResponse response = service.path("services")
					.path("customerservices")
					.type(MediaType.APPLICATION_FORM_URLENCODED)
					.post(ClientResponse.class, form);
			System.out.println("Form response "
					+ response.getEntity(String.class));

			System.out.println(service.path("services")
					.path("customerservices").accept(MediaType.TEXT_PLAIN)
					.get(String.class));
			// // Get XML
			// System.out.println(service.path("services").path("customerservices")
			// .accept(MediaType.TEXT_XML).get(String.class));
			// // The HTML
			// System.out.println(service.path("services").path("customerservices")
			// .accept(MediaType.TEXT_HTML).get(String.class));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

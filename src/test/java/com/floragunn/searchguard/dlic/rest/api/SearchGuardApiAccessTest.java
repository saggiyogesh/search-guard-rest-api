package com.floragunn.searchguard.dlic.rest.api;

import org.apache.http.HttpStatus;
import org.apache.http.message.BasicHeader;
import org.junit.Assert;
import org.junit.Test;

public class SearchGuardApiAccessTest extends AbstractRestApiUnitTest {

	@Test
	public void testRestApi() throws Exception {

		setup();

		// test with no cert, must fail
		Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED,
				rh.executeGetRequest("_searchguard/api/configuration/internalusers").getStatusCode());
		Assert.assertEquals(HttpStatus.SC_FORBIDDEN,
				rh.executeGetRequest("_searchguard/api/configuration/internalusers",
						new BasicHeader("Authorization", "Basic " + encodeBasicHeader("admin", "admin")))
						.getStatusCode());

		// test with non-admin cert, must fail
		rh.keystore = "node-0-keystore.jks";
		rh.sendHTTPClientCertificate = true;
		Assert.assertEquals(HttpStatus.SC_UNAUTHORIZED,
				rh.executeGetRequest("_searchguard/api/configuration/internalusers").getStatusCode());
		Assert.assertEquals(HttpStatus.SC_FORBIDDEN,
				rh.executeGetRequest("_searchguard/api/configuration/internalusers",
						new BasicHeader("Authorization", "Basic " + encodeBasicHeader("admin", "admin")))
						.getStatusCode());

	}

}
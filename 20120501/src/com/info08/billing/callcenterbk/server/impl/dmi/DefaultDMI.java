package com.info08.billing.callcenterbk.server.impl.dmi;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;

public class DefaultDMI {

	public DSResponse add(DSRequest r) throws Exception {
		return execute(r);
	}

	protected DSResponse execute(DSRequest r) throws Exception {
		DSResponse res = r.execute();
		res.setInvalidateCache(true);
		return res;
	}

	public DSResponse update(DSRequest r) throws Exception {
		return execute(r);
	}

	public DSResponse remove(DSRequest r) throws Exception {
		return execute(r);
	}
}

package com.info08.billing.callcenterbk.server.impl.dmi;

import java.util.Map;
import java.util.TreeMap;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.util.DataTools;

public class DMIUtils {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Map findRecordById(String dsName, String operationId,
			long id, String idName) throws Exception {
		Map criteria = new TreeMap();
		criteria.put(idName, id);
		DSRequest request = new DSRequest(criteria);
		request.setOperationId(operationId);
		request.setDataSourceName(dsName);
		request.setCriteria(criteria);
		request.setOperationType("fetch");
		DSResponse resp = request.execute();
		Map result = resp.getDataMap();
		return result;

	}

	public static final void findRecordById(String dsName, String operationId,
			long id, String idName, Object bean) throws Exception {
		DataTools.setProperties(
				findRecordById(dsName, operationId, id, idName), bean);
	}
}

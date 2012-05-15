package com.info08.billing.callcenterbk.server.impl.dmi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.isomorphic.datasource.DSRequest;
import com.isomorphic.datasource.DSResponse;
import com.isomorphic.util.DataTools;

public class DMIUtils {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final List<Map> findRecordsByCriteria(String dsName,
			String operationId, Map<?, ?> criteria) throws Exception {

		DSRequest request = new DSRequest(criteria);
		request.setOperationId(operationId);
		request.setDataSourceName(dsName);
		request.setCriteria(criteria);
		request.setOperationType("fetch");
		DSResponse resp = request.execute();
		List<Map> result = resp.getDataList();
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static final Map<?, ?> findRecordById(String dsName,
			String operationId, long id, String idName) throws Exception {
		Map criteria = new TreeMap();
		criteria.put(idName, id);
		List<Map> list = findRecordsByCriteria(dsName, operationId, criteria);
		Map result = new TreeMap();
		if (list != null && !list.isEmpty())
			result = list.get(0);
		return result;

	}

	public static final void findRecordById(String dsName, String operationId,
			long id, String idName, Object bean) throws Exception {
		DataTools.setProperties(
				findRecordById(dsName, operationId, id, idName), bean);
	}

	@SuppressWarnings("rawtypes")
	public static final <D> List<D> findObjectsdByCriteria(String dsName,
			String operationId, Map<?, ?> criteria, Class<D> clazz)
			throws Exception {
		List<Map> list = findRecordsByCriteria(dsName, operationId, criteria);
		if (list == null)
			list = new ArrayList<Map>();

		List<D> result = new ArrayList<D>();
		for (Map map : list) {
			D o = clazz.newInstance();
			DataTools.setProperties(map, o);
			result.add(o);
		}
		return result;
	}

}

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

//	public DSResponse locateRecord(DSRequest r) throws Exception {
//		Map<?, ?> mp = r.getCriteria();
//		String dataSource = mp.get("DataSourceName").toString();
//		String operationId = mp.get("OperationId").toString();
//		String fieldName = mp.get("FieldName").toString();
//		Object fieldValue = mp.get("FieldValue");
//
//
//
//		DataSource ds = DataSource.forName(dataSource, new DSRequest());
//		Map<String, String> currentOperation = null;
//		List<Map> operations = ds.getOperationBindings();
//		for (Map map : operations) {
//			if (map.get("operationType").equals("fetch")
//					&& map.get("operationId").equals(operationId)) {
//				currentOperation = map;
//				break;
//			}
//		}
//		if (currentOperation == null)
//			return null;
//		String customSQL = currentOperation.get("customSQL");
//		String sql = null;
//		if (customSQL != null) {
//			sql = "select myRowIndexxxxxx from ( select tbllll." + fieldName
//					+ ", rownum myRowIndexxxxxx from ( " + customSQL
//					+ ") tbllll) tbllll11 where  " + fieldName + " = '"
//					+ fieldValue + "'";
//		} else {
//			String selectClause = fieldName + ", rownum myRowIndexxxxxx";
//			String tableClause = currentOperation.get("tableClause");
//			String groupClause = currentOperation.get("groupClause");
//			String orderClause = currentOperation.get("orderClause");
//			String whereClause = currentOperation.get("whereClause");
//
//			sql = "select myRowIndexxxxxx from ( select " + selectClause
//					+ " from " + tableClause
//					+ (whereClause != null ? (" where " + whereClause) : "")
//					+ (orderClause != null ? (" order by " + orderClause) : "")
//					+ (groupClause != null ? (" group by " + groupClause) : "")
//					+ " ) kkkkkkkkk where " + fieldName + " = '" + fieldValue
//					+ "'";
//		}
//
//		String xml = "<DataSource 	ID=\"AAAAAAAAAAAAAAAAAAAAAA\" 	serverType=\"sql\" dropExtraFields=\"false\">\n"
//				+ "<fields>\n"
//				+ "	<field name=\"myRowIndexxxxxx\"				type=\"integer\"		title=\"ID\"/>\n"
//				+ "</fields>\n"
//				+ "<operationBindings>\n"
//				+ "<binding operationType=\"fetch\" operationId=\"locateRecord\">\n"
//				+ "	            <customSQL>\n"
//				+ "       	 <![CDATA[\n"
//				+ sql
//				+ "       	 ]]>\n"
//				+ "       </customSQL>\n"
//				+ "</binding>\n"
//				+ "</operationBindings>\n" + "</DataSource>";
//		DataSource dsNew = DataSource.fromXML(xml);
//		DSRequest dsRequest = new DSRequest();
//		dsRequest.setOperationId("locateRecord");
//		dsRequest.setOperationType("fetch");
//		dsRequest.setDataSource(dsNew);
//		DSResponse resp = dsNew.executeFetch(dsRequest);
//		Map map=resp.getDataMap();
//		System.out.println(map);
//		return resp;
//	}
}

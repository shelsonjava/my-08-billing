package com.info08.billing.callcenterbk.server.common;

public interface QueryConstants {
	
	
	public static final String Q_GET_VIRTUAL_SESSION_ID = " select 'VIRT.'||ccare.seq_virtual_session_id.nextval as session_id from dual ";
	
	public static final String Q_GET_TEL_COMP_IND =" select count(1) from ccare.tel_comps_ind t where ? between t.st_ind and t.end_ind ";
	
	public static final String Q_GET_TEL_COMP_IND_BY_ID =" select count(1) from ccare.tel_comps_ind t where ? between t.st_ind and t.end_ind and t.tel_comp_id <> ? ";
	
	public static final String Q_GET_TEL_COMP_BILL_BY_DAY = 
			"select\n" +
					"      t.phone as phonea,\n" + 
					"     '11808' as phoneb,\n" + 
					"      ch.rec_date as charge_date,\n" + 
					"      to_char(ch.rec_date,'DD/MM/YYYY HH24:MI:SS') as charge_date1,\n" + 
					"      60 as duration,\n" + 
					"      ch.price/100 as rate,\n" + 
					"      ch.price/100 as price\n" + 
					"from ccare.log_sessions t, ccare.log_session_charges ch\n" + 
					"where\n" + 
					"     t.session_id  = ch.session_id and\n" + 
					"     trunc(t.start_date) = trunc(?) and\n" + 
					"     trunc(ch.rec_date) = trunc(?) and\n" + 
					"      exists (\n" + 
					"        select\n" + 
					"          to_number(decode(length(i.st_ind),7,'32'||i.st_ind,i.st_ind)),\n" + 
					"          to_number(decode(length(i.end_ind),7,'32'||i.st_ind,i.end_ind))\n" + 
					"        from ccare.tel_comps tc\n" + 
					"               inner join ccare.tel_comps_ind i on i.tel_comp_id = tc.tel_comp_id\n" + 
					"        where tc.tel_comp_id = ? \n" + 
					"              and to_number(t.phone) >= to_number(decode(length(i.st_ind),7,'32'||i.st_ind,i.st_ind))\n" + 
					"              and to_number(t.phone) <= to_number(decode(length(i.end_ind),7,'32'||i.end_ind,i.end_ind))\n" + 
					"      )\n" + 
					"      and ccare.getcontractorpricenew(t.phone)=-999999999\n" + 
					"      and t.phone like '322%'";
	
	
	public static final String Q_GET_TEL_COMP_BILL_BY_YM = "select substr(t.phone,3, length(t.phone)) as phonea,\n"
			+ "   '11808' as phoneb,\n"
			+ "    ch.rec_date as charge_date,\n"
			+ "    to_char(ch.rec_date,'DD/MM/YYYY HH24:MI:SS') as charge_date1,\n"
			+ "    60 as duration,\n"
			+ "    ch.price/100 as rate,\n"
			+ "    ch.price/100 as price\n"
			+ "from ccare.log_sessions t, ccare.log_session_charges ch\n"
			+ "where\n"
			+ "     t.session_id  = ch.session_id and\n"
			+ "     t.ym = ? and ch.ym = ? and\n"
			+ "      exists (\n"
			+ "        select\n"
			+ "          to_number(decode(length(i.st_ind),7,'32'||i.st_ind,i.st_ind)),\n"
			+ "          to_number(decode(length(i.end_ind),7,'32'||i.st_ind,i.end_ind))\n"
			+ "        from ccare.tel_comps tc\n"
			+ "               inner join ccare.tel_comps_ind i on i.tel_comp_id = tc.tel_comp_id\n"
			+ "        where tc.tel_comp_id = ? \n"
			+ "              and to_number(t.phone) >= to_number(decode(length(i.st_ind),7,'32'||i.st_ind,i.st_ind))\n"
			+ "              and to_number(t.phone) <= to_number(decode(length(i.end_ind),7,'32'||i.end_ind,i.end_ind))\n"
			+ "      )\n"
			+ "      and ccare.getcontractorpricenew(t.phone)=-999999999\n"
			+ "      and t.phone like '322%'";
	
	
	public static final String Q_GET_ORG_CALL_CNT_BY_YM = "select getCallsByMainAndYM(to_number(to_char(sysdate,'YYMM')),?) as depCallsCnt from dual";
	
	public static final String Q_GET_DEP_CALL_CNT_BY_YM = "select getCallsByMainDetAndYM(to_number(to_char(sysdate,'YYMM')),?) as depCallsCnt from dual";
	
	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY_NEW = "select distinct p.phone from (\n" +
				"       select ms.main_id from ccare.main_services ms\n" + 
				"       start with ms.main_id = ? and ms.service_id = 3\n" + 
				"       connect by prior ms.main_id = ms.main_master_id) r\n" + 
				"inner join ccare.abonents a on a.main_id = r.main_id\n" + 
				"inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
				"where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
				"    select p.phone from (\n" + 
				"    select\n" + 
				"      t.main_id\n" + 
				"    from ccare.main_services t\n" + 
				"    start with t.main_id in (\n" + 
				"          select\n" + 
				"               c.main_id\n" + 
				"          from ccare.contracts c\n" + 
				"          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in (\n" + 
				"                select\n" + 
				"                      t.main_id\n" + 
				"                from ccare.main_services t\n" + 
				"                where level>1\n" + 
				"          start with t.main_id = ? and t.service_id = 3\n" + 
				"          connect by prior t.main_id = t.main_master_id\n" + 
				"          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n" + 
				"    ) and t.service_id = 3\n" + 
				"    connect by prior t.main_id = t.main_master_id\n" + 
				"    ) a1\n" + 
				"    inner join ccare.abonents a on a.main_id = a1.main_id\n" + 
				"    inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
				"    where a.deleted = 0 and p.deleted = 0\n" + 
				") and p.phone not in (\n" + 
				"    select\n" + 
				"      /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
				"    from ccare.abonents a\n" + 
				"         inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
				"    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n" + 
				"          (select\n" + 
				"                 t.main_detail_id\n" + 
				"           from ccare.main_details t\n" + 
				"           start with t.main_detail_id in\n" + 
				"                 (select\n" + 
				"                        c.main_detail_id\n" + 
				"                  from ccare.contracts c\n" + 
				"                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in\n" + 
				"                        (select\n" + 
				"                               t.main_id\n" + 
				"                         from ccare.main_services t\n" + 
				"                         where level>1\n" + 
				"                         start with t.main_id = ? and t.service_id = 3\n" + 
				"                         connect by prior t.main_id = t.main_master_id\n" + 
				"                         )\n" + 
				"                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n" + 
				"                  ) and t.deleted = 0\n" + 
				"           connect by prior t.main_detail_id = t.main_detail_master_id)\n" + 
				")\n";

	
	
	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY_BLOCK_LIST = " select\n" +
			"   distinct p.phone\n" + 
			"from ccare.abonents a\n" + 
			"   inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
			"where a.main_id in (\n" + 
			"      select\n" + 
			"         t.main_id\n" + 
			"      from ccare.main_services t\n" + 
			"      start with t.main_id = ? and t.service_id = 3\n" + 
			"      connect by prior t.main_id = t.main_master_id\n" + 
			" )\n" + 
			" and p.deleted = 0 and a.deleted = 0 and p.phone in (select tt.phone\n" + 
			"                         from ccare.block_list_phones tt\n" + 
			"                        where tt.block_list_id = ? )";
	
	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY_EXCEPT_BLOCK_LIST = " select\n" +
			"   distinct p.phone\n" + 
			"from ccare.abonents a\n" + 
			"   inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
			"where a.main_id in (\n" + 
			"      select\n" + 
			"         t.main_id\n" + 
			"      from ccare.main_services t\n" + 
			"      start with t.main_id = ? and t.service_id = 3\n" + 
			"      connect by prior t.main_id = t.main_master_id\n" + 
			" )\n" + 
			" and p.deleted = 0 and a.deleted = 0 and p.phone not in (select tt.phone\n" + 
			"                         from ccare.block_list_phones tt\n" + 
			"                        where tt.block_list_id = ? )";
	
	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_EXCEPT_BLOCK_LIST = "select /*+ index(p PHN_PRY_KS001)*/ p.phone\n" +
					"  from ccare.abonents a\n" + 
					" inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					" where a.main_detail_id in\n" + 
					"       (select t.main_detail_id from ccare.main_details t\n" + 
					"        start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"        connect by prior t.main_detail_id = t.main_detail_master_id)\n" + 
					"   and p.deleted = 0 and a.deleted = 0\n" + 
					"   and p.phone not in (select tt.phone\n" + 
					"                         from ccare.block_list_phones tt\n" + 
					"                        where tt.block_list_id = ? )";
	
	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_BLOCK_LIST = "select /*+ index(p PHN_PRY_KS001)*/ p.phone\n" +
			"  from ccare.abonents a\n" + 
			" inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
			" where a.main_detail_id in\n" + 
			"       (select t.main_detail_id from ccare.main_details t\n" + 
			"        start with t.main_detail_id = ? and t.deleted = 0\n" + 
			"        connect by prior t.main_detail_id = t.main_detail_master_id)\n" + 
			"   and p.deleted = 0 and a.deleted = 0\n" + 
			"   and p.phone in (select tt.phone\n" + 
			"                         from ccare.block_list_phones tt\n" + 
			"                        where tt.block_list_id = ? )";
	
		
	public static final String Q_GET_BLOCK_LIST_PHONES = "select tt.phone from ccare.block_list_phones tt where tt.block_list_id = ? ";
	

	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY = " select\n" +
					"   distinct p.phone\n" + 
					"from ccare.abonents a\n" + 
					"   inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"where a.main_id in (\n" + 
					"      select\n" + 
					"         t.main_id\n" + 
					"      from ccare.main_services t\n" + 
					"      start with t.main_id = ? and t.service_id = 3\n" + 
					"      connect by prior t.main_id = t.main_master_id\n" + 
					" )\n" + 
					" and p.deleted = 0 and a.deleted = 0";

	
	
	
	

	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_NEW = "select /*+ index(p PHN_PRY_KS001)*/\n" +
					"\t distinct p.phone\n" + 
					"\tfrom ccare.abonents a\n" + 
					"\t     inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"\twhere a.main_detail_id in\n" + 
					"\t       (\n" + 
					"\t         select\n" + 
					"\t               t.main_detail_id\n" + 
					"\t         from ccare.main_details t\n" + 
					"           start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"           connect by prior t.main_detail_id = t.main_detail_master_id\n" +
					"			union "+
					"			select -999999999 from dual "+
					"         )\n" + 
					"  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
					"       select\n" + 
					"             /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
					"       from ccare.abonents a\n" + 
					"            inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n" + 
					"          select\n" + 
					"                c.main_detail_id\n" + 
					"          from ccare.contracts c\n" + 
					"          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n" + 
					"            (\n" + 
					"              select\n" + 
					"                    t.main_detail_id\n" + 
					"              from ccare.main_details t\n" + 
					"              where level>1\n" + 
					"              start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"              connect by prior t.main_detail_id = t.main_detail_master_id\n" +
					"			   union "+
					"			   select -999999999 from dual "+
					"            )\n" +
					"			union "+
					"			select -999999999 from dual "+
					"        )\n" + 
					"  )";
	
	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY = " select /*+ index(p PHN_PRY_KS001)*/ p.phone\n" +
					"from ccare.abonents a\n" + 
					"   inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"where a.main_detail_id in (\n" + 
					"      select\n" + 
					"          t.main_detail_id from ccare.main_details t\n" + 
					"      start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"      connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
					") and p.deleted = 0 and a.deleted = 0 ";
//	
//	public static final String Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID = " select /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1) from ccare.log_sessions ls\n" +
//					"       inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n" + 
//					"       inner join ccare.contracts c on c.contract_id = ? \n" + 
//					"where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n" + 
//					"      select /*+ index(p PHN_PRY_KS001)*/\n" + 
//					"         distinct '32'||p.phone as phone\n" + 
//					"      from ccare.abonents a\n" + 
//					"         inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
//					"      where a.main_detail_id in (\n" + 
//					"            select\n" + 
//					"                t.main_detail_id from ccare.main_details t\n" + 
//					"            start with t.main_detail_id = ((select c.main_detail_id from ccare.contracts c where c.contract_id = ? )) and t.deleted = 0\n" + 
//					"            connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
//					"      )\n" + 
//					")";
	
	

	public static final String Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID = "select\n" +
					"      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1)\n" + 
					"from ccare.log_sessions ls\n" + 
					"     inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n" + 
					"     inner join ccare.contracts c on c.contract_id = ? \n" + 
					"where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n" + 
					"     select /*+ index(p PHN_PRY_KS001)*/\n" + 
					"           distinct decode(length(p.phone),7,'32'||p.phone,p.phone) \n" + 
					"          from ccare.abonents a\n" + 
					"               inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"          where a.main_detail_id in\n" + 
					"                 (\n" + 
					"                   select\n" + 
					"                         t.main_detail_id\n" + 
					"                   from ccare.main_details t\n" + 
					"                     start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"                     connect by prior t.main_detail_id = t.main_detail_master_id\n" +
					"					union	"+
					"					select -999999999 from dual	"+
					"                   )\n" + 
					"            and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
					"                 select\n" + 
					"                       /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
					"                 from ccare.abonents a\n" + 
					"                      inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"                 where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n" + 
					"                    select\n" + 
					"                          c.main_detail_id\n" + 
					"                    from ccare.contracts c\n" + 
					"                    where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n" + 
					"                      (\n" + 
					"                        select\n" + 
					"                          t.main_detail_id\n" + 
					"			             from ccare.main_details t\n" + 
					"                        where level>1\n" + 
					"                        start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"                        connect by prior t.main_detail_id = t.main_detail_master_id\n" +
					"					     union	"+
					"					     select -999999999 from dual	"+
					"                      )\n" +
					"					   union	"+
					"					   select -999999999 from dual	"+
					"                   )\n" + 
					"                 )\n" + 
					")";

	public static final String Q_GET_CHARGES_BY_CONT_AND_MAIN_DET_ID = "select\n" +
					"      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ nvl(sum(ch.price)/100,0) as charges \n" + 
					"from ccare.log_sessions ls\n" + 
					"     inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n" + 
					"     inner join ccare.contracts c on c.contract_id = ? \n" + 
					"where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n" + 
					"     select /*+ index(p PHN_PRY_KS001)*/\n" + 
					"           distinct decode(length(p.phone),7,'32'||p.phone,p.phone) \n" + 
					"          from ccare.abonents a\n" + 
					"               inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"          where a.main_detail_id in\n" + 
					"                 (\n" + 
					"                   select\n" + 
					"                         t.main_detail_id\n" + 
					"                   from ccare.main_details t\n" + 
					"                     start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"                     connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
					"                   )\n" + 
					"            and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
					"                 select\n" + 
					"                       /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
					"                 from ccare.abonents a\n" + 
					"                      inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"                 where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n" + 
					"                    select\n" + 
					"                          c.main_detail_id\n" + 
					"                    from ccare.contracts c\n" + 
					"                    where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n" + 
					"                      (\n" + 
					"                        select\n" + 
					"      \t\t                    t.main_detail_id\n" + 
					"\t\t\t\t\t              from ccare.main_details t\n" + 
					"\t\t\t\t\t              where level>1\n" + 
					"\t\t\t\t\t              start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"\t\t\t\t\t              connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
					"\t\t\t\t\t            )\n" + 
					"\t\t\t\t\t        )\n" + 
					"\t\t\t\t\t  )\n" + 
					")";
	
	
//	public static final String Q_GET_CALL_CNT_BY_CONT_AND_MAIN_ID = " select /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1) from ccare.log_sessions ls\n" +
//					"       inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n" + 
//					"       inner join ccare.contracts c on c.contract_id = ?\n" + 
//					"where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n" + 
//					"      select\n" + 
//					"         distinct '32'||p.phone as phone\n" + 
//					"      from ccare.abonents a\n" + 
//					"         inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
//					"      where a.main_id in (\n" + 
//					"            select\n" + 
//					"               t.main_id\n" + 
//					"            from ccare.main_services t\n" + 
//					"            start with t.main_id =((select c.main_id from ccare.contracts c where c.contract_id = ?))  and t.service_id = 3\n" + 
//					"            connect by prior t.main_id = t.main_master_id\n" + 
//					"       )\n" + 
//					")";
	

	public static final String Q_GET_CALL_CNT_BY_CONT_AND_MAIN_ID =  "select\n" +
					"      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1)\n" + 
					"from ccare.log_sessions ls\n" + 
					"     inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n" + 
					"     inner join ccare.contracts c on c.contract_id = ? \n" + 
					"where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n" + 
					"     select distinct decode(length(p.phone),7,'32'||p.phone,p.phone) from (\n" + 
					"               select ms.main_id from ccare.main_services ms\n" + 
					"               start with ms.main_id = ? and ms.service_id = 3\n" + 
					"               connect by prior ms.main_id = ms.main_master_id) r\n" + 
					"        inner join ccare.abonents a on a.main_id = r.main_id\n" + 
					"        inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"        where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
					"            select p.phone from (\n" + 
					"            select\n" + 
					"              t.main_id\n" + 
					"            from ccare.main_services t\n" + 
					"            start with t.main_id in (\n" + 
					"                  select\n" + 
					"                       c.main_id\n" + 
					"                  from ccare.contracts c\n" + 
					"                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in (\n" + 
					"                        select\n" + 
					"                              t.main_id\n" + 
					"                        from ccare.main_services t\n" + 
					"                        where level>1\n" + 
					"                  start with t.main_id = ? and t.service_id = 3\n" + 
					"                  connect by prior t.main_id = t.main_master_id\n" + 
					"                  ) and (c.main_detail_id is null or c.main_detail_id = 0)\n" + 
					"            ) and t.service_id = 3\n" + 
					"            connect by prior t.main_id = t.main_master_id\n" + 
					"            ) a1\n" + 
					"            inner join ccare.abonents a on a.main_id = a1.main_id\n" + 
					"            inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"            where a.deleted = 0 and p.deleted = 0\n" + 
					"        ) and p.phone not in (\n" + 
					"            select\n" + 
					"              /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
					"            from ccare.abonents a\n" + 
					"                 inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"            where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n" + 
					"                  (select\n" + 
					"                         t.main_detail_id\n" + 
					"                   from ccare.main_details t\n" + 
					"                   start with t.main_detail_id in\n" + 
					"                         (select\n" + 
					"                                c.main_detail_id\n" + 
					"                          from ccare.contracts c\n" + 
					"                          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in\n" + 
					"                                (select\n" + 
					"                                       t.main_id\n" + 
					"                                 from ccare.main_services t\n" + 
					"                                 where level>1\n" + 
					"                                 start with t.main_id = ? and t.service_id = 3\n" + 
					"\t\t\t\t                         connect by prior t.main_id = t.main_master_id\n" + 
					"\t\t\t\t                         )\n" + 
					"\t\t\t\t                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n" + 
					"\t\t\t\t                  ) and t.deleted = 0\n" + 
					"\t\t\t\t           connect by prior t.main_detail_id = t.main_detail_master_id)\n" + 
					"\t\t\t\t)\n" + 
					")";

	
	public static final String Q_GET_CHARGES_BY_CONT_AND_MAIN_ID =  "select\n" +
					"      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ nvl(sum(ch.price)/100,0) as charges \n" + 
					"from ccare.log_sessions ls\n" + 
					"     inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n" + 
					"     inner join ccare.contracts c on c.contract_id = ? \n" + 
					"where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n" + 
					"     select distinct decode(length(p.phone),7,'32'||p.phone,p.phone) from (\n" + 
					"               select ms.main_id from ccare.main_services ms\n" + 
					"               start with ms.main_id = ? and ms.service_id = 3\n" + 
					"               connect by prior ms.main_id = ms.main_master_id) r\n" + 
					"        inner join ccare.abonents a on a.main_id = r.main_id\n" + 
					"        inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"        where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
					"            select p.phone from (\n" + 
					"            select\n" + 
					"              t.main_id\n" + 
					"            from ccare.main_services t\n" + 
					"            start with t.main_id in (\n" + 
					"                  select\n" + 
					"                       c.main_id\n" + 
					"                  from ccare.contracts c\n" + 
					"                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in (\n" + 
					"                        select\n" + 
					"                              t.main_id\n" + 
					"                        from ccare.main_services t\n" + 
					"                        where level>1\n" + 
					"                  start with t.main_id = ? and t.service_id = 3\n" + 
					"                  connect by prior t.main_id = t.main_master_id\n" + 
					"                  ) and (c.main_detail_id is null or c.main_detail_id = 0)\n" + 
					"            ) and t.service_id = 3\n" + 
					"            connect by prior t.main_id = t.main_master_id\n" + 
					"            ) a1\n" + 
					"            inner join ccare.abonents a on a.main_id = a1.main_id\n" + 
					"            inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"            where a.deleted = 0 and p.deleted = 0\n" + 
					"        ) and p.phone not in (\n" + 
					"            select\n" + 
					"              /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
					"            from ccare.abonents a\n" + 
					"                 inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"            where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n" + 
					"                  (select\n" + 
					"                         t.main_detail_id\n" + 
					"                   from ccare.main_details t\n" + 
					"                   start with t.main_detail_id in\n" + 
					"                         (select\n" + 
					"                                c.main_detail_id\n" + 
					"                          from ccare.contracts c\n" + 
					"                          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in\n" + 
					"                                (select\n" + 
					"                                       t.main_id\n" + 
					"                                 from ccare.main_services t\n" + 
					"                                 where level>1\n" + 
					"                                 start with t.main_id = ? and t.service_id = 3\n" + 
					"\t\t\t\t                         connect by prior t.main_id = t.main_master_id\n" + 
					"\t\t\t\t                         )\n" + 
					"\t\t\t\t                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n" + 
					"\t\t\t\t                  ) and t.deleted = 0\n" + 
					"\t\t\t\t           connect by prior t.main_detail_id = t.main_detail_master_id)\n" + 
					"\t\t\t\t)\n" + 
					")";
	
	
	
	public static final String Q_MYSQL_DELETE_BLOCK_PHONE = " delete from asteriskcdrdb.block where code like CONCAT('%', ?) ";
	public static final String Q_MYSQL_INSERT_BLOCK_PHONE = " insert into asteriskcdrdb.block (code,proriti,len) values (?, ?, ?) ";
	

	public static final String Q_CHECK_PHONE_MAIN_ORG =  "select count(1) from ccare.main_services ms\n" +
					"       inner join ccare.abonents a on a.main_id = ms.main_id\n" + 
					"       inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"where ms.main_id in\n" + 
					"      (select t.main_id from ccare.main_services t\n" + 
					"       start with t.main_id = ? and t.service_id = 3 and t.deleted = 0\n" + 
					"       connect by prior t.main_id = t.main_master_id\n" + 
					"       )\n" + 
					"and p.deleted = 0 and a.deleted = 0 and ms.deleted = 0 and p.phone is not null and p.phone not like '8%'\n" + 
					"and length(p.phone)>6 and ms.service_id = 3 and ms.deleted = 0 and p.phone = ? ";
	

	public static final String Q_CHECK_PHONE_MAIN_ORG_DET =  "select p.phone from ccare.phones p\n" +
					"       inner join ccare.abonents a on a.phone_id = p.phone_id\n" + 
					"       inner join ccare.main_services ms on ms.main_id = a.main_id\n" + 
					"where a.main_detail_id in\n" + 
					"      (select t.main_detail_id from ccare.main_details t\n" + 
					"       start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"       connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
					"       )\n" + 
					"and p.deleted = 0 and a.deleted = 0 and ms.service_id = 3 and ms.deleted = 0 and p.phone = ? ";
	
	
	
//	public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST = "select distinct t.phone from ccare.phones t\n" +
//					"inner join ccare.abonents a on a.phone_id = t.phone_id\n" + 
//					"inner join ccare.main_services ms on ms.main_id = a.main_id\n" + 
//					"where t.deleted = 0 and a.deleted = 0 and ms.service_id = 3 and a.main_id = ? and ms.main_id = ?\n" + 
//					"  and a.main_detail_id = ? and t.phone is not null and length(t.phone)>6 and t.phone not in (\n" + 
//					"      select tt.phone from ccare.contractor_phones tt\n" + 
//					"             inner join ccare.contracts c on c.contract_id = tt.contract_id\n" + 
//					"      where tt.contract_id = ? and tt.deleted = 0 and c.main_id = a.main_id and c.main_detail_id = a.main_detail_id and c.deleted = 0\n" + 
//					")";
	
	

	public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST = "select\n" +
					"      /*+ index(p PHN_PRY_KS001)*/ distinct p.phone\n" + 
					"from ccare.abonents a\n" + 
					"     inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"where a.main_detail_id in\n" + 
					"       (\n" + 
					"         select\n" + 
					"               t.main_detail_id\n" + 
					"         from ccare.main_details t\n" + 
					"           start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"           connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
					"         )\n" + 
					"  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
					"       select\n" + 
					"             /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
					"       from ccare.abonents a\n" + 
					"            inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n" + 
					"          select\n" + 
					"                c.main_detail_id\n" + 
					"          from ccare.contracts c\n" + 
					"          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n" + 
					"            (\n" + 
					"              select\n" + 
					"                    t.main_detail_id\n" + 
					"              from ccare.main_details t\n" + 
					"              where level>1\n" + 
					"              start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"              connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
					"            )\n" + 
					"        )\n" + 
					"  )\n" + 
					"  and p.phone not in (\n" + 
					"      select tt.phone from ccare.contractor_phones tt where tt.contract_id = ? and tt.deleted = 0 " +
					"  )\n";

	
	public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST = "select\n" +
					"      /*+ index(p PHN_PRY_KS001)*/ distinct p.phone\n" + 
					"from ccare.abonents a\n" + 
					"     inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"where a.main_detail_id in\n" + 
					"       (\n" + 
					"         select\n" + 
					"               t.main_detail_id\n" + 
					"         from ccare.main_details t\n" + 
					"           start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"           connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
					"         )\n" + 
					"  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
					"       select\n" + 
					"             /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
					"       from ccare.abonents a\n" + 
					"            inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n" + 
					"          select\n" + 
					"                c.main_detail_id\n" + 
					"          from ccare.contracts c\n" + 
					"          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n" + 
					"            (\n" + 
					"              select\n" + 
					"                    t.main_detail_id\n" + 
					"              from ccare.main_details t\n" + 
					"              where level>1\n" + 
					"              start with t.main_detail_id = ? and t.deleted = 0\n" + 
					"              connect by prior t.main_detail_id = t.main_detail_master_id\n" + 
					"            )\n" + 
					"        )\n" + 
					"  )\n" + 
					"  and p.phone in (\n" + 
					"      select tt.phone from ccare.contractor_phones tt where tt.contract_id = ? and tt.deleted = 0 " +
					"  )\n";
	
	
//	public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST = "select distinct t.phone from ccare.phones t\n" +
//			"inner join ccare.abonents a on a.phone_id = t.phone_id\n" + 
//			"inner join ccare.main_services ms on ms.main_id = a.main_id\n" + 
//			"where t.deleted = 0 and a.deleted = 0 and ms.service_id = 3 and a.main_id = ? and ms.main_id = ?\n" + 
//			"  and a.main_detail_id = ? and t.phone is not null and length(t.phone)>6 and t.phone in (\n" + 
//			"      select tt.phone from ccare.contractor_phones tt\n" + 
//			"             inner join ccare.contracts c on c.contract_id = tt.contract_id\n" + 
//			"      where tt.contract_id = ? and tt.deleted = 0 and c.main_id = a.main_id and c.main_detail_id = a.main_detail_id and c.deleted = 0\n" + 
//			")";
	
	

//	public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST1 = "select distinct p.phone from ccare.main_services ms\n" +
//			"       inner join ccare.abonents a on a.main_id = ms.main_id\n" + 
//			"       inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
//			"where ms.main_id in\n" + 
//			"      (select t.main_id from ccare.main_services t\n" + 
//			"       start with t.main_id = ? and t.service_id = 3 and t.deleted = 0\n" + 
//			"       connect by prior t.main_id = t.main_master_id\n" + 
//			"       )\n" + 
//			"and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n" + 
//			"and length(p.phone)>6 and ms.service_id = 3 and ms.deleted = 0 and p.phone not in (\n" + 
//			"      select tt.phone from ccare.contractor_phones tt\n" + 
//			"             inner join ccare.contracts c on c.contract_id = tt.contract_id\n" + 
//			"      where tt.contract_id = ? and tt.deleted = 0 and c.main_id = a.main_id and c.deleted = 0\n" + 
//			")";
	
	
	

	public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST1 = "select distinct p.phone from (\n" +
					"       select ms.main_id from ccare.main_services ms\n" + 
					"       start with ms.main_id = ? and ms.service_id = 3\n" + 
					"       connect by prior ms.main_id = ms.main_master_id) r\n" + 
					"inner join ccare.abonents a on a.main_id = r.main_id\n" + 
					"inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
					"    select p.phone from (\n" + 
					"    select\n" + 
					"      t.main_id\n" + 
					"    from ccare.main_services t\n" + 
					"    start with t.main_id in (\n" + 
					"          select\n" + 
					"               c.main_id\n" + 
					"          from ccare.contracts c\n" + 
					"          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in (\n" + 
					"                select\n" + 
					"                      t.main_id\n" + 
					"                from ccare.main_services t\n" + 
					"                where level>1\n" + 
					"          start with t.main_id = ? and t.service_id = 3\n" + 
					"          connect by prior t.main_id = t.main_master_id\n" + 
					"          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n" + 
					"    ) and t.service_id = 3\n" + 
					"    connect by prior t.main_id = t.main_master_id\n" + 
					"    ) a1\n" + 
					"    inner join ccare.abonents a on a.main_id = a1.main_id\n" + 
					"    inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"    where a.deleted = 0 and p.deleted = 0\n" + 
					") and p.phone not in (\n" + 
					"    select\n" + 
					"      /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
					"    from ccare.abonents a\n" + 
					"         inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n" + 
					"          (select\n" + 
					"                 t.main_detail_id\n" + 
					"           from ccare.main_details t\n" + 
					"           start with t.main_detail_id in\n" + 
					"                 (select\n" + 
					"                        c.main_detail_id\n" + 
					"                  from ccare.contracts c\n" + 
					"                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in\n" + 
					"                        (select\n" + 
					"                               t.main_id\n" + 
					"                         from ccare.main_services t\n" + 
					"                         where level>1\n" + 
					"                         start with t.main_id = ? and t.service_id = 3\n" + 
					"                         connect by prior t.main_id = t.main_master_id\n" + 
					"                         )\n" + 
					"                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n" + 
					"                  ) and t.deleted = 0\n" + 
					"           connect by prior t.main_detail_id = t.main_detail_master_id)\n" + 
					") \n" + 
					" and p.phone not in (\n" + 
					"      select tt.phone from ccare.contractor_phones tt where tt.contract_id = ? and tt.deleted = 0 " +
					"  )\n";

	
	public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST1 = "select distinct p.phone from (\n" +
			"       select ms.main_id from ccare.main_services ms\n" + 
			"       start with ms.main_id = ? and ms.service_id = 3\n" + 
			"       connect by prior ms.main_id = ms.main_master_id) r\n" + 
			"inner join ccare.abonents a on a.main_id = r.main_id\n" + 
			"inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
			"where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n" + 
			"    select p.phone from (\n" + 
			"    select\n" + 
			"      t.main_id\n" + 
			"    from ccare.main_services t\n" + 
			"    start with t.main_id in (\n" + 
			"          select\n" + 
			"               c.main_id\n" + 
			"          from ccare.contracts c\n" + 
			"          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in (\n" + 
			"                select\n" + 
			"                      t.main_id\n" + 
			"                from ccare.main_services t\n" + 
			"                where level>1\n" + 
			"          start with t.main_id = ? and t.service_id = 3\n" + 
			"          connect by prior t.main_id = t.main_master_id\n" + 
			"          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n" + 
			"    ) and t.service_id = 3\n" + 
			"    connect by prior t.main_id = t.main_master_id\n" + 
			"    ) a1\n" + 
			"    inner join ccare.abonents a on a.main_id = a1.main_id\n" + 
			"    inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
			"    where a.deleted = 0 and p.deleted = 0\n" + 
			") and p.phone not in (\n" + 
			"    select\n" + 
			"      /*+ index(p PHN_PRY_KS001)*/ p.phone\n" + 
			"    from ccare.abonents a\n" + 
			"         inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
			"    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n" + 
			"          (select\n" + 
			"                 t.main_detail_id\n" + 
			"           from ccare.main_details t\n" + 
			"           start with t.main_detail_id in\n" + 
			"                 (select\n" + 
			"                        c.main_detail_id\n" + 
			"                  from ccare.contracts c\n" + 
			"                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_id in\n" + 
			"                        (select\n" + 
			"                               t.main_id\n" + 
			"                         from ccare.main_services t\n" + 
			"                         where level>1\n" + 
			"                         start with t.main_id = ? and t.service_id = 3\n" + 
			"                         connect by prior t.main_id = t.main_master_id\n" + 
			"                         )\n" + 
			"                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n" + 
			"                  ) and t.deleted = 0\n" + 
			"           connect by prior t.main_detail_id = t.main_detail_master_id)\n" + 
			")\n" + 
			" and p.phone in (\n" + 
			"      select tt.phone from ccare.contractor_phones tt where tt.contract_id = ? and tt.deleted = 0 " +
			"  )\n";

	
//	public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST1 = "select distinct p.phone from ccare.main_services ms\n" +
//			"       inner join ccare.abonents a on a.main_id = ms.main_id\n" + 
//			"       inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
//			"where ms.main_id in\n" + 
//			"      (select t.main_id from ccare.main_services t\n" + 
//			"       start with t.main_id = ? and t.service_id = 3 and t.deleted = 0\n" + 
//			"       connect by prior t.main_id = t.main_master_id\n" + 
//			"       )\n" + 
//			"and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n" + 
//			"and length(p.phone)>6 and ms.service_id = 3 and ms.deleted = 0 and p.phone in (\n" + 
//			"      select tt.phone from ccare.contractor_phones tt\n" + 
//			"             inner join ccare.contracts c on c.contract_id = tt.contract_id\n" + 
//			"      where tt.contract_id = ? and tt.deleted = 0 and c.main_id = a.main_id and c.deleted = 0\n" + 
//			")";
	

	
	public static final String Q_GET_PHONE_LIST_BY_MAIN_ID = "select distinct p.phone from ccare.main_services ms\n" +
					"       inner join ccare.abonents a on a.main_id = ms.main_id\n" + 
					"       inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"where ms.main_id in\n" + 
					"      (select t.main_id from ccare.main_services t\n" + 
					"       start with t.main_id = ? and t.service_id = 3 and t.deleted = 0\n" + 
					"       connect by prior t.main_id = t.main_master_id\n" + 
					"       )\n" + 
					"       and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n" + 
					"       and length(p.phone)>6\n" + 
					"       and ms.service_id = 3 and ms.deleted = 0";


	public static final String Q_GET_PHONE_LIST_BY_MAIN_ID1111 = "select\n" +
					"  distinct p.phone\n" + 
					"from ccare.main_orgs mo\n" + 
					"inner join ccare.main_services ms on ms.main_id = mo.main_id\n" + 
					"inner join ccare.main_details md on md.main_id = mo.main_id\n" + 
					"inner join ccare.abonents a on a.main_detail_id = md.main_detail_id\n" + 
					"inner join ccare.phones p on p.phone_id = a.phone_id\n" + 
					"where mo.main_id in\n" + 
					"      (select\n" + 
					"         t.main_id\n" + 
					"       from ccare.main_services t\n" + 
					"       start with t.main_id = ? and t.service_id = 3 and t.deleted = 0\n" + 
					"       connect by prior t.main_id = t.main_master_id\n" + 
					"      )\n" + 
					"      and md.main_detail_type_id in (5, 30, 59, 60, 61)\n" + 
					"      and p.deleted = 0 and a.deleted = 0 and md.deleted = 0 and p.phone is not null and p.phone not like '8%'\n" + 
					"      and length(p.phone)>4\n" + 
					"      and ms.service_id = 3 and ms.deleted = 0";

	
	public static final String Q_DELETE_CONTRACT_PRICES = "delete from ccare.contract_price_items t where t.contract_id = ? ";
	public static final String Q_DELETE_CONTRACT_PHONES = "delete from ccare.contractor_phones t where t.contract_id = ? ";
	public static final String Q_DELETE_BLOCKLIST_PHONES = "delete from ccare.block_list_phones t where t.block_list_id = ? ";
	public static final String Q_DELETE_TELCOMP = "delete from ccare.tel_comps t where t.tel_comp_id = ? ";
	public static final String Q_DELETE_TELCOMP_IND = "delete from ccare.tel_comps_ind t where t.tel_comp_id = ? ";
	
	public static final String Q_REMOVE_PHONE_FROM_AST_DB = " delete from asteriskcdrdb.block where code = ? and proriti = ? and len = ? "; 
	public static final String Q_ADD_PHONE_INTO_AST_DB = " insert into asteriskcdrdb.block (code,proriti,len) values (?, ?, ?) ";
	
	
	public static final String Q_GET_CONTRACTOR_ADV_PRICE = " select sum(nvl(t.price,0)) as advPrice from ccare.contract_price_items t where t.contract_id = ? and t.call_count_start<=? and t.call_count_end > ? ";

	
	public static final String Q_GET_CONTRACTOR_INFO = "select distinct c.contract_id,\n" +
					"       ccare.date_to_msec(c.start_date) as start_date,\n" + 
					"       ccare.date_to_msec(c.end_date) as end_date,\n" + 
					"       c.critical_number,\n" + 
					"       c.is_budget,\n" + 
					"       c.price_type,\n" + 
					"       c.price,\n" + 
					"       c.block, \n" + 
					"       c.main_id,\n" + 
					"       c.main_detail_id \n" + 
					"  from ccare.phones t\n" + 
					" inner join ccare.abonents a on a.phone_id = t.phone_id\n" + 
					" inner join ccare.contracts c on c.main_id = a.main_id\n" + 
					" where t.phone = ? and a.deleted = 0 and c.deleted = 0 ";	

	public static final String Q_GET_CALL_CENTER_REQ_MSG = "select t.text from ccare.call_center_req_msg t where sysdate between t.start_date and t.end_date";

	
	public static final String Q_GET_DISCOVERY_RECORD = "select tt.discover_type,\n" +
					"       r.response_type,\n" + 
					"       t.discover_id,\n" + 
					"       t.call_id,\n" + 
					"       t.phone,\n" + 
					"       t.discover_txt,\n" + 
					"       t.contact_phone,\n" + 
					"       t.discover_type_id,\n" + 
					"       t.rec_date,\n" + 
					"       t.rec_user,\n" + 
					"       t.deleted,\n" + 
					"       t.upd_date,\n" + 
					"       t.upd_user,\n" + 
					"       t.response_type_id,\n" + 
					"       t.contact_person,\n" + 
					"       t.execution_status,\n" + 
					"       t.ccr,\n" + 
					"       t.iscorrect,\n" + 
					"       t.is_locked,\n" + 
					"       ss.start_date,\n" + 
					"       p.personnel_id as personnel_id, \n" +
					"       p1.personnel_id as personnel_id1 \n" +
					"  from discover             t,\n" + 
					"       discover_types       tt,\n" + 
					"       ccare.discover_rtypes r,\n" + 
					"       log_sessions         ss,\n" + 
					"       personnel            p,\n" + 
					"       personnel            p1\n" + 
					" where t.discover_type_id = tt.discover_type_id(+)\n" + 
					"   and t.response_type_id = r.response_type_id(+)\n" + 
					"   and t.call_id = ss.session_id(+)\n" + 
					"   and t.rec_user = p.user_name\n" + 
					"   and t.discover_type_id <> 4\n" + 
					"   and t.upd_user = p1.user_name(+)\n" + 
					"   and t.discover_id = ? ";
	
	public static final String Q_GET_OPERATOR_REMARKS = " select count(1)\n" +
					"  from ccare.log_personell_notes t\n" + 
					" where t.ym = to_char(sysdate, 'YYmm')\n" + 
					"   and trunc(t.rec_date) >= trunc(sysdate - 10)\n" + 
					"   and t.visible_options = 0\n" + 
					"   and t.user_name = ? \n" + 
					"   and t.received = 0\n" + 
					" order by t.rec_user desc";

	
	public static final String Q_GET_DISC_SMS_FOR_SEND = " SELECT      t.discover_id,\n" +
			"            t.call_id,\n" + 
			"            case when t.phone like '5%' then t.phone else t.contact_phone end as phone\n" + 
			"       FROM discover t WHERE\n" + 
			"        t.discover_type_id <> 4 and trunc(t.rec_date) = trunc(sysdate)\n" + 
			"         and t.execution_status = 0\n" + 
			"         and ((trunc( mod( (sysdate-t.rec_date)*24, 24 ) )*60) + trunc( mod( (sysdate-t.rec_date)*24*60, 60 ) ))  > 15\n" +
			"		  and (t.phone like '5%' or t.contact_phone like '5%') "+
			"         and not exists (select tt.discovery_id from ccare.discovery_sms_hist tt\n" + 
			"                         where tt.discovery_id = t.discover_id and trunc(tt.rec_date) = trunc(sysdate)\n" + 
			"                         ) ";

	public static final String Q_GET_SPECIAL_TEXT_BY_NUMBER = " select t.note from call_special t where t.phone = ? ";
	public static final String Q_GET_NON_CHARGE_ABONENT = "select count(t.phone) from NON_CHARGE_PHONES t where t.phone = ? ";
	public static final String Q_GET_MOBITEL_NOTE = "select t.ccn_note as note from call_center_note t where t.ccn_id = 2 ";
	public static final String Q_GET_MOB_ABONENT_NAME = " select nm, sex from my_mobbase where phone = ? ";
	public static final String Q_GET_ORG_ABONENT = " select * from searchOrg where phone = ? ";
	public static final String Q_GET_WEB_SESSION_ID =" select (to_number(to_char(sysdate,'YYMM'))*1000000 + log_calls_seq.nextval) AS sessionID from dual ";

	public static final String Q_GET_DEP_LIST_BY_ORG = "select distinct * from (\n" +
			"SELECT md.main_detail_id,\n" + 
			"       md.main_detail_master_id,\n" + 
			"       md.deleted,\n" + 
			"       md.fields_order,\n" + 
			"       case\n" + 
			"         when main_detail_type_id = 5 then\n" + 
			"          md.main_detail_geo\n" + 
			"         when main_detail_type_id = 61 then\n" + 
			"          '...' || md.main_detail_geo\n" + 
			"         when main_detail_type_id = 60 then\n" + 
			"          '.........' || md.main_detail_geo\n" + 
			"         when main_detail_type_id = 59 then\n" + 
			"          '...............' || md.main_detail_geo\n" + 
			"         when main_detail_type_id = 30 then\n" + 
			"          '.....................' || md.main_detail_geo\n" + 
			"         else\n" + 
			"          md.main_detail_geo\n" + 
			"       end as main_detail_geo,\n" + 
			"       md.main_detail_eng,\n" + 
			"       md.main_detail_note_eng,\n" + 
			"       md.main_detail_note_geo,\n" + 
			"       md.main_detail_type_id,\n" + 
			"       md.main_id,\n" + 
			"       md.old_id,\n" + 
			"       md.rec_user,\n" + 
			"       md.rec_date,\n" + 
			"       md.upd_user,\n" + 
			"       md.upd_date,\n" + 
			"       getorgdeptphones(main_detail_id) as phones\n" + 
			"  FROM ccare.main_details md\n" + 
			" start with md.main_id = ? and md.deleted = 0 and md.main_detail_geo like ? \n" + 
			" connect by prior md.main_detail_master_id = md.main_detail_id\n" + 
			"union all\n" + 
			"SELECT md.main_detail_id,\n" + 
			"       md.main_detail_master_id,\n" + 
			"       md.deleted,\n" + 
			"       md.fields_order,\n" + 
			"       case\n" + 
			"         when main_detail_type_id = 5 then\n" + 
			"          md.main_detail_geo\n" + 
			"         when main_detail_type_id = 61 then\n" + 
			"          '...' || md.main_detail_geo\n" + 
			"         when main_detail_type_id = 60 then\n" + 
			"          '.........' || md.main_detail_geo\n" + 
			"         when main_detail_type_id = 59 then\n" + 
			"          '...............' || md.main_detail_geo\n" + 
			"         when main_detail_type_id = 30 then\n" + 
			"          '.....................' || md.main_detail_geo\n" + 
			"         else\n" + 
			"          md.main_detail_geo\n" + 
			"       end as main_detail_geo,\n" + 
			"       md.main_detail_eng,\n" + 
			"       md.main_detail_note_eng,\n" + 
			"       md.main_detail_note_geo,\n" + 
			"       md.main_detail_type_id,\n" + 
			"       md.main_id,\n" + 
			"       md.old_id,\n" + 
			"       md.rec_user,\n" + 
			"       md.rec_date,\n" + 
			"       md.upd_user,\n" + 
			"       md.upd_date,\n" + 
			"       getorgdeptphones(main_detail_id) as phones\n" + 
			"  FROM ccare.main_details md\n" + 
			" start with md.main_id = ? and md.deleted = 0 and md.main_detail_geo like ? \n" + 
			" connect by prior md.main_detail_id = md.main_detail_master_id\n" + 
			") tt\n" + 
			"order by tt.fields_order\n" + 
			"";


	public static final String Q_GET_DISCOVERY_LIST = "select\n" +
			"  tt.discover_type,\n" + 
			"  r.response_type,\n" + 
			"  t.discover_id,\n" + 
			"  t.call_id,\n" + 
			"  t.phone,\n" + 
			"  t.discover_txt,\n" + 
			"  t.contact_phone,\n" + 
			"  t.discover_type_id,\n" + 
			"  t.rec_date,\n" + 
			"  t.rec_user,\n" + 
			"  t.deleted,\n" + 
			"  t.upd_date,\n" + 
			"  t.upd_user,\n" + 
			"  t.response_type_id,\n" + 
			"  t.contact_person,\n" + 
			"  t.execution_status,\n" + 
			"  t.ccr,\n" + 
			"  t.iscorrect\n" + 
			"from ccare.discover t\n" + 
			"       inner join ccare.discover_types tt on tt.discover_type_id = t.discover_type_id\n" + 
			"       left join ccare.discover_rtypes r on r.response_type_id = t.response_type_id\n" + 
			"where t.discover_type_id <> 4 and t.execution_status <>0 and trunc(t.rec_date) = trunc(sysdate)\n" + 
			"";

	public static final String Q_GET_TRANSPORT_BY_ID = "select\n"
			+ "        getdaysdescription(t.days) as days_descr,\n"
			+ "        tt.transp_type_name_geo,\n"
			+ "        oc.city_name_geo||' '||o.transport_place_geo as transport_place_geo_out,\n"
			+ "        ic.city_name_geo||' '||i.transport_place_geo as transport_place_geo_in,\n"
			+ "        tc.transport_company_geo,\n"
			+ "        tp.transport_plane_geo\n"
			+ "\n"
			+ "from ccare.transports t, transp_types tt,transport_places o, cities oc,transport_places i, cities ic, transport_companies tc, transport_plane tp\n"
			+ "where\n"
			+ "          t.transp_type_id = tt.transp_type_id and\n"
			+ "          t.out_transport_place_id = o.transport_place_id and o.city_id = oc.city_id and\n"
			+ "          t.in_transport_place_id = i.transport_place_id and i.city_id = ic.city_id and\n"
			+ "          t.transport_company_id = tc.transport_company_id(+) and\n"
			+ "          t.transport_plane_id = tp.transport_plane_id  and\n"
			+ "          t.transport_id = ? \n" + "order by t.days";

	public static final String Q_GET_SESSION_BY_ID = "select * from ccare.log_sessions t where t.session_id = ? ";
	public static final String Q_GET_PERS_NOTE_BY_ID = "select t.note_id from ccare.log_personell_notes t where t.note_id = ? ";
	public static final String Q_GET_PERS_NOTE_BY_ID_ADV = "select t.note_id,\n"
			+ "       t.session_id,\n"
			+ "       r.user_name || ' ( ' || r.personnel_name || ' ' ||\n"
			+ "       r.personnel_surname || ' ) ' as receiver,\n"
			+ "       s.user_name || ' ( ' || s.personnel_name || ' ' ||\n"
			+ "       s.personnel_surname || ' ) ' as sender,\n"
			+ "       t.phone,\n"
			+ "       t.note,\n"
			+ "       t.rec_date,\n"
			+ "       decode(t.visible_options, 0, 'ღია', 'დაფარული') as visibility,\n"
			+ "       t.visible_options as visibilityInt,\n"
			+ "       decode(t.particular,\n"
			+ "              0,\n"
			+ "              'ჩვეულებრივი',\n"
			+ "              'მნიშვნელოვანი') as particular,\n"
			+ "       t.particular as particularInt\n"
			+ "\n"
			+ "  from log_personell_notes t, personnel r, personnel s\n"
			+ " where t.note_id = ? \n"
			+ "   and t.user_name = r.user_name(+)\n"
			+ "   and t.rec_user = s.user_name(+)";

	public static final String Q_GET_ALL_PERSONS = "select * from (\n"
			+ "select t.personnel_id,\n"
			+ "       t.personnel_name,\n"
			+ "       t.personnel_surname,\n"
			+ "       t.user_name,\n"
			+ "       to_number(substr(t.user_name,3,length(t.user_name))) as orderBy\n"
			+ "from personnel t\n" + "where substr(t.user_name,0,2) = 'op'\n"
			+ "      and t.deleted=0\n" + "order by orderBy)\n" + "union all\n"
			+ "select * from (\n" + "select t.personnel_id,\n"
			+ "       t.personnel_name,\n" + "       t.personnel_surname,\n"
			+ "       t.user_name,\n" + "       0\n" + "from personnel t\n"
			+ "where substr(t.user_name,0,2) <> 'op'\n"
			+ "      and t.deleted=0\n" + "order by t.user_name)\n" + "";

	public static final String Q_GET_LOG_SESSION = "select p.personnel_id,\n"
			+ "        s.session_id,\n"
			+ "        s.ym,\n"
			+ "        substr(s.user_name,3,length(s.user_name)) as user_name,\n"
			+ "        s.start_date,\n"
			+ "        s.phone,\n"
			+ "        s.duration,\n"
			+ "        decode(s.hungup,0,'აბონენტმა',1,'ოპერატორმა','უცნობია') as hangUp,\n"
			+ "        count(ch.session_id) as chargeCount,\n"
			+ "        s.session_quality,\n"
			+ "        decode(s.session_quality,0,'მოუსმენელი', 4,'ჩვეულებრივი',-1,'ძალიან ცუდი',1,'ძალიან კარგი',2,'კარგი',3,'ცუდი',5, 'მენეჯერის დახმარება','უცნობი') as session_quality_desc,\n"
			+ "        p.personnel_name||' '||p.personnel_surname as person_name\n"
			+ "from\n"
			+ "        log_sessions s,\n"
			+ "\t\t\t\tlog_session_charges ch,\n"
			+ "\t\t\t\tccare.personnel p\n"
			+ "where\n"
			+ "        s.session_id=ch.session_id(+) and\n"
			+ "        p.user_name = s.user_name and\n"
			+ "        s.session_id = ? \n"
			+ "\n"
			+ "group by\n"
			+ "        p.personnel_id,\n"
			+ "\t\t\t\ts.session_id,\n"
			+ "\t\t\t\ts.ym,\n"
			+ "\t\t\t\tsubstr(s.user_name,3,length(s.user_name)),\n"
			+ "\t\t\t\ts.start_date,\n"
			+ "\t\t\t\ts.phone,\n"
			+ "\t\t\t\ts.duration,\n"
			+ "\t\t\t\tdecode(s.hungup,0,'აბონენტმა',1,'ოპერატორმა','უცნობია'),\n"
			+ "\t\t\t\ts.session_quality,\n"
			+ "\t\t\t\tdecode(s.session_quality,0,'ხარისხიანი',-1,'უხარისხო','უცნობი'),\n"
			+ "\t\t\t\tp.personnel_name||' '||p.personnel_surname\n";

	public static final String Q_GET_ORGS_BY_PHONE = "select\n"
			+ "  t.main_id,\n"
			+ "  t.org_name,\n"
			+ "  t.note,\n"
			+ "  t.workinghours,\n"
			+ "  t.director,\n"
			+ "  t.identcode,\n"
			+ "  t.founded,\n"
			+ "  t.legaladdress,\n"
			+ "  t.mail,\n"
			+ "  t.webaddress,\n"
			+ "  t.org_info,\n"
			+ "  t.contactperson,\n"
			+ "  t.dayoffs,\n"
			+ "  t.statuse,\n"
			+ "  t.legal_statuse_id,\n"
			+ "  ls.legal_statuse,\n"
			+ "  t.partnerbank_id,\n"
			+ "  b.partnerbank,\n"
			+ "  t.workpersoncountity,\n"
			+ "  t.upd_user,\n"
			+ "  t.upd_date,\n"
			+ "  t.ind,\n"
			+ "  t.note_crit,\n"
			+ "  t.org_name_eng,\n"
			+ "  t.extra_priority,\n"
			+ "  t.new_identcode\n"
			+ "from ccare.main_orgs t\n"
			+ "     left join ccare.legal_statuses ls on ls.legal_statuse_id = t.legal_statuse_id\n"
			+ "     left join ccare.org_partnerbank_lists b on b.main_id = t.partnerbank_id\n"
			+ "where t.main_id in (\n"
			+ "select\n"
			+ "      distinct mo.main_id\n"
			+ "from ccare.phones t\n"
			+ "inner join ccare.abonent_to_phones ap on ap.phone_id = t.phone_id\n"
			+ "inner join ccare.abonents a on a.abonent_id = ap.abonent_id\n"
			+ "inner join ccare.main_services ms on ms.main_id = a.main_id\n"
			+ "inner join ccare.main_orgs mo on mo.main_id = a.main_id\n"
			+ "where t.phone = ? and ms.service_id = 3 )";

	public static final String Q_GET_FIRST_NAME_COUNT = " select count(1) from ccare.firstnames t where t.firstname = ? ";
	public static final String Q_GET_LAST_NAME_COUNT = " select count(1) from ccare.lastnames t where t.lastname = ? ";
	public static final String Q_GET_FIRST_NAME_COUNT_ALL = " select count(1) from ccare.firstnames t ";
	public static final String Q_GET_FIRST_NAMES_ALL = " select t.deleted,decode(t.deleted, 0, 'აქტიური', 'გაუქმებული') as deletedText,t.firstname, t.firstname_id,t.rec_date from ccare.firstnames t where t.deleted=0 order by t.firstname ";
	public static final String Q_GET_LAST_NAMES_ALL = " select t.deleted,decode(t.deleted, 0, 'აქტიური', 'გაუქმებული') as deletedText,t.lastname, t.lastname_id,t.rec_date from ccare.lastnames t where t.deleted=0 order by t.lastname ";
	public static final String Q_GET_PERS_TYPES_ALL = " select * from ccare.personnel_types t order by 1 ";
	public static final String Q_GET_PERS_TO_ACC = " select t.access_id from ccare.personnel_to_access t where t.personnel_id = ? ";

	public static final String Q_GET_ALL_CITY_REGIONS = "select t.city_region_id, t.city_id, t.city_region_name_geo, t.deleted\n"
			+ "  from city_regions t\n"
			+ " where t.deleted = 0\n"
			+ " order by 3";

	public static final String Q_GET_FIRST_NAME_BY_ID = "select t.firstname_Id,\n"
			+ "       t.firstname,\n"
			+ "       t.rec_date,\n"
			+ "       t.deleted,\n"
			+ "       decode(t.deleted, 0, 'აქტიური', 'გაუქმებული') as deletedText \n"
			+ "from ccare.firstnames t\n" + "where t.firstname_id = ? ";

	public static final String Q_GET_LAST_NAME_BY_ID = "select t.lastname_Id,\n"
			+ "       t.lastname,\n"
			+ "       t.rec_date,\n"
			+ "       t.deleted,\n"
			+ "       decode(t.deleted, 0, 'აქტიური', 'გაუქმებული') as deletedText \n"
			+ "from ccare.lastnames t\n" + "where t.lastname_id = ? ";

	public static final String Q_GET_ABONENT_BY_ID = "select t.abonent_id,\n"
			+ "       t.main_id,\n" + "       ma.address_id,\n"
			+ "       p.phone_id,\n" + "       f.firstname,\n"
			+ "       l.lastname,\n" + "       p.phone,\n"
			+ "       ps.phone_state,\n" + "       ps.phone_state_id,\n"
			+ "       t.upd_date,\n"
			+ "       c.city_name_geo         as city,\n"
			+ "       str.street_name_geo     as street,\n"
			+ "       t.upd_user,\n" + "       f.firstname_id,\n"
			+ "       l.lastname_id,\n"
			+ "       ap.is_hide               as abonent_hide,\n"
			+ "       ap.is_parallel           as phone_parallel,\n"
			+ "       ap.phone_status_id,\n" + "       p.phone_type_id,\n"
			+ "       c.city_id,\n" + "       str.street_id,\n"
			+ "       sd.city_region_id,\n" + "       ma.address_hide,\n"
			+ "       ma.address_suffix_geo,\n" + "       ma.addr_number,\n"
			+ "       ma.addr_block,\n" + "       ma.addr_appt,\n"
			+ "       ma.addr_descr,\n" + "       sd.street_district_id,\n"
			+ "       str.street_location_geo,\n" + "       t.deleted\n"
			+ "  from abonents        t,\n" + "       main_services   tt,\n"
			+ "       firstnames      f,\n" + "       lastnames       l,\n"
			+ "       phones          p,\n" + "       abonent_to_phones ap,\n"
			+ "       phone_states    ps,\n" + "       main_address    ma,\n"
			+ "       streets         str,\n" + "       cities          c,\n"
			+ "       street_district sd\n" + " where tt.service_id = 7\n"
			+ "   and tt.main_id = t.main_id\n"
			+ "   and f.firstname_id = t.firstname_id\n"
			+ "   and l.lastname_id = t.lastname_id\n"
			+ "   and t.abonent_id = ap.abonent_id\n"
			+ "   and p.phone_id = ap.phone_id\n"
			+ "   and ps.phone_state_id = p.phone_state_id\n"
			+ "   and ma.main_id = t.main_id\n"
			+ "   and str.street_id = ma.street_id\n"
			+ "   and c.city_id = str.city_id\n"
			+ "   and str.city_id = sd.city_id(+)\n"
			+ "   and str.street_id = sd.street_id(+)\n"
			+ "   AND t.abonent_id = ? ";

	public static final String Q_GET_STREET_BY_ID = "select\n"
			+ "  t.city_id,\n" + "  t.street_id,\n" + "  t.street_name_geo,\n"
			+ "  t.street_name_eng,\n" + "  t.map_id,\n" + "  t.rec_date,\n"
			+ "  t.rec_user,\n" + "  t.street_note_eng,\n" + "  t.deleted,\n"
			+ "  t.street_location_geo,\n" + "  t.street_location_eng,\n"
			+ "  t.upd_user,\n" + "  t.visible_options,\n"
			+ "  t.record_type, \n" + "  t.descr_id_level_1, \n"
			+ "  t.descr_id_level_2, \n" + "  t.descr_id_level_3, \n"
			+ "  t.descr_id_level_4, \n" + "  t.descr_id_level_5, \n"
			+ "  t.descr_id_level_6, \n" + "  t.descr_id_level_7, \n"
			+ "  t.descr_id_level_8, \n" + "  t.descr_id_level_9, \n"
			+ "  t.descr_id_level_10, \n" + "  t.descr_type_id_level_1, \n"
			+ "  t.descr_type_id_level_2, \n" + "  t.descr_type_id_level_3, \n"
			+ "  t.descr_type_id_level_4, \n" + "  t.descr_type_id_level_5, \n"
			+ "  t.descr_type_id_level_6, \n" + "  t.descr_type_id_level_7, \n"
			+ "  t.descr_type_id_level_8, \n" + "  t.descr_type_id_level_9, \n"
			+ "  t.descr_type_id_level_10 \n" + "from\n"
			+ "    ccare.streets t \n" + "where\n" + "  t.street_id = ? ";

	public static final String Q_GET_STREET_ALL = "select\n" + "  t.city_id,\n"
			+ "  t.street_id,\n" + "  t.street_name_geo,\n"
			+ "  t.street_name_eng,\n" + "  t.map_id,\n" + "  t.rec_date,\n"
			+ "  t.rec_user,\n" + "  t.street_note_eng,\n" + "  t.deleted,\n"
			+ "  t.street_location_geo,\n" + "  t.street_location_eng,\n"
			+ "  t.upd_user,\n" + "  t.visible_options,\n"
			+ "  t.record_type,\n" + "  t.descr_id_level_1, \n"
			+ "  t.descr_id_level_2, \n" + "  t.descr_id_level_3, \n"
			+ "  t.descr_id_level_4, \n" + "  t.descr_id_level_5, \n"
			+ "  t.descr_id_level_6, \n" + "  t.descr_id_level_7, \n"
			+ "  t.descr_id_level_8, \n" + "  t.descr_id_level_9, \n"
			+ "  t.descr_id_level_10, \n" + "  t.descr_type_id_level_1, \n"
			+ "  t.descr_type_id_level_2, \n" + "  t.descr_type_id_level_3, \n"
			+ "  t.descr_type_id_level_4, \n" + "  t.descr_type_id_level_5, \n"
			+ "  t.descr_type_id_level_6, \n" + "  t.descr_type_id_level_7, \n"
			+ "  t.descr_type_id_level_8, \n" + "  t.descr_type_id_level_9, \n"
			+ "  t.descr_type_id_level_10 \n" + "from\n"
			+ "    ccare.streets t\n";

	public static final String Q_GET_STREET_DISTRICTS_ALL = "select t.street_district_id,t.street_id,t.city_region_id,c.city_region_name_geo from ccare.street_district t\n"
			+ "inner join ccare.city_regions c on c.city_region_id = t.city_region_id\n"
			+ "where t.street_district_id is not null and t.street_id is not null and t.city_region_id is not null and c.city_region_name_geo is not null";

	// inserts
	public static final String Q_INSERT_PERS_NOTES = "BEGIN insert into ccare.log_personell_notes t\n"
			+ "(t.ym,t.session_id,t.user_name,t.note,t.rec_user,t.rec_date,t.visible_options,t.phone,t.call_date,t.particular)\n"
			+ "values (?,?,?,?,?,?,?,?,?,?) returning id into ?; END; ";
	
	
	public static final String Q_INSERT_SESSION =" insert into ccare.LOG_SESSIONS (YM, user_name, phone, session_id, call_type, is_new_bill) values (?,?,?,?,?,?) ";
	
	// updates
	public static final String Q_UPDATE_PERS_NOTES = "update ccare.log_personell_notes t set t.note = ?, t.visible_options = ?, t.particular = ?\n"
			+ "where t.note_id = ? ";

	public static final String Q_UPDATE_FIRST_NAME = "update ccare.firstnames t set t.firstname = ?, t.upd_user = ? where t.firstname_id = ? ";
	public static final String Q_UPDATE_LAST_NAME = "update ccare.lastnames t set t.lastname = ?, t.upd_user = ? where t.lastname_id = ? ";

	public static final String Q_UPDATE_FIRST_NAME_STATUS = "update ccare.firstnames t set t.deleted = ? , t.upd_user = ? where t.firstname_id = ? ";
	public static final String Q_UPDATE_LAST_NAME_STATUS = "update ccare.lastnames t set t.deleted = ? , t.upd_user = ? where t.lastname_id = ? ";
	
	
	
	
	
	
	public static final String Q_UPDATE_LOCK_STATUS =" update ccare.lock_status t set t.status = 1 where t.session_id = ? ";
	
	
	

	public static final String Q_UPDATE_SESSION_QUALITY = " update ccare.log_sessions t set t.session_quality = ? 	where t.session_id = ? ";

	public static final String Q_UPDATE_ABONENT = " update ccare.abonents t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.abonent_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE = " update ccare.main_services t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.main_id = ? ";

	public static final String Q_UPDATE_MAIN_ADDRESS = " update ccare.main_address t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.address_id = ? and t.main_id = ? ";

	// public static final String Q_UPDATE_PHONES =
	// " update ccare.phones t set t.deleted = ?, t.upd_user = ? where t.abonent_id = ? ";

	public static final String Q_UPDATE_PERSONNEL = " update ccare.personnel t set\n"
			+ "       t.personnel_name = ?,\n"
			+ "       t.personnel_surname = ?,\n"
			+ "       t.user_name = ?,\n"
			+ "       t.password = ?,\n"
			+ "       t.upd_user = ?,\n"
			+ "       t.personnel_type_id = ? where t.personnel_id = ?";

	public static final String Q_UPDATE_COUNTRY = "update ccare.countries t set\n"
			+ "       t.country_name_geo = ?,\n"
			+ "       t.country_name_eng = ?,\n"
			+ "       t.country_code = ?,\n"
			+ "       t.upd_user = ?,\n"
			+ "       t.upd_date = ?,\n"
			+ "       t.continent_id = ?\n"
			+ "where t.country_id = ?";

	public static final String Q_UPDATE_PERSONNEL_STATUS = " update ccare.personnel t set \n"
			+ "       t.deleted = ? ,\n"
			+ "       t.upd_user = ? \n"
			+ "where t.personnel_id = ? ";

	public static final String Q_UPDATE_COUNTRY_STATUS = "update ccare.countries t set\n"
			+ "       t.deleted = ?,\n"
			+ "       t.upd_user = ?,\n"
			+ "       t.upd_date = ? where t.country_id = ?";

	public static final String Q_UPDATE_CITY = "update ccare.cities t set\n"
			+ "       t.city_name_geo = ?, t.city_name_eng = ?,\n"
			+ "       t.country_id = ?, t.city_type_id = ?,\n"
			+ "       t.of_gmt = ?, t.of_gmt_winter = ?,\n"
			+ "       t.is_capital = ?, t.upd_user = ?,\n"
			+ "       t.city_code = ?, t.upd_date = ?,\n"
			+ "       t.city_new_code = ? where t.city_id = ?";

	public static final String Q_UPDATE_CITY_STATUS = "update ccare.cities t set\n"
			+ "       t.upd_user = ?,\n"
			+ "       t.upd_date = ?,\n"
			+ "       t.deleted = ? where t.city_id = ?";

	public static final String Q_UPDATE_STREET_TYPE = "update ccare.street_types t set t.street_type_name_geo = ?, t.street_type_name_eng = ?, t.upd_user = ? where t.street_type_id = ? ";

	public static final String Q_UPDATE_STREET_TYPE_STATUS = "update ccare.street_types t set t.deleted = ?, t.upd_user = ? where t.street_type_id = ? ";

	public static final String Q_UPDATE_STREET_DESCR = " update ccare.street_descr t set t.street_descr_name_geo = ?, t.street_descr_name_eng = ?, t.upd_user = ? where t.street_descr_id = ? ";

	public static final String Q_UPDATE_STREET_DESCR_STATUS = "update ccare.street_descr t set t.deleted = ?, t.upd_user = ? where t.street_descr_id = ? ";

	public static final String Q_UPDATE_STREET = " update ccare.streets t set\n"
			+ "        t.city_id = ?,\n" + "        t.street_name_geo = ?,\n"
			+ "        t.street_location_geo = ?,\n"
			+ "        t.upd_user = ?,\n" + "        t.record_type = ?,\n"
			+ "        t.descr_id_level_1 = ?,\n"
			+ "        t.descr_id_level_2 = ?,\n"
			+ "        t.descr_id_level_3 = ?,\n"
			+ "        t.descr_id_level_4 = ?,\n"
			+ "        t.descr_id_level_5 = ?,\n"
			+ "        t.descr_id_level_6 = ?,\n"
			+ "        t.descr_id_level_7 = ?,\n"
			+ "        t.descr_id_level_8 = ?,\n"
			+ "        t.descr_id_level_9 = ?,\n"
			+ "        t.descr_id_level_10 = ?,\n"
			+ "        t.descr_type_id_level_1 = ?,\n"
			+ "        t.descr_type_id_level_2 = ?,\n"
			+ "        t.descr_type_id_level_3 = ?,\n"
			+ "        t.descr_type_id_level_4 = ?,\n"
			+ "        t.descr_type_id_level_5 = ?,\n"
			+ "        t.descr_type_id_level_6 = ?,\n"
			+ "        t.descr_type_id_level_7 = ?,\n"
			+ "        t.descr_type_id_level_8 = ?,\n"
			+ "        t.descr_type_id_level_9 = ?,\n"
			+ "        t.descr_type_id_level_10 = ?\n"
			+ "where t.street_id = ? \n";

	public static final String Q_UPDATE_STREET_STATUS = "update ccare.streets t set t.deleted = ?, t.upd_user = ? where t.street_id = ? ";

	// deletes
	public static final String Q_DELETE_PERS_NOTES = "delete from ccare.log_personell_notes t where t.note_id = ?";

	public static final String Q_DELETE_PHONES_BY_ABONENT = "delete from ccare.abonent_to_phones t where t.abonent_id = ? ";

	public static final String Q_DELETE_PERSONNEL = " delete from ccare.personnel_to_access t where t.personnel_id = ? ";

	public static final String Q_DELETE_STREET_DISCTRICTS_BY_STREET_ID = " delete from ccare.street_district t where t.street_id = ? ";

	public static final String Q_ROLLBACK_UNAPPROVED_MESSAGES = "update ccare.call_center_queue t set t.status=1 ,t.upd_date=sysdate where t.status=2 and t.upd_date<=sysdate-1/24/4";
	

	public static final String Q_UPDATE_MAIN_SERVICE_SORT = " update ccare.main_services t set t.priority = ?,t.upd_user = ?,t.upd_date = ? where t.main_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE_DEL_STAT = " update ccare.main_services t set t.deleted = ?,t.upd_user = ?,t.upd_date = ? where t.main_id = ? ";
	
	public static final String Q_UPDATE_MAIN_SERVICE_HIST = " update ccare.main_services t set t.upd_user = ?,t.upd_date = ? where t.main_id = ? ";

	public static final String Q_UPDATE_MAIN_ORG_STATUS = " update ccare.main_orgs t set t.statuse = ? where t.main_id = ? ";

	public static final String Q_GET_MAIN_ORG_BY_ID = "select ms.main_id,\n" +
			"       ms.main_master_id,\n" + 
			"       mo1.org_name,\n" + 
			"       mo1.identcode,\n" + 
			"       mo1.director,\n" + 
			"       mo1.legaladdress,\n" + 
			"       ms.deleted,\n" + 
			"       mo1.note,\n" + 
			"       mo1.workinghours,\n" + 
			"       mo1.founded,\n" + 
			"       mo1.mail,\n" + 
			"       mo1.org_info,\n" + 
			"       mo1.webaddress,\n" + 
			"       mo1.contactperson,\n" + 
			"       mo1.dayoffs,\n" + 
			"       mo1.workpersoncountity,\n" + 
			"       mo1.ind,\n" + 
			"       mo1.org_name_eng,\n" + 
			"       mo1.new_identcode,\n" + 
			"       mo1.legal_statuse_id,\n" +  
			"       mo1.statuse,\n" + 
			"       str.street_name_geo || ' ' || ma.address_suffix_geo as real_address\n" + 
			"  from ccare.main_services ms,\n" + 
			"       ccare.main_orgs     mo1,\n" + 
			"       main_address       ma,\n" + 
			"       streets            str\n" + 
			" where mo1.main_id = ms.main_id\n" + 
			"   and ma.main_id = ms.main_id\n" + 
			"   and str.street_id = ma.street_id\n" + 
			"   and ms.service_id = 3\n" + 
			"   and ms.main_id = ?\n" + 
			" order by ms.priority";
	
}

package com.info08.billing.callcenterbk.server.common;

public interface QueryConstants {

	public static final String Q_GET_VIRTUAL_SESSION_ID = " select 'VIRT.'||ccare.seq_virtual_session_id.nextval as session_id from dual ";

	public static final String Q_GET_BILLING_COMP_IND = " select count(1) from ccare.BILLING_COMPANIES_IND t where ? between t.bill_index_start and t.bill_index_end ";

	public static final String Q_GET_BILLING_COMP_IND_BY_ID = " select count(1) from ccare.BILLING_COMPANIES_IND t where ? between t.bill_index_start and t.bill_index_end and t.billing_company_id <> ? ";

	public static final String Q_GET_BILLING_COMP_BILL_BY_DAY = "select\n"
			+ "      t.phone as phonea,\n"
			+ "     '11808' as phoneb,\n"
			+ "      ch.rec_date as charge_date,\n"
			+ "      to_char(ch.rec_date,'DD/MM/YYYY HH24:MI:SS') as charge_date1,\n"
			+ "      60 as duration,\n"
			+ "      ch.price/100 as rate,\n"
			+ "      ch.price/100 as price\n"
			+ "from ccare.log_sessions t, ccare.log_session_charges ch\n"
			+ "where\n"
			+ "     t.session_id  = ch.session_id and\n"
			+ "     trunc(t.start_date) = trunc(?) and\n"
			+ "     trunc(ch.rec_date) = trunc(?) and\n"
			+ "      exists (\n"
			+ "        select\n"
			+ "          to_number(decode(length(i.bill_index_start),7,'32'||i.bill_index_start,i.bill_index_start)),\n"
			+ "          to_number(decode(length(i.bill_index_end),7,'32'||i.bill_index_start,i.bill_index_end))\n"
			+ "        from ccare.BILLING_COMPANIES tc\n"
			+ "               inner join ccare.BILLING_COMPANIES_IND i on i.billing_company_id = tc.billing_company_id\n"
			+ "        where tc.billing_company_id = ? \n"
			+ "              and to_number(t.phone) >= to_number(decode(length(i.bill_index_start),7,'32'||i.bill_index_start,i.bill_index_start))\n"
			+ "              and to_number(t.phone) <= to_number(decode(length(i.bill_index_end),7,'32'||i.bill_index_end,i.bill_index_end))\n"
			+ "      )\n"
			+ "      and ccare.getcontractorpricenew(t.phone)=-999999999\n"
			+ "      and t.phone like '322%'";

	public static final String Q_GET_BILLING_COMP_BILL_BY_YM = "select substr(t.phone,3, length(t.phone)) as phonea,\n"
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
			+ "          to_number(decode(length(i.bill_index_start),7,'32'||i.bill_index_start,i.bill_index_start)),\n"
			+ "          to_number(decode(length(i.bill_index_end),7,'32'||i.bill_index_start,i.bill_index_end))\n"
			+ "        from ccare.BILLING_COMPANIES tc\n"
			+ "               inner join ccare.BILLING_COMPANIES_IND i on i.billing_company_id = tc.billing_company_id\n"
			+ "        where tc.billing_company_id = ? \n"
			+ "              and to_number(t.phone) >= to_number(decode(length(i.bill_index_start),7,'32'||i.bill_index_start,i.bill_index_start))\n"
			+ "              and to_number(t.phone) <= to_number(decode(length(i.bill_index_end),7,'32'||i.bill_index_end,i.bill_index_end))\n"
			+ "      )\n"
			+ "      and ccare.getcontractorpricenew(t.phone)=-999999999\n"
			+ "      and t.phone like '322%'";

	public static final String Q_GET_ORG_CALL_CNT_BY_YM = "select getCallsByMainAndYM(to_number(to_char(sysdate,'YYMM')),?) as depCallsCnt from dual";

	public static final String Q_GET_DEP_CALL_CNT_BY_YM = "select getCallsByMainDetAndYM(to_number(to_char(sysdate,'YYMM')),?) as depCallsCnt from dual";

	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY_NEW = "select distinct p.phone from (\n"
			+ "       select ms.organization_id from ccare.main_services ms\n"
			+ "       start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "       connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "inner join ccare.abonents a on a.organization_id = r.organization_id\n"
			+ "inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "    select p.phone from (\n"
			+ "    select\n"
			+ "      t.organization_id\n"
			+ "    from ccare.main_services t\n"
			+ "    start with t.organization_id in (\n"
			+ "          select\n"
			+ "               c.organization_id\n"
			+ "          from ccare.contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                select\n"
			+ "                      t.organization_id\n"
			+ "                from ccare.main_services t\n"
			+ "                where level>1\n"
			+ "          start with t.organization_id = ? and t.service_id = 3\n"
			+ "          connect by prior t.organization_id = t.main_master_id\n"
			+ "          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "    ) and t.service_id = 3\n"
			+ "    connect by prior t.organization_id = t.main_master_id\n"
			+ "    ) a1\n"
			+ "    inner join ccare.abonents a on a.organization_id = a1.organization_id\n"
			+ "    inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "    where a.deleted = 0 and p.deleted = 0\n"
			+ ") and p.phone not in (\n"
			+ "    select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "    from ccare.abonents a\n"
			+ "         inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "          (select\n"
			+ "                 t.main_detail_id\n"
			+ "           from ccare.main_details t\n"
			+ "           start with t.main_detail_id in\n"
			+ "                 (select\n"
			+ "                        c.main_detail_id\n"
			+ "                  from ccare.contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                        (select\n"
			+ "                               t.organization_id\n"
			+ "                         from ccare.main_services t\n"
			+ "                         where level>1\n"
			+ "                         start with t.organization_id = ? and t.service_id = 3\n"
			+ "                         connect by prior t.organization_id = t.main_master_id\n"
			+ "                         )\n"
			+ "                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n"
			+ "                  ) and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ ")\n";

	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY_BLOCK_LIST = " select\n"
			+ "   distinct p.phone\n"
			+ "from ccare.abonents a\n"
			+ "   inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where a.organization_id in (\n"
			+ "      select\n"
			+ "         t.organization_id\n"
			+ "      from ccare.main_services t\n"
			+ "      start with t.organization_id = ? and t.service_id = 3\n"
			+ "      connect by prior t.organization_id = t.main_master_id\n"
			+ " )\n"
			+ " and p.deleted = 0 and a.deleted = 0 and p.phone in (select tt.phone\n"
			+ "                         from ccare.block_list_phones tt\n"
			+ "                        where tt.block_list_id = ? )";

	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY_EXCEPT_BLOCK_LIST = " select\n"
			+ "   distinct p.phone\n"
			+ "from ccare.abonents a\n"
			+ "   inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where a.organization_id in (\n"
			+ "      select\n"
			+ "         t.organization_id\n"
			+ "      from ccare.main_services t\n"
			+ "      start with t.organization_id = ? and t.service_id = 3\n"
			+ "      connect by prior t.organization_id = t.main_master_id\n"
			+ " )\n"
			+ " and p.deleted = 0 and a.deleted = 0 and p.phone not in (select tt.phone\n"
			+ "                         from ccare.block_list_phones tt\n"
			+ "                        where tt.block_list_id = ? )";

	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_EXCEPT_BLOCK_LIST = "select /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "  from ccare.abonents a\n"
			+ " inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ " where a.main_detail_id in\n"
			+ "       (select t.main_detail_id from ccare.main_details t\n"
			+ "        start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "        connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ "   and p.deleted = 0 and a.deleted = 0\n"
			+ "   and p.phone not in (select tt.phone\n"
			+ "                         from ccare.block_list_phones tt\n"
			+ "                        where tt.block_list_id = ? )";

	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_BLOCK_LIST = "select /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "  from ccare.abonents a\n"
			+ " inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ " where a.main_detail_id in\n"
			+ "       (select t.main_detail_id from ccare.main_details t\n"
			+ "        start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "        connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ "   and p.deleted = 0 and a.deleted = 0\n"
			+ "   and p.phone in (select tt.phone\n"
			+ "                         from ccare.block_list_phones tt\n"
			+ "                        where tt.block_list_id = ? )";

	public static final String Q_GET_BLOCK_LIST_PHONES = "select tt.phone from ccare.block_list_phones tt where tt.block_list_id = ? ";

	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY = " select\n"
			+ "   distinct p.phone\n" + "from ccare.abonents a\n"
			+ "   inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where a.organization_id in (\n" + "      select\n"
			+ "         t.organization_id\n"
			+ "      from ccare.main_services t\n"
			+ "      start with t.organization_id = ? and t.service_id = 3\n"
			+ "      connect by prior t.organization_id = t.main_master_id\n"
			+ " )\n" + " and p.deleted = 0 and a.deleted = 0";

	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_NEW = "select /*+ index(p PHN_PRY_KS001)*/\n"
			+ "\t distinct p.phone\n"
			+ "\tfrom ccare.abonents a\n"
			+ "\t     inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "\twhere a.main_detail_id in\n"
			+ "\t       (\n"
			+ "\t         select\n"
			+ "\t               t.main_detail_id\n"
			+ "\t         from ccare.main_details t\n"
			+ "           start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "            union "
			+ "            select -999999999 from dual "
			+ "         )\n"
			+ "  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "       select\n"
			+ "             /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "       from ccare.abonents a\n"
			+ "            inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "          select\n"
			+ "                c.main_detail_id\n"
			+ "          from ccare.contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "            (\n"
			+ "              select\n"
			+ "                    t.main_detail_id\n"
			+ "              from ccare.main_details t\n"
			+ "              where level>1\n"
			+ "              start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "              connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "               union "
			+ "               select -999999999 from dual "
			+ "            )\n"
			+ "            union "
			+ "            select -999999999 from dual "
			+ "        )\n" + "  )";

	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY = " select /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "from ccare.abonents a\n"
			+ "   inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where a.main_detail_id in (\n"
			+ "      select\n"
			+ "          t.main_detail_id from ccare.main_details t\n"
			+ "      start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "      connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ ") and p.deleted = 0 and a.deleted = 0 ";
	//
	// public static final String Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID =
	// " select /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1) from ccare.log_sessions ls\n"
	// +
	// "       inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n"
	// +
	// "       inner join ccare.contracts c on c.contract_id = ? \n" +
	// "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
	// +
	// "      select /*+ index(p PHN_PRY_KS001)*/\n" +
	// "         distinct '32'||p.phone as phone\n" +
	// "      from ccare.abonents a\n" +
	// "         inner join ccare.phones p on p.phone_id = a.phone_id\n" +
	// "      where a.main_detail_id in (\n" +
	// "            select\n" +
	// "                t.main_detail_id from ccare.main_details t\n" +
	// "            start with t.main_detail_id = ((select c.main_detail_id from ccare.contracts c where c.contract_id = ? )) and t.deleted = 0\n"
	// +
	// "            connect by prior t.main_detail_id = t.main_detail_master_id\n"
	// +
	// "      )\n" +
	// ")";

	public static final String Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID = "select\n"
			+ "      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1)\n"
			+ "from ccare.log_sessions ls\n"
			+ "     inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n"
			+ "     inner join ccare.contracts c on c.contract_id = ? \n"
			+ "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
			+ "     select /*+ index(p PHN_PRY_KS001)*/\n"
			+ "           distinct decode(length(p.phone),7,'32'||p.phone,p.phone) \n"
			+ "          from ccare.abonents a\n"
			+ "               inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "          where a.main_detail_id in\n"
			+ "                 (\n"
			+ "                   select\n"
			+ "                         t.main_detail_id\n"
			+ "                   from ccare.main_details t\n"
			+ "                     start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "                     connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "                    union    "
			+ "                    select -999999999 from dual    "
			+ "                   )\n"
			+ "            and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "                 select\n"
			+ "                       /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "                 from ccare.abonents a\n"
			+ "                      inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "                 where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "                    select\n"
			+ "                          c.main_detail_id\n"
			+ "                    from ccare.contracts c\n"
			+ "                    where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "                      (\n"
			+ "                        select\n"
			+ "                          t.main_detail_id\n"
			+ "                         from ccare.main_details t\n"
			+ "                        where level>1\n"
			+ "                        start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "                        connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "                         union    "
			+ "                         select -999999999 from dual    "
			+ "                      )\n"
			+ "                       union    "
			+ "                       select -999999999 from dual    "
			+ "                   )\n" + "                 )\n" + ")";

	public static final String Q_GET_CHARGES_BY_CONT_AND_MAIN_DET_ID = "select\n"
			+ "      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ nvl(sum(ch.price)/100,0) as charges \n"
			+ "from ccare.log_sessions ls\n"
			+ "     inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n"
			+ "     inner join ccare.contracts c on c.contract_id = ? \n"
			+ "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
			+ "     select /*+ index(p PHN_PRY_KS001)*/\n"
			+ "           distinct decode(length(p.phone),7,'32'||p.phone,p.phone) \n"
			+ "          from ccare.abonents a\n"
			+ "               inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "          where a.main_detail_id in\n"
			+ "                 (\n"
			+ "                   select\n"
			+ "                         t.main_detail_id\n"
			+ "                   from ccare.main_details t\n"
			+ "                     start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "                     connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "                   )\n"
			+ "            and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "                 select\n"
			+ "                       /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "                 from ccare.abonents a\n"
			+ "                      inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "                 where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "                    select\n"
			+ "                          c.main_detail_id\n"
			+ "                    from ccare.contracts c\n"
			+ "                    where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "                      (\n"
			+ "                        select\n"
			+ "      \t\t                    t.main_detail_id\n"
			+ "\t\t\t\t\t              from ccare.main_details t\n"
			+ "\t\t\t\t\t              where level>1\n"
			+ "\t\t\t\t\t              start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "\t\t\t\t\t              connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "\t\t\t\t\t            )\n"
			+ "\t\t\t\t\t        )\n"
			+ "\t\t\t\t\t  )\n" + ")";

	// public static final String Q_GET_CALL_CNT_BY_CONT_AND_organization_id =
	// " select /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1) from ccare.log_sessions ls\n"
	// +
	// "       inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n"
	// +
	// "       inner join ccare.contracts c on c.contract_id = ?\n" +
	// "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
	// +
	// "      select\n" +
	// "         distinct '32'||p.phone as phone\n" +
	// "      from ccare.abonents a\n" +
	// "         inner join ccare.phones p on p.phone_id = a.phone_id\n" +
	// "      where a.organization_id in (\n" +
	// "            select\n" +
	// "               t.organization_id\n" +
	// "            from ccare.main_services t\n" +
	// "            start with t.organization_id =((select c.organization_id from ccare.contracts c where c.contract_id = ?))  and t.service_id = 3\n"
	// +
	// "            connect by prior t.organization_id = t.main_master_id\n" +
	// "       )\n" +
	// ")";

	public static final String Q_GET_CALL_CNT_BY_CONT_AND_ORGANIZATION_ID = "select\n"
			+ "      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1)\n"
			+ "from ccare.log_sessions ls\n"
			+ "     inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n"
			+ "     inner join ccare.contracts c on c.contract_id = ? \n"
			+ "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
			+ "     select distinct decode(length(p.phone),7,'32'||p.phone,p.phone) from (\n"
			+ "               select ms.organization_id from ccare.main_services ms\n"
			+ "               start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "               connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "        inner join ccare.abonents a on a.organization_id = r.organization_id\n"
			+ "        inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "        where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "            select p.phone from (\n"
			+ "            select\n"
			+ "              t.organization_id\n"
			+ "            from ccare.main_services t\n"
			+ "            start with t.organization_id in (\n"
			+ "                  select\n"
			+ "                       c.organization_id\n"
			+ "                  from ccare.contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                        select\n"
			+ "                              t.organization_id\n"
			+ "                        from ccare.main_services t\n"
			+ "                        where level>1\n"
			+ "                  start with t.organization_id = ? and t.service_id = 3\n"
			+ "                  connect by prior t.organization_id = t.main_master_id\n"
			+ "                  ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "            ) and t.service_id = 3\n"
			+ "            connect by prior t.organization_id = t.main_master_id\n"
			+ "            ) a1\n"
			+ "            inner join ccare.abonents a on a.organization_id = a1.organization_id\n"
			+ "            inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "            where a.deleted = 0 and p.deleted = 0\n"
			+ "        ) and p.phone not in (\n"
			+ "            select\n"
			+ "              /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "            from ccare.abonents a\n"
			+ "                 inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "            where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "                  (select\n"
			+ "                         t.main_detail_id\n"
			+ "                   from ccare.main_details t\n"
			+ "                   start with t.main_detail_id in\n"
			+ "                         (select\n"
			+ "                                c.main_detail_id\n"
			+ "                          from ccare.contracts c\n"
			+ "                          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                                (select\n"
			+ "                                       t.organization_id\n"
			+ "                                 from ccare.main_services t\n"
			+ "                                 where level>1\n"
			+ "                                 start with t.organization_id = ? and t.service_id = 3\n"
			+ "\t\t\t\t                         connect by prior t.organization_id = t.main_master_id\n"
			+ "\t\t\t\t                         )\n"
			+ "\t\t\t\t                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n"
			+ "\t\t\t\t                  ) and t.deleted = 0\n"
			+ "\t\t\t\t           connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ "\t\t\t\t)\n" + ")";

	public static final String Q_GET_CHARGES_BY_CONT_AND_ORGANIZATION_ID = "select\n"
			+ "      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ nvl(sum(ch.price)/100,0) as charges \n"
			+ "from ccare.log_sessions ls\n"
			+ "     inner join ccare.log_session_charges ch on ch.session_id = ls.session_id\n"
			+ "     inner join ccare.contracts c on c.contract_id = ? \n"
			+ "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
			+ "     select distinct decode(length(p.phone),7,'32'||p.phone,p.phone) from (\n"
			+ "               select ms.organization_id from ccare.main_services ms\n"
			+ "               start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "               connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "        inner join ccare.abonents a on a.organization_id = r.organization_id\n"
			+ "        inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "        where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "            select p.phone from (\n"
			+ "            select\n"
			+ "              t.organization_id\n"
			+ "            from ccare.main_services t\n"
			+ "            start with t.organization_id in (\n"
			+ "                  select\n"
			+ "                       c.organization_id\n"
			+ "                  from ccare.contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                        select\n"
			+ "                              t.organization_id\n"
			+ "                        from ccare.main_services t\n"
			+ "                        where level>1\n"
			+ "                  start with t.organization_id = ? and t.service_id = 3\n"
			+ "                  connect by prior t.organization_id = t.main_master_id\n"
			+ "                  ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "            ) and t.service_id = 3\n"
			+ "            connect by prior t.organization_id = t.main_master_id\n"
			+ "            ) a1\n"
			+ "            inner join ccare.abonents a on a.organization_id = a1.organization_id\n"
			+ "            inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "            where a.deleted = 0 and p.deleted = 0\n"
			+ "        ) and p.phone not in (\n"
			+ "            select\n"
			+ "              /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "            from ccare.abonents a\n"
			+ "                 inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "            where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "                  (select\n"
			+ "                         t.main_detail_id\n"
			+ "                   from ccare.main_details t\n"
			+ "                   start with t.main_detail_id in\n"
			+ "                         (select\n"
			+ "                                c.main_detail_id\n"
			+ "                          from ccare.contracts c\n"
			+ "                          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                                (select\n"
			+ "                                       t.organization_id\n"
			+ "                                 from ccare.main_services t\n"
			+ "                                 where level>1\n"
			+ "                                 start with t.organization_id = ? and t.service_id = 3\n"
			+ "\t\t\t\t                         connect by prior t.organization_id = t.main_master_id\n"
			+ "\t\t\t\t                         )\n"
			+ "\t\t\t\t                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n"
			+ "\t\t\t\t                  ) and t.deleted = 0\n"
			+ "\t\t\t\t           connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ "\t\t\t\t)\n" + ")";

	public static final String Q_MYSQL_DELETE_BLOCK_PHONE = " delete from asteriskcdrdb.block where code like CONCAT('%', ?) ";
	public static final String Q_MYSQL_INSERT_BLOCK_PHONE = " insert into asteriskcdrdb.block (code,proriti,len) values (?, ?, ?) ";

	public static final String Q_SELECT_BLACK_LIST_PHONES = "select wm_concat(PHONE_NUMBER) black_list_phones from BLACK_LIST_PHONES tt where tt.black_list_id=?";
	public static final String Q_DELETE_BLACK_LIST_PHONES = "delete from BLACK_LIST_PHONES tt where tt.black_list_id=?";
	public static final String Q_DELETE_BLACK_LIST = "delete from BLACK_LIST tt where tt.black_list_id=?";

	public static final String Q_CHECK_PHONE_MAIN_ORG = "select count(1) from ccare.main_services ms\n"
			+ "       inner join ccare.abonents a on a.organization_id = ms.organization_id\n"
			+ "       inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where ms.organization_id in\n"
			+ "      (select t.organization_id from ccare.main_services t\n"
			+ "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
			+ "       connect by prior t.organization_id = t.main_master_id\n"
			+ "       )\n"
			+ "and p.deleted = 0 and a.deleted = 0 and ms.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
			+ "and length(p.phone)>6 and ms.service_id = 3 and ms.deleted = 0 and p.phone = ? ";

	public static final String Q_CHECK_PHONE_MAIN_ORG_DET = "select p.phone from ccare.phones p\n"
			+ "       inner join ccare.abonents a on a.phone_id = p.phone_id\n"
			+ "       inner join ccare.main_services ms on ms.organization_id = a.organization_id\n"
			+ "where a.main_detail_id in\n"
			+ "      (select t.main_detail_id from ccare.main_details t\n"
			+ "       start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "       connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "       )\n"
			+ "and p.deleted = 0 and a.deleted = 0 and ms.service_id = 3 and ms.deleted = 0 and p.phone = ? ";

	// public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST =
	// "select distinct t.phone from ccare.phones t\n" +
	// "inner join ccare.abonents a on a.phone_id = t.phone_id\n" +
	// "inner join ccare.main_services ms on ms.organization_id = a.organization_id\n"
	// +
	// "where t.deleted = 0 and a.deleted = 0 and ms.service_id = 3 and a.organization_id = ? and ms.organization_id = ?\n"
	// +
	// "  and a.main_detail_id = ? and t.phone is not null and length(t.phone)>6 and t.phone not in (\n"
	// +
	// "      select tt.phone from ccare.contractor_phones tt\n" +
	// "             inner join ccare.contracts c on c.contract_id = tt.contract_id\n"
	// +
	// "      where tt.contract_id = ? and tt.deleted = 0 and c.organization_id = a.organization_id and c.main_detail_id = a.main_detail_id and c.deleted = 0\n"
	// +
	// ")";

	public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST = "select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ distinct p.phone\n"
			+ "from ccare.abonents a\n"
			+ "     inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where a.main_detail_id in\n"
			+ "       (\n"
			+ "         select\n"
			+ "               t.main_detail_id\n"
			+ "         from ccare.main_details t\n"
			+ "           start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "         )\n"
			+ "  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "       select\n"
			+ "             /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "       from ccare.abonents a\n"
			+ "            inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "          select\n"
			+ "                c.main_detail_id\n"
			+ "          from ccare.contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "            (\n"
			+ "              select\n"
			+ "                    t.main_detail_id\n"
			+ "              from ccare.main_details t\n"
			+ "              where level>1\n"
			+ "              start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "              connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "            )\n"
			+ "        )\n"
			+ "  )\n"
			+ "  and p.phone not in (\n"
			+ "      select tt.phone from ccare.contractor_phones tt where tt.contract_id = ? and tt.deleted = 0 "
			+ "  )\n";

	public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST = "select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ distinct p.phone\n"
			+ "from ccare.abonents a\n"
			+ "     inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where a.main_detail_id in\n"
			+ "       (\n"
			+ "         select\n"
			+ "               t.main_detail_id\n"
			+ "         from ccare.main_details t\n"
			+ "           start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "         )\n"
			+ "  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "       select\n"
			+ "             /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "       from ccare.abonents a\n"
			+ "            inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "          select\n"
			+ "                c.main_detail_id\n"
			+ "          from ccare.contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "            (\n"
			+ "              select\n"
			+ "                    t.main_detail_id\n"
			+ "              from ccare.main_details t\n"
			+ "              where level>1\n"
			+ "              start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "              connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "            )\n"
			+ "        )\n"
			+ "  )\n"
			+ "  and p.phone in (\n"
			+ "      select tt.phone from ccare.contractor_phones tt where tt.contract_id = ? and tt.deleted = 0 "
			+ "  )\n";

	// public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST =
	// "select distinct t.phone from ccare.phones t\n" +
	// "inner join ccare.abonents a on a.phone_id = t.phone_id\n" +
	// "inner join ccare.main_services ms on ms.organization_id = a.organization_id\n"
	// +
	// "where t.deleted = 0 and a.deleted = 0 and ms.service_id = 3 and a.organization_id = ? and ms.organization_id = ?\n"
	// +
	// "  and a.main_detail_id = ? and t.phone is not null and length(t.phone)>6 and t.phone in (\n"
	// +
	// "      select tt.phone from ccare.contractor_phones tt\n" +
	// "             inner join ccare.contracts c on c.contract_id = tt.contract_id\n"
	// +
	// "      where tt.contract_id = ? and tt.deleted = 0 and c.organization_id = a.organization_id and c.main_detail_id = a.main_detail_id and c.deleted = 0\n"
	// +
	// ")";

	// public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST1 =
	// "select distinct p.phone from ccare.main_services ms\n" +
	// "       inner join ccare.abonents a on a.organization_id = ms.organization_id\n"
	// +
	// "       inner join ccare.phones p on p.phone_id = a.phone_id\n" +
	// "where ms.organization_id in\n" +
	// "      (select t.organization_id from ccare.main_services t\n" +
	// "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
	// +
	// "       connect by prior t.organization_id = t.main_master_id\n" +
	// "       )\n" +
	// "and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
	// +
	// "and length(p.phone)>6 and ms.service_id = 3 and ms.deleted = 0 and p.phone not in (\n"
	// +
	// "      select tt.phone from ccare.contractor_phones tt\n" +
	// "             inner join ccare.contracts c on c.contract_id = tt.contract_id\n"
	// +
	// "      where tt.contract_id = ? and tt.deleted = 0 and c.organization_id = a.organization_id and c.deleted = 0\n"
	// +
	// ")";

	public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST1 = "select distinct p.phone from (\n"
			+ "       select ms.organization_id from ccare.main_services ms\n"
			+ "       start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "       connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "inner join ccare.abonents a on a.organization_id = r.organization_id\n"
			+ "inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "    select p.phone from (\n"
			+ "    select\n"
			+ "      t.organization_id\n"
			+ "    from ccare.main_services t\n"
			+ "    start with t.organization_id in (\n"
			+ "          select\n"
			+ "               c.organization_id\n"
			+ "          from ccare.contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                select\n"
			+ "                      t.organization_id\n"
			+ "                from ccare.main_services t\n"
			+ "                where level>1\n"
			+ "          start with t.organization_id = ? and t.service_id = 3\n"
			+ "          connect by prior t.organization_id = t.main_master_id\n"
			+ "          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "    ) and t.service_id = 3\n"
			+ "    connect by prior t.organization_id = t.main_master_id\n"
			+ "    ) a1\n"
			+ "    inner join ccare.abonents a on a.organization_id = a1.organization_id\n"
			+ "    inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "    where a.deleted = 0 and p.deleted = 0\n"
			+ ") and p.phone not in (\n"
			+ "    select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "    from ccare.abonents a\n"
			+ "         inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "          (select\n"
			+ "                 t.main_detail_id\n"
			+ "           from ccare.main_details t\n"
			+ "           start with t.main_detail_id in\n"
			+ "                 (select\n"
			+ "                        c.main_detail_id\n"
			+ "                  from ccare.contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                        (select\n"
			+ "                               t.organization_id\n"
			+ "                         from ccare.main_services t\n"
			+ "                         where level>1\n"
			+ "                         start with t.organization_id = ? and t.service_id = 3\n"
			+ "                         connect by prior t.organization_id = t.main_master_id\n"
			+ "                         )\n"
			+ "                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n"
			+ "                  ) and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ ") \n"
			+ " and p.phone not in (\n"
			+ "      select tt.phone from ccare.contractor_phones tt where tt.contract_id = ? and tt.deleted = 0 "
			+ "  )\n";

	public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST1 = "select distinct p.phone from (\n"
			+ "       select ms.organization_id from ccare.main_services ms\n"
			+ "       start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "       connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "inner join ccare.abonents a on a.organization_id = r.organization_id\n"
			+ "inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "    select p.phone from (\n"
			+ "    select\n"
			+ "      t.organization_id\n"
			+ "    from ccare.main_services t\n"
			+ "    start with t.organization_id in (\n"
			+ "          select\n"
			+ "               c.organization_id\n"
			+ "          from ccare.contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                select\n"
			+ "                      t.organization_id\n"
			+ "                from ccare.main_services t\n"
			+ "                where level>1\n"
			+ "          start with t.organization_id = ? and t.service_id = 3\n"
			+ "          connect by prior t.organization_id = t.main_master_id\n"
			+ "          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "    ) and t.service_id = 3\n"
			+ "    connect by prior t.organization_id = t.main_master_id\n"
			+ "    ) a1\n"
			+ "    inner join ccare.abonents a on a.organization_id = a1.organization_id\n"
			+ "    inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "    where a.deleted = 0 and p.deleted = 0\n"
			+ ") and p.phone not in (\n"
			+ "    select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "    from ccare.abonents a\n"
			+ "         inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "          (select\n"
			+ "                 t.main_detail_id\n"
			+ "           from ccare.main_details t\n"
			+ "           start with t.main_detail_id in\n"
			+ "                 (select\n"
			+ "                        c.main_detail_id\n"
			+ "                  from ccare.contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                        (select\n"
			+ "                               t.organization_id\n"
			+ "                         from ccare.main_services t\n"
			+ "                         where level>1\n"
			+ "                         start with t.organization_id = ? and t.service_id = 3\n"
			+ "                         connect by prior t.organization_id = t.main_master_id\n"
			+ "                         )\n"
			+ "                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n"
			+ "                  ) and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ ")\n"
			+ " and p.phone in (\n"
			+ "      select tt.phone from ccare.contractor_phones tt where tt.contract_id = ? and tt.deleted = 0 "
			+ "  )\n";

	// public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST1 =
	// "select distinct p.phone from ccare.main_services ms\n" +
	// "       inner join ccare.abonents a on a.organization_id = ms.organization_id\n"
	// +
	// "       inner join ccare.phones p on p.phone_id = a.phone_id\n" +
	// "where ms.organization_id in\n" +
	// "      (select t.organization_id from ccare.main_services t\n" +
	// "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
	// +
	// "       connect by prior t.organization_id = t.main_master_id\n" +
	// "       )\n" +
	// "and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
	// +
	// "and length(p.phone)>6 and ms.service_id = 3 and ms.deleted = 0 and p.phone in (\n"
	// +
	// "      select tt.phone from ccare.contractor_phones tt\n" +
	// "             inner join ccare.contracts c on c.contract_id = tt.contract_id\n"
	// +
	// "      where tt.contract_id = ? and tt.deleted = 0 and c.organization_id = a.organization_id and c.deleted = 0\n"
	// +
	// ")";

	public static final String Q_GET_PHONE_LIST_BY_ORGANIZATION_ID = "select distinct p.phone from ccare.main_services ms\n"
			+ "       inner join ccare.abonents a on a.organization_id = ms.organization_id\n"
			+ "       inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where ms.organization_id in\n"
			+ "      (select t.organization_id from ccare.main_services t\n"
			+ "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
			+ "       connect by prior t.organization_id = t.main_master_id\n"
			+ "       )\n"
			+ "       and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
			+ "       and length(p.phone)>6\n"
			+ "       and ms.service_id = 3 and ms.deleted = 0";

	public static final String Q_GET_PHONE_LIST_BY_ORGANIZATION_ID1111 = "select\n"
			+ "  distinct p.phone\n"
			+ "from ccare.main_orgs mo\n"
			+ "inner join ccare.main_services ms on ms.organization_id = mo.organization_id\n"
			+ "inner join ccare.main_details md on md.organization_id = mo.organization_id\n"
			+ "inner join ccare.abonents a on a.main_detail_id = md.main_detail_id\n"
			+ "inner join ccare.phones p on p.phone_id = a.phone_id\n"
			+ "where mo.organization_id in\n"
			+ "      (select\n"
			+ "         t.organization_id\n"
			+ "       from ccare.main_services t\n"
			+ "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
			+ "       connect by prior t.organization_id = t.main_master_id\n"
			+ "      )\n"
			+ "      and md.main_detail_type_id in (5, 30, 59, 60, 61)\n"
			+ "      and p.deleted = 0 and a.deleted = 0 and md.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
			+ "      and length(p.phone)>4\n"
			+ "      and ms.service_id = 3 and ms.deleted = 0";

	public static final String Q_DELETE_CONTRACT_PRICES = "delete from ccare.contract_price_items t where t.contract_id = ? ";
	public static final String Q_DELETE_OLD_STREET_NAMES = "delete from street_old_names t where t.street_id = ? ";
	public static final String Q_DELETE_CONTRACT_PHONES = "delete from ccare.contractor_phones t where t.contract_id = ? ";
	public static final String Q_DELETE_BLOCKLIST_PHONES = "delete from ccare.block_list_phones t where t.block_list_id = ? ";
	public static final String Q_DELETE_BILLINGCOMP = "delete from ccare.BILLING_COMPANIES t where t.billing_company_id = ? ";
	public static final String Q_DELETE_BILLINGCOMP_IND = "delete from ccare.BILLING_COMPANIES_IND t where t.billing_company_id = ? ";

	public static final String Q_REMOVE_PHONE_FROM_AST_DB = " delete from asteriskcdrdb.block where code = ? and proriti = ? and len = ? ";
	public static final String Q_ADD_PHONE_INTO_AST_DB = " insert into asteriskcdrdb.block (code,proriti,len) values (?, ?, ?) ";

	public static final String Q_GET_CONTRACTOR_ADV_PRICE = " select sum(nvl(t.price,0)) as advPrice from ccare.contract_price_items t where t.contract_id = ? and t.call_count_start<=? and t.call_count_end > ? ";

	public static final String Q_GET_CONTRACTOR_INFO = "select distinct c.contract_id,\n"
			+ "       ccare.date_to_msec(c.start_date) as start_date,\n"
			+ "       ccare.date_to_msec(c.end_date) as end_date,\n"
			+ "       c.critical_number,\n"
			+ "       c.is_budget,\n"
			+ "       c.price_type,\n"
			+ "       c.price,\n"
			+ "       c.block, \n"
			+ "       c.organization_id,\n"
			+ "       c.main_detail_id \n"
			+ "  from ccare.phones t\n"
			+ " inner join ccare.abonents a on a.phone_id = t.phone_id\n"
			+ " inner join ccare.contracts c on c.organization_id = a.organization_id\n"
			+ " where t.phone = ? and a.deleted = 0 and c.deleted = 0 ";
	
	public static final String Q_GET_IS_UNKNOWN_PHONE_NUMBER = 
				"select sum(m.cnt) as cnt\n" +
				"  from (select /*+ use_nl(pn, sp, dp) */\n" + 
				"         count(8) as cnt\n" + 
				"          from ccare.phone_numbers pn\n" + 
				"         where pn.phone = ? \n" + 
				"           and (exists\n" + 
				"                (select 1\n" + 
				"                   from ccare.subscriber_to_phones sp\n" + 
				"                  where sp.phone_number_id = pn.phone_number_id) or exists\n" + 
				"                (select 1\n" + 
				"                   from ccare.organization_depart_to_phones sp\n" + 
				"                  where sp.phone_number_id = pn.phone_number_id))\n" + 
				"        union all\n" + 
				"        select count(8) as cnt\n" + 
				"          from ccare.unknown_phone_numbers u\n" + 
				"         where u.phone_number = ? ) m ";


	public static final String INS_UNKNOWN_NUMBER = 
				"insert into ccare.unknown_phone_numbers\n" +
				"  (phone_number_id, phone_number)\n" + 
				"values\n" + 
				"  (SEQ_UNKNOWN_PHONE_NUMBER.Nextval, ?)";
	
	public static final String DEL_UNKNOWN_NUMBER = "delete from ccare.unknown_phone_numbers t where t.phone_number_id = ?";




	public static final String Q_GET_CALL_CENTER_REQ_MSG = "select t.description from ccare.descriptions t where t.description_id = 57100 ";

	public static final String Q_GET_OPERATOR_REMARKS = 
					"select count(1)\n" +
					"  from ccare.operator_warns t\n" + 
					" where t.operator = ? \n" + 
					"   and trunc(t.warn_send_date) >= trunc(sysdate - 10)\n" + 
					"   and t.hidden = 0\n" + 
					"   and t.delivered = 0";

	public static final String Q_GET_SPECIAL_TEXT_BY_NUMBER = " select t.note from ALERTS_BY_PHONE t where t.phone_number = ? ";
	public static final String Q_GET_NON_CHARGE_ABONENT = "select count(t.phone_number) from FREE_OF_CHARGE_PHONE t where t.phone_number = ? and trunc(sysdate) between t.start_date and t.end_date ";
	public static final String Q_GET_NON_CHARGE_ABONENT_REMARK = "select t.remark from FREE_OF_CHARGE_PHONE t where t.phone_number = ? and trunc(sysdate) between t.start_date and t.end_date ";
	public static final String Q_GET_MOBITEL_NOTE = "select t.description from ccare.descriptions t where t.description_id = 56101 ";
	public static final String Q_GET_TREATMENT = " select treatment, gender from treatments where phone_number = ? ";
	public static final String Q_GET_ORG_ABONENT = 
			"select /*+ use_nl(ph, dtp, od, o)*/\n" +
					" o.organization_id\n" + 
					",o.organization_name\n" + 
					",decode(to_char(o.found_date, 'dd/mm'), to_char(sysdate, 'dd/mm'), 1, 0) as bid\n" + 
					"  from ccare.phone_numbers                 pn\n" + 
					"      ,ccare.organization_depart_to_phones dtp\n" + 
					"      ,ccare.organization_department       od\n" + 
					"      ,ccare.organizations                 o\n" + 
					" where dtp.phone_number_id = pn.phone_number_id\n" + 
					"   and od.org_department_id = dtp.org_department_id\n" + 
					"   and o.organization_id = od.organization_id\n" + 
					"   and o.parrent_organization_id is null\n" + 
					"   and pn.phone = ? \n" + 
					"   and rownum < 2";

	public static final String Q_GET_WEB_SESSION_ID = " select (to_number(to_char(sysdate,'YYMM'))*1000000 + log_calls_seq.nextval) AS sessionID from dual ";

	public static final String Q_GET_DEP_LIST_BY_ORG = "select distinct * from (\n"
			+ "SELECT md.main_detail_id,\n"
			+ "       md.main_detail_master_id,\n"
			+ "       md.deleted,\n"
			+ "       md.fields_order,\n"
			+ "       case\n"
			+ "         when main_detail_type_id = 5 then\n"
			+ "          md.main_detail_geo\n"
			+ "         when main_detail_type_id = 61 then\n"
			+ "          '...' || md.main_detail_geo\n"
			+ "         when main_detail_type_id = 60 then\n"
			+ "          '.........' || md.main_detail_geo\n"
			+ "         when main_detail_type_id = 59 then\n"
			+ "          '...............' || md.main_detail_geo\n"
			+ "         when main_detail_type_id = 30 then\n"
			+ "          '.....................' || md.main_detail_geo\n"
			+ "         else\n"
			+ "          md.main_detail_geo\n"
			+ "       end as main_detail_geo,\n"
			+ "       md.main_detail_eng,\n"
			+ "       md.main_detail_note_eng,\n"
			+ "       md.main_detail_note_geo,\n"
			+ "       md.main_detail_type_id,\n"
			+ "       md.organization_id,\n"
			+ "       md.old_id,\n"
			+ "       md.rec_user,\n"
			+ "       md.rec_date,\n"
			+ "       md.upd_user,\n"
			+ "       md.upd_date,\n"
			+ "       getorgdeptphones(main_detail_id) as phones\n"
			+ "  FROM ccare.main_details md\n"
			+ " start with md.organization_id = ? and md.deleted = 0 and md.main_detail_geo like ? \n"
			+ " connect by prior md.main_detail_master_id = md.main_detail_id\n"
			+ "union all\n"
			+ "SELECT md.main_detail_id,\n"
			+ "       md.main_detail_master_id,\n"
			+ "       md.deleted,\n"
			+ "       md.fields_order,\n"
			+ "       case\n"
			+ "         when main_detail_type_id = 5 then\n"
			+ "          md.main_detail_geo\n"
			+ "         when main_detail_type_id = 61 then\n"
			+ "          '...' || md.main_detail_geo\n"
			+ "         when main_detail_type_id = 60 then\n"
			+ "          '.........' || md.main_detail_geo\n"
			+ "         when main_detail_type_id = 59 then\n"
			+ "          '...............' || md.main_detail_geo\n"
			+ "         when main_detail_type_id = 30 then\n"
			+ "          '.....................' || md.main_detail_geo\n"
			+ "         else\n"
			+ "          md.main_detail_geo\n"
			+ "       end as main_detail_geo,\n"
			+ "       md.main_detail_eng,\n"
			+ "       md.main_detail_note_eng,\n"
			+ "       md.main_detail_note_geo,\n"
			+ "       md.main_detail_type_id,\n"
			+ "       md.organization_id,\n"
			+ "       md.old_id,\n"
			+ "       md.rec_user,\n"
			+ "       md.rec_date,\n"
			+ "       md.upd_user,\n"
			+ "       md.upd_date,\n"
			+ "       getorgdeptphones(main_detail_id) as phones\n"
			+ "  FROM ccare.main_details md\n"
			+ " start with md.organization_id = ? and md.deleted = 0 and md.main_detail_geo like ? \n"
			+ " connect by prior md.main_detail_id = md.main_detail_master_id\n"
			+ ") tt\n" + "order by tt.fields_order\n" + "";

	public static final String Q_GET_DISCOVERY_LIST = "select\n"
			+ "  tt.survey_kind_name,\n"
			+ "  r.survey_reply_type_name,\n"
			+ "  t.survey_id,\n"
			+ "  t.session_call_id,\n"
			+ "  t.phone,\n"
			+ "  t.survey_descript,\n"
			+ "  t.survey_phone,\n"
			+ "  t.survey_kind_id,\n"
			+ "  t.rec_date,\n"
			+ "  t.rec_user,\n"
			+ "  t.deleted,\n"
			+ "  t.upd_date,\n"
			+ "  t.upd_user,\n"
			+ "  t.survey_reply_type_id,\n"
			+ "  t.contact_person,\n"
			+ "  t.survery_responce_status,\n"
			+ "  t.ccr,\n"
			+ "  t.survey_done\n"
			+ "from ccare.discover t\n"
			+ "       inner join ccare.survey_kind tt on tt.survey_kind_id = t.survey_kind_id\n"
			+ "       left join ccare.discover_rtypes r on r.survey_reply_type_id = t.survey_reply_type_id\n"
			+ "where t.survey_kind_id <> 4 and t.survery_responce_status <>0 and trunc(t.rec_date) = trunc(sysdate)\n"
			+ "";

	public static final String Q_GET_TRANSPORT_BY_ID = "select\n"
			+ "        getdaysdescription(t.days) as days_descr,\n"
			+ "        tt.name_descr transport_type,\n"
			+ "        dep_c.town_name||' '||dep_st.name_descr as departure_station,\n"
			+ "        arr_c.town_name||' '||arr_st.name_descr as arrival_station,\n"
			+ "        tc.name_descr as transport_company,\n"
			+ "        tr.name_descr as transport_resource\n"
			+ "\n"
			+ "from transp_schedules t, transp_types tt,transp_stations dep_st, towns dep_c,transp_stations arr_st, towns arr_c, transp_companies tc, transp_resource tr\n"
			+ "where\n"
			+ "          t.transp_type_id = tt.transp_type_id and\n"
			+ "          t.depart_transp_stat_id = dep_st.transp_stat_id and dep_st.town_id = dep_c.town_id and\n"
			+ "          t.arrival_transp_stat_id = arr_st.transp_stat_id and arr_st.town_id = arr_c.town_id and\n"
			+ "          t.transp_comp_id = tc.transp_comp_id(+) and\n"
			+ "          t.transp_res_id = tr.transp_res_id and\n"
			+ "          t.transp_schedule_id = ? \n" + "order by t.days";;

	public static final String Q_GET_SESSION_BY_ID = "select * from ccare.log_sessions t where t.session_id = ? ";
	public static final String Q_GET_PERS_NOTE_BY_ID = "select t.note_id from ccare.log_personell_notes t where t.note_id = ? ";
	public static final String Q_GET_PERS_NOTE_BY_ID_ADV = "select t.note_id,\n"
			+ "       t.session_id,\n"
			+ "       r.user_name || ' ( ' || r.user_firstname || ' ' ||\n"
			+ "       r.user_lastname || ' ) ' as receiver,\n"
			+ "       s.user_name || ' ( ' || s.user_firstname || ' ' ||\n"
			+ "       s.user_lastname || ' ) ' as sender,\n"
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
			+ "  from log_personell_notes t, users r, users s\n"
			+ " where t.note_id = ? \n"
			+ "   and t.user_name = r.user_name(+)\n"
			+ "   and t.rec_user = s.user_name(+)";

	public static final String Q_CHECK_COUNTRY_FK = "select count(t1.country_id) as cnt, 1 as n\n"
			+ "  from country_indexes t1\n"
			+ " where country_id = ? \n"
			+ "union all\n"
			+ "select count(t2.country_id) as cnt, 2 as n\n"
			+ "  from currency t2\n"
			+ " where t2.country_id = ? \n"
			+ "union all\n"
			+ "select count(t3.country_id) as cnt, 3 as n\n"
			+ "  from towns t3\n"
			+ " where t3.country_id = ? \n"
			+ "union all\n"
			+ "select count(t4.country_id) as cnt, 4 as n\n"
			+ "  from c_regional_centers t4\n" + " where country_id = ?";
	
	public static final String Q_CHECK_FIRSTNAME_FK = "select count(1) as cnt, 1 as n \n"
			+ "  from subscribers t\n"
			+ " where name_id = ? \n";
	
	public static final String Q_CHECK_LASTNAME_FK = "select count(1) as cnt, 1 as n \n"
			+ "  from subscribers t\n"
			+ " where family_name_id = ? \n";

	public static final String Q_CHECK_TOWN_FK =

	"select count(t1.town_id) as cnt, 'ქალაქის რეგიონები' as n\n"
			+ "  from town_district t1\n"
			+ " where town_id = ? \n"
			+ "union all\n"
			+ "select count(t2.town_id) as cnt, 'ქუჩების რეგიონები' as n\n"
			+ "  from street_to_town_districts t2\n"
			+ " where t2.town_id = ? \n"
			+ "union all\n"
			+ "select count(t3.town_id) as cnt, 'ქუჩების ძველი სახელები' as n\n"
			+ "  from street_old_names t3\n" + " where t3.town_id = ? \n"
			+ "union all\n"
			+ "select count(t4.town_id) as cnt, 'ქუჩები' as n\n"
			+ "  from streets t4\n" + " where town_id = ? \n" + "union all\n"
			+ "select count(t5.town_id) as cnt, 'მისამართები' as n\n"
			+ "  from addresses t5\n" + " where town_id = ?";

	public static final String Q_CHECK_TOWN_DISTRICT_FK =

	"select count(t2.town_district_id) as cnt, 'ქუჩების რეგიონები' as n\n"
			+ "  from street_to_town_districts t2\n"
			+ " where t2.town_district_id = ? \n" + "union all\n"
			+ " select count(t5.town_district_id) as cnt, 'მისამართები' as n\n"
			+ "  from addresses t5\n" + " where t5.town_district_id = ?";

	public static final String Q_CHECK_STREET_FK = "select count(t2.street_id) as cnt, 'ქუჩების ძველი სახელები' as n\n"
			+ "  from street_old_names t2\n"
			+ " where t2.street_id = ?\n"
			+ "union all\n"
			+ "select count(t3.street_id) as cnt, 'ქუჩების ინდექსები' as n\n"
			+ "  from street_indexes t3\n"
			+ " where t3.street_id = ?\n"
			+ "union all\n"
			+ "select count(t4.street_id) as cnt, 'ტრანსპორტები' as n\n"
			+ "  from public_transp_dir_street t4\n"
			+ " where street_id = ?\n"
			+ "union all\n"
			+ "select count(t5.street_id) as cnt, 'მისამართები' as n\n"
			+ "  from addresses t5\n" + " where street_id = ?";

	public static final String Q_CHECK_STREET_HIDE_FK = "select count(t3.street_id) as cnt, 'ქუჩების ინდექსები' as n\n"
			+ "  from street_indexes t3\n"
			+ " where t3.street_id = ?\n"
			+ "union all\n"
			+ "select count(t4.street_id) as cnt, 'ტრანსპორტები' as n\n"
			+ "  from public_transp_dir_street t4\n"
			+ " where street_id = ?\n"
			+ "union all\n"
			+ "select count(t5.street_id) as cnt, 'მისამართები' as n\n"
			+ "  from addresses t5\n" + " where street_id = ?";

	public static final String Q_GET_ALL_USERS = "select * from (\n"
			+ "select t.user_id,\n"
			+ "       t.user_firstname,\n"
			+ "       t.user_lastname,\n"
			+ "       t.user_name,\n"
			+ "       to_number(substr(t.user_name,3,length(t.user_name))) as orderBy\n"
			+ "from users t\n" + "where substr(t.user_name,0,2) = 'op'\n"
			+ "order by orderBy)\n" + "union all\n" + "select * from (\n"
			+ "select t.user_id,\n" + "       t.user_firstname,\n"
			+ "       t.user_lastname,\n" + "       t.user_name,\n"
			+ "       0\n" + "from users t\n"
			+ "where substr(t.user_name,0,2) <> 'op'\n"
			+ "order by t.user_name)\n" + "";


	// Herio bichebo
	public static final String Q_GET_FIRST_NAME_COUNT = " select count(1) from ccare.firstnames t where t.firstname = ? ";
	public static final String Q_GET_LAST_NAME_COUNT = " select count(1) from ccare.lastnames t where t.lastname = ? ";
	public static final String Q_GET_FIRST_NAME_COUNT_ALL = " select count(1) from ccare.firstnames t ";
	public static final String Q_GET_FIRST_NAMES_ALL = " select t.firstname, t.firstname_id from ccare.firstnames t order by t.firstname ";
	public static final String Q_GET_LAST_NAMES_ALL = " select t.deleted,decode(t.deleted, 0, 'აქტიური', 'გაუქმებული') as deletedText,t.lastname, t.lastname_id,t.rec_date from ccare.lastnames t where t.deleted=0 order by t.lastname ";
	public static final String Q_GET_DEPARTMENTS = " select * from departments t order by 1 ";
	public static final String Q_GET_USER_PERMISSIONS = " select t.permission_id from user_permission t where t.user_id = ? ";
	public static final String Q_DELETE_USER_PERMISSIONS = " delete from user_permission t where t.user_id = ? ";

	public static final String Q_GET_ALL_CITY_REGIONS = "select t.town_district_id, t.town_id, t.town_district_name, t.deleted\n"
			+ "  from city_regions t\n"
			+ " where t.deleted = 0\n"
			+ " order by 3";

	public static final String Q_GET_FIRST_NAME_BY_ID = "select t.firstname_id,\n"
			+ "       t.firstname \n"
			+ "from ccare.firstnames t\n" + "where t.firstname_id = ? ";

	public static final String Q_GET_LAST_NAME_BY_ID = "select t.lastname_id,\n"
			+ "       t.lastname \n"
			+ "from ccare.lastnames t\n" + "where t.lastname_id = ? ";


	public static final String Q_GET_STREET_BY_ID = "select\n"
			+ "  t.town_id,\n" + "  t.street_id,\n" + "  t.street_name,\n"
			+ "  t.street_name_eng,\n" + "  t.map_id,\n" + "  t.rec_date,\n"
			+ "  t.rec_user,\n" + "  t.street_note_eng,\n" + "  t.deleted,\n"
			+ "  t.street_location,\n" + "  t.street_location_eng,\n"
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

	public static final String Q_GET_STREET_ALL = "select\n" + "  t.town_id,\n"
			+ "  t.street_id,\n" + "  t.street_name,\n"
			+ "  t.street_name_eng,\n" + "  t.map_id,\n" + "  t.rec_date,\n"
			+ "  t.rec_user,\n" + "  t.street_note_eng,\n" + "  t.deleted,\n"
			+ "  t.street_location,\n" + "  t.street_location_eng,\n"
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

	public static final String Q_GET_STREET_DISTRICTS_ALL =

	"select t.street_to_town_district_id\n" + "      ,t.street_id\n"
			+ "      ,t.town_district_id\n" + "      ,c.town_district_name\n"
			+ "  from ccare.street_to_town_districts t\n"
			+ " inner join ccare.town_district c\n"
			+ "    on c.town_district_id = t.town_district_id\n"
			+ " where t.street_to_town_district_id is not null\n"
			+ "   and t.street_id is not null\n"
			+ "   and t.town_district_id is not null\n"
			+ "   and c.town_district_name is not null";

	// inserts
	public static final String Q_INSERT_PERS_NOTES = "BEGIN insert into ccare.log_personell_notes t\n"
			+ "(t.ym,t.session_id,t.user_name,t.note,t.rec_user,t.rec_date,t.visible_options,t.phone,t.call_date,t.particular)\n"
			+ "values (?,?,?,?,?,?,?,?,?,?) returning id into ?; END; ";

	public static final String Q_INSERT_SESSION = "insert into call_sessions\n"
			+ "  (call_session_id, year_month, uname, call_phone, session_id, call_kind)\n"
			+ "values\n" + "  (Seq_Call_Session_Id.Nextval, ?, ?, ?, ?, ?";
	// updates
	public static final String Q_UPDATE_PERS_NOTES = "update ccare.log_personell_notes t set t.note = ?, t.visible_options = ?, t.particular = ?\n"
			+ "where t.note_id = ? ";

	public static final String Q_UPDATE_FIRST_NAME = "update ccare.firstnames t set t.firstname = ?, t.upd_user = ? where t.firstname_id = ? ";
	public static final String Q_UPDATE_LAST_NAME = "update ccare.lastnames t set t.lastname = ?, t.upd_user = ? where t.lastname_id = ? ";

	public static final String Q_UPDATE_FIRST_NAME_STATUS = "update ccare.firstnames t set t.deleted = ? , t.upd_user = ? where t.firstname_id = ? ";
	public static final String Q_UPDATE_LAST_NAME_STATUS = "update ccare.lastnames t set t.deleted = ? , t.upd_user = ? where t.lastname_id = ? ";

	public static final String Q_UPDATE_LOCK_STATUS = " update ccare.call_record_lock t set t.LOCK_STATUS = 1 where t.CALL_SESSION = ? ";

	public static final String Q_UPDATE_SESSION_QUALITY = " update call_sessions t set t.call_quality = ? where t.call_session_id = ? ";

	public static final String Q_UPDATE_ABONENT = " update ccare.abonents t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.abonent_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE = " update ccare.main_services t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.organization_id = ? ";

	public static final String Q_UPDATE_MAIN_ADDRESS = " update ccare.main_address t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.address_id = ? and t.organization_id = ? ";

	// public static final String Q_UPDATE_PHONES =
	// " update ccare.phones t set t.deleted = ?, t.upd_user = ? where t.abonent_id = ? ";

	public static final String Q_UPDATE_USER = " update users t set\n"
			+ "       t.user_firstname = ?,\n"
			+ "       t.user_lastname = ?,\n" + "       t.user_name = ?,\n"
			+ "       t.user_password = ?,\n"
			+ "       t.department_id = ? where t.user_id = ?";

	public static final String Q_UPDATE_COUNTRY = "update ccare.countries t set\n"
			+ "       t.country_name = ?,\n"
			+ "       t.phone_code = ?,\n"
			+ "       t.continent_id = ?\n" + "where t.country_id = ?";

	public static final String Q_DELETE_USER = " delete from users t \n"
			+ "where t.user_id = ? ";

	public static final String Q_DELETE_COUNTRY = "delete from ccare.countries t \n"
			+ " where t.country_id = ?";

	public static final String Q_UPDATE_TOWN = "update towns t set\n"
			+ "       t.town_name = ?, \n"
			+ "       t.country_id = ?, t.town_type_id = ?,\n"
			+ "       t.normal_gmt = ?, t.winter_gmt = ?,\n"
			+ "       t.capital_town = ?, \n" + "       t.town_code = ?, \n"
			+ "       t.town_new_code = ? where t.town_id = ?";

	public static final String Q_DELETE_TOWN = "delete from towns t \n"
			+ "       where t.town_id = ?";

	public static final String Q_UPDATE_STREET_TYPE = "update ccare.street_types t set t.street_type_name_geo = ?, t.street_type_name_eng = ?, t.upd_user = ? where t.street_type_id = ? ";

	public static final String Q_UPDATE_STREET_TYPE_STATUS = "update ccare.street_types t set t.deleted = ?, t.upd_user = ? where t.street_type_id = ? ";

	public static final String Q_UPDATE_STREET_DESCR = " update ccare.street_descr t set t.street_descr_name_geo = ?, t.street_descr_name_eng = ?, t.upd_user = ? where t.street_descr_id = ? ";

	public static final String Q_UPDATE_STREET_DESCR_STATUS = "update ccare.street_descr t set t.deleted = ?, t.upd_user = ? where t.street_descr_id = ? ";

	public static final String Q_UPDATE_STREET = " update ccare.streets t set\n"
			+ "        t.town_id = ?,\n"
			+ "        t.street_name = ?,\n"
			+ "        t.street_location = ?,\n"
			+ "        t.upd_user = ?,\n"
			+ "        t.record_type = ?,\n"
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

	public static final String Q_DELETE_PHONES_BY_SUBSCRIBER = "delete from ccare.SUBSCRIBER_TO_PHONES t where t.SUBSCRIBER_ID = ? ";
	public static final String Q_SUBSCRIBER_ADDRESS_ID = "select s.addr_id from subscribers s where s.subscriber_id=? ";
	public static final String Q_DELETE_SUBSCRIBER_ADDRESS = "delete from addresses a where a.addr_id= ? ";
	public static final String Q_DELETE_SUBSCRIBER = "delete subscribers s where s.subscriber_id=?";

	public static final String Q_DELETE_USER_PERMISSION = " delete from user_permission t where t.user_id = ? ";

	public static final String Q_DELETE_STREET_DISCTRICTS_BY_STREET_ID = " delete from ccare.street_to_town_districts t where t.street_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE_SORT = " update ccare.main_services t set t.priority = ?,t.upd_user = ?,t.upd_date = ? where t.organization_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE_DEL_STAT = " update ccare.main_services t set t.deleted = ?,t.upd_user = ?,t.upd_date = ? where t.organization_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE_HIST = " update ccare.main_services t set t.upd_user = ?,t.upd_date = ? where t.organization_id = ? ";

	public static final String Q_UPDATE_MAIN_ORG_STATUS = " update ccare.main_orgs t set t.statuse = ? where t.organization_id = ? ";

	public static final String Q_GET_MAIN_ORG_BY_ID = "select ms.organization_id,\n"
			+ "       ms.main_master_id,\n"
			+ "       mo1.org_name,\n"
			+ "       mo1.identcode,\n"
			+ "       mo1.director,\n"
			+ "       mo1.legaladdress,\n"
			+ "       ms.deleted,\n"
			+ "       mo1.note,\n"
			+ "       mo1.workinghours,\n"
			+ "       mo1.founded,\n"
			+ "       mo1.mail,\n"
			+ "       mo1.org_info,\n"
			+ "       mo1.webaddress,\n"
			+ "       mo1.contactperson,\n"
			+ "       mo1.dayoffs,\n"
			+ "       mo1.workpersoncountity,\n"
			+ "       mo1.ind,\n"
			+ "       mo1.org_name_eng,\n"
			+ "       mo1.new_identcode,\n"
			+ "       mo1.legal_statuse_id,\n"
			+ "       mo1.statuse,\n"
			+ "       str.street_name || ' ' || ma.address_suffix_geo as real_address\n"
			+ "  from ccare.main_services ms,\n"
			+ "       ccare.main_orgs     mo1,\n"
			+ "       main_address       ma,\n"
			+ "       streets            str\n"
			+ " where mo1.organization_id = ms.organization_id\n"
			+ "   and ma.organization_id = ms.organization_id\n"
			+ "   and str.street_id = ma.street_id\n"
			+ "   and ms.service_id = 3\n"
			+ "   and ms.organization_id = ?\n"
			+ " order by ms.priority";
	public static final String Q_DELETE_TRANSPORT_ITEMS = "delete from transp_items t where t.transp_schedule_id = ? ";

	public static final String Q_GET_STAFF_EDUCATION_ACTIONS = "select *\n"
			+ "  from (select u.column_value as id, 'delete' as command\n"
			+ "          from (select to_char(t.staff_education_id) as column_value\n"
			+ "                  from ccare.staff_education t\n"
			+ "                 where t.staff_id = ? \n"
			+ "                minus\n"
			+ "                select column_value\n"
			+ "                  from table(split_table(?, ','))) u\n"
			+ "        union all\n"
			+ "        select p.column_value as id, 'insert' as command\n"
			+ "          from (select column_value\n"
			+ "                  from table(split_table(?, ','))\n"
			+ "                minus\n"
			+ "                select to_char(t.staff_education_id) as column_value\n"
			+ "                  from ccare.staff_education t\n"
			+ "                 where t.staff_id = ?) p\n"
			+ "        union all\n"
			+ "        select to_char(t.staff_education_id) as id, 'update' as command\n"
			+ "          from ccare.staff_education t\n"
			+ "         where t.staff_id = ?\n"
			+ "           and exists\n"
			+ "         (select column_value\n"
			+ "                  from table(split_table(?, ','))\n"
			+ "                 where column_value = t.staff_education_id)) y\n"
			+ " where y.id is not null  order by y.command";

	public static final String Q_DELETE_STAFF_EDUCATION = "delete from ccare.staff_education t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_COMPUTER_SKILLS = "delete from ccare.staff_computer_skills t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_LANGUAGES = "delete from ccare.staff_languages t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_PHONES = "delete from ccare.staff_phones t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_WORKS = "delete from ccare.staff_works t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_RELATIVE_09 = "delete from ccare.staff_relative_09 t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_FAMOUS_PEOPLE = "delete from ccare.staff_famous_people t \n "
			+ "where t.staff_id = ?";
	

}
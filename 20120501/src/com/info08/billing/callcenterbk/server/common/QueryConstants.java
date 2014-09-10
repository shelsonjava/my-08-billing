package com.info08.billing.callcenterbk.server.common;

public interface QueryConstants {
	
	
	public static final String Q_GET_CALL_KIND = " select nvl(max(t.call_kind),0) from ccare.call_sessions t where t.call_session_id = ? ";
	public static final String Q_GET_CALL_OPERATOR_SRC = " select to_number(nvl(max(t.operator_src),11808)) as operator_src from ccare.call_sessions t where t.call_session_id = ? ";
	
	
	public static final String Q_GET_CC_USER_NEWS_CNT = 
					"select count(1) from ccare.call_center_news t\n" +
							"where t.call_center_news_id >= 2514002 and not exists ( \n" + 
							"  select 1 from ccare.user_cc_news_status tt where tt.news_id = t.call_center_news_id and tt.user_id = ? \n" + 
				     ")";
	
	public static final String Q_UPDATEORG_DEP_PHONE = " update ccare.organization_depart_to_phones t set t.org_department_id = ?, t.phone_number_id = ?, t.hidden_by_request = ?, t.phone_contract_type = ?, "+
            									" t.for_contact = ?, t.phone_order = ?, t.rec_upd_date = ? "+
            								    " where t.org_dep_to_ph_id = ? ";                                                 
	
	public static final String Q_UPDATE_PHONE = " update ccare.phone_numbers t set t.phone = ?, t.phone_state_id = ?, t.phone_type_id = ?, t.is_parallel = ? where t.phone_number_id = ? ";
	
	public static final String Q_GET_PHONE_ORG_BIRTHDAY = "select count(1)\n" +
			"from ccare.phone_numbers t\n" + 
			"       inner join ccare.organization_depart_to_phones dp on dp.phone_number_id = t.phone_number_id\n" + 
			"       inner join ccare.organization_department od on od.org_department_id = dp.org_department_id\n" + 
			"       inner join ccare.organizations o on o.organization_id = od.organization_id\n" + 
			"where t.phone = ? and dp.phone_contract_type = 53102 and t.is_parallel = 0\n" + 
			"      and o.found_date is not null and to_char(o.found_date,'DDMM') = to_char(trunc(sysdate),'DDMM')\n" + 
			"      and t.phone not like '790%' and t.phone not like '5%' and dp.hidden_by_request = 0";

	
	public static final String Q_GET_PHONE_FREE_OF_CHARGE =" select t.remark from free_of_charge_phone t where t.phone_number = ? and t.operator_src = ? and sysdate between t.start_date and t.end_date ";
	
	
	public static final String Q_UPDATE_STREET_TRUXA =" update ccare.addresses t set t.town_id = t.town_id where t.street_id = ? ";
	
	
	public static final String Q_GET_CALL_PRICE_NEW = "select getChargePriceNew(?,?,?,?) as price from dual";
	
	public static final String Q_GET_VIRTUAL_SESSION_ID = " select 'VIRT.'||seq_virtual_session_id.nextval as session_id from dual ";

	public static final String Q_GET_BILLING_COMP_IND = "select count(1) from ccare.BILLING_COMPANIES_IND t\n" +
					"       inner join ccare.billing_companies c on c.billing_company_id = t.billing_company_id\n" + 
					"where ? between t.bill_index_start and t.bill_index_end and c.operator_src = ? ";

	public static final String Q_GET_BILLING_COMP_IND_BY_ID = "select count(1) from BILLING_COMPANIES_IND t\n" +
					"       inner join ccare.billing_companies c on c.billing_company_id = t.billing_company_id\n" + 
					"where ? between t.bill_index_start and t.bill_index_end and t.billing_company_id <> ? and c.operator_src = ? ";


	public static final String Q_GET_BILLING_COMP_BILL_BY_DAY = "select\n"
			+ "      t.phone as phonea,\n"
			+ "     '11808' as phoneb,\n"
			+ "      ch.rec_date as charge_date,\n"
			+ "      to_char(ch.rec_date,'DD/MM/YYYY HH24:MI:SS') as charge_date1,\n"
			+ "      60 as duration,\n"
			+ "      ch.price/100 as rate,\n"
			+ "      ch.price/100 as price\n"
			+ "from log_sessions t, log_session_charges ch\n"
			+ "where\n"
			+ "     t.session_id  = ch.session_id and\n"
			+ "     trunc(t.start_date) = trunc(?) and\n"
			+ "     trunc(ch.rec_date) = trunc(?) and\n"
			+ "      exists (\n"
			+ "        select\n"
			+ "          to_number(decode(length(i.bill_index_start),7,'32'||i.bill_index_start,i.bill_index_start)),\n"
			+ "          to_number(decode(length(i.bill_index_end),7,'32'||i.bill_index_start,i.bill_index_end))\n"
			+ "        from BILLING_COMPANIES tc\n"
			+ "               inner join BILLING_COMPANIES_IND i on i.billing_company_id = tc.billing_company_id\n"
			+ "        where tc.billing_company_id = ? \n"
			+ "              and to_number(t.phone) >= to_number(decode(length(i.bill_index_start),7,'32'||i.bill_index_start,i.bill_index_start))\n"
			+ "              and to_number(t.phone) <= to_number(decode(length(i.bill_index_end),7,'32'||i.bill_index_end,i.bill_index_end))\n"
			+ "      )\n"
			+ "      and getcontractorpricenew(t.phone)=-999999999\n"
			+ "      and t.phone like '322%'";

	public static final String Q_GET_BILLING_COMP_BILL_BY_YM = "select substr(t.phone,3, length(t.phone)) as phonea,\n"
			+ "   '11808' as phoneb,\n"
			+ "    ch.rec_date as charge_date,\n"
			+ "    to_char(ch.rec_date,'DD/MM/YYYY HH24:MI:SS') as charge_date1,\n"
			+ "    60 as duration,\n"
			+ "    ch.price/100 as rate,\n"
			+ "    ch.price/100 as price\n"
			+ "from log_sessions t, log_session_charges ch\n"
			+ "where\n"
			+ "     t.session_id  = ch.session_id and\n"
			+ "     t.ym = ? and ch.ym = ? and\n"
			+ "      exists (\n"
			+ "        select\n"
			+ "          to_number(decode(length(i.bill_index_start),7,'32'||i.bill_index_start,i.bill_index_start)),\n"
			+ "          to_number(decode(length(i.bill_index_end),7,'32'||i.bill_index_start,i.bill_index_end))\n"
			+ "        from BILLING_COMPANIES tc\n"
			+ "               inner join BILLING_COMPANIES_IND i on i.billing_company_id = tc.billing_company_id\n"
			+ "        where tc.billing_company_id = ? \n"
			+ "              and to_number(t.phone) >= to_number(decode(length(i.bill_index_start),7,'32'||i.bill_index_start,i.bill_index_start))\n"
			+ "              and to_number(t.phone) <= to_number(decode(length(i.bill_index_end),7,'32'||i.bill_index_end,i.bill_index_end))\n"
			+ "      )\n"
			+ "      and getcontractorpricenew(t.phone)=-999999999\n"
			+ "      and t.phone like '322%'";

	public static final String Q_GET_ORG_CALL_CNT_BY_YM = "select nvl(get_contractor_calls_count(to_number(to_char(sysdate,'YYMM')),?), 0) as depCallsCnt from dual";
	public static final String Q_GET_ORG_CALL_CNT_BY_ALL = "select nvl(get_contractor_calls_count(null,?), 0) as depCallsCnt from dual";
	public static final String Q_GET_ORG_CALL_CNT_BY_ALL_SUM = "select nvl(get_contractor_calls_count(null,?,2), 0) as depCallsCnt from dual";

	public static final String Q_GET_DEP_CALL_CNT_BY_YM = "select getCallsByMainDetAndYM(to_number(to_char(sysdate,'YYMM')),?) as depCallsCnt from dual";

	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY_NEW = "select distinct p.phone from (\n"
			+ "       select ms.organization_id from main_services ms\n"
			+ "       start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "       connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "inner join abonents a on a.organization_id = r.organization_id\n"
			+ "inner join phones p on p.phone_id = a.phone_id\n"
			+ "where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "    select p.phone from (\n"
			+ "    select\n"
			+ "      t.organization_id\n"
			+ "    from main_services t\n"
			+ "    start with t.organization_id in (\n"
			+ "          select\n"
			+ "               c.organization_id\n"
			+ "          from contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                select\n"
			+ "                      t.organization_id\n"
			+ "                from main_services t\n"
			+ "                where level>1\n"
			+ "          start with t.organization_id = ? and t.service_id = 3\n"
			+ "          connect by prior t.organization_id = t.main_master_id\n"
			+ "          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "    ) and t.service_id = 3\n"
			+ "    connect by prior t.organization_id = t.main_master_id\n"
			+ "    ) a1\n"
			+ "    inner join abonents a on a.organization_id = a1.organization_id\n"
			+ "    inner join phones p on p.phone_id = a.phone_id\n"
			+ "    where a.deleted = 0 and p.deleted = 0\n"
			+ ") and p.phone not in (\n"
			+ "    select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "    from abonents a\n"
			+ "         inner join phones p on p.phone_id = a.phone_id\n"
			+ "    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "          (select\n"
			+ "                 t.main_detail_id\n"
			+ "           from main_details t\n"
			+ "           start with t.main_detail_id in\n"
			+ "                 (select\n"
			+ "                        c.main_detail_id\n"
			+ "                  from contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                        (select\n"
			+ "                               t.organization_id\n"
			+ "                         from main_services t\n"
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
			+ "from abonents a\n"
			+ "   inner join phones p on p.phone_id = a.phone_id\n"
			+ "where a.organization_id in (\n"
			+ "      select\n"
			+ "         t.organization_id\n"
			+ "      from main_services t\n"
			+ "      start with t.organization_id = ? and t.service_id = 3\n"
			+ "      connect by prior t.organization_id = t.main_master_id\n"
			+ " )\n"
			+ " and p.deleted = 0 and a.deleted = 0 and p.phone in (select tt.phone\n"
			+ "                         from block_list_phones tt\n"
			+ "                        where tt.block_list_id = ? )";

	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY_EXCEPT_BLOCK_LIST = " select\n"
			+ "   distinct p.phone\n"
			+ "from abonents a\n"
			+ "   inner join phones p on p.phone_id = a.phone_id\n"
			+ "where a.organization_id in (\n"
			+ "      select\n"
			+ "         t.organization_id\n"
			+ "      from main_services t\n"
			+ "      start with t.organization_id = ? and t.service_id = 3\n"
			+ "      connect by prior t.organization_id = t.main_master_id\n"
			+ " )\n"
			+ " and p.deleted = 0 and a.deleted = 0 and p.phone not in (select tt.phone\n"
			+ "                         from block_list_phones tt\n"
			+ "                        where tt.block_list_id = ? )";

	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_EXCEPT_BLOCK_LIST = "select /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "  from abonents a\n"
			+ " inner join phones p on p.phone_id = a.phone_id\n"
			+ " where a.main_detail_id in\n"
			+ "       (select t.main_detail_id from main_details t\n"
			+ "        start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "        connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ "   and p.deleted = 0 and a.deleted = 0\n"
			+ "   and p.phone not in (select tt.phone\n"
			+ "                         from block_list_phones tt\n"
			+ "                        where tt.block_list_id = ? )";

	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_BLOCK_LIST = "select /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "  from abonents a\n"
			+ " inner join phones p on p.phone_id = a.phone_id\n"
			+ " where a.main_detail_id in\n"
			+ "       (select t.main_detail_id from main_details t\n"
			+ "        start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "        connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ "   and p.deleted = 0 and a.deleted = 0\n"
			+ "   and p.phone in (select tt.phone\n"
			+ "                         from block_list_phones tt\n"
			+ "                        where tt.block_list_id = ? )";

	public static final String Q_GET_BLOCK_LIST_PHONES = "select tt.phone from block_list_phones tt where tt.block_list_id = ? ";

	public static final String Q_GET_MAIN_ORGS_PHONES_HIERARCHY = " select\n"
			+ "   distinct p.phone\n" + "from abonents a\n"
			+ "   inner join phones p on p.phone_id = a.phone_id\n"
			+ "where a.organization_id in (\n" + "      select\n"
			+ "         t.organization_id\n" + "      from main_services t\n"
			+ "      start with t.organization_id = ? and t.service_id = 3\n"
			+ "      connect by prior t.organization_id = t.main_master_id\n"
			+ " )\n" + " and p.deleted = 0 and a.deleted = 0";

	public static final String Q_GET_MAIN_DET_PHONES_HIERARCHY_NEW = "select /*+ index(p PHN_PRY_KS001)*/\n"
			+ "\t distinct p.phone\n"
			+ "\tfrom abonents a\n"
			+ "\t     inner join phones p on p.phone_id = a.phone_id\n"
			+ "\twhere a.main_detail_id in\n"
			+ "\t       (\n"
			+ "\t         select\n"
			+ "\t               t.main_detail_id\n"
			+ "\t         from main_details t\n"
			+ "           start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "            union "
			+ "            select -999999999 from dual "
			+ "         )\n"
			+ "  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "       select\n"
			+ "             /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "       from abonents a\n"
			+ "            inner join phones p on p.phone_id = a.phone_id\n"
			+ "       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "          select\n"
			+ "                c.main_detail_id\n"
			+ "          from contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "            (\n"
			+ "              select\n"
			+ "                    t.main_detail_id\n"
			+ "              from main_details t\n"
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
			+ "from abonents a\n"
			+ "   inner join phones p on p.phone_id = a.phone_id\n"
			+ "where a.main_detail_id in (\n"
			+ "      select\n"
			+ "          t.main_detail_id from main_details t\n"
			+ "      start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "      connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ ") and p.deleted = 0 and a.deleted = 0 ";
	//
	// public static final String Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID =
	// " select /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1) from log_sessions ls\n"
	// +
	// "       inner join log_session_charges ch on ch.session_id = ls.session_id\n"
	// +
	// "       inner join contracts c on c.corporate_client_id = ? \n" +
	// "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
	// +
	// "      select /*+ index(p PHN_PRY_KS001)*/\n" +
	// "         distinct '32'||p.phone as phone\n" +
	// "      from abonents a\n" +
	// "         inner join phones p on p.phone_id = a.phone_id\n" +
	// "      where a.main_detail_id in (\n" +
	// "            select\n" +
	// "                t.main_detail_id from main_details t\n" +
	// "            start with t.main_detail_id = ((select c.main_detail_id from contracts c where c.corporate_client_id = ? )) and t.deleted = 0\n"
	// +
	// "            connect by prior t.main_detail_id = t.main_detail_master_id\n"
	// +
	// "      )\n" +
	// ")";

	public static final String Q_GET_CALL_CNT_BY_CONT_AND_MAIN_DET_ID = "select\n"
			+ "      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1)\n"
			+ "from log_sessions ls\n"
			+ "     inner join log_session_charges ch on ch.session_id = ls.session_id\n"
			+ "     inner join contracts c on c.corporate_client_id = ? \n"
			+ "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
			+ "     select /*+ index(p PHN_PRY_KS001)*/\n"
			+ "           distinct decode(length(p.phone),7,'32'||p.phone,p.phone) \n"
			+ "          from abonents a\n"
			+ "               inner join phones p on p.phone_id = a.phone_id\n"
			+ "          where a.main_detail_id in\n"
			+ "                 (\n"
			+ "                   select\n"
			+ "                         t.main_detail_id\n"
			+ "                   from main_details t\n"
			+ "                     start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "                     connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "                    union    "
			+ "                    select -999999999 from dual    "
			+ "                   )\n"
			+ "            and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "                 select\n"
			+ "                       /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "                 from abonents a\n"
			+ "                      inner join phones p on p.phone_id = a.phone_id\n"
			+ "                 where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "                    select\n"
			+ "                          c.main_detail_id\n"
			+ "                    from contracts c\n"
			+ "                    where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "                      (\n"
			+ "                        select\n"
			+ "                          t.main_detail_id\n"
			+ "                         from main_details t\n"
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
			+ "from log_sessions ls\n"
			+ "     inner join log_session_charges ch on ch.session_id = ls.session_id\n"
			+ "     inner join contracts c on c.corporate_client_id = ? \n"
			+ "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
			+ "     select /*+ index(p PHN_PRY_KS001)*/\n"
			+ "           distinct decode(length(p.phone),7,'32'||p.phone,p.phone) \n"
			+ "          from abonents a\n"
			+ "               inner join phones p on p.phone_id = a.phone_id\n"
			+ "          where a.main_detail_id in\n"
			+ "                 (\n"
			+ "                   select\n"
			+ "                         t.main_detail_id\n"
			+ "                   from main_details t\n"
			+ "                     start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "                     connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "                   )\n"
			+ "            and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "                 select\n"
			+ "                       /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "                 from abonents a\n"
			+ "                      inner join phones p on p.phone_id = a.phone_id\n"
			+ "                 where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "                    select\n"
			+ "                          c.main_detail_id\n"
			+ "                    from contracts c\n"
			+ "                    where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "                      (\n"
			+ "                        select\n"
			+ "      \t\t                    t.main_detail_id\n"
			+ "\t\t\t\t\t              from main_details t\n"
			+ "\t\t\t\t\t              where level>1\n"
			+ "\t\t\t\t\t              start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "\t\t\t\t\t              connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "\t\t\t\t\t            )\n"
			+ "\t\t\t\t\t        )\n"
			+ "\t\t\t\t\t  )\n" + ")";

	// public static final String Q_GET_CALL_CNT_BY_CONT_AND_organization_id =
	// " select /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1) from log_sessions ls\n"
	// +
	// "       inner join log_session_charges ch on ch.session_id = ls.session_id\n"
	// +
	// "       inner join contracts c on c.corporate_client_id = ?\n" +
	// "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
	// +
	// "      select\n" +
	// "         distinct '32'||p.phone as phone\n" +
	// "      from abonents a\n" +
	// "         inner join phones p on p.phone_id = a.phone_id\n" +
	// "      where a.organization_id in (\n" +
	// "            select\n" +
	// "               t.organization_id\n" +
	// "            from main_services t\n" +
	// "            start with t.organization_id =((select c.organization_id from contracts c where c.corporate_client_id = ?))  and t.service_id = 3\n"
	// +
	// "            connect by prior t.organization_id = t.main_master_id\n" +
	// "       )\n" +
	// ")";

	public static final String Q_GET_CALL_CNT_BY_CONT_AND_ORGANIZATION_ID = "select\n"
			+ "      /*+ index(ls IDX_LOG_SESS_PHANDDT) */ count(1)\n"
			+ "from log_sessions ls\n"
			+ "     inner join log_session_charges ch on ch.session_id = ls.session_id\n"
			+ "     inner join contracts c on c.corporate_client_id = ? \n"
			+ "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
			+ "     select distinct decode(length(p.phone),7,'32'||p.phone,p.phone) from (\n"
			+ "               select ms.organization_id from main_services ms\n"
			+ "               start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "               connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "        inner join abonents a on a.organization_id = r.organization_id\n"
			+ "        inner join phones p on p.phone_id = a.phone_id\n"
			+ "        where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "            select p.phone from (\n"
			+ "            select\n"
			+ "              t.organization_id\n"
			+ "            from main_services t\n"
			+ "            start with t.organization_id in (\n"
			+ "                  select\n"
			+ "                       c.organization_id\n"
			+ "                  from contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                        select\n"
			+ "                              t.organization_id\n"
			+ "                        from main_services t\n"
			+ "                        where level>1\n"
			+ "                  start with t.organization_id = ? and t.service_id = 3\n"
			+ "                  connect by prior t.organization_id = t.main_master_id\n"
			+ "                  ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "            ) and t.service_id = 3\n"
			+ "            connect by prior t.organization_id = t.main_master_id\n"
			+ "            ) a1\n"
			+ "            inner join abonents a on a.organization_id = a1.organization_id\n"
			+ "            inner join phones p on p.phone_id = a.phone_id\n"
			+ "            where a.deleted = 0 and p.deleted = 0\n"
			+ "        ) and p.phone not in (\n"
			+ "            select\n"
			+ "              /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "            from abonents a\n"
			+ "                 inner join phones p on p.phone_id = a.phone_id\n"
			+ "            where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "                  (select\n"
			+ "                         t.main_detail_id\n"
			+ "                   from main_details t\n"
			+ "                   start with t.main_detail_id in\n"
			+ "                         (select\n"
			+ "                                c.main_detail_id\n"
			+ "                          from contracts c\n"
			+ "                          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                                (select\n"
			+ "                                       t.organization_id\n"
			+ "                                 from main_services t\n"
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
			+ "from log_sessions ls\n"
			+ "     inner join log_session_charges ch on ch.session_id = ls.session_id\n"
			+ "     inner join contracts c on c.corporate_client_id = ? \n"
			+ "where ch.deleted = 0 and trunc(c.start_date) <= trunc(ls.start_date) and trunc(c.end_date)>=trunc(ls.start_date) and ls.phone in (\n"
			+ "     select distinct decode(length(p.phone),7,'32'||p.phone,p.phone) from (\n"
			+ "               select ms.organization_id from main_services ms\n"
			+ "               start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "               connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "        inner join abonents a on a.organization_id = r.organization_id\n"
			+ "        inner join phones p on p.phone_id = a.phone_id\n"
			+ "        where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "            select p.phone from (\n"
			+ "            select\n"
			+ "              t.organization_id\n"
			+ "            from main_services t\n"
			+ "            start with t.organization_id in (\n"
			+ "                  select\n"
			+ "                       c.organization_id\n"
			+ "                  from contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                        select\n"
			+ "                              t.organization_id\n"
			+ "                        from main_services t\n"
			+ "                        where level>1\n"
			+ "                  start with t.organization_id = ? and t.service_id = 3\n"
			+ "                  connect by prior t.organization_id = t.main_master_id\n"
			+ "                  ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "            ) and t.service_id = 3\n"
			+ "            connect by prior t.organization_id = t.main_master_id\n"
			+ "            ) a1\n"
			+ "            inner join abonents a on a.organization_id = a1.organization_id\n"
			+ "            inner join phones p on p.phone_id = a.phone_id\n"
			+ "            where a.deleted = 0 and p.deleted = 0\n"
			+ "        ) and p.phone not in (\n"
			+ "            select\n"
			+ "              /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "            from abonents a\n"
			+ "                 inner join phones p on p.phone_id = a.phone_id\n"
			+ "            where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "                  (select\n"
			+ "                         t.main_detail_id\n"
			+ "                   from main_details t\n"
			+ "                   start with t.main_detail_id in\n"
			+ "                         (select\n"
			+ "                                c.main_detail_id\n"
			+ "                          from contracts c\n"
			+ "                          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                                (select\n"
			+ "                                       t.organization_id\n"
			+ "                                 from main_services t\n"
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

	public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST = "select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ distinct p.phone\n"
			+ "from abonents a\n"
			+ "     inner join phones p on p.phone_id = a.phone_id\n"
			+ "where a.main_detail_id in\n"
			+ "       (\n"
			+ "         select\n"
			+ "               t.main_detail_id\n"
			+ "         from main_details t\n"
			+ "           start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "         )\n"
			+ "  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "       select\n"
			+ "             /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "       from abonents a\n"
			+ "            inner join phones p on p.phone_id = a.phone_id\n"
			+ "       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "          select\n"
			+ "                c.main_detail_id\n"
			+ "          from contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "            (\n"
			+ "              select\n"
			+ "                    t.main_detail_id\n"
			+ "              from main_details t\n"
			+ "              where level>1\n"
			+ "              start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "              connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "            )\n"
			+ "        )\n"
			+ "  )\n"
			+ "  and p.phone not in (\n"
			+ "      select tt.phone_number from corp_client_phones tt where tt.corporate_client_id = ? and tt.deleted = 0 "
			+ "  )\n";

	public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST = "select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ distinct p.phone\n"
			+ "from abonents a\n"
			+ "     inner join phones p on p.phone_id = a.phone_id\n"
			+ "where a.main_detail_id in\n"
			+ "       (\n"
			+ "         select\n"
			+ "               t.main_detail_id\n"
			+ "         from main_details t\n"
			+ "           start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "         )\n"
			+ "  and p.deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "       select\n"
			+ "             /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "       from abonents a\n"
			+ "            inner join phones p on p.phone_id = a.phone_id\n"
			+ "       where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in (\n"
			+ "          select\n"
			+ "                c.main_detail_id\n"
			+ "          from contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.main_detail_id in\n"
			+ "            (\n"
			+ "              select\n"
			+ "                    t.main_detail_id\n"
			+ "              from main_details t\n"
			+ "              where level>1\n"
			+ "              start with t.main_detail_id = ? and t.deleted = 0\n"
			+ "              connect by prior t.main_detail_id = t.main_detail_master_id\n"
			+ "            )\n"
			+ "        )\n"
			+ "  )\n"
			+ "  and p.phone in (\n"
			+ "      select tt.phone from corp_client_phones tt where tt.corporate_client_id = ? and tt.deleted = 0 "
			+ "  )\n";

	// public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST =
	// "select distinct t.phone from phones t\n" +
	// "inner join abonents a on a.phone_id = t.phone_id\n" +
	// "inner join main_services ms on ms.organization_id = a.organization_id\n"
	// +
	// "where t.deleted = 0 and a.deleted = 0 and ms.service_id = 3 and a.organization_id = ? and ms.organization_id = ?\n"
	// +
	// "  and a.main_detail_id = ? and t.phone is not null and length(t.phone)>6 and t.phone in (\n"
	// +
	// "      select tt.phone from corp_client_phones tt\n" +
	// "             inner join contracts c on c.corporate_client_id = tt.corporate_client_id\n"
	// +
	// "      where tt.corporate_client_id = ? and tt.deleted = 0 and c.organization_id = a.organization_id and c.main_detail_id = a.main_detail_id and c.deleted = 0\n"
	// +
	// ")";

	// public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST1 =
	// "select distinct p.phone from main_services ms\n" +
	// "       inner join abonents a on a.organization_id = ms.organization_id\n"
	// +
	// "       inner join phones p on p.phone_id = a.phone_id\n" +
	// "where ms.organization_id in\n" +
	// "      (select t.organization_id from main_services t\n" +
	// "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
	// +
	// "       connect by prior t.organization_id = t.main_master_id\n" +
	// "       )\n" +
	// "and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
	// +
	// "and length(p.phone)>6 and ms.service_id = 3 and ms.deleted = 0 and p.phone not in (\n"
	// +
	// "      select tt.phone from corp_client_phones tt\n" +
	// "             inner join contracts c on c.corporate_client_id = tt.corporate_client_id\n"
	// +
	// "      where tt.corporate_client_id = ? and tt.deleted = 0 and c.organization_id = a.organization_id and c.deleted = 0\n"
	// +
	// ")";

	public static final String Q_GET_PHONE_LIST_ONLY_CONTR_LIST1 = "select distinct p.phone from (\n"
			+ "       select ms.organization_id from main_services ms\n"
			+ "       start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "       connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "inner join abonents a on a.organization_id = r.organization_id\n"
			+ "inner join phones p on p.phone_id = a.phone_id\n"
			+ "where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "    select p.phone from (\n"
			+ "    select\n"
			+ "      t.organization_id\n"
			+ "    from main_services t\n"
			+ "    start with t.organization_id in (\n"
			+ "          select\n"
			+ "               c.organization_id\n"
			+ "          from contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                select\n"
			+ "                      t.organization_id\n"
			+ "                from main_services t\n"
			+ "                where level>1\n"
			+ "          start with t.organization_id = ? and t.service_id = 3\n"
			+ "          connect by prior t.organization_id = t.main_master_id\n"
			+ "          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "    ) and t.service_id = 3\n"
			+ "    connect by prior t.organization_id = t.main_master_id\n"
			+ "    ) a1\n"
			+ "    inner join abonents a on a.organization_id = a1.organization_id\n"
			+ "    inner join phones p on p.phone_id = a.phone_id\n"
			+ "    where a.deleted = 0 and p.deleted = 0\n"
			+ ") and p.phone not in (\n"
			+ "    select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "    from abonents a\n"
			+ "         inner join phones p on p.phone_id = a.phone_id\n"
			+ "    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "          (select\n"
			+ "                 t.main_detail_id\n"
			+ "           from main_details t\n"
			+ "           start with t.main_detail_id in\n"
			+ "                 (select\n"
			+ "                        c.main_detail_id\n"
			+ "                  from contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                        (select\n"
			+ "                               t.organization_id\n"
			+ "                         from main_services t\n"
			+ "                         where level>1\n"
			+ "                         start with t.organization_id = ? and t.service_id = 3\n"
			+ "                         connect by prior t.organization_id = t.main_master_id\n"
			+ "                         )\n"
			+ "                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n"
			+ "                  ) and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ ") \n"
			+ " and p.phone not in (\n"
			+ "      select tt.phone_number from corp_client_phones tt where tt.corporate_client_id = ? and tt.deleted = 0 "
			+ "  )\n";

	public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST1 = "select distinct p.phone from (\n"
			+ "       select ms.organization_id from main_services ms\n"
			+ "       start with ms.organization_id = ? and ms.service_id = 3\n"
			+ "       connect by prior ms.organization_id = ms.main_master_id) r\n"
			+ "inner join abonents a on a.organization_id = r.organization_id\n"
			+ "inner join phones p on p.phone_id = a.phone_id\n"
			+ "where p. deleted = 0 and a.deleted = 0 and p.phone not in (\n"
			+ "    select p.phone from (\n"
			+ "    select\n"
			+ "      t.organization_id\n"
			+ "    from main_services t\n"
			+ "    start with t.organization_id in (\n"
			+ "          select\n"
			+ "               c.organization_id\n"
			+ "          from contracts c\n"
			+ "          where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in (\n"
			+ "                select\n"
			+ "                      t.organization_id\n"
			+ "                from main_services t\n"
			+ "                where level>1\n"
			+ "          start with t.organization_id = ? and t.service_id = 3\n"
			+ "          connect by prior t.organization_id = t.main_master_id\n"
			+ "          ) and (c.main_detail_id is null or c.main_detail_id = 0)\n"
			+ "    ) and t.service_id = 3\n"
			+ "    connect by prior t.organization_id = t.main_master_id\n"
			+ "    ) a1\n"
			+ "    inner join abonents a on a.organization_id = a1.organization_id\n"
			+ "    inner join phones p on p.phone_id = a.phone_id\n"
			+ "    where a.deleted = 0 and p.deleted = 0\n"
			+ ") and p.phone not in (\n"
			+ "    select\n"
			+ "      /*+ index(p PHN_PRY_KS001)*/ p.phone\n"
			+ "    from abonents a\n"
			+ "         inner join phones p on p.phone_id = a.phone_id\n"
			+ "    where p.deleted = 0 and a.deleted = 0 and a.main_detail_id in\n"
			+ "          (select\n"
			+ "                 t.main_detail_id\n"
			+ "           from main_details t\n"
			+ "           start with t.main_detail_id in\n"
			+ "                 (select\n"
			+ "                        c.main_detail_id\n"
			+ "                  from contracts c\n"
			+ "                  where c.deleted = 0 and trunc(sysdate)>=trunc(c.start_date) and trunc(sysdate)<trunc(c.end_date) and c.organization_id in\n"
			+ "                        (select\n"
			+ "                               t.organization_id\n"
			+ "                         from main_services t\n"
			+ "                         where level>1\n"
			+ "                         start with t.organization_id = ? and t.service_id = 3\n"
			+ "                         connect by prior t.organization_id = t.main_master_id\n"
			+ "                         )\n"
			+ "                         and (c.main_detail_id is not null and c.main_detail_id <> 0)\n"
			+ "                  ) and t.deleted = 0\n"
			+ "           connect by prior t.main_detail_id = t.main_detail_master_id)\n"
			+ ")\n"
			+ " and p.phone in (\n"
			+ "      select tt.phone_number from corp_client_phones tt where tt.corporate_client_id = ? and tt.deleted = 0 "
			+ "  )\n";

	// public static final String Q_GET_PHONE_LIST_EXCEPT_CONTR_LIST1 =
	// "select distinct p.phone from main_services ms\n" +
	// "       inner join abonents a on a.organization_id = ms.organization_id\n"
	// +
	// "       inner join phones p on p.phone_id = a.phone_id\n" +
	// "where ms.organization_id in\n" +
	// "      (select t.organization_id from main_services t\n" +
	// "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
	// +
	// "       connect by prior t.organization_id = t.main_master_id\n" +
	// "       )\n" +
	// "and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
	// +
	// "and length(p.phone)>6 and ms.service_id = 3 and ms.deleted = 0 and p.phone in (\n"
	// +
	// "      select tt.phone from corp_client_phones tt\n" +
	// "             inner join contracts c on c.corporate_client_id = tt.corporate_client_id\n"
	// +
	// "      where tt.corporate_client_id = ? and tt.deleted = 0 and c.organization_id = a.organization_id and c.deleted = 0\n"
	// +
	// ")";

	public static final String Q_GET_PHONE_LIST_BY_ORGANIZATION_ID = "select distinct p.phone from main_services ms\n"
			+ "       inner join abonents a on a.organization_id = ms.organization_id\n"
			+ "       inner join phones p on p.phone_id = a.phone_id\n"
			+ "where ms.organization_id in\n"
			+ "      (select t.organization_id from main_services t\n"
			+ "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
			+ "       connect by prior t.organization_id = t.main_master_id\n"
			+ "       )\n"
			+ "       and p.deleted = 0 and a.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
			+ "       and length(p.phone)>6\n"
			+ "       and ms.service_id = 3 and ms.deleted = 0";

	public static final String Q_GET_PHONE_LIST_BY_ORGANIZATION_ID1111 = "select\n"
			+ "  distinct p.phone\n"
			+ "from main_orgs mo\n"
			+ "inner join main_services ms on ms.organization_id = mo.organization_id\n"
			+ "inner join main_details md on md.organization_id = mo.organization_id\n"
			+ "inner join abonents a on a.main_detail_id = md.main_detail_id\n"
			+ "inner join phones p on p.phone_id = a.phone_id\n"
			+ "where mo.organization_id in\n"
			+ "      (select\n"
			+ "         t.organization_id\n"
			+ "       from main_services t\n"
			+ "       start with t.organization_id = ? and t.service_id = 3 and t.deleted = 0\n"
			+ "       connect by prior t.organization_id = t.main_master_id\n"
			+ "      )\n"
			+ "      and md.main_detail_type_id in (5, 30, 59, 60, 61)\n"
			+ "      and p.deleted = 0 and a.deleted = 0 and md.deleted = 0 and p.phone is not null and p.phone not like '8%'\n"
			+ "      and length(p.phone)>4\n"
			+ "      and ms.service_id = 3 and ms.deleted = 0";
	
	public static final String Q_DELETE_CONTRACT_PRICES = "delete from corp_client_price_items t where t.corporate_client_id = ? ";
	public static final String Q_DELETE_OLD_STREET_NAMES = "delete from street_old_names t where t.street_id = ? ";
	public static final String Q_DELETE_CONTRACT_PHONES = "delete from corp_client_phones t where t.corporate_client_id = ? ";
	public static final String Q_DELETE_BLOCKLIST_PHONES = "delete from block_list_phones t where t.block_list_id = ? ";
	public static final String Q_DELETE_BILLINGCOMP = "delete from BILLING_COMPANIES t where t.billing_company_id = ? ";
	public static final String Q_DELETE_BILLINGCOMP_IND = "delete from BILLING_COMPANIES_IND t where t.billing_company_id = ? ";

	public static final String Q_REMOVE_PHONE_FROM_AST_DB = " delete from asteriskcdrdb.block where code = ? and proriti = ? and len = ? ";
	public static final String Q_ADD_PHONE_INTO_AST_DB = " insert into asteriskcdrdb.block (code,proriti,len) values (?, ?, ?) ";

	public static final String Q_GET_CONTRACTOR_ADV_PRICE = " select sum(nvl(t.price,0)) as advPrice from corp_client_price_items t where t.corporate_client_id = ? and t.call_count_start<=? and t.call_count_end > ? ";

	public static final String Q_GET_CONTRACTOR_INFO = "select distinct c.corporate_client_id,\n"
			+ "       dt2ms(c.start_date) as start_date,\n"
			+ "       dt2ms(c.end_date) as end_date,\n"
			+ "       c.critical_number,\n"
			+ "       c.is_budget,\n"
			+ "       c.price_type,\n"
			+ "       c.price,\n"
			+ "       c.block, \n"
			+ "       c.organization_id,\n"
			+ "       c.main_detail_id \n"
			+ "  from phones t\n"
			+ " inner join abonents a on a.phone_id = t.phone_id\n"
			+ " inner join contracts c on c.organization_id = a.organization_id\n"
			+ " where t.phone = ? and a.deleted = 0 and c.deleted = 0 ";

	public static final String Q_GET_IS_UNKNOWN_PHONE_NUMBER = "select sum(m.cnt) as cnt\n"
			+ "  from (select /*+ use_nl(pn, sp, dp) */\n"
			+ "         count(8) as cnt\n"
			+ "          from phone_numbers pn\n"
			+ "         where pn.phone = ? and pn.phone_state_id <> 52102 \n"
			+ "           and (exists\n"
			+ "                (select 1\n"
			+ "                   from subscriber_to_phones sp\n"
			+ "                  where sp.phone_number_id = pn.phone_number_id) or exists\n"
			+ "                (select 1\n"
			+ "                   from organization_depart_to_phones sp\n"
			+ "                  where sp.phone_number_id = pn.phone_number_id))\n"
			+ "        union all\n"
			+ "        select count(8) as cnt\n"
			+ "          from unknown_phone_numbers u\n"
			+ "         where u.phone_number = ? ) m ";

	public static final String INS_UNKNOWN_NUMBER = "insert into unknown_phone_numbers\n"
			+ "  (phone_number_id, phone_number)\n"
			+ "values\n"
			+ "  (SEQ_UNKNOWN_PHONE_NUMBER.Nextval, ?)";

	public static final String DEL_UNKNOWN_NUMBER = "delete from unknown_phone_numbers t where t.phone_number_id = ?";

	public static final String Q_GET_CALL_CENTER_REQ_MSG = "select t.description from descriptions t where t.description_id = 57100 ";

	public static final String Q_GET_OPERATOR_REMARKS = "select count(1)\n"
			+ "  from operator_warns t\n" + " where t.operator = ? \n"
			+ "   and trunc(t.warn_send_date) >= trunc(sysdate - 10)\n"
			+ "   and t.hidden = 0\n" + "   and t.delivered = 0";

	public static final String Q_GET_SPECIAL_TEXT_BY_NUMBER = " select t.note from ALERTS_BY_PHONE t where t.phone_number = ? ";
	public static final String Q_GET_NON_CHARGE_ABONENT = "select count(t.phone_number) from FREE_OF_CHARGE_PHONE t where t.phone_number = ? and t.operator_src = ? and trunc(sysdate) between t.start_date and t.end_date ";
	public static final String Q_GET_NON_CHARGE_ABONENT_REMARK = "select t.remark from FREE_OF_CHARGE_PHONE t where t.phone_number = ? and trunc(sysdate) between t.start_date and t.end_date ";
	public static final String Q_GET_MOBITEL_NOTE = "select t.description from descriptions t where t.description_id = 56101 ";
	public static final String Q_GET_TREATMENT = " select treatment, gender from treatments where phone_number = ? ";
	public static final String Q_GET_ORG_ABONENT = "select /*+ use_nl(ph, dtp, od, o)*/\n"
			+ " o.organization_id\n"
			+ ",o.organization_name\n"
			+ ",decode(to_char(o.found_date, 'dd/mm'), to_char(sysdate, 'dd/mm'), 1, 0) as bid\n"
			+ "  from phone_numbers                 pn\n"
			+ "      ,organization_depart_to_phones dtp\n"
			+ "      ,organization_department       od\n"
			+ "      ,organizations                 o\n"
			+ " where dtp.phone_number_id = pn.phone_number_id\n"
			+ "   and od.org_department_id = dtp.org_department_id\n"
			+ "   and o.organization_id = od.organization_id\n"
			+ "   and o.parrent_organization_id is null\n"
			+ "   and pn.phone = ? \n" + "   and rownum < 2";
	
	

	public static final String Q_GET_ORG_ABONENT_NEW = "select l1.organization_id,l1.organization_name,l1.bid from (\n" +
			"  select l.organization_id,l.organization_name,l.bid from (\n" + 
			"    select kk.organization_id,kk.organization_name,\n" + 
			"           decode(to_char(kk.found_date, 'dd/mm'), to_char(sysdate, 'dd/mm'), 1, 0) as bid ,\n" + 
			"           1 as priority\n" + 
			"    from (\n" + 
			"      select k.organization_id,k.organization_name,k.parrent_organization_id,k.found_date from (\n" + 
			"        select\n" + 
			"          oo.organization_id,\n" + 
			"          oo.organization_name,\n" + 
			"          oo.parrent_organization_id,\n" + 
			"          oo.found_date\n" + 
			"        from ccare.organizations oo\n" + 
			"        start with oo.organization_id in (\n" + 
			"            select\n" + 
			"                   o.organization_id\n" + 
			"            from ccare.phone_numbers t\n" + 
			"                   inner join ccare.organization_depart_to_phones p on p.phone_number_id = t.phone_number_id\n" + 
			"                   inner join ccare.organization_department od on od.org_department_id = p.org_department_id\n" + 
			"                   inner join ccare.organizations o on o.organization_id = od.organization_id\n" + 
			"            where t.phone = ?)\n" + 
			"        connect by prior oo.organization_id = oo.parrent_organization_id\n" + 
			"        ) k\n" + 
			"      order by k.parrent_organization_id desc, k.organization_id\n" + 
			"      ) kk\n" + 
			"    where kk.organization_name like '%(%' and rownum< 2\n" + 
			"\n" + 
			"    union all\n" + 
			"\n" + 
			"    select kk.organization_id,kk.organization_name,\n" + 
			"           decode(to_char(kk.found_date, 'dd/mm'), to_char(sysdate, 'dd/mm'), 1, 0) as bid ,\n" + 
			"           2 as priority\n" + 
			"    from (\n" + 
			"      select k.organization_id,k.organization_name,k.parrent_organization_id,k.found_date from (\n" + 
			"        select\n" + 
			"          oo.organization_id,\n" + 
			"          oo.organization_name,\n" + 
			"          oo.parrent_organization_id,\n" + 
			"          oo.found_date\n" + 
			"        from ccare.organizations oo\n" + 
			"        start with oo.organization_id in (\n" + 
			"            select\n" + 
			"                   o.organization_id\n" + 
			"            from ccare.phone_numbers t\n" + 
			"                   inner join ccare.organization_depart_to_phones p on p.phone_number_id = t.phone_number_id\n" + 
			"                   inner join ccare.organization_department od on od.org_department_id = p.org_department_id\n" + 
			"                   inner join ccare.organizations o on o.organization_id = od.organization_id\n" + 
			"            where t.phone = ? )\n" + 
			"        connect by prior oo.organization_id = oo.parrent_organization_id\n" + 
			"        ) k\n" + 
			"      order by k.parrent_organization_id desc, k.organization_id\n" + 
			"      ) kk\n" + 
			"    ) l\n" + 
			"  order by l.priority ) l1\n" + 
			"where rownum < 2";

	
	

	public static final String Q_GET_WEB_SESSION_ID = " select (to_number(to_char(sysdate,'YYMM'))*1000000 + SEQ_LOG_CALLS.nextval) AS sessionID from dual ";

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
			+ "from discover t\n"
			+ "       inner join survey_kind tt on tt.survey_kind_id = t.survey_kind_id\n"
			+ "       left join discover_rtypes r on r.survey_reply_type_id = t.survey_reply_type_id\n"
			+ "where t.survey_kind_id <> 4 and t.survery_responce_status <>0 and trunc(t.rec_date) = trunc(sysdate)\n"
			+ "";

	public static final String Q_GET_TRANSPORT_BY_ID = "select\n"
			+ "        getWeekDays(t.days) as days_descr,\n"
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

	public static final String Q_GET_SESSION_BY_ID = "select * from log_sessions t where t.session_id = ? ";
	public static final String Q_GET_PERS_NOTE_BY_ID = "select t.note_id from log_personell_notes t where t.note_id = ? ";
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
			+ "  from subscribers t\n" + " where name_id = ? \n";

	public static final String Q_CHECK_LASTNAME_FK = "select count(1) as cnt, 1 as n \n"
			+ "  from subscribers t\n" + " where family_name_id = ? \n";

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
	public static final String Q_GET_FIRST_NAME_COUNT = " select count(1) from names t where t.name_descr = ? ";
	public static final String Q_GET_LAST_NAME_COUNT = " select count(1) from familynames t where t.familyname = ? ";
	public static final String Q_GET_FIRST_NAME_COUNT_ALL = " select count(1) from names t ";
	public static final String Q_GET_FIRST_NAMES_ALL = " select t.name_descr, t.name_id from names t order by t.name_descr ";
	public static final String Q_GET_LAST_NAMES_ALL = " select t.deleted,decode(t.deleted, 0, 'აქტიური', 'გაუქმებული') as deletedText,t.lastname, t.familyname_id,t.rec_date from lastnames t where t.deleted=0 order by t.lastname ";
	public static final String Q_GET_DEPARTMENTS = " select * from departments t order by 1 ";
	public static final String Q_GET_USER_PERMISSIONS = " select t.permission_id from user_permission t where t.user_id = ? ";
	public static final String Q_DELETE_USER_PERMISSIONS = " delete from user_permission t where t.user_id = ? ";

	public static final String Q_GET_ALL_CITY_REGIONS = "select t.town_district_id, t.town_id, t.town_district_name, t.deleted\n"
			+ "  from city_regions t\n"
			+ " where t.deleted = 0\n"
			+ " order by 3";

	public static final String Q_GET_FIRST_NAME_BY_ID = "select t.firstname_id,\n"
			+ "       t.firstname \n"
			+ "from names t\n"
			+ "where t.firstname_id = ? ";

	public static final String Q_GET_LAST_NAME_BY_ID = "select t.familyname_id,\n"
			+ "       t.lastname \n"
			+ "from familynames t\n"
			+ "where t.familyname_id = ? ";

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
			+ "  t.descr_type_id_level_10 \n" + "from\n" + "    streets t \n"
			+ "where\n" + "  t.street_id = ? ";

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
			+ "  t.descr_type_id_level_10 \n" + "from\n" + "    streets t\n";

	public static final String Q_GET_STREET_DISTRICTS_ALL =

	"select t.street_to_town_district_id\n" + "      ,t.street_id\n"
			+ "      ,t.town_district_id\n" + "      ,c.town_district_name\n"
			+ "  from street_to_town_districts t\n"
			+ " inner join town_district c\n"
			+ "    on c.town_district_id = t.town_district_id\n"
			+ " where t.street_to_town_district_id is not null\n"
			+ "   and t.street_id is not null\n"
			+ "   and t.town_district_id is not null\n"
			+ "   and c.town_district_name is not null";

	// inserts
	public static final String Q_INSERT_PERS_NOTES = "BEGIN insert into log_personell_notes t\n"
			+ "(t.ym,t.session_id,t.user_name,t.note,t.rec_user,t.rec_date,t.visible_options,t.phone,t.call_date,t.particular)\n"
			+ "values (?,?,?,?,?,?,?,?,?,?) returning id into ?; END; ";

	public static final String Q_INSERT_SESSION = "insert into call_sessions\n"
			+ "  (call_session_id, year_month, uname, call_phone, session_id, call_kind)\n"
			+ "values\n" + "  (Seq_Call_Session_Id.Nextval, ?, ?, ?, ?, ?";
	// updates
	public static final String Q_UPDATE_PERS_NOTES = "update log_personell_notes t set t.note = ?, t.visible_options = ?, t.particular = ?\n"
			+ "where t.note_id = ? ";

	public static final String Q_UPDATE_FIRST_NAME = "update names t set t.firstname = ?, t.upd_user = ? where t.firstname_id = ? ";
	public static final String Q_UPDATE_LAST_NAME = "update familynames t set t.familyname = ?, t.upd_user = ? where t.familyname_id = ? ";

	public static final String Q_UPDATE_FIRST_NAME_STATUS = "update names t set t.deleted = ? , t.upd_user = ? where t.firstname_id = ? ";
	public static final String Q_UPDATE_LAST_NAME_STATUS = "update lastnames t set t.deleted = ? , t.upd_user = ? where t.familyname_id = ? ";

	public static final String Q_UPDATE_LOCK_STATUS = " update call_record_lock t set t.LOCK_STATUS = 1 where t.CALL_SESSION = ? ";

	public static final String Q_UPDATE_SESSION_QUALITY = " update call_sessions t set t.call_quality = ? where t.call_session_id = ? ";

	public static final String Q_UPDATE_ABONENT = " update abonents t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.abonent_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE = " update main_services t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.organization_id = ? ";

	public static final String Q_UPDATE_MAIN_ADDRESS = " update main_address t set t.deleted = ?, t.upd_user = ?, t.upd_date = ? where t.address_id = ? and t.organization_id = ? ";

	// public static final String Q_UPDATE_PHONES =
	// " update phones t set t.deleted = ?, t.upd_user = ? where t.abonent_id = ? ";

	public static final String Q_UPDATE_USER = " update users t set\n"
			+ "       t.user_firstname = ?,\n"
			+ "       t.user_lastname = ?,\n" + "       t.user_name = ?,\n"
			+ "       t.user_password = ?,\n"
			+ "       t.department_id = ? where t.user_id = ?";

	public static final String Q_UPDATE_COUNTRY = "update countries t set\n"
			+ "       t.country_name = ?,\n" + "       t.phone_code = ?,\n"
			+ "       t.continent_id = ?\n" + "where t.country_id = ?";

	public static final String Q_DELETE_USER = " delete from users t \n"
			+ "where t.user_id = ? ";

	public static final String Q_DELETE_COUNTRY = "delete from countries t \n"
			+ " where t.country_id = ?";

	public static final String Q_UPDATE_TOWN = "update towns t set\n"
			+ "       t.town_name = ?, \n"
			+ "       t.country_id = ?, t.town_type_id = ?,\n"
			+ "       t.normal_gmt = ?, t.winter_gmt = ?,\n"
			+ "       t.capital_town = ?, \n" + "       t.town_code = ?, \n"
			+ "       t.town_new_code = ? where t.town_id = ?";

	public static final String Q_DELETE_TOWN = "delete from towns t \n"
			+ "       where t.town_id = ?";

	public static final String Q_UPDATE_STREET_TYPE = "update street_types t set t.street_type_name_geo = ?, t.street_type_name_eng = ?, t.upd_user = ? where t.street_type_id = ? ";

	public static final String Q_UPDATE_STREET_TYPE_STATUS = "update street_types t set t.deleted = ?, t.upd_user = ? where t.street_type_id = ? ";

	public static final String Q_UPDATE_STREET_DESCR = " update street_descr t set t.street_descr_name_geo = ?, t.street_descr_name_eng = ?, t.upd_user = ? where t.street_descr_id = ? ";

	public static final String Q_UPDATE_STREET_DESCR_STATUS = "update street_descr t set t.deleted = ?, t.upd_user = ? where t.street_descr_id = ? ";

	public static final String Q_UPDATE_STREET = " update streets t set\n"
			+ "        t.town_id = ?,\n" + "        t.street_name = ?,\n"
			+ "        t.street_location = ?,\n" + "        t.upd_user = ?,\n"
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

	public static final String Q_UPDATE_STREET_STATUS = "update streets t set t.deleted = ?, t.upd_user = ? where t.street_id = ? ";

	// deletes
	public static final String Q_DELETE_PERS_NOTES = "delete from log_personell_notes t where t.note_id = ?";

	public static final String Q_DELETE_PHONES_BY_SUBSCRIBER = "delete from SUBSCRIBER_TO_PHONES t where t.SUBSCRIBER_ID = ? ";
	public static final String Q_SUBSCRIBER_ADDRESS_ID = "select s.addr_id from subscribers s where s.subscriber_id=? ";
	public static final String Q_DELETE_SUBSCRIBER_ADDRESS = "delete from addresses a where a.addr_id= ? ";
	public static final String Q_DELETE_SUBSCRIBER = "delete subscribers s where s.subscriber_id=?";

	public static final String Q_DELETE_USER_PERMISSION = " delete from user_permission t where t.user_id = ? ";

	public static final String Q_DELETE_STREET_DISCTRICTS_BY_STREET_ID = " delete from street_to_town_districts t where t.street_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE_SORT = " update main_services t set t.priority = ?,t.upd_user = ?,t.upd_date = ? where t.organization_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE_DEL_STAT = " update main_services t set t.deleted = ?,t.upd_user = ?,t.upd_date = ? where t.organization_id = ? ";

	public static final String Q_UPDATE_MAIN_SERVICE_HIST = " update main_services t set t.upd_user = ?,t.upd_date = ? where t.organization_id = ? ";

	public static final String Q_UPDATE_MAIN_ORG_STATUS = " update main_orgs t set t.statuse = ? where t.organization_id = ? ";

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
			+ "  from main_services ms,\n"
			+ "       main_orgs     mo1,\n"
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
			+ "                  from staff_education t\n"
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
			+ "                  from staff_education t\n"
			+ "                 where t.staff_id = ?) p\n"
			+ "        union all\n"
			+ "        select to_char(t.staff_education_id) as id, 'update' as command\n"
			+ "          from staff_education t\n"
			+ "         where t.staff_id = ?\n"
			+ "           and exists\n"
			+ "         (select column_value\n"
			+ "                  from table(split_table(?, ','))\n"
			+ "                 where column_value = t.staff_education_id)) y\n"
			+ " where y.id is not null  order by y.command";

	public static final String Q_DELETE_STAFF_EDUCATION = "delete from staff_education t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_COMPUTER_SKILLS = "delete from staff_computer_skills t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_LANGUAGES = "delete from staff_languages t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_PHONES = "delete from staff_phones t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_WORKS = "delete from staff_works t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_RELATIVE = "delete from staff_relative t \n "
			+ "where t.staff_id = ?";
	public static final String Q_DELETE_STAFF_FAMOUS_PEOPLE = "delete from staff_famous_people t \n "
			+ "where t.staff_id = ?";

	public static final String Q_GET_CORP_CLIENT_PHONES = "select phone_number from corp_client_phones cp where cp.corporate_client_id=?";
	
	public static final String Q_GET_SURVEYS_BY_PHONE = "select tt.survey_kind_name,\n" +
			"            r.survey_reply_type_name,\n" + 
			"            t.survey_id,\n" + 
			"            t.session_call_id,\n" + 
			"            t.p_numb,\n" + 
			"            t.survey_descript,\n" + 
			"            t.survey_phone,\n" + 
			"            t.survey_kind_id,\n" + 
			"            t.survey_reply_type_id,\n" + 
			"            t.survey_person,\n" + 
			"            t.survery_responce_status,\n" + 
			"            t.survey_done,\n" + 
			"            t.bblocked,\n" + 
			"            t.survey_creator,\n" + 
			"            t.survey_created,\n" + 
			"            t.loked_user,\n" + 
			"            ss.call_start_date start_date,\n" + 
			"             p.user_id personnel_id,\n" + 
			"             ss.operator_src,\n" + 
			"             decode (t.survery_responce_status,1,'დასრულებულია', 'ირკვევა') as survey_stat_descr\n" +			
			"  from ccare.survey t\n" + 
			"       left join ccare.survey_kind tt on tt.survey_kind_id = t.survey_kind_id\n" + 
			"       left join ccare.survey_reply_type r on r.survey_reply_type_id = t.survey_reply_type_id\n" + 
			"       left join ccare.call_sessions ss on ss.session_id = t.session_call_id\n" + 
			"       left join ccare.users p on p.user_name = t.survey_creator\n" + 
			" where trunc(t.survey_created) >= trunc(sysdate - 1)\n" + 
			"   and (t.survey_phone = ? or t.p_numb = ?)\n" + 
			"order by t.survey_created desc\n" + 
			"\n" + 
			"";
	
	public static final String Q_GET_PORT_CHECK_PHONES = "select t.call_session_id,t.call_phone from ccare.call_sessions t where t.port_checked = 0 and rownum < 101 for update ";
	public static final String Q_GET_UPDATE_SESSION = " update ccare.call_sessions t set t.port_checked = 1, t.port_company_id = ? where t.call_session_id = ? ";
	
	public static final String Q_GET_ORG_SUBS_CALL = "select decode(count(1),1,2,1) as org_subs_call from ccare.phone_numbers ph\n" +
			"         inner join ccare.organization_depart_to_phones odp on odp.phone_number_id = ph.phone_number_id\n" + 
			"         inner join ccare.organization_department od on od.org_department_id = odp.org_department_id\n" + 
			"         inner join ccare.organizations o on o.organization_id = od.organization_id\n" + 
			"where ph.phone = ccare.normalizecityphoneifpossibe(?) and rownum < 2 and ph.phone not like '5%' and ph.phone not like '790%'";
	
	
	public static final String Q_RED_ORG = "delete from ccare.org_priority_list t where t.id = ? ";
}
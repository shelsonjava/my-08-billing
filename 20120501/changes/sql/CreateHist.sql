create table hist_ORG_PARTNER_BANKS_old as
select * from hist_ORG_PARTNER_BANKS;
truncate table hist_ORG_PARTNER_BANKS;



insert into hist_ORG_PARTNER_BANKS
  (organization_id, part_bank_org_id ,partnior_bank_name  , hist_start_date, start_rcn, on_user)
select t.organization_id, part_bank_org_id,o.organization_name,sysdate,seq_rcn.NEXTVAL,'starter' from ORGANIZATION_PARTNER_BANKS t
inner join organizations O on o.organization_id=t.part_bank_org_id;



insert into hist_V_ORGANIZATION
select t.*,sysdate,null,seq_rcn.NEXTVAL,null,'starter',null from V_ORGANIZATION t;

select nvl(sph.hidden_by_request, phh.hidden_by_request) hidden_by_request,
       nvl(sph.phone_contract_type, sph.phone_contract_type) phone_contract_type,
       nvl(sph.phone_number_id, phh.phone_number_id) phone_number_id,
       nvl(phh.phone, sph.phone) phone,
       nvl(phh.phone_state_id, sph.phone_state_id) phone_state_id,
       nvl(phh.phone_type_id, sph.phone_type_id) phone_type_id,
       nvl(phh.is_parallel, sph.is_parallel) is_parallel,
       sph.rdeleted,
       sph.off_user,
       sph.end_rcn
from dual
  left join (select hs.hidden_by_request,
                    hs.phone_contract_type,
                    hs.phone_number_id,
                    ph.phone,
                    ph.phone_state_id,
                    ph.phone_type_id,
                    ph.is_parallel
               from hist_subscriber_to_phones hs
               left join phone_numbers ph
                 on ph.phone_number_id = hs.phone_number_id
              where hs.subscriber_id = 333398
                and hs.start_rcn = 376532) sph
    on 1 = 1
  left join (select sp.hidden_by_request,
                    sp.phone_contract_type,
                    sp.phone_number_id,
                    hp.phone,
                    hp.phone_state_id,
                    hp.phone_type_id,
                    hp.is_parallel
               from subscriber_to_phones sp
              inner join hist_phone_numbers hp
                 on sp.phone_number_id = hp.phone_number_id
              where sp.subscriber_id = 333398
                and hp.start_rcn = 376532) phh
    on 1 = 1
 where nvl(phh.phone_number_id, sph.phone_number_id) = sph.phone_number_id
    or nvl(sph.phone_number_id, phh.phone_number_id) = phh.phone_number_id

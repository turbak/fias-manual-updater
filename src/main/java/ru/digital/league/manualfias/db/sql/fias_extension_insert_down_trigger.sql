create function fias_extension_houses() returns trigger
    language plpgsql
as
$$
DECLARE
    var text;
BEGIN
    IF (NEW.end_date < CURRENT_DATE) THEN
        RETURN NEW;
    END IF;
    WITH RECURSIVE temp ( "ao_guid","parent_guid",ADDRESS) AS (
        SELECT T1."house_guid" as ao_guid,T1."ao_guid" as parent_guid, (COALESCE(T1.house_num::text, ''::text)||COALESCE(CASE WHEN (T1.build_num IS NOT NULL) THEN ('к'::text || (T1.build_num)::text) ELSE NULL::text END, ''::text)||COALESCE(CASE WHEN (T1.struc_num IS NOT NULL) THEN ('с'::text || (T1.struc_num)::text) ELSE NULL::text END, ''::text)) as ADDRESS
        FROM fias.tbfias_house T1 WHERE T1."house_id" = NEW.house_id
        UNION
        SELECT T2."ao_guid", T2."parent_guid", ( COALESCE((T2."short_name")::text, ''::text)|| '. '::text || COALESCE((T2."formal_name")::text,''::text) ||', '::text|| temp.ADDRESS)
        FROM fias.tbfias_address_obj T2 INNER JOIN temp ON( temp."parent_guid"= T2."ao_guid" AND T2.act_status = 1))

    SELECT fias.set_fias_extension_address(NEW.house_guid, (SELECT address from temp WHERE parent_guid IS NULL LIMIT 1), 100::INTEGER) INTO var;

    RETURN NEW;
END
$$;

alter function fias_extension_houses() owner to isod;
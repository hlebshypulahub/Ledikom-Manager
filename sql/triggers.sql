CREATE OR REPLACE FUNCTION insert_course_hours_sum()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    NEW.course_hours_sum = 0;
    RETURN NEW;
END;
$$;

CREATE TRIGGER insert_course_hours_sum
    BEFORE INSERT
    ON employee_info
    FOR EACH ROW
EXECUTE PROCEDURE insert_course_hours_sum();







CREATE OR REPLACE FUNCTION insert_employee_position_on_null()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    if(NEW.position is null) then
        NEW.position = '';
    end if;
    RETURN NEW;
END;
$$;

CREATE TRIGGER insert_employee_position_on_null
    BEFORE INSERT
    ON employee_info
    FOR EACH ROW
EXECUTE PROCEDURE insert_employee_position_on_null();







CREATE OR REPLACE FUNCTION update_5_year_start_end_dates_on_edu()
    returns trigger
    LANGUAGE plpgsql
AS
$$
BEGIN
    if NEW.graduation_date > (select five_year_start from employee_info where id_employee = NEW.id_employee)
        OR (select five_year_start from employee_info where id_employee = NEW.id_employee) is null then
        update employee_info
        set five_year_start = NEW.graduation_date,
            course_deadline_date = (NEW.graduation_date + interval '5 year')::TIMESTAMP::DATE,
            five_year_end   = (NEW.graduation_date + interval '5 year')::TIMESTAMP::DATE
        where id_employee = NEW.id_employee
          AND position::varchar LIKE ANY (is_pharm());
    end if;
    return NEW;
END;
$$;

CREATE TRIGGER update_5_year_start_end_dates_on_edu
    AFTER INSERT OR UPDATE
    ON edu
    FOR EACH ROW
EXECUTE PROCEDURE update_5_year_start_end_dates_on_edu();








CREATE OR REPLACE FUNCTION update_5_year_start_end_dates_on_category()
    returns trigger
    LANGUAGE plpgsql
AS
$$
BEGIN
    if NEW.category_assignment_date > (select five_year_start from employee_info where id_employee = NEW.id_employee)
        or (select five_year_start from employee_info where id_employee = NEW.id_employee) is null then
        update employee_info
        set five_year_start = NEW.category_assignment_date,
            course_deadline_date = (NEW.category_assignment_date + interval '5 year')::TIMESTAMP::DATE,
            five_year_end   = (NEW.category_assignment_date + interval '5 year')::TIMESTAMP::DATE
        where id_employee = NEW.id_employee
          AND position::varchar LIKE ANY (is_pharm());
    end if;
    return NEW;
END;
$$;

CREATE TRIGGER update_5_year_start_end_dates_on_category
    AFTER INSERT OR UPDATE OF category_assignment_date
    ON employee_info
    FOR EACH ROW
EXECUTE PROCEDURE update_5_year_start_end_dates_on_category();






CREATE OR REPLACE FUNCTION update_course_hours_sum_on_add_course()
    returns trigger
    LANGUAGE plpgsql
AS
$$
BEGIN
    if ((select course_deadline_date from employee_info where id_employee = NEW.id_employee) is null
        and ((select five_year_start from employee_info where id_employee = NEW.id_employee) is null and
             (select five_year_end from employee_info where id_employee = NEW.id_employee) is null)) then
        raise exception 'Следует добавить образование или категорию.
            У сотрудника не заданы даты пятилетки и конечный срок прохождения курсов.';
    elseif NEW.start_date > (select five_year_start from employee_info where id_employee = NEW.id_employee)
    then
        update employee_info
        set course_hours_sum     = course_hours_sum + NEW.hours,
            course_deadline_date = (NEW.end_date + interval '5 year 1 day')::TIMESTAMP::DATE
        where id_employee = NEW.id_employee
          AND position::varchar LIKE ANY (is_pharm());
        if (((select maternity_start_date from employee_info where id_employee = NEW.id_employee) is not null
            and (select maternity_end_date from employee_info where id_employee = NEW.id_employee) is not null)
            and ((select maternity_start_date from employee_info where id_employee = NEW.id_employee) between
                     (select five_year_start from employee_info where id_employee = NEW.id_employee)
                     and (select five_year_end from employee_info where id_employee = NEW.id_employee)
                or (select maternity_end_date from employee_info where id_employee = NEW.id_employee) between
                     (select five_year_start from employee_info where id_employee = NEW.id_employee)
                     and (select five_year_end from employee_info where id_employee = NEW.id_employee))) then
            update employee_info
            set course_deadline_date = (NEW.end_date + interval '5 year 1 day')::TIMESTAMP::DATE
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());
        end if;
        if (select course_deadline_date from employee_info where id_employee = NEW.id_employee)
            > (select five_year_end from employee_info where id_employee = NEW.id_employee) then
            update employee_info
            set course_deadline_date = five_year_end
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());
        end if;
    end if;

    if (select course_hours_sum from employee_info where id_employee = NEW.id_employee)
           >= employee_course_hours((select position from employee_info where id_employee = NEW.id_employee),
                                    (select course_deadline_date
                                     from employee_info
                                     where id_employee = NEW.id_employee))
        and ((((select maternity_start_date from employee_info where id_employee = NEW.id_employee) is not null
            and (select maternity_end_date from employee_info where id_employee = NEW.id_employee) is not null)
            and ((select maternity_start_date from employee_info where id_employee = NEW.id_employee) between
                     (select five_year_start from employee_info where id_employee = NEW.id_employee)
                     and (select five_year_end from employee_info where id_employee = NEW.id_employee)
                or (select maternity_end_date from employee_info where id_employee = NEW.id_employee) between
                     (select five_year_start from employee_info where id_employee = NEW.id_employee)
                     and (select five_year_end from employee_info where id_employee = NEW.id_employee)))
        and ((select maternity_end_date from employee_info where id_employee = NEW.id_employee) + interval '2 year')::TIMESTAMP::DATE
         > (select course_deadline_date from employee_info where id_employee = NEW.id_employee)) then
        update employee_info
        set course_deadline_date = (NEW.end_date + interval '5 year 1 day')::TIMESTAMP::DATE,
            five_year_start      = NEW.end_date,
            five_year_end        = (NEW.end_date + interval '5 year 1 day')::TIMESTAMP::DATE,
            course_hours_sum     = 0
        where id_employee = NEW.id_employee
          AND position::varchar LIKE ANY (is_pharm());
    else
        if (select course_hours_sum from employee_info where id_employee = NEW.id_employee)
               >= employee_course_hours((select position from employee_info where id_employee = NEW.id_employee),
                                        (select course_deadline_date
                                         from employee_info
                                         where id_employee = NEW.id_employee))
            and (now())::TIMESTAMP::DATE not between
               (select five_year_start from employee_info where id_employee = NEW.id_employee)
               and (case
                        when extract(doy from (select five_year_end
                                               from employee_info
                                               where id_employee = NEW.id_employee)) > 181
                            then ((date_trunc('year', (select five_year_end
                                                       from employee_info
                                                       where id_employee = NEW.id_employee))) +
                                  interval '1 year')::TIMESTAMP::DATE
                        else ((date_trunc('year', (select five_year_end
                                                   from employee_info
                                                   where id_employee = NEW.id_employee))) +
                              interval '181 day')::TIMESTAMP::DATE end) then
            update employee_info
            set course_deadline_date = (NEW.end_date + interval '5 year 1 day')::TIMESTAMP::DATE,
                course_hours_sum     = 0,
                five_year_start      = (five_year_end + interval '1 day')::TIMESTAMP::DATE,
                five_year_end        = (five_year_end + interval '5 year 1 day')::TIMESTAMP::DATE
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());
        else
            if (select course_hours_sum from employee_info where id_employee = NEW.id_employee)
                   >= employee_course_hours((select position from employee_info where id_employee = NEW.id_employee),
                                            (select course_deadline_date
                                             from employee_info
                                             where id_employee = NEW.id_employee))
                and NEW.end_date between
                   (select five_year_start from employee_info where id_employee = NEW.id_employee)
                   and (case
                            when extract(doy from (select five_year_end
                                                   from employee_info
                                                   where id_employee = NEW.id_employee)) > 181
                                then ((date_trunc('year', (select five_year_end
                                                           from employee_info
                                                           where id_employee = NEW.id_employee))) +
                                      interval '1 year')::TIMESTAMP::DATE
                            else ((date_trunc('year', (select five_year_end
                                                       from employee_info
                                                       where id_employee = NEW.id_employee))) +
                                  interval '181 day')::TIMESTAMP::DATE end) then
                update employee_info
                set course_deadline_date = (NEW.end_date + interval '5 year 1 day')::TIMESTAMP::DATE,
                    course_hours_sum     = 0
                where id_employee = NEW.id_employee
                  AND position::varchar LIKE ANY (is_pharm());
            end if;
        end if;
    end if;
    return NEW;
END;
$$;


CREATE TRIGGER update_course_hours_sum_on_add_course
    AFTER INSERT OR UPDATE
    ON course
    FOR EACH ROW
EXECUTE PROCEDURE update_course_hours_sum_on_add_course();






CREATE OR REPLACE FUNCTION update_course_dates_on_maternity()
    returns trigger
    LANGUAGE plpgsql
AS
$$
BEGIN
    if (select course_deadline_date from employee_info where id_employee = NEW.id_employee) is not null
        and (NEW.maternity_start_date between
                 (select five_year_start from employee_info where id_employee = NEW.id_employee)
                 and (select five_year_end from employee_info where id_employee = NEW.id_employee)
            or NEW.maternity_end_date between
                 (select five_year_start from employee_info where id_employee = NEW.id_employee)
                 and (select five_year_end from employee_info where id_employee = NEW.id_employee)) then
        --         if (NEW.maternity_end_date + interval '2 year')::TIMESTAMP::DATE
--             < (select course_deadline_date from employee_info where id_employee = NEW.id_employee) then
--             update employee_info
--             set five_year_end        = (course_deadline_date + interval '2 year')::TIMESTAMP::DATE,
--                 five_year_start        = (course_deadline_date - interval '3 year')::TIMESTAMP::DATE,
--                 course_deadline_date = (course_deadline_date + interval '2 year')::TIMESTAMP::DATE
--             where id_employee = NEW.id_employee
--               AND position::varchar LIKE ANY (is_pharm());
        -- else
        if (NEW.maternity_end_date + interval '2 year')::TIMESTAMP::DATE
            > (select course_deadline_date from employee_info where id_employee = NEW.id_employee) then
            update employee_info
            set five_year_end        = (NEW.maternity_end_date + interval '2 year')::TIMESTAMP::DATE,
                five_year_start      = (NEW.maternity_end_date - interval '3 year')::TIMESTAMP::DATE,
                course_deadline_date = (NEW.maternity_end_date + interval '2 year')::TIMESTAMP::DATE
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());
        end if;
    end if;
    return NEW;
END;
$$;

CREATE TRIGGER update_course_dates_on_maternity
    AFTER INSERT OR UPDATE OF maternity_start_date, maternity_end_date
    ON employee_info
    FOR EACH ROW
EXECUTE PROCEDURE update_course_dates_on_maternity();










CREATE OR REPLACE FUNCTION update_5_year_start_end_dates_on_position_add()
    returns trigger
    LANGUAGE plpgsql
AS
$$
BEGIN
    if NEW.position::varchar LIKE ANY (is_pharm())
        and (((select five_year_start from employee_info where id_employee = NEW.id_employee) is null
            or (select five_year_end from employee_info where id_employee = NEW.id_employee) is null)
            or ((select course_deadline_date from employee_info where id_employee = NEW.id_employee) is null)) then
        if (((select category_assignment_date from employee_info where id_employee = NEW.id_employee) is not null
            and (select graduation_date from edu where edu.id_employee = NEW.id_employee) is not null)
            or ((select category_assignment_date from employee_info where id_employee = NEW.id_employee)
                > (select graduation_date from edu where edu.id_employee = NEW.id_employee))) then
            raise notice '1';
            update employee_info
            set five_year_start      = category_assignment_date,
                course_deadline_date = (category_assignment_date + interval '5 year')::TIMESTAMP::DATE,
                five_year_end        = (category_assignment_date + interval '5 year')::TIMESTAMP::DATE
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());

        elseif ((select category_assignment_date from employee_info where id_employee = NEW.id_employee) is not null
            and (select graduation_date from edu where edu.id_employee = NEW.id_employee) is not null)
            and ((select category_assignment_date from employee_info where id_employee = NEW.id_employee)
                < (select graduation_date from edu where edu.id_employee = NEW.id_employee)) then
                raise notice '2';
            update employee_info
            set five_year_start      = (select graduation_date from edu where edu.id_employee = NEW.id_employee),
                course_deadline_date = ((select graduation_date from edu where edu.id_employee = NEW.id_employee) +
                                        interval '5 year')::TIMESTAMP::DATE,
                five_year_end        = ((select graduation_date from edu where edu.id_employee = NEW.id_employee) +
                                        interval '5 year')::TIMESTAMP::DATE
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());

        elseif ((select category_assignment_date from employee_info where id_employee = NEW.id_employee) is null
            and (select graduation_date from edu where edu.id_employee = NEW.id_employee) is not null) then
                raise notice '3';
            update employee_info
            set five_year_start      = (select graduation_date
                                        from edu
                                        where edu.id_employee = NEW.id_employee),
                course_deadline_date = (
                        (select graduation_date from edu where edu.id_employee = NEW.id_employee) +
                        interval '5 year')::TIMESTAMP::DATE,
                five_year_end        = (
                        (select graduation_date from edu where edu.id_employee = NEW.id_employee) +
                        interval '5 year')::TIMESTAMP::DATE
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());

        elseif ((select category_assignment_date
                 from employee_info
                 where id_employee = NEW.id_employee) is not null
            and (select graduation_date from edu where edu.id_employee = NEW.id_employee) is null) then
                raise notice '4';
            update employee_info
            set five_year_start      = category_assignment_date,
                course_deadline_date = (category_assignment_date + interval '5 year')::TIMESTAMP::DATE,
                five_year_end        = (category_assignment_date + interval '5 year')::TIMESTAMP::DATE
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());
        end if;
        update employee_info
            set course_hours_sum = 0
            where id_employee = NEW.id_employee
              AND position::varchar LIKE ANY (is_pharm());
    end if;
    return NEW;
END;
$$;

CREATE TRIGGER update_5_year_start_end_dates_on_position_add
    AFTER INSERT OR UPDATE OF position
    ON employee_info
    FOR EACH ROW
EXECUTE PROCEDURE update_5_year_start_end_dates_on_position_add();








CREATE OR REPLACE FUNCTION insert_facility_code_on_add()
    RETURNS TRIGGER
    LANGUAGE plpgsql
AS
$$
BEGIN
    if (NEW.status is not null) then
        if (select substring((select name from facility where id_facility = NEW.id_facility) from
                             '[0-9]+')) is not null then
            NEW.code = concat(left(NEW.status::text, 1),
                              substring((select name from facility where id_facility = NEW.id_facility) from
                                        '[0-9]+'));
        else
            NEW.code = concat(left(NEW.status::text, 1), NEW.id_facility);
        end if;
    elseif (NEW.status is null) then
        NEW.code = concat(left((select name from facility where id_facility = NEW.id_facility), 1),
                          substring((select name from facility where id_facility = NEW.id_facility) from
                                    '[0-9]+'));
    end if;
    RETURN NEW;
END;
$$;

CREATE TRIGGER insert_facility_code_on_add
    BEFORE INSERT
    ON facility_info
    FOR EACH ROW
EXECUTE PROCEDURE insert_facility_code_on_add();








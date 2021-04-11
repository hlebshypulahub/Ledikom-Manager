CREATE OR REPLACE FUNCTION is_pharm()
    returns varchar[]
    LANGUAGE plpgsql
AS
$$
BEGIN
    return array['%Провизор%', '%Фармацевт%', '%провизор%', '%фармацевт%'];
END;
$$;


create or replace function dob_notifications_period() returns integer
    language plpgsql
as
$$
begin
    return 365;
end;
$$;



create or replace function farmacevt_course_hours() returns integer
    language plpgsql
as
$$
begin
    return 40;
end;
$$;


create or replace function provizor_course_hours() returns integer
    language plpgsql
as
$$
begin
    return 100;
end;
$$;

create or replace function provizor_course_hours(coursedeadlinedate date) returns integer
    language plpgsql
as
$$
begin
    if courseDeadlineDate < '2005-01-01'::DATE then
        return 80;
    else
        return 100;
    end if;
end;
$$;



create or replace function employee_course_hours(valPosition employee_position) returns integer
    language plpgsql
as
$$
begin
    if (valPosition::varchar LIKE ANY ('{"%Провизор%", "%провизор%"}')) then
        return provizor_course_hours();
    elseif (valPosition::varchar LIKE ANY ('{"%Фармацевт%", "%фармацевт%"}')) then
        return farmacevt_course_hours();
    else
        return 0;
    end if;
end;
$$;

create or replace function employee_course_hours(valposition employee_position, coursedeadlinedate date) returns integer
    language plpgsql
as
$$
begin
    if (valPosition::varchar LIKE ANY ('{"%Провизор%", "%провизор%"}')) then
        return provizor_course_hours(courseDeadlineDate);
    elseif (valPosition::varchar LIKE ANY ('{"%Фармацевт%", "%фармацевт%"}')) then
        return farmacevt_course_hours();
    end if;
end;
$$;



CREATE OR REPLACE FUNCTION update_employee_dates_on_app_start()
    returns void
    LANGUAGE plpgsql
AS
$$
BEGIN
    update employee_info
    set five_year_start = case
                              when (five_year_end is not null)
                                  and ((five_year_end + interval '5 year 1 day')::TIMESTAMP::DATE
                                      between (now() +interval '4 year 180 day')::TIMESTAMP::DATE and (now() + interval '5 year 1 day')::TIMESTAMP::DATE)
                                  then five_year_end + interval '1 day'
                              else five_year_start
        end;
    update employee_info
    set five_year_end = case
                            when (five_year_end is not null)
                                and (five_year_end < five_year_start)
                                then five_year_end + interval '5 year 1 day'
                            else five_year_end
        end;
END;
$$;




CREATE OR REPLACE FUNCTION parse_date_to_varchar(dateToParse date)
    returns varchar
    LANGUAGE plpgsql
AS
$$
BEGIN
    return concat(substring(dateToParse::varchar, 9, 2), '.',
                  substring(dateToParse::varchar, 6, 2), '.',
                  substring(dateToParse::varchar, 1, 4));
END;
$$;





CREATE OR REPLACE FUNCTION get_employees_courses()
    returns table
            (
                id_employee      int,
                employee_name    text,
                pos              employee_position,
                edu              text,
                category         text,
                course_name      varchar,
                course_data      text,
                next_course_data text
            )
    LANGUAGE plpgsql
AS
$$
BEGIN
    return query (select c.id_employee,
                         concat(last_name, ' ', substring(first_name, 1, 1), '. ', substring(patronymic, 1, 1),
                                '.')                                                   as employee_name,
                         ei.position,
                         concat(e.name, ' ', parse_date_to_varchar(e.graduation_date)) as edu,
                         case
                             when ei.category is not null then concat(ei.category, ' категория, ', ei.category_num,
                                                                      ' от ',
                                                                      parse_date_to_varchar(ei.category_assignment_date))
                             else '' end                                               as category,
                         c.name                                                        as course_name,
                         concat(parse_date_to_varchar(c.start_date), '-', parse_date_to_varchar(c.end_date), ' (',
                                c.hours,
                                ' ч)')                                                 as course_data,
                         concat('До ', parse_date_to_varchar(ei.course_deadline_date), ' в объёме ',
                                employee_course_hours(ei.position) - ei.course_hours_sum,
                                ' ч.')                                                 as next_course_data
                  from employee
                           join course c on employee.id_employee = c.id_employee
                           join edu e on employee.id_employee = e.id_employee
                           join employee_info ei on employee.id_employee = ei.id_employee
                  order by id_employee, start_date desc);
END;
$$;

























CREATE OR REPLACE FUNCTION addFacility(varchar, facility_status)
    RETURNS BOOLEAN AS
$$
DECLARE
    nameAdd ALIAS FOR $1;
    statusAdd ALIAS FOR $2;
    existsCheck BOOLEAN;
    id          RECORD;
BEGIN
    SELECT EXISTS(SELECT f.name, fi.status
                  FROM facility f
                           JOIN facility_info fi ON f.id_facility = fi.id_facility
                  WHERE f.name = nameAdd
                    AND fi.status = statusAdd)
    INTO existsCheck;
    IF (SELECT existsCheck = FALSE) THEN
        INSERT INTO facility (name) VALUES (nameAdd);
        SELECT id_facility INTO id FROM facility WHERE name = nameAdd;
        INSERT INTO facility_info (id_facility, status) VALUES (id.id_facility, statusAdd);
    ELSE
        RAISE EXCEPTION 'Obiekt o takim imieniu i statusie już istnieje!';
    END IF;
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	



CREATE OR REPLACE FUNCTION addEmployee(firstNameAdd varchar, lastNameAdd varchar, usernameAdd varchar,
                                       passwordAdd varchar, roleAdd employee_role, dobAdd date, phoneAdd varchar,
                                       addressAdd varchar, positionAdd employee_position, categoryAdd employee_category,
                                       salaryAdd integer, ppeAdd date, courseHourseSumAdd integer)
    RETURNS BOOLEAN AS
$$
DECLARE
    existsCheck BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT ed.username
                  FROM employee_data ed
                  WHERE ed.username = usernameAdd)
    INTO existsCheck;
    IF (SELECT existsCheck = FALSE) THEN

        WITH id
                 AS (INSERT INTO employee (last_name, first_name) VALUES (firstNameAdd, lastNameAdd) RETURNING id_employee)
        INSERT
        INTO employee_data (id_employee, username, password, role)
        SELECT id_employee, usernameAdd, passwordAdd, roleAdd
        FROM id;

        WITH id
                 AS (SELECT ed.id_employee
                     FROM employee_data ed WHERE ed.username = usernameAdd)
        INSERT
        INTO employee_info (id_employee, dob, phone, address, position, category, salary, ppe, course_hours_sum)
        SELECT id_employee,
               dobAdd,
               phoneAdd,
               addressAdd,
               positionAdd,
               categoryAdd,
               salaryAdd,
               ppeAdd,
               courseHourseSumAdd
        FROM id;

    ELSE
        RAISE EXCEPTION 'Nazwa użytkownika jest zajęta!';
    END IF;
    RETURN TRUE;
END ;
$$
    LANGUAGE 'plpgsql';
	
	
	
	

CREATE OR REPLACE FUNCTION addEmployeeToFacility(employeeId integer, facilityStatus facility_status, facilityName varchar)
    RETURNS BOOLEAN AS
$$
DECLARE
    existsCheck BOOLEAN;
    facilityId  INTEGER;
BEGIN
    SELECT (SELECT f.id_facility
    FROM facility f,
         facility_info fi
    WHERE f.name = facilityName
      AND fi.status = facilityStatus)
    INTO facilityId;
    SELECT EXISTS(SELECT ef.id_facility, ef.id_employee
                  FROM employee_facility ef
                  WHERE ef.id_facility = facilityId
                    AND ef.id_employee = employeeId)
    INTO existsCheck;
    IF (SELECT existsCheck = FALSE) THEN
        INSERT INTO employee_facility (id_facility, id_employee) VALUES (facilityId, employeeId);
    ELSE
        RAISE EXCEPTION 'Ten pracownik już jest zarejestrowany na tym obiekcie!';
    END IF;
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	
	
	
	
	
CREATE OR REPLACE FUNCTION loginAndFillInfo(employeeUsername varchar, employeePassword varchar)
    RETURNS TABLE
            (
                id_emp    integer,
                role             employee_role,
                first_name       varchar,
                last_name        varchar,
                dob              date,
                category         employee_category,
                pos         employee_position,
                ppe              date,
                salary           integer,
                phone            varchar,
                address          varchar,
                course_hours_sum integer
            )
AS
$$
DECLARE
    existsCheck BOOLEAN;
    employeeId  INTEGER;
BEGIN
    SELECT EXISTS(SELECT id_employee
                  FROM employee_data
                  WHERE username = employeeUsername
                    AND password = employeePassword)
    INTO existsCheck;
    IF (SELECT existsCheck = TRUE) THEN
        SELECT (SELECT id_employee
                FROM employee_data
                WHERE username = employeeUsername
                  AND password = employeePassword)
        INTO employeeId;
        RETURN QUERY SELECT * FROM employee_data_view edv WHERE edv.id_employee = employeeId;
    ELSE
        RAISE EXCEPTION 'Sprawdź poprawność username lub password!';
    END IF;
END;
$$
	LANGUAGE 'plpgsql';
	
	
	
	
	
	
CREATE OR REPLACE FUNCTION addCheckup(inspectionIdAdd integer, questionAdd checkup_question, answerAdd checkup_answer, dateAdd date, employeeIdAdd integer, descriptionAdd varchar)
    RETURNS BOOLEAN AS
$$
BEGIN
    IF (answerAdd = 'Nie') THEN

        WITH checkupId
                 AS (INSERT INTO checkup (id_inspection, question, answer) VALUES (inspectionIdAdd, questionAdd, answerAdd) RETURNING id_checkup)
        INSERT
        INTO violation (id_employee, id_checkup, correction_term, description)
        SELECT employeeIdAdd, id_checkup, dateAdd, descriptionAdd
        FROM checkupId;

    ELSE

        INSERT INTO checkup (id_inspection, question, answer) VALUES (inspectionIdAdd, questionAdd, answerAdd);

    END IF;
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	
	
	
	
	
	
	
CREATE OR REPLACE FUNCTION addHolidayForFacility(facilityIdAdd integer, employeeIdAdd integer, holidayNameAdd holiday_name,
                                                 holidayDateAdd date, holidayProceedsAdd float)
    RETURNS BOOLEAN AS
$$
DECLARE
    holidayId integer;
BEGIN
    INSERT INTO holiday (date, name) VALUES (holidayDateAdd, holidayNameAdd) RETURNING id_holiday INTO holidayId;
    INSERT INTO employee_holiday (id_holiday, id_employee) VALUES (holidayId, employeeIdAdd);
    INSERT INTO facility_holiday (id_holiday, id_facility, proceeds) VALUES (holidayId, facilityIdAdd, holidayProceedsAdd);
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	
	
	
	
	
	
CREATE OR REPLACE FUNCTION deleteEmployee(employeeId integer)
    RETURNS BOOLEAN AS
$$
DECLARE
    existsCheck BOOLEAN;
BEGIN
    SELECT EXISTS(SELECT id_employee
                  FROM employee
                  WHERE id_employee = employeeId)
    INTO existsCheck;
    IF (SELECT existsCheck = TRUE) THEN

        DELETE FROM violation WHERE id_employee = employeeId OR id_checkup IN (SELECT checkup.id_checkup FROM checkup WHERE id_inspection IN (SELECT id_inspection FROM inspection WHERE id_employee = employeeId));
        DELETE FROM checkup WHERE id_inspection IN (SELECT id_inspection FROM inspection WHERE id_employee = employeeId);
        DELETE FROM inspection WHERE id_employee = employeeId;
        DELETE FROM employee_facility WHERE id_employee = employeeId;
        DELETE FROM employee_holiday WHERE id_employee = employeeId;
        DELETE FROM employee_data WHERE id_employee = employeeId;
        DELETE FROM employee_info WHERE id_employee = employeeId;
        DELETE FROM employee_course WHERE id_employee = employeeId;
        DELETE FROM employee WHERE id_employee = employeeId;

    ELSE
        RAISE EXCEPTION 'Pracownik już nie istnieje w bazie!';
    END IF;
    RETURN TRUE;
END;
$$
    LANGUAGE 'plpgsql';
	
	
	
	
	
CREATE OR REPLACE FUNCTION addCourse(employeeIdAdd integer, courseNameAdd varchar, courseHoursAdd integer)
    RETURNS INTEGER AS
$$
DECLARE
    courseId INTEGER;
    hours INTEGER;
BEGIN
    INSERT INTO course (name) VALUES (courseNameAdd) RETURNING id_course INTO courseId;
    INSERT INTO course_info (id_course, hours) VALUES (courseId, courseHoursAdd);
    INSERT INTO employee_course (id_employee, id_course) VALUES (employeeIdAdd, courseId);
    SELECT course_hours_sum INTO hours FROM employee_info WHERE id_employee = employeeIdAdd;
    hours = hours + courseHoursAdd;
    UPDATE employee_info SET course_hours_sum = hours WHERE id_employee = employeeIdAdd;
    RETURN hours;
END;
$$
    LANGUAGE 'plpgsql';
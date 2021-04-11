CREATE SEQUENCE public.employee_id_employee_seq;

CREATE TABLE public.employee
(
    id_employee INTEGER     NOT NULL DEFAULT nextval('public.employee_id_employee_seq'),
    last_name   VARCHAR NOT NULL,
    first_name  VARCHAR NOT NULL,
    patronymic  VARCHAR,
    CONSTRAINT employee_pk PRIMARY KEY (id_employee)
);

ALTER SEQUENCE public.employee_id_employee_seq OWNED BY public.employee.id_employee;

CREATE TABLE public.employee_info (
                id_employee INTEGER NOT NULL,
                DOB DATE,
                phone VARCHAR,
                address VARCHAR,
                salary INTEGER,
                PPE DATE,
                five_year_start DATE,
                five_year_end DATE,
                course_hours_sum INTEGER,
                course_deadline_date DATE,
                dismissal_date DATE,
                position employee_position,
                category employee_category,
                category_num VARCHAR,
                category_assignment_date DATE,
				maternity_start_date DATE,
				maternity_end_date DATE,
                CONSTRAINT employee_info_pk PRIMARY KEY (id_employee)
);
COMMENT ON COLUMN public.employee_info.PPE IS 'personal protective equipment';

ALTER TABLE public.employee_info ADD CONSTRAINT employee_emp_info_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
			
			
			
			
			
CREATE SEQUENCE public.edu_id_edu_seq;

CREATE TABLE public.edu (
                id_edu INTEGER NOT NULL DEFAULT nextval('public.edu_id_edu_seq'),
                id_employee INTEGER NOT NULL,
                name VARCHAR,
                graduation_date DATE,
                CONSTRAINT edu_pk PRIMARY KEY (id_edu)
);

ALTER SEQUENCE public.edu_id_edu_seq OWNED BY public.edu.id_edu;

ALTER TABLE public.edu ADD CONSTRAINT employee_edu_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

create unique index id_employee_edu_index on edu(id_employee);





CREATE SEQUENCE public.course_id_course_seq;

CREATE TABLE public.course (
                id_course INTEGER NOT NULL DEFAULT nextval('public.course_id_course_seq'),
                id_employee INTEGER NOT NULL,
                name VARCHAR,
                description VARCHAR,
                hours INTEGER,
                start_date DATE,
                end_date DATE,
                CONSTRAINT course_pk PRIMARY KEY (id_course)
);

ALTER SEQUENCE public.course_id_course_seq OWNED BY public.course.id_course;

ALTER TABLE public.course ADD CONSTRAINT employee_course_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;






CREATE SEQUENCE public.facility_id_facility_seq;

CREATE TABLE public.facility (
                id_facility INTEGER NOT NULL DEFAULT nextval('public.facility_id_facility_seq'),
                name VARCHAR,
                CONSTRAINT facility_pk PRIMARY KEY (id_facility)
);

ALTER SEQUENCE public.facility_id_facility_seq OWNED BY public.facility.id_facility;

CREATE TABLE public.facility_info (
                id_facility INTEGER NOT NULL,
                address VARCHAR,
				city VARCHAR,
                status facility_status,
				category facility_category,
                code VARCHAR,
                schedule VARCHAR,
                email VARCHAR,
                phone VARCHAR,
                CONSTRAINT facility_info_pk PRIMARY KEY (id_facility)
);

CREATE TABLE public.employee_facility (
                id_facility INTEGER NOT NULL,
                id_employee INTEGER NOT NULL,
                CONSTRAINT employee_facility_pk PRIMARY KEY (id_facility, id_employee)
);


CREATE SEQUENCE public.contract_id_contract_seq;

CREATE TABLE public.contract (
                id_contract INTEGER NOT NULL DEFAULT nextval('public.contract_id_contract_seq'),
                id_facility INTEGER NOT NULL,
                id_employee INTEGER NOT NULL,
                type contract_type,
                start_date DATE,
                expiration_date DATE,
                CONSTRAINT contract_pk PRIMARY KEY (id_contract, id_facility, id_employee)
);


ALTER SEQUENCE public.contract_id_contract_seq OWNED BY public.contract.id_contract;

ALTER TABLE public.facility_info ADD CONSTRAINT facility_fac_info_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_facility ADD CONSTRAINT facility_emp_fac_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.employee_facility ADD CONSTRAINT employee_emp_fac_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.contract ADD CONSTRAINT emp_fac_contract_fk
FOREIGN KEY (id_facility, id_employee)
REFERENCES public.employee_facility (id_facility, id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

CREATE SEQUENCE public.checkup_type_id_checkup_type_seq;

CREATE TABLE public.checkup_type (
                id_checkup_type INTEGER NOT NULL DEFAULT nextval('public.checkup_type_id_checkup_type_seq'),
                type_name UNKNOWN NOT NULL,
                CONSTRAINT checkup_type_pk PRIMARY KEY (id_checkup_type)
);


ALTER SEQUENCE public.checkup_type_id_checkup_type_seq OWNED BY public.checkup_type.id_checkup_type;

CREATE SEQUENCE public.checkup_question_id_checkup_question_seq;

CREATE TABLE public.checkup_question (
                id_checkup_question REAL NOT NULL DEFAULT nextval('public.checkup_question_id_checkup_question_seq'),
                id_checkup_type INTEGER NOT NULL,
                question VARCHAR,
                CONSTRAINT checkup_question_pk PRIMARY KEY (id_checkup_question)
);


ALTER SEQUENCE public.checkup_question_id_checkup_question_seq OWNED BY public.checkup_question.id_checkup_question;

CREATE SEQUENCE public.inspection_id_inspection_seq;

CREATE TABLE public.inspection (
                id_inspection INTEGER NOT NULL DEFAULT nextval('public.inspection_id_inspection_seq'),
                id_employee INTEGER NOT NULL,
                id_facility INTEGER NOT NULL,
                date DATE,
                note VARCHAR,
                CONSTRAINT inspection_pk PRIMARY KEY (id_inspection)
);


ALTER SEQUENCE public.inspection_id_inspection_seq OWNED BY public.inspection.id_inspection;

CREATE SEQUENCE public.checkup_id_checkup_seq;

CREATE TABLE public.checkup (
                id_checkup INTEGER NOT NULL DEFAULT nextval('public.checkup_id_checkup_seq'),
                id_inspection INTEGER NOT NULL,
                id_checkup_question REAL NOT NULL,
                answer UNKNOWN NOT NULL,
                note VARCHAR,
                CONSTRAINT checkup_pk PRIMARY KEY (id_checkup)
);


ALTER SEQUENCE public.checkup_id_checkup_seq OWNED BY public.checkup.id_checkup;

CREATE SEQUENCE public.violation_id_violation_seq;

CREATE TABLE public.violation (
                id_violation INTEGER NOT NULL DEFAULT nextval('public.violation_id_violation_seq'),
                id_employee INTEGER NOT NULL,
                id_checkup INTEGER NOT NULL,
                note VARCHAR,
                correction_term DATE,
                correction_date DATE,
                CONSTRAINT violation_pk PRIMARY KEY (id_violation)
);


ALTER SEQUENCE public.violation_id_violation_seq OWNED BY public.violation.id_violation;

ALTER TABLE public.checkup_question ADD CONSTRAINT checkup_type_checkup_question_fk
FOREIGN KEY (id_checkup_type)
REFERENCES public.checkup_type (id_checkup_type)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.checkup ADD CONSTRAINT checkup_question_checkup_fk
FOREIGN KEY (id_checkup_question)
REFERENCES public.checkup_question (id_checkup_question)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.inspection ADD CONSTRAINT facility_inspection_fk
FOREIGN KEY (id_facility)
REFERENCES public.facility (id_facility)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.inspection ADD CONSTRAINT employee_inspection_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.violation ADD CONSTRAINT employee_violation_fk
FOREIGN KEY (id_employee)
REFERENCES public.employee (id_employee)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.checkup ADD CONSTRAINT inspection_check_fk
FOREIGN KEY (id_inspection)
REFERENCES public.inspection (id_inspection)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.violation ADD CONSTRAINT check_violation_fk
FOREIGN KEY (id_checkup)
REFERENCES public.checkup (id_checkup)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;
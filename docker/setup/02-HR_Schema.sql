ALTER
    SESSION SET CONTAINER=PDB;
ALTER
    SESSION SET CURRENT_SCHEMA = hr;

-- DEPARTMENTS table holds company department information.
CREATE SEQUENCE departments_seq
    START WITH 10
    INCREMENT BY 1
    MAXVALUE 9990 NOCACHE
    NOCYCLE;

CREATE TABLE departments
(
    department_id   NUMBER(4) DEFAULT departments_seq.nextval NOT NULL
        CONSTRAINT dept_id_pk
            PRIMARY KEY,
    department_name VARCHAR2(30)                              NOT NULL,
    location        VARCHAR2(16)                              NOT NULL
);

-- insert data into the DEPARTMENTS table
BEGIN
    INSERT INTO departments
    VALUES ( departments_seq.nextval
           , 'Administration'
           , 'Moldova');

    INSERT INTO departments
    VALUES ( departments_seq.nextval
           , 'Marketing'
           , 'Amsterdam');

    INSERT INTO departments
    VALUES ( departments_seq.nextval
           , 'Purchasing'
           , 'Berlin');

    INSERT INTO departments
    VALUES ( departments_seq.nextval
           , 'Human Resources'
           , 'USA');

    INSERT INTO departments
    VALUES ( departments_seq.nextval
           , 'Shipping'
           , 'Brazil');

    INSERT INTO departments
    VALUES ( departments_seq.nextval
           , 'IT'
           , 'China');

    INSERT INTO departments
    VALUES ( departments_seq.nextval
           , 'Public Relations'
           , 'Madagascar');

    INSERT INTO departments
    VALUES ( departments_seq.nextval
           , 'Sales'
           , 'Italy');
END;
/


-- EMPLOYEES table holds the employee personnel  information for the company.
CREATE SEQUENCE employees_seq
    START WITH 100
    INCREMENT BY 1
    NOCACHE
    NOCYCLE;

CREATE TABLE employees
(
    employee_id   NUMBER(6) DEFAULT employees_seq.nextval NOT NULL
        CONSTRAINT emp_emp_id_pk
            PRIMARY KEY,
    first_name    VARCHAR2(20)
        CONSTRAINT emp_first_name_nn NOT NULL,
    last_name     VARCHAR2(25)
        CONSTRAINT emp_last_name_nn NOT NULL,
    department_id NUMBER(4),
    CONSTRAINT emp_dept_fk
        FOREIGN KEY (department_id)
            REFERENCES departments,
    email         VARCHAR2(25)
        CONSTRAINT emp_email_nn NOT NULL,
    CONSTRAINT emp_email_uk
        UNIQUE (email),
    phone_number  VARCHAR2(20)
        CONSTRAINT emp_phone_number_nn NOT NULL,
    CONSTRAINT emp_phone_number_uk
        UNIQUE (phone_number),
    salary        NUMBER(8, 2)
        CONSTRAINT emp_salary_min
            CHECK (salary >= 1.0)
);

-- insert data into the EMPLOYEES table
BEGIN
    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Steven'
           , 'King'
           , 10
           , 'sking@com.f'
           , '515.123.4567'
           , 24000);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Neena'
           , 'Kochhar'
           , 11
           , 'nkochhar@com.d'
           , '515.123.4568'
           , 17000);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Lex'
           , 'De Haan'
           , 13
           , 'ldehaan@com.d'
           , '515.123.4569'
           , 17000);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Alexander'
           , 'Hunold'
           , 14
           , 'ahunold@com.d'
           , '590.423.4567'
           , 9000);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Bruce'
           , 'Ernst'
           , 13
           , 'bernst@com.d'
           , '590.423.4568'
           , 6065);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'David'
           , 'Austin'
           , 15
           , 'daustin@com.d'
           , '590.423.4569'
           , 4800);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Valli'
           , 'Pataballa'
           , 16
           , 'vpatabal@com.d'
           , '590.423.4560'
           , 4800);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Diana'
           , 'Lorentz'
           , 15
           , 'dlorentz@com.d'
           , '590.423.5567'
           , 4200);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Nancy'
           , 'Greenberg'
           , 16
           , 'ngreenbe@com.d'
           , '515.124.4569'
           , 1014);

    INSERT INTO employees
    VALUES ( employees_seq.nextval
           , 'Daniel'
           , 'Faviet'
           , 13
           , 'dfaviet@com.d'
           , '515.124.4169'
           , 9000);
END;
/


COMMENT
    ON TABLE departments
    IS 'Departments table that shows details of departments where employees work. Contains 27 rows; references with locations, employees, and job_history tables.';


COMMENT
    ON COLUMN departments.department_id
    IS 'Primary key column of departments table.';


COMMENT
    ON COLUMN departments.department_name
    IS 'A not null column that shows name of a department. Administration, Marketing, Purchasing, Human Resources, Shipping, IT, Executive, Public Relations, Sales, Finance, and Accounting. ';


COMMENT
    ON COLUMN departments.location
    IS 'Location  where a department is located. Foreign key to location_id column of locations table.';
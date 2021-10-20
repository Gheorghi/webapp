# Webapp

SpringData implementation of a simple web application to perform REST CRUD.

Steps to run this project:

1. Install Docker Desktop
2. You need to pull an Oracle 12c docker image: aleanca/oracledb-12.2.0.1-ee

- docker pull aleanca/oracledb-12.2.0.1-ee:12.2.0.1-ee-01

3. Open powershell or cmd.exe in 'docker' folder inside this project
4. Run: docker compose up -d
5. The setup should last a while. You can verify the startup progress with docker logs -f oracle (double CTRL+C to exit)
6. Connect to the pluggable database with this:
   JDBC URL: jdbc:oracle:thin:@//localhost:1521/PDB user: hr password: hr

Structure:

- In app used <Layered architecture>
- Exist only two tables (Employees & Departments).

Validation:

- Empl salary column validation for min salary in DB level and API.
- POST/PUT Empl. email & phone_number columns have validation for "uniqueness" in DB level.
- All columns have validation for "not null" in DB level and API.
- All columns have validation for "not blank" at the API level.
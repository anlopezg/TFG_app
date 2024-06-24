# TFG Web App Project 

## Requirements

- Node 18.
- Java 17 (tested with Eclipse Temurin).
- Maven 3.8+.
- MySQL 8.

## Database creation

```
Start Mysql server if not running (e.g. mysqld).

mysqladmin -u root create yarncrafters -p
mysqladmin -u root create yarncrafterstest -p

mysql -u root -p
    CREATE USER 'yarncrafters'@'localhost' IDENTIFIED BY 'yarncrafters';
    GRANT ALL PRIVILEGES ON yarncrafters.* to 'yarncrafters'@'localhost' WITH GRANT OPTION;
    GRANT ALL PRIVILEGES ON yarncrafterstest.* to 'yarncrafters'@'localhost' WITH GRANT OPTION;
    exit
```

## Run

```
cd backend
mvn sql:execute (only first time to create tables)
mvn spring-boot:run

cd frontend
npm install (only first time to download libraries)
npm run dev
```

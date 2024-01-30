DROP TABLE User;

CREATE TABLE User (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userName VARCHAR(60) COLLATE latin1_bin NOT NULL,
    email VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    firstName VARCHAR(60) NOT NULL,
    language VARCHAR(60) NOT NULL,
    country VARCHAR(60) NOT NULL,
    crochetLevel TINYINT NOT NULL,
    knitLevel TINYINT NOT NULL,
    bio VARCHAR(200),
    role TINYINT NOT NULL,
    CONSTRAINT UserPK PRIMARY KEY (id),
    CONSTRAINT UserNameUniqueKey UNIQUE (userName),
    CONSTRAINT EmailUniqueKey UNIQUE (email)

) ENGINE = InnoDB;

CREATE INDEX UserIndexByUserName ON User (userName);

CREATE INDEX UserIndexByEmail ON User (email);

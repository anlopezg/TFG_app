DROP TABLE Pattern;
DROP TABLE Product;
DROP TABLE Category;
DROP TABLE Craft;
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


CREATE TABLE Craft (
    id BIGINT NOT NULL AUTO_INCREMENT,
    craftName VARCHAR(60) NOT NULL,
    CONSTRAINT CraftPK PRIMARY KEY (id),
    CONSTRAINT CraftNameUnique UNIQUE (craftName)

) ENGINE = InnoDB;

CREATE INDEX CraftIndexByCraftName ON Craft (craftName);

CREATE TABLE Category (
    id BIGINT NOT NULL AUTO_INCREMENT,
    parentId BIGINT,
    categoryName VARCHAR(60) NOT NULL,
    CONSTRAINT CategoryPK PRIMARY KEY (id),
    CONSTRAINT CategoryNameUnique UNIQUE (categoryName),
    CONSTRAINT CategoryParentFK FOREIGN KEY (parentId) REFERENCES Category(id)
) ENGINE = InnoDB;

CREATE INDEX CategoryIndexByCategoryName ON Category (categoryName);

CREATE TABLE Product (

    id BIGINT NOT NULL AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    craftId BIGINT NOT NULL,
    categoryId BIGINT NOT NULL,


    title VARCHAR(60) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(11,2) NOT NULL,
    active BOOLEAN NOT NULL,
    creationDate DATETIME NOT NULL,

    amount INT NOT NULL,
    size VARCHAR(60) NOT NULL,
    color VARCHAR(60) NOT NULL,
    details VARCHAR(500) NOT NULL,

    CONSTRAINT ProductPK PRIMARY KEY (id),
    CONSTRAINT ProductUserFK FOREIGN KEY (userId) REFERENCES User(id),
    CONSTRAINT ProductCraftFK FOREIGN KEY (craftId) REFERENCES Craft(id),
    CONSTRAINT ProductCategoryFK FOREIGN KEY (categoryId) REFERENCES Category(id),
    CONSTRAINT ProductTitleUnique UNIQUE (title)

) ENGINE = InnoDB;

CREATE INDEX ProductIndexByTitle ON Product(title);

CREATE TABLE Pattern (

    id BIGINT NOT NULL AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    craftId BIGINT NOT NULL,
    categoryId BIGINT NOT NULL,

    title VARCHAR(60) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(11,2) NOT NULL,
    active BOOLEAN NOT NULL,
    creationDate DATETIME NOT NULL,

    introduction VARCHAR(500) NOT NULL,
    abbreviations VARCHAR(500) NOT NULL,
    notes VARCHAR(500) NOT NULL,
    gauge VARCHAR(200) NOT NULL,
    size VARCHAR(200) NOT NULL,
    difficultyLevel TINYINT NOT NULL,
    time VARCHAR(60) NOT NULL,

    CONSTRAINT PatternPK PRIMARY KEY (id),
    CONSTRAINT PatternUserFK FOREIGN KEY (userId) REFERENCES User(id),
    CONSTRAINT PatternCraftFK FOREIGN KEY (craftId) REFERENCES Craft(id),
    CONSTRAINT PatternCategoryFK FOREIGN KEY (categoryId) REFERENCES Category(id)
) ENGINE = InnoDB;

CREATE INDEX PatternIndexByTitle ON Pattern(title);
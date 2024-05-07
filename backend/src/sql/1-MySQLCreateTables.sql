DROP TABLE PurchaseItem;
DROP TABLE Purchase;
DROP TABLE ShoppingCartItem;
DROP TABLE ShoppingCart;
DROP TABLE ProductImages;
DROP TABLE Favorite;
DROP TABLE Product;
DROP TABLE Subcategory;
DROP TABLE Category;
DROP TABLE Craft;
DROP TABLE User;

CREATE TABLE User (
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(60) COLLATE latin1_bin NOT NULL,
    email VARCHAR(60) NOT NULL,
    password VARCHAR(60) NOT NULL,
    firstName VARCHAR(60) NOT NULL,
    language VARCHAR(60) NOT NULL,
    country VARCHAR(60) NOT NULL,
    region VARCHAR(60) NOT NULL,
    crochetLevel TINYINT NOT NULL,
    knitLevel TINYINT NOT NULL,
    bio VARCHAR(200),
    role TINYINT NOT NULL,
    CONSTRAINT UserPK PRIMARY KEY (id),
    CONSTRAINT UsernameUniqueKey UNIQUE (username),
    CONSTRAINT EmailUniqueKey UNIQUE (email)

) ENGINE = InnoDB;

CREATE INDEX UserIndexByUsername ON User (username);

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
    categoryName VARCHAR(60) NOT NULL,
    CONSTRAINT CategoryPK PRIMARY KEY (id),
    CONSTRAINT CategoryNameUnique UNIQUE (categoryName)
) ENGINE = InnoDB;

CREATE INDEX CategoryIndexByCategoryName ON Category (categoryName);

CREATE TABLE Subcategory(
    id BIGINT NOT NULL AUTO_INCREMENT,
    subcategoryName VARCHAR(60) NOT NULL,
    categoryId BIGINT NOT NULL,
    CONSTRAINT SubcategoryPK PRIMARY KEY (id),
    CONSTRAINT CategoryParentFK FOREIGN KEY (categoryId) REFERENCES Category(id)
) ENGINE = InnoDB;

CREATE TABLE Product (

    id BIGINT NOT NULL AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    craftId BIGINT NOT NULL,
    subcategoryId BIGINT NOT NULL,

    -- Common attributes
    title VARCHAR(60) NOT NULL,
    description VARCHAR(500) NOT NULL,
    price DECIMAL(11,2) NOT NULL,
    active BOOLEAN NOT NULL,
    creationDate DATETIME NOT NULL,
    productType VARCHAR(20) NOT NULL,

    -- Physical products attributes
    amount INT,
    size VARCHAR(60),
    color VARCHAR(60),
    details VARCHAR(500),

    -- Pattern attributes
    introduction VARCHAR(500),
    notes VARCHAR(500),
    time VARCHAR(60) ,
    difficultyLevel TINYINT,
    abbreviations VARCHAR(60),
    specialAbbreviations VARCHAR(500),
    gauge VARCHAR(200),
    sizing VARCHAR(200),
    tools VARCHAR(500),


    CONSTRAINT ProductPK PRIMARY KEY (id),
    CONSTRAINT ProductUserFK FOREIGN KEY (userId) REFERENCES User(id),
    CONSTRAINT ProductCraftFK FOREIGN KEY (craftId) REFERENCES Craft(id),
    CONSTRAINT ProductSubcategoryFK FOREIGN KEY (subcategoryId) REFERENCES Subcategory(id)

) ENGINE = InnoDB;

CREATE INDEX ProductIndexByTitle ON Product(title);


CREATE TABLE ProductImages(

    id BIGINT NOT NULL AUTO_INCREMENT,
    productId BIGINT NOT NULL,
    imageUrl VARCHAR(255) NOT NULL,

    CONSTRAINT ProductImagePK PRIMARY KEY (id),
    CONSTRAINT ProductFK FOREIGN KEY (productId) REFERENCES Product(id)

) ENGINE = InnoDB;




CREATE TABLE Favorite (

    id BIGINT NOT NULL AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    productId BIGINT NOT NULL,
    liked BOOLEAN NOT NULL,

    CONSTRAINT FavoritePK PRIMARY KEY (id),
    CONSTRAINT FavoriteUserFK FOREIGN KEY (userId) REFERENCES User(id),
    CONSTRAINT FavoriteProductFK FOREIGN KEY (productId) REFERENCES Product(id)

) ENGINE = InnoDB;



CREATE TABLE ShoppingCart (

    id BIGINT NOT NULL AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    CONSTRAINT ShoppingCartPK PRIMARY KEY (id),
    CONSTRAINT ShoppingCartUserIdFK FOREIGN KEY (userId) REFERENCES User(id)

) ENGINE = InnoDB;


CREATE TABLE ShoppingCartItem(
    id BIGINT NOT NULL AUTO_INCREMENT,
    productId BIGINT NOT NULL,
    quantity SMALLINT NOT NULL,
    shoppingCartId BIGINT NOT NULL,
    CONSTRAINT ShoppingCartItemPK PRIMARY KEY (id),
    CONSTRAINT ShoppingCartItemProductIdFK FOREIGN KEY (productId) REFERENCES Product (id),
    CONSTRAINT ShoppingCartItemShoppingCartIdFK FOREIGN KEY (shoppingCartId) REFERENCES ShoppingCart (id)

) ENGINE = InnoDB;

CREATE TABLE Purchase (
    id BIGINT NOT NULL AUTO_INCREMENT,
    userId BIGINT NOT NULL,
    date DATETIME NOT NULL,
    postalAddress VARCHAR(200) NOT NULL,
    postalCode VARCHAR(20) NOT NULL,

    CONSTRAINT PurchasePK PRIMARY KEY (id),
    CONSTRAINT PurchaseUserIdFK FOREIGN KEY(userId) REFERENCES User (id)

) ENGINE = InnoDB;

CREATE TABLE PurchaseItem (
    id BIGINT NOT NULL AUTO_INCREMENT,
    productId BIGINT NOT NULL,
    productPrice DECIMAL(11, 2) NOT NULL,
    quantity SMALLINT NOT NULL,
    purchaseId BIGINT NOT NULL,

    CONSTRAINT PurchaseItemPK PRIMARY KEY (id),
    CONSTRAINT PurchaseItemProductIdFK FOREIGN KEY(productId) REFERENCES Product (id),
    CONSTRAINT PurchaseItemPurchaseIdFK FOREIGN KEY(purchaseId) REFERENCES Purchase (id)

) ENGINE = InnoDB;
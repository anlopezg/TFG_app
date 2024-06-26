-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "paproject" database.
-------------------------------------------------------------------------------

INSERT INTO User (userName, email, password, firstName, language, country, region, crochetLevel, knitLevel, bio, role) VALUES
       --User buyer, contra buyer
             ('buyer', 'buyer@a.com', '$2y$10$RjWEOnkjKGYWzcodWB9mmemDMy63YIT.zVhkuCcs1hacAjwhTpRy.', 'Aspen', 'English', 'USA', 'NY', 2, 1, 'Just a girl', 0),
       -- User seller, contra seller
             ('seller', 'seller@a.com','$2y$10$XlNfLZgdFFrHivw7XZM3nugEnlAb6d6O9fexozZWrswrmkwBNmKQ2', 'Warn', 'French', 'Canada', 'Toronto', 1, 0, 'Hellow', 1),

       -- User para probar a borrar, perfil buyer, contra seller
       ('buyer3', 'buyer3@a.com','$2y$10$XlNfLZgdFFrHivw7XZM3nugEnlAb6d6O9fexozZWrswrmkwBNmKQ2', 'Warn', 'French', 'Canada', 'Vancouver', 1, 0, 'Hellow', 0),

       -- Alice Buyer, contra buyer
       ('alice', 'alice@example.com', '$2y$10$RjWEOnkjKGYWzcodWB9mmemDMy63YIT.zVhkuCcs1hacAjwhTpRy.', 'Alice', 'English', 'USA', 'California', 2, 1, 'Love to crochet', 0);



INSERT INTO Craft (craftName) VALUES
        -- id: 1
             ('crochet'),
        -- id : 2
             ('knit');

INSERT INTO Category (categoryName) VALUES
      -- id: 1
            ('Clothing'),
      -- id: 2
            ('Accessory'),
            ('HomeDecor');

INSERT INTO Subcategory (subcategoryName, categoryId) VALUES
      -- In Clothing
            ('Tops', 1),
            ('Sweater', 1),
            ('Pants', 1),
      -- In Accesory
            ('Scarf', 2),
            ('Hat', 2),
            ('Bag', 2),
      -- In Home Decor
            ('Blanket', 3),
            ('Pillow', 3);


INSERT INTO Product (userId, craftId, subcategoryId, title, description, price, active, creationDate, productType, avgRating,
                     amount, size, color, details,
                     introduction, notes, time, difficultyLevel, abbreviations, specialAbbreviations, gauge, sizing, language) VALUES
      -- Physical Product
            (2, 1, 1, 'Floral Crochet Top', 'Top with floral pattern', 25.00, TRUE, '2024-01-20 12:30:00', 'PHYSICAL',  4.0,
             5, 'EU M', 'Light green', 'Made with acrylic yarn',
             NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),

      -- Physical Product
            (2, 2, 4, 'Long Knit Scarf', 'Wool scarf', 14.00, TRUE, '2024-02-02 16:00:00', 'PHYSICAL',  0.0,
             10, 'Only size', 'Pastel pink', 'Made with wool',
             NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),

      -- Pattern id = 3
            (2, 1, 2, 'Off shoulder sweater', 'Wool sweater', 10.00, TRUE, '2024-02-05 18:15:00','PATTERN', 0.0,
             NULL, NULL, NULL, NULL,
             'How to crochet the sweater',  'Crochet loosely', '25 hours', 2 ,'US', 'Ss - slip stitch', '4x4 15 stitches 12 rows', 'EU S', 'en'),
      -- Pattern id=4
            (2, 1, 6, 'Crochet bag', 'This is a pattern to make a crochet bag', 20.00, TRUE, '2024-03-05 18:15:00', 'PATTERN', 0.0,
             NULL, NULL, NULL, NULL,
             'How to crochet a bag', 'Crochet loosely', '25 hours', 2 ,'US', 'Ss - slip stitch', '4x4 15 stitches 12 rows', 'EU S', 'en'),

      -- Pattern id=5
      (2, 1, 6, 'Summer crochet bag', 'This is a pattern to make a summer crochet bag', 12.00, TRUE, '2024-06-05 11:00:00', 'PATTERN', 0.0,
       NULL, NULL, NULL, NULL,
       'How to crochet a bag', 'Crochet loosely', '40 hours', 2 ,'US', 'None', '4x4 15 stitches 12 rows', 'Unique', 'en');


INSERT INTO Favorite (userId, productId, liked) VALUES
      -- Physical product id=1 marked as fav
            (1, 1, TRUE),
      -- Pattern id=3 marked as fav
            (1, 3, TRUE);


INSERT INTO ProductImages (productId, imageUrl) VALUES
            (1, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1713604747/tfg/gi66cdfv4b8hezj18wdt.jpg"),
            (2, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1713604747/tfg/secs98vxxvbmib2ikrd4.jpg"),
            (3, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1713604747/tfg/lksbybwmvllhm7o6tozo.jpg"),
            (4, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1713639493/tfg/k9rmyhnlqa5i8dbfuaff.jpg"),
            (4, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1713639494/tfg/yevn2muo0alh47ytqygi.jpg"),
            (4, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1713639494/tfg/jbsfohay7hg5lpg1wph4.jpg"),
            (4, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1713639494/tfg/pflndu5cr3dwmu523luz.jpg"),
            (5, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1717177497/wfihgxnik8jb9veu7tge.png");


INSERT INTO ShoppingCart (userId) VALUES
            (1),
            (2),
            (3),
            (4);

INSERT INTO ShoppingCartItem (productId, quantity, shoppingCartId) VALUES
            (2, 1, 1),
            (2, 2, 2);

INSERT INTO Purchase (userId, date, postalAddress, locality, region, country, postalCode) VALUES
            (1, '2024-02-02 16:00:00', 'Calle Alfonso 3', 'A Coruña', 'A Coruña', 'España', '15003'),
            (4, '2024-04-06 12:00:00', 'Avenida Montserrat', 'Madrid', 'Madrid', 'España', '52000'),
            (1, '2024-02-02 16:00:00', 'Calle Alfonso 3', 'A Coruña', 'A Coruña', 'España', '15003');


INSERT INTO PurchaseItem(productId, productPrice, quantity, purchaseId) VALUES
            (3, 20.0, 2, 1),
            (3, 22.0, 1, 2),
            (5, 12.0, 1, 3);


INSERT INTO Review(userId, productId, rating, comment, date) VALUES
            (1, 3, 4, "Very well made product", '2024-04-22 12:00:00' ),
            (4, 3, 3, "I expected to be better because of its price", '2024-05-01 19:00:00');


INSERT INTO StripeAccount(userId, stripeAccountId, stripeEmail, accountStatus) VALUES
            (2, "acct_1PRZrtPGEwNaqJ7s", "seller@a.com", "new");


INSERT INTO Yarn (productId, brand, name, color, amount, fiberContent, weight, length) VALUES
    (3, 'Stylecraft', 'Special Chunky', 'Red', '2', 'Wool', '500g', '200m'),
    (3, 'Stylecraft', 'Special DK', 'Blue', '5', 'Cotton', '100g', '300m'),
    (5, 'Stylecraft', 'Special DK', 'Beis', '3', 'Cord', '200g', '400m');

INSERT INTO Tool (productId, toolName, amount) VALUES
    (3, '5mm Hook',  1),
    (3, 'Needle', 1),
    (4, '10mm Hook', 1),
    (5, '7mmHook', 1);

INSERT INTO Section (productId, title, description, sectionOrder) VALUES
    (3, 'Front Section', 'This sections describes the process of how to do the front part of the sweater', 1),
    (3, 'Back Section', 'This sections describes the process of how to do the back part of the sweater', 2),
    (3, 'Final Section', 'This sections describes the process of how to do the end of the sweater', 3),
    (5, 'Front Part', 'This sections describes how to crochet the front of the bag', 1),
    (5, 'Side Section', 'This sections describes how to crochet the side part of the bag', 2);

INSERT INTO Step (sectionId, rowNumber, instructions, stepOrder) VALUES
    (1, 'Row 1', 'Ch 100', 1),
    (1, 'Row 2', 'Sc 100', 2),
    (2, 'Row 1', 'Ch 4', 1),
    (2, 'Rows 2 to 50', 'Hdc 4', 2),
    (4, 'Row 1', '5 SC', 1),
    (4, 'Row 2', '5 SC + 2 SCTOG', 2);

INSERT INTO Payment (paymentId, paymentMethod, paymentStatus, amount, currency, paymentDate, stripeAccountId, stripeTransactionId, purchaseItemId)
VALUES
    ('pi_3PNYi8AurJ6JByYJ1h9BR7Mu', 'pm_card_visa', 'succeeded', 9.00, 'eur', NOW(), 'acct_1PMy8yPC211iYDLj',
     'req_ZoDS0pI4yAYiLg', 1),
    ('pi_3PNYi8AurJ6JByYJ1h9BR7Mu', 'pm_card_visa', 'succeeded', 10.00, 'eur', NOW(), 'acct_1PMy8yPC211iYDLj',
     'req_ZoDS0pI4yAYiLg', 2),
    ('pi_3PNYi8AurJ6JByYJ1h9BR7Mu', 'pm_card_visa', 'succeeded', 12.00, 'eur', NOW(), 'acct_1PMy8yPC211iYDLj',
     'req_ZoDS0pI4yAYiLg', 3);


INSERT INTO SectionImages (sectionId, imageUrl) VALUES
    (4, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1717177497/aoqiwqq7eudza27kyyeo.png"),
    (5, "https://res.cloudinary.com/dhj64eq7m/image/upload/v1717173905/qwxkk4h0jphjh54outgn.png");
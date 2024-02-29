-- ----------------------------------------------------------------------------
-- Put here INSERT statements for inserting data required by the application
-- in the "paproject" database.
-------------------------------------------------------------------------------

INSERT INTO User (userName, email, password, firstName, language, country, crochetLevel, knitLevel, bio, role) VALUES
       --User buyer, contra buyer
             ('buyer', 'buyer@a.com', '$2y$10$RjWEOnkjKGYWzcodWB9mmemDMy63YIT.zVhkuCcs1hacAjwhTpRy.', 'Aspen', 'English', 'USA', 2, 1, 'Just a girl', 0),
       -- User seller, contra seller
             ('seller', 'seller@a.com','$2y$10$XlNfLZgdFFrHivw7XZM3nugEnlAb6d6O9fexozZWrswrmkwBNmKQ2', 'Warn', 'French', 'Canada', 1, 0, 'Hellow', 1),

       -- User para probar a borrar, perfil buyer, contra seller
       ('buyer3', 'buyer3@a.com','$2y$10$XlNfLZgdFFrHivw7XZM3nugEnlAb6d6O9fexozZWrswrmkwBNmKQ2', 'Warn', 'French', 'Canada', 1, 0, 'Hellow', 0);

INSERT INTO Craft (craftName) VALUES
        -- id: 1
             ('crochet'),
        -- id : 2
             ('knit');

INSERT INTO Category (categoryName) VALUES
      -- id: 1
            ('Clothing'),
      -- id: 2
            ('Accessory');

INSERT INTO Subcategory (subcategoryName, categoryId) VALUES
      -- In Clothing
            ('Tops', 1),
      -- In Accesory
            ('Scarf', 2),
      -- In Clothing
            ('Sweater', 1);

INSERT INTO Product (userId, craftId, subcategoryId, title, description, price, active, creationDate, productType,
                     amount, size, color, details,
                     introduction, abbreviations,  notes, gauge, sizing, difficultyLevel, time) VALUES
      -- Physical Product
            (2, 1, 1, 'Floral Crochet Top', 'Top with floral pattern', 25.00, TRUE, '2024-01-20 12:30:00', 'PHYSICAL',
             5, 'EU M', 'Light green', 'Made with acrylic yarn',
             NULL, NULL, NULL, NULL, NULL, NULL, NULL),

      -- Physical Product
            (2, 2, 2, 'Long Knit Scarf', 'Wool scarf', 14.00, TRUE, '2024-02-02 16:00:00', 'PHYSICAL',
             10, 'Only size', 'Pastel pink', 'Made with wool',
             NULL, NULL, NULL, NULL, NULL, NULL, NULL),

      -- Pattern
            (2, 1, 3, 'Off shoulder sweater', 'Wool sweater', 10.00, TRUE, '2024-02-05 18:15:00','PATTERN',
             NULL, NULL, NULL, NULL,
             'How to crochet the sweater', 'Ss - slip stitch', 'Crochet loosely', '4x4 15 stitches 12 rows', 'EU S', 2,
             '25 hours');


INSERT INTO Favorite (userId, productId, isFavorite) VALUES
      -- Physical product id=1 marked as fav
            (1, 1, TRUE),
      -- Pattern id=3 marked as fav
            (1, 3, TRUE);

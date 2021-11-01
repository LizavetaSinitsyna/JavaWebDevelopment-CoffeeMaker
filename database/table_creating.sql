CREATE TABLE `accounts` (
  `account_id` bigint NOT NULL AUTO_INCREMENT,
  `balance` int DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `bonus_accounts` (
  `bonus_account_id` bigint NOT NULL AUTO_INCREMENT,
  `balance` int DEFAULT NULL,
  PRIMARY KEY (`bonus_account_id`),
  UNIQUE KEY `bonus_account_id_UNIQUE` (`bonus_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `drink_ingredients` (
  `drink_id` bigint NOT NULL,
  `ingredient_id` bigint NOT NULL,
  `ingredient_amount` int NOT NULL,
  `is_optional` tinyint NOT NULL,
  PRIMARY KEY (`drink_id`,`ingredient_id`),
  KEY `drink_id_idx` (`drink_id`),
  KEY `ingredient_id_idx` (`ingredient_id`),
  CONSTRAINT `drink_ingredients_drink_id` FOREIGN KEY (`drink_id`) REFERENCES `drinks` (`drink_id`),
  CONSTRAINT `ingredient_id` FOREIGN KEY (`ingredient_id`) REFERENCES `ingredients` (`ingredient_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `drinks` (
  `drink_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `image_path` varchar(150) DEFAULT '/CoffeeMachine/images/default_coffee_image.jpg',
  `price` int NOT NULL,
  `description` varchar(350) DEFAULT NULL,
  `is_deleted` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`drink_id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `drink_id_UNIQUE` (`drink_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `feedbacks` (
  `comment_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `drink_id` bigint NOT NULL,
  `comment_message` varchar(300) NOT NULL,
  `rating` int NOT NULL,
  `date_time` timestamp NOT NULL,
  PRIMARY KEY (`comment_id`),
  UNIQUE KEY `comment_id_UNIQUE` (`comment_id`),
  KEY `comments_drink_id_idx` (`drink_id`),
  KEY `comments_user_id_idx` (`user_id`),
  CONSTRAINT `comments_drink_id` FOREIGN KEY (`drink_id`) REFERENCES `drinks` (`drink_id`),
  CONSTRAINT `comments_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `ingredients` (
  `ingredient_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `current_amount` int DEFAULT NULL,
  `image_path` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`ingredient_id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  UNIQUE KEY `ingredient_id_UNIQUE` (`ingredient_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `order_drinks` (
  `order_id` bigint NOT NULL,
  `drink_id` bigint NOT NULL,
  `drink_count` int DEFAULT NULL,
  PRIMARY KEY (`order_id`,`drink_id`),
  KEY `order_id_idx` (`order_id`),
  KEY `drink_id_idx` (`drink_id`),
  CONSTRAINT `drink_id` FOREIGN KEY (`drink_id`) REFERENCES `drinks` (`drink_id`),
  CONSTRAINT `order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `date_time` timestamp NOT NULL,
  `status` varchar(20) NOT NULL DEFAULT 'created',
  `cost` int NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `order_id_UNIQUE` (`order_id`),
  KEY `orders_user_id_idx` (`user_id`),
  CONSTRAINT `orders_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

CREATE TABLE `roles` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`role_id`),
  UNIQUE KEY `role_id_UNIQUE` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `users` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` varchar(60) NOT NULL,
  `role_id` bigint NOT NULL DEFAULT '2',
  `bonus_account_id` bigint NOT NULL,
  `account_id` bigint NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(13) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `login_UNIQUE` (`login`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`),
  UNIQUE KEY `bonus_account_id_UNIQUE` (`bonus_account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`),
  KEY `fk_user_bonus_account1_idx` (`bonus_account_id`),
  KEY `fk_user_account1_idx` (`account_id`),
  KEY `role_id_idx` (`role_id`),
  CONSTRAINT `account_id` FOREIGN KEY (`account_id`) REFERENCES `accounts` (`account_id`),
  CONSTRAINT `bonus_account_id` FOREIGN KEY (`bonus_account_id`) REFERENCES `bonus_accounts` (`bonus_account_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3;

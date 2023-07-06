START TRANSACTION;

DROP TABLE IF EXISTS password, account, recipe CASCADE;

CREATE TABLE password (
	password_id serial,
	hash varchar(150) NOT NULL,
	salt varchar(150) NOT NULL,
	CONSTRAINT PK_password PRIMARY KEY (password_id)
);

CREATE TABLE account (
	account_id serial,
	first_name varchar(20) NOT NULL,
	last_name varchar(30) NOT NULL,
	email varchar(30) NOT NULL,
	profile_picture varchar(150),
	bio varchar(500),
	username varchar(30) NOT NULL UNIQUE,
	password_id int NOT NULL,
	date_added date NOT NULL,
	active boolean NOT NULL DEFAULT true,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_password FOREIGN KEY (password_id) REFERENCES password(password_id)
);

CREATE TABLE recipe (
	recipe_id serial,
	title varchar(100) NOT NULL,
	ingredients varchar(500) NOT NULL,
	instructions varchar(500) NOT NULL,
	glass varchar(50) NOT NULL DEFAULT 'Cocktail Glass',
	account_id int NOT NULL DEFAULT 1,
	rating int,
	post_date date NOT NULL,
	post_time time NOT NULL,
	active boolean NOT NULL DEFAULT true,
	CONSTRAINT PK_recipe PRIMARY KEY (recipe_id),
	CONSTRAINT FK_user FOREIGN KEY (account_id) REFERENCES account(account_id)
);

INSERT INTO password(hash, salt)
VALUES ('f3596a02e0f00374aef1ce5c25a62818', 'sfjei39389dfjjdfls');

INSERT INTO account(first_name, last_name, email, bio, username, password_id, date_added)
VALUES('Stephen', 'Kaczmarowski', 'srkaz94@gmail.com', 'Creator of the app', 'Kazman1596', 1, '6-28-2023');

INSERT INTO recipe(title, ingredients, instructions, glass, rating, post_date, post_time)
VALUES('MARGARITA', 'lime + salt + simple syrup + triple sec + tequila', 'Salt rim of glass, add lime juice, simple syrup, triple sec, and tequila. Shake well. Strain into glass', 'Cocktail Glass', 5, '1/5/2023', '5:30:00');
INSERT INTO recipe(title, ingredients, instructions, glass, rating, post_date, post_time)
VALUES('OLD FASHIONED', 'bitters + peel of orange + whiskey + maraschino cherry (optional) + sugar cube', 'Place sugar cube in glass, and add bitters. Crush sugar cube with bitters, until partially dissolved. Add whiskey and ice, stir well. Add cherry and orange peel as garnish.', 'Cocktail Glass', 4, '2/20/2023', '5:30:00');
INSERT INTO recipe(title, ingredients, instructions, glass, rating, post_date, post_time)
VALUES('VODKA MULE', 'lime + ginger beer + vodka + simple syrup', 'Mix all ingredients into a copper mug. Garnish with lime wedge.', 'Copper Mug', 5, '7/6/2023', '5:30:00');
INSERT INTO recipe(title, ingredients, instructions, glass, rating, post_date, post_time)
VALUES('PALOMA', 'lime + grapefruit juice (or Squirt) + simple syrup + tequila', 'Add all ingredients into glass and garnish with lime wedge.', 'Collins Glass', 3, '4/24/2023', '7:30:00');

COMMIT;
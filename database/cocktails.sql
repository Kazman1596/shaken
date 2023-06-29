START TRANSACTION;

DROP TABLE IF EXISTS password, account, recipe, review CASCADE;

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

CREATE TABLE review (
	review_id serial,
	recipe_id int NOT NULL,
	account_id int NOT NULL,
	description varchar(250) NOT NULL,
	rating int NOT NULL,
	post_date date NOT NULL,
	post_time time NOT NULL,
	active boolean NOT NULL DEFAULT true,
	CONSTRAINT PK_review PRIMARY KEY (review_id),
	CONSTRAINT FK_recipe FOREIGN KEY (recipe_id) REFERENCES recipe(recipe_id),
	CONSTRAINT FK_account FOREIGN KEY (account_id) REFERENCES account(account_id)
	
);

INSERT INTO password(hash, salt)
VALUES ('f3596a02e0f00374aef1ce5c25a62818', 'sfjei39389dfjjdfls');

INSERT INTO account(first_name, last_name, email, bio, username, password_id, date_added)
VALUES('Stephen', 'Kaczmarowski', 'srkaz94@gmail.com', 'Creator of the app', 'Kazman1596', 1, '6-28-2023');


COMMIT;
ALTER TABLE app_user
    ADD password VARCHAR(255);

ALTER TABLE app_user
    ALTER COLUMN password SET NOT NULL;

ALTER TABLE app_user
DROP
COLUMN password_hash;

ALTER TABLE app_user
DROP
COLUMN password_salt;
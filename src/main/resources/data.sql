/** Oauth - populate the oauth_client_details table */
-- password encoded with BCript (new BCryptPasswordEncoder()).encode("secret"))
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `scope`, `authorized_grant_types`, `access_token_validity`, `additional_information`)
VALUES
('web', '$2a$10$HuuzUjO5OfRwXDAI0zjnlu.TFt5dB66ag4tEFokkxxwzsHJh1dXLm', 'read,write', 'authorization_code,password,refresh_token,implicit', '900', '{}');

-- password encoded with BCript (new BCryptPasswordEncoder()).encode("dazito.com"))
INSERT INTO `oauth_user_details` (`username`, `password`) VALUES ('dazito', '$2a$10$FOqrjeX9h7VGscD2oqMtXeHM4p8UPuVUcD3GuMadJgnDMq0SOJDDC');


/** Oauth - populate the oauth_client_details table */
INSERT INTO `oauth_client_details` (`client_id`, `client_secret`, `scope`, `authorized_grant_types`, `access_token_validity`, `additional_information`)
VALUES
('web', 'secret', 'read', 'authorization_code,password,refresh_token,implicit', '900', '{}');

INSERT INTO `account` (`id`, `password`, `username`) VALUES ('	1	', 'dazito.com', 'dazito');


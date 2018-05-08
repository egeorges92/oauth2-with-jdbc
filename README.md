# oAuth 2 server with jdbc / JPA
Complete implementation of an oAuth 2 server using database back-end and REST API to manage oAuth clients and users.

Now support :
- authentication
- check token for resource server
- client management via REST API
- user management via REST API

### Using curl

basic auth :

```sh
curl -s -u "web-secret:secret" -X POST -d "grant_type=password&username=dazito&password=dazito.com" http://localhost:8181/oauth/token
```

form auth :

```sh
curl -s -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "grant_type=password&client_id=web&client_secret=secret&username=dazito&password=dazito.com" http://localhost:8181/rest/oauth/token
```
response :

```json
{
	"access_token":"dbbe45c8-22f5-4996-a7af-232c7c2f2b9b",
	"token_type":"bearer",
	"refresh_token":"76aa9640-5824-412f-9340-a8eba39069a3",
	"expires_in":899,
	"scope":"read write"
}
```

Check token :

```sh
curl -s -u "web:secret" http://localhost:8181/oauth/check_token?token=dbbe45c8-22f5-4996-a7af-232c7c2f2b9b
```

Refresh token :

```sh
curl -s -X POST -H "Content-Type: application/x-www-form-urlencoded" -d "grant_type=refresh_token&client_id=web&client_secret=secret&refresh_token=76aa9640-5824-412f-9340-a8eba39069a3" http://localhost:8181/oauth/token
```

Get principal :

```ssh
curl -s -X POST --header "Authorization: Bearer dbbe45c8-22f5-4996-a7af-232c7c2f2b9b" http://localhost:8181/me
```

Response :

```json
{  
   "authorities":[  
      {  
         "authority":"ROLE_USER"
      }
   ],
   "details":{  
      "remoteAddress":"0:0:0:0:0:0:0:1",
      "sessionId":null,
      "tokenValue":"dbbe45c8-22f5-4996-a7af-232c7c2f2b9b",
      "tokenType":"Bearer",
      "decodedDetails":null
   },
   "authenticated":true,
   "userAuthentication":{  
      "authorities":[  
         {  
            "authority":"ROLE_USER"
         }
      ],
      "details":{  
         "grant_type":"password",
         "username":"dazito"
      },
      "authenticated":true,
      "principal":{  
         "username":"dazito",
         "password":"$2a$10$FOqrjeX9h7VGscD2oqMtXeHM4p8UPuVUcD3GuMadJgnDMq0SOJDDC",
         "authorities":[  
            {  
               "authority":"ROLE_USER"
            }
         ],
         "accountExpiredOn":null,
         "credentialsExpiredOn":null,
         "locked":false,
         "enabled":true,
         "accountNonExpired":true,
         "accountNonLocked":true,
         "credentialsNonExpired":true
      },
      "credentials":null,
      "name":"dazito"
   },
   "principal":{  
      "username":"dazito",
      "password":"$2a$10$FOqrjeX9h7VGscD2oqMtXeHM4p8UPuVUcD3GuMadJgnDMq0SOJDDC",
      "authorities":[  
         {  
            "authority":"ROLE_USER"
         }
      ],
      "accountExpiredOn":null,
      "credentialsExpiredOn":null,
      "locked":false,
      "enabled":true,
      "accountNonExpired":true,
      "accountNonLocked":true,
      "credentialsNonExpired":true
   },
   "oauth2Request":{  
      "clientId":"web",
      "scope":[  
         "read",
         "write"
      ],
      "requestParameters":{  
         "grant_type":"password",
         "username":"dazito"
      },
      "resourceIds":[  

      ],
      "authorities":[  

      ],
      "approved":true,
      "refresh":false,
      "redirectUri":null,
      "responseTypes":[  

      ],
      "extensions":{  

      },
      "grantType":"password",
      "refreshTokenRequest":null
   },
   "credentials":"",
   "clientOnly":false,
   "name":"dazito"
}
```

### SSL
[spring-boot-starter-acme](https://github.com/creactiviti/spring-boot-starter-acme)

[ssl-configuration-on-spring-boot-application-with-self-signed-certificate](https://malalanayake.wordpress.com/2017/01/28/ssl-configuration-on-spring-boot-application-with-self-signed-certificate/)

### AD
[oauth2-authorization-server-with-spring-security](https://raymondhlee.wordpress.com/2015/12/05/oauth2-authorization-server-with-spring-security/)

### Docker
build image :
```sh
mvn clean package
```
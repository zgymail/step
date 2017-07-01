文档Api
http://localhost:8080/swagger-ui.html

状态码
200 成功
400 请求错误




浏览器授权OAuth2.0 
Path : http://localhost:8080/oauth/token
HTTP Method : POST
URL Params ： 无
Request Headers : 
    {
    	Accept: application/json
		Content-Type: application/x-www-form-urlencoded
		Authorization: Basic Y2xpZW50YXBwOjEyMzQ1Ng==
	}
Request Body ： 
	username=xxxx&password=123456&grant_type=password&scope=read write
	
Response Body：
	{
	    "access_token":"529b37e7-d2c8-4912-a6c2-e5d28de40346",
	    "token_type":"bearer",
	    "refresh_token":"b153820e-2dcd-4bf7-a716-15a48fc3bfaa",
	    "expires_in":22796,
	    "scope":"read write"
	}
更新令牌
Path : http://localhost:8080/oauth/token
HTTP Method : POST
URL Params : 无
Request Headers:
	{
		Accept: application/json
		Content-Type: application/x-www-form-urlencoded
		Authorization: Basic Y2xpZW50YXBwOjEyMzQ1Ng==
	}
Request Body :
	grant_type=refresh_token&refresh_token=134cb18f-101f-4bac-80eb-946211e39170
	
#### token持久化建表语句


CREATE TABLE `oauth_access_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication_id` varchar(256) DEFAULT NULL,
  `user_name` varchar(100) NOT NULL,
  `client_id` varchar(256) DEFAULT NULL,
  `authentication` blob,
  `refresh_token` varchar(256) DEFAULT NULL,
  `access_token_validity` int(11) DEFAULT NULL,
  `refresh_token_validity` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `oauth_refresh_token` */

DROP TABLE IF EXISTS `oauth_refresh_token`;

CREATE TABLE `oauth_refresh_token` (
  `token_id` varchar(256) DEFAULT NULL,
  `token` blob,
  `authentication` blob
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




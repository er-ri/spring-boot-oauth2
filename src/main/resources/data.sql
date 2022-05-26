create table "user_entity" (
	"user_name" varchar(255) not null primary key,
	"password" varchar(255) not null, 
	"github_id" varchar(255),
	"line_id" varchar(255),
	"authorities" varchar(255), 
	"active" boolean not null
	);
	
INSERT INTO "user_entity"("user_name","password","github_id","line_id","authorities","active") VALUES('l001@lmail.com','lpassword','','','ROLE_ADMIN',true);
INSERT INTO "user_entity"("user_name","password","github_id","line_id","authorities","active") VALUES('testuser','password','','','ROLE_USER',true);
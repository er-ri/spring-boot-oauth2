create table "user_table" (
	"user_name" varchar(255) not null primary key,
	"password" varchar(255) not null, 
	"github_id" varchar(255),
	"line_id" varchar(255),
	"roles" varchar(255), 
	"active" boolean not null
	);
	
INSERT INTO "user_table"("user_name","password","github_id","line_id","roles","active") VALUES('l001@lmail.com','lpassword','','','ADMIN',true);
INSERT INTO "user_table"("user_name","password","github_id","line_id","roles","active") VALUES('testuser','password','','','ROLE_USER',true);
-- AUTO_INCREMENT:  MySQL dialect
-- For derby
-- See: https://stackoverflow.com/questions/38181941/i-get-this-error-error-code-30000-sql-state-42x01-syntax-error-encountered
create table "user_table" (
	"user_name" varchar(255) not null primary key,
	"password" varchar(255) not null, 
	"roles" varchar(255), 
	"active" boolean not null
	);
	
INSERT INTO "user_table"("user_name","password","roles","active") VALUES('testuser','password','ADMIN',true);
INSERT INTO "user_table"("user_name","password","roles","active") VALUES('user2','pass2','ROLE_USER',true);
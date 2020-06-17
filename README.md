# Install database
```
sudo yum install postgresql-server
sudo postgresql-setup initdb
sudo systemctl start postgresql
```
# create user tomcat/tomcat and database test
Check /var/lib/pgsql/data/pg_hba.conf (METHOD md5)
```
sudo su - postgres
psql -c "alter user postgres with password 'tomcat'"
createuser -U postgres -S -D -R -P tomcat
createdb -U postgres -O tomcat test
```


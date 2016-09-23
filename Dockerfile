FROM postgres

ADD ./set-up.sql /docker-entrypoint-initdb.d/

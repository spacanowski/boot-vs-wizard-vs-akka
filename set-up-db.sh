#!/bin/bash

docker-compose up -d fight
sleep 10
psql -h localhost -U postgres -a -f set-up.sql
psql -h localhost -U test -d test -a -f schema.sql

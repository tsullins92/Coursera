#!/usr/bin/bash

for f in .data/transactions/json/transactionsPerDept75.json
do
	mongoimport -h localhost:3001 --db meteor --collection transactions --type json --file ${f} --jsonArray	
done

for f in .data/addresses/json/ban-75.json
do
	mongoimport -h localhost:3001 --db meteor --collection addresses --type json --file ${f} --jsonArray
done	


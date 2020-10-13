#!/bin/bash

cd src/main/resources
mongoimport --type csv -d newpatientdb -c patient --headerline maindata.csv
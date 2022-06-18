#!/bin/bash

source .env
export $(grep -v -e '^$\|#' .env | cut -d= -f1)
./mvnw spring-boot:run
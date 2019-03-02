#!/usr/bin/env bash

java -jar ./wiremock-standalone-2.21.0.jar --port=5555 --https-port=5556 --container-threads=24 --jetty-acceptor-threads=4 --async-response-enabled=true

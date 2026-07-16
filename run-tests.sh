#!/usr/bin/env bash

BROWSER=$1

sbt scalafmtAll scalafmtCheckAll scalafmtSbtCheck clean compile -Dbrowser="${BROWSER:=chrome}" -Denvironment="local" test

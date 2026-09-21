#!/usr/bin/env bash

BROWSER=$1
ENVIRONMENT=$2
HEADLESSFLAG=$3
PREVIOUSVERSIONFLAG=$4

sbt scalafmtAll scalafmtCheckAll scalafmtSbtCheck clean -Dbrowser="${BROWSER:=chrome}" -Denvironment="${ENVIRONMENT:=local}" -Dbrowser.option.headless="${HEADLESSFLAG:=false}" -Dbrowser.usePreviousVersion="${PREVIOUSVERSIONFLAG:=false}" "testOnly uk.gov.hmrc.ui.specs.* -- -n AcceptanceTests" testReport

#!/bin/bash

LOG_FILE=matcher.log

nohup lein boot >> $LOG_FILE 2>&1 &
echo "Matcher started, log file: ${LOG_FILE}"

#!/bin/bash

ps auxwww | grep java | grep matcher | grep jetty | cut -d " " -f 8 | xargs kill
rc=$?
if [[ $rc != 0 ]] ; then
  echo "Matcher failed to stop"
  exit $rc
else
  echo "Matcher was stopped"
fi

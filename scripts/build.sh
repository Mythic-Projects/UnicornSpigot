#!/usr/bin/env bash

(
set -e
basedir="$(cd "$1" && pwd -P)"
gitcmd="git -c commit.gpgsign=false"

$gitcmd submodule update --init
cd "base/Paper"
$gitcmd submodule update --init
cd "$basedir"

if [ "$2" == "--setup" ] || [ "$2" == "--jar" ]; then
    ./base/Paper/paper setup
fi
./scripts/applyPatches.sh "$basedir"

if [ "$2" == "--jar" ]; then
    ./gradlew build
fi
) || exit 1

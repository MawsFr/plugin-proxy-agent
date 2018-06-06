#!/bin/bash

PATH_GIT=/repos/git/

#Verify if environment variables exists
if [ -z "$REPOSITORY" ]; then
		exit -1
fi

REPOSITORY_PATH="$PATH_GIT/repos/$REPOSITORY.git"

#verify if repository already exists
if [ -d $REPOSITORY_PATH ]; then
	// exists
	exit 1
else
	//does not exist
	exit 0
fi
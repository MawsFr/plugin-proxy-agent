#!/bin/bash

#Verify if environment variables exists
if [ -z "$REPO_NAME" ]; then
		echo {"message":"The repository name must be setted", "messageKey"="git.validation.blank.reponame"}
		exit 1
fi
if [ -z "$PROJECT_NAME" ]; then
		echo {"message":"The project name must be setted", "messageKey"="git.validation.blank.projectname"}
		exit 1
fi
if [ -z "$CLIENT_NAME" ]; then
		echo {"message":"The client name must be setted", "messageKey"="git.validation.blank.clientname"}
		exit 1
fi
if [ -z "$LDAP_GROUPS" ]; then
		echo {"message":"There must be at least one ldap group selected", "messageKey"="git.validation.blank.ldapgroups"}
		exit 1
fi

#compose keys and paths
#verify if repository already exists
#init repository
#create apache config
#reload apache
#checking that the repository works

echo {"message":"The GIT repository has been successfully created", "messageKey"="git.message.success"}
exit 0
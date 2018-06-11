#!/bin/bash

PATH_GIT=/repos/git/
MODS_HOME=/repos/configs/

#Verify if environment variables exists
if [ -z "$URL" ]; then
		# echo {"message":"The url of the server must be setted in env vars", "messageKey"="git.validation.blank.url"}
		exit 1
fi
if [ -z "$PROJECT" ]; then
		# echo {"message":"The project name must be setted in env vars", "messageKey"="git.validation.blank.projectname"}
		exit 2
fi
if [ -z "$OU" ]; then
		# echo {"message":"The client name must be setted in env vars", "messageKey"="git.validation.blank.clientname"}
		exit 3
fi
if [ -z "$LDAP_GROUPS" ]; then
		# echo {"message":"There must be at least one ldap group selected", "messageKey"="git.validation.blank.ldapgroups"}
		exit 4
fi

if [ -z "$USER" ]; then
		# echo {"message":"The user must be setted in env vars", "messageKey"="git.validation.blank.password"}
		exit 5
fi
if [ -z "$PASSWORD" ]; then
		# echo {"message":"The password must be setted in env vars", "messageKey"="git.validation.blank.password"}
		exit 6
fi

#compose keys and paths
REPOSITORY=$OU-$PROJECT
REPOSITORY_PATH="$PATH_GIT/repos/$REPOSITORY.git"

#verify if repository already exists
if [ -d $REPOSITORY_PATH ]; then
	# echo {"message":"A Git repository with the same name already exists", "messageKey"="git.validation.exists"}
	exit 7
else
	#init repository
	git --bare init "$REPOSITORY_PATH"
	#chown -R -H apache:apache "$REPOSITORY_PATH"
	#chmod -R ug=rwx,o= "$REPOSITORY_PATH"
	#chcon -R -h -t httpd_sys_content_t "$REPOSITORY_PATH"
fi

#create apache config
MOD_HOME="$MODS_HOME/scm/git"
MOD_CONF="$MOD_HOME/$REPOSITORY.conf"
cp $MOD_HOME/.template_git $MOD_CONF

# Use sed to replace keys by values in template.conf
# put => Require ldap-group cn=SECURITY_GROUP,ou=CLIENT,ou=projects,dc=gfi,dc=fr <= for each ldapgroups
sed -i "s|REPOSITORY|$REPOSITORY|g" $MOD_CONF

# Applying read and write rights to ldap groups
IFS=' ' read -r -a array <<< "$LDAP_GROUPS"
for element in "${array[@]}"
do
	sed -i "s|READ_LDAP_GROUPS|	Require ldap-group $element\nREAD_LDAP_GROUPS|g" $MOD_CONF
	sed -i "s|WRITE_LDAP_GROUPS|	Require ldap-group $element\nWRITE_LDAP_GROUPS|g" $MOD_CONF
done

sed -i "s|READ_LDAP_GROUPS||g" $MOD_CONF
sed -i "s|WRITE_LDAP_GROUPS||g" $MOD_CONF

#reload apache
#chown apache:apache $MOD_CONF
#chmod u=r,og= $MOD_CONF
#chcon -R -h -t httpd_sys_content_t $MOD_CONF

#service httpd reload
#checking that the repository works
git ls-remote "https://$USER:$PASSWORD@$URL$REPOSITORY.git"
if (( $? != 0 )); then
  # echo {"message":"There has been an error in the Git repository creation", "messageKey"="git.fail"}
  exit 8
fi

# echo {"message":"The Git repository has been successfully created", "messageKey"="git.success"}
exit 0

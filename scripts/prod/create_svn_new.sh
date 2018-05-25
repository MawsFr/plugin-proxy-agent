#!/bin/bash

#Verify if environment variables exists
if [ -z "$URL" ]; then
		echo {"message":"The url of the server must be setted", "messageKey"="svn.validation.blank.url"}
		exit 1
fi
if [ -z "$PROJECT" ]; then
		echo {"message":"The project name must be setted", "messageKey"="svn.validation.blank.projectname"}
		exit 1
fi
if [ -z "$CLIENT" ]; then
		echo {"message":"The client name must be setted", "messageKey"="svn.validation.blank.clientname"}
		exit 1
fi
if [ -z "$LDAP_GROUPS" ]; then
		echo {"message":"There must be at least one ldap group selected", "messageKey"="svn.validation.blank.ldapgroups"}
		exit 1
fi
if [ -z "$USER" ]; then
		echo {"message":"The user must be setted", "messageKey"="svn.validation.blank.password"}
		exit 1
fi
if [ -z "$PASSWORD" ]; then
		echo {"message":"The password must be setted", "messageKey"="svn.validation.blank.password"}
		exit 1
fi

#compose keys and paths
REPOSITORY=$CLIENT-$PROJECT
REPOSITORY_PATH="$PATH_SVN/repos/$KEY"

#verify if repository already exists
if [ -d $REPOSITORY_PATH ]; then
	echo {"message":"A svn repository with the same name already exists", "messageKey"="svn.validation.exists"}
	exit 2
else
	#init repository
	svnadmin create "$REPOSITORY_PATH"
	chown -R apache:apache "$REPOSITORY_PATH"
	chmod -R u=rwX,go= "$REPOSITORY_PATH"
	chmod u+s "$REPOSITORY_PATH/db"
	chcon -R -h -t httpd_sys_content_t "$REPOSITORY_PATH"

	TMP_PATH="/tmp/_newproject/$REPOSITORY"
	mkdir -p "$TMP_PATH"
	rm -f -R "$TMP_PATH"
	svn co "file://$REPOSITORY_PATH" "$TMP_PATH"
	for pathElt in $SVN_ROOTS; do
		svn mkdir "$TMP_PATH/$pathElt";
	done
	svn ci -m "Initialize common paths" "$TMP_PATH/"
	rm -f -R "$TMP_PATH"

	chown -R apache:apache "$REPO"
	chcon -R -h -t httpd_sys_content_t "$REPO"
fi

#create apache config
MOD_HOME="$MODS_HOME/scm/svn"
MOD_CONF="$MODS_HOME/$REPOSITORY.conf"
cp $MOD_HOME/.template_svn.conf $MOD_CONF

# Use sed to replace keys by values in template.conf
# put => Require ldap-group cn=SECURITY_GROUP,ou=CLIENT,ou=projects,dc=gfi,dc=fr <= for each ldapgroups
sed -i "s|URL|$URL|g" $MOD_CONF
sed -i "s|REPOSITORY|$REPOSITORY|g" $MOD_CONF

# Applying read and write rights to ldap groups
for element in "${LDAP_GROUPS[@]}"
do
	sed -i "s|READ_LDAP_GROUPS|		Require ldap-group $element\nREAD_LDAP_GROUPS|g" $MOD_CONF
	sed -i "s|WRITE_LDAP_GROUPS|		Require ldap-group $element\nWRITE_LDAP_GROUPS|g" $MOD_CONF
done

sed -i "s|READ_LDAP_GROUPS||g" $MOD_CONF
sed -i "s|WRITE_LDAP_GROUPS||g" $MOD_CONF

# TODO : replace SVNPath "/home/subversion/repos/ministere-justice-romeo" in template svn

#reload apache
chown apache:apache $MOD_CONF
chmod u=r,og= $MOD_CONF
chcon -R -h -t httpd_sys_content_t $MOD_CONF

service httpd reload
#checking that the repository works
repourl="$URL/$REPOSITORY/"
status=`curl $repourl -o /dev/null --silent --head --write-out "%{http_code}" -u '$USER:$PASSWORD'`
if (( $? != 0 )); then
  echo {"message":"There has been an error in the Svn repository creation", "messageKey"="svn.fail"}
  exit 3
fi

echo {"message":"The svn repository has been successfully created", "messageKey"="svn.success"}
exit 0
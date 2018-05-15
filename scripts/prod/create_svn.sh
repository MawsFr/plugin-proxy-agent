#!/bin/bash
if [ "x$1" = "x" ] || [ "x$2" = "x" ]; then
        echo $"Usage: $0 <Client> <Project> [Security Group=Client-Project]"
        echo $"Exemple : $0 gfi saas"
        exit 1
fi
CLIENT=$1
PROJECT=$2
SECURITY_GROUP=$3
KEY=$CLIENT-$PROJECT
if [ "x$SECURITY_GROUP" = "x" ]; then
        SECURITY_GROUP="$KEY"
fi
echo "Security group will be $SECURITY_GROUP"

REPO="$PATH_SVN/repos/$KEY"
SVN_ROOTS="trunk branches tags"
echo -n "Create repository < $REPO > ..."
if [ -d $REPO ]; then
  echo "FAIL : already exists"
  exit 2
else
   svnadmin create "$REPO"
   chown -R apache:apache "$REPO"
   chmod -R u=rwX,go= "$REPO"
   chmod u+s "$REPO/db"
   chcon -R -h -t httpd_sys_content_t "$REPO"
   echo " OK"
   echo -n "Create basic folders ($SVN_ROOTS) in repository ..."
   TMP_PATH="/tmp/_newproject/$KEY"
   mkdir -p "$TMP_PATH"
   rm -f -R "$TMP_PATH"
   svn co "file://$REPO" "$TMP_PATH"
   for pathElt in $SVN_ROOTS; do
      svn mkdir "$TMP_PATH/$pathElt";
   done
   svn ci -m "Initialize common paths" "$TMP_PATH/"
   rm -f -R "$TMP_PATH"
   echo " OK"
   echo -n "Restore ownership on $REPO ..."
   chown -R apache:apache "$REPO"
   chcon -R -h -t httpd_sys_content_t "$REPO"
   echo " OK"
fi
echo "Create Apache configuration ..."
MOD_HOME="$MODS_HOME/scm/svn"
MOD_CONF="$MOD_HOME/$KEY.conf"
cp $MOD_HOME/.template $MOD_CONF
sed -i "s|PROJECT|$PROJECT|g" $MOD_CONF
sed -i "s|SECURITY_GROUP|$SECURITY_GROUP|g" $MOD_CONF
sed -i "s|CLIENT|$CLIENT|g" $MOD_CONF

chown apache:apache $MOD_CONF
chmod u=r,og= $MOD_CONF
chcon -R -h -t httpd_sys_content_t $MOD_CONF
service httpd reload
url="https://delivery.gfi.fr/svn/$KEY/"
echo -n "Checking the repository with URL : $url"
status=`curl $url -o /dev/null --silent --head --write-out "%{http_code}" -u 'scm:Ruiy-6|k;3'`
if (( $status != 200 )); then
  echo " FAIL : wget returned status $status"
  exit 3
fi
chown -R apache:apache "$REPO"
echo " OK"
exit 0
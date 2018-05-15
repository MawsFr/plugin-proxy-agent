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

REPO="$PATH_GIT/repos/$KEY.git"
echo -n "Create repository < $REPO > ... "
if [ -d $REPO ]; then
  echo "FAIL : already exists"
  exit 2
else
   git --bare init "$REPO"
   echo -n "Restore ownership on $REPO ..."
   chown -R -H apache:apache "$REPO"
   chmod -R ug=rwx,o= "$REPO"
   chcon -R -h -t httpd_sys_content_t "$REPO"
   echo " OK"
fi
echo "Create Apache configuration ..."
MOD_HOME="$MODS_HOME/scm/git"
MOD_CONF="$MOD_HOME/$KEY.conf"
cp $MOD_HOME/.template $MOD_CONF
sed -i "s|PROJECT|$PROJECT|g" $MOD_CONF
sed -i "s|SECURITY_GROUP|$SECURITY_GROUP|g" $MOD_CONF
sed -i "s|CLIENT|$CLIENT|g" $MOD_CONF
chown apache:apache $MOD_CONF
chmod u=r,og= $MOD_CONF
chcon -R -h -t httpd_sys_content_t $MOD_CONF
service httpd reload
url="https://delivery.gfi.fr/git/$KEY.git/"
echo -n "Checking the repository with URL : $url"
git ls-remote "https://scm:Ruiy-6|k;3@delivery.gfi.fr/git/$KEY.git"
if (( $? != 0 )); then
  echo " : FAIL"
  exit 3
fi
echo " : OK"
exit 0
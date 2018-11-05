#!/usr/bin/env bash

set -e

echo "starting gpg script"

# create a random passphrase
export GPG_PASSPHRASE=$(echo "$RANDOM$(date)" | md5sum | cut -d\  -f1)

# configuration to generate gpg keys
cat >gen-key-script <<EOF
    %echo Generating a basic OpenPGP key
    Key-Type: RSA
    Key-Length: 4096
    Subkey-Type: 1
    Subkey-Length: 4096
    Name-Real: Opensource Idealo
    Name-Email: opensource-logback-redis@idealo.de
    Expire-Date: 2y
    Passphrase: ${GPG_PASSPHRASE}
    %commit
    %echo done
EOF

# create a local keypair with given configuration
gpg --batch --gen-key gen-key-script


# export created GPG key
#
# example output
# sec   4096R/EDD32E8B 2016-09-08 [verfällt: 2018-09-08]
# uid                  Lars K.W. Gohlke <lars.gohlke@idealo.de>
# ssb   4096R/CC1613B2 2016-09-08
# ssb   4096R/55B7CAA2 2016-09-08
export GPG_KEYNAME=$(gpg -K | grep ^sec | cut -d/  -f2 | cut -d\  -f1 | head -n1)

# cleanup local configuration
shred gen-key-script

# publish the gpg key
# (use keyserver.ubuntu.com as travis request keys from this server, 
#  we avoid synchronization issues, while releasing) 
gpg --keyserver keyserver.ubuntu.com --send-keys ${GPG_KEYNAME}

# wait for the key beeing accessible
while(true); do
  date
  gpg --keyserver keyserver.ubuntu.com  --recv-keys ${GPG_KEYNAME} && break || sleep 30
done

echo "ending gpg script"
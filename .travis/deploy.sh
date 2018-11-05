#!/usr/bin/env bash

set -e

echo "starting deployment script"

# only do deployment, when travis detects a new tag
if [ ! -z "$TRAVIS_TAG" ]
then
    echo "on a tag -> set pom.xml <version> to $TRAVIS_TAG"
    mvn --settings .travis/settings.xml org.codehaus.mojo:versions-maven-plugin:2.3:set -DnewVersion=$TRAVIS_TAG -Prelease

    if [ ! -z "$TRAVIS" -a -f "$HOME/.gnupg" ]; then
        shred -v ~/.gnupg/*
        rm -rf ~/.gnupg
    fi

    source .travis/gpg.sh

    mvn clean deploy --settings .travis/settings.xml -DskipTests=true --batch-mode --update-snapshots -Prelease


    if [ ! -z "$TRAVIS" ]; then
        shred -v ~/.gnupg/*
        rm -rf ~/.gnupg
    fi
elif [ "$TRAVIS_BRANCH" = "develop" ]
then
    echo "on develop branch. Deploying SNAPSHOT to Maven Central"

    mvn clean deploy --settings .travis/settings.xml
elif [ "$TRAVIS_BRANCH" = "master" ]
then
    echo "on master branch. Deploying SNAPSHOT to Maven Central"

    mvn clean deploy --settings .travis/settings.xml
else
    echo "not on a tag, develop, or master branch. Not deploying to Maven Central."
fi

echo "ending deployment script"
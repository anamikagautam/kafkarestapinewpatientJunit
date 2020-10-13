#!/bin/bash

# variables
kafkaversion=2.0.0
builddir=/tmp/test_kakfa/test_kafka_connect/

cd ${builddir}
rm -rf

curdir=`pwd`

mkdir -p /workspace/kafka_setup/

/bin/rm -rf ${builddir}
mkdir -p ${builddir}/connectors
mkdir -p ${builddir}/bin
mkdir -p ${builddir}/config
mkdir -p ${builddir}/libs

# Build the package
echo "Building the connector package ..."

# Download kafka
echo "Downloading kafka_2.11-${kafkaversion} ..."
wget -q https://archive.apache.org/dist/kafka/${kafkaversion}/kafka_2.11-${kafkaversion}.tgz -P ${builddir}
cd ${builddir} && tar xzf kafka_2.11-${kafkaversion}.tgz

# Copy over kafka connect runtime
echo "Copy over kafka connect runtime ..."
cp * /workspace/kafka_setup/
cp kafka_2.11-${kafkaversion}/bin/connect-distributed.sh ${builddir}/bin
cp kafka_2.11-${kafkaversion}/bin/kafka-run-class.sh ${builddir}/bin
cp kafka_2.11-${kafkaversion}/config/connect-log4j.properties ${builddir}/config
cp kafka_2.11-${kafkaversion}/libs/*.jar ${builddir}/libs

# Clean up
echo "Clean up ..."
/bin/rm -rf kafka_2.11-${kafkaversion}
/bin/rm -f kafka_2.11-${kafkaversion}.tgz

cd /workspace/kafka_setup
tar xzf kafka_2.11-${kafkaversion}.tgz
cd kafka_2.11-${kafkaversion}

echo "Running Servers... "
bin/zookeeper-server-start.sh config/zookeeper.properties &
bin/kafka-server-start.sh config/server.properties &

cd /workspace/kafkatutorial

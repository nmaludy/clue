# clue
Clue game for Software Engineering class in Masters program

# Pre-reqs

```
# Install ant to compile our project
sudo yum -y install ant

# Install the protobuf compiler (protoc)
# If you're running RedHat based distros you can follow below
# otherwise check out the protobuf website for instructions
# https://developers.google.com/protocol-buffers/
sudo yum -y install protobuf-compiler

# or, from source
wget https://github.com/google/protobuf/releases/download/v3.1.0/protobuf-java-3.1.0.tar.gz
tar -xvf protobuf-java-3.0.1.tar.gz
cd protobuf-3.1.0
./configure
make -j`nproc`
sudo make install

```

# Compile
```
ant compile
```


# Run
```
# since run is the default target
ant

# or, if you want to call it explicitly explicitly
ant run

# or, from the jar
ant build
java -jar dist/clue.jar
```


# Clean
```
# clean all compiled sources
ant clean
```


# Generate API Docs (JavaDoc)
```
ant doc
google-chrome doc/index.html
```

# Configuration

```
# application configuration settings
config/application.yaml
```

# Logging
```
# config
config/log4j2.properties

# logs written to
log/clue.log
```


# Dependencies

Note: these are already done, just putting it here as a reference

## Messages - Protocol Buffers

```
sudo yum -y install maven
wget https://github.com/google/protobuf/releases/download/v3.1.0/protobuf-java-3.1.0.tar.gz
tar -xvf protobuf-java-3.0.1.tar.gz
cd protobuf-3.1.0
./configure
make -j`nproc`
sudo make install
cd java
mvn package
cp ./core/target/protobuf-java-3.1.0.jar ~/src/git/nmaludy/clue/lib
cp ./util/target/protobuf-java-util-3.1.0.jar ~/src/git/nmaludy/clue/lib

```

# Logging - slf4j

```
# log4j
wget http://www-us.apache.org/dist/logging/log4j/2.8/apache-log4j-2.8-bin.tar.gz
tar -xvf apache-log4j-2.8-bin.tar.gz
cd apache-log4j-2.8-bin
cp ./log4j-api-2.8.jar ~/src/git/nmaludy/clue/lib
cp ./log4j-core-2.8.jar ~/src/git/nmaludy/clue/lib
cp ./log4j-slf4j-impl-2.8.jar ~/src/git/nmaludy/clue/lib

# slf4j
wget https://www.slf4j.org/dist/slf4j-1.7.23.tar.gz
tar -xvf slf4j-1.7.23.tar.gz
cd slf4j-1.7.23
cp ./slf4j-api-1.7.23.jar ~/src/git/nmaludy/clue/lib

```

# Configuration - cf4j

```
# snakeyaml
hg clone hg clone ssh://hg@bitbucket.org/asomov/snakeyaml
cd snakeyaml
hg checkout v1.17
mvn package
cp ./target/snakeyaml-1.17.jar ~/src/git/nmaludy/clue/lib

# org.json JSON
cd ~/src/git/nmaludy/clue/lib
wget http://central.maven.org/maven2/org/json/json/20160810/json-20160810.jar

# type-parser
wget https://github.com/drapostolos/type-parser/archive/type-parser-0.6.0.tar.gz
tar -xvf type-parser-0.6.0.tar.gz
cd type-parser-0.6.0
./gradlew jar
cp ./build/libs/type-parser-0.6.0.jar ~/src/git/nmaludy/clue/lib


# cfg4j
wget https://github.com/cfg4j/cfg4j/archive/v.4.4.0.tar.gz
tar -xvf cfg4j-v.4.4.0.tar.gz
cd cfg4j-v.4.4.0
./gradlew jar
cp ./cfg4j-core/build/libs/cfg4j-core-4.4.0.jar ~/src/git/nmaludy/clue/lib

```


# Networking - jeromq

```
# jeromq
wget https://github.com/zeromq/jeromq/archive/v0.3.6.tar.gz -O jeromq-0.3.6.tar.gz
tar -xvf jeromq-0.3.6.tar.gz
cd jeromq-0.3.6
mvn package -DskipTests=true
cp ./target/jeromq-0.3.6.jar ~/src/git/nmaludy/clue/lib
# examples https://github.com/zeromq/jeromq/tree/master/src/test/java/guide
```



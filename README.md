# clue
Clue game for Software Engineering class in Masters program

# Pre-requisites

Install ant to compile our project
```
sudo yum -y install ant
```

# Compile the project
```
ant compile
```


# Run the project
```
# since run is the default target...
ant

# or, if you want to call it explicitly explicitly
ant run

```


# Clean all sources
```
ant clean
```


# How to setup protobuf dependency from source

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


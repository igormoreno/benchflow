REPONAME = data-manager
DOCKERIMAGENAME = benchflow/$(REPONAME)

VERSION = dev
JAVA_VERSION_FOR_COMPILATION = (^|/)java-8-oracle($|\s)
UNAME = $(shell uname)

find_java:
ifeq ($(UNAME), Darwin)
	$(eval JAVA_HOME := $(shell /usr/libexec/java_home))
else ifeq ($(UNAME),Linux)
ifndef TRAVIS
	$(eval JAVA_HOME := $(shell update-java-alternatives -l | cut -d' ' -f3 | egrep '$(JAVA_VERSION_FOR_COMPILATION)'))
endif 
endif

.PHONY: all build_release

all: build_release

clean: find_java
	sbt clean

package: find_java
	sbt package

build: find_java
	sbt universal:packageZipTarball

build_release: find_java
	sbt publish-local

install: build_release

test: find_java
	sbt test

build_container:
	docker build -t $(DOCKERIMAGENAME):$(VERSION) -f Dockerfile .

build_container_local: find_java
	sbt universal:packageZipTarball
	docker build -t $(DOCKERIMAGENAME):$(VERSION) -f Dockerfile.test .

test_container_local:
	docker run -p 8080:8080 -e "ENVCONSUL_CONSUL=demo.consul.io" --name $(REPONAME) $(DOCKERIMAGENAME):$(VERSION)

rm_container_local:
	docker rm -f -v $(REPONAME)

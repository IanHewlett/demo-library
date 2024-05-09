#!/usr/bin/env just --justfile
app_name := "demo-library"
podman_machine := "demo-machine"

_default:
  @just --list

# run the api on localhost:8080
start:
    ./gradlew bootrun

# check and test api code
test:
    ./gradlew check
    ./gradlew test

# initialize podman-machine if it does not exist, and then start the podman-machine if it is not running
@podman user="":
  (podman machine ls | grep -q {{podman_machine}} && [[ $? -eq 0 ]] && echo "podman machine exists") \
    || (echo "initializing podman machine" && podman machine init --cpus 6 --memory 10048 --disk-size 20 --rootful -v /Users/{{user}}:/Users/{{user}} {{podman_machine}})
  (podman machine inspect {{podman_machine}} | grep -q '"State": "running"' && [[ $? -eq 0 ]] && echo "podman machine running") \
      || (echo "starting podman machine" && podman machine start {{podman_machine}})
  podman system connection default {{podman_machine}}-root

# build api image with provided tag
build tag="latest":
    ./gradlew jibDockerBuild --image={{app_name}}:{{tag}}

# run the api image as a container exposed on localhost:8080
run tag="latest":
  docker run -it --rm \
    -p 8080:8080 \
    --name {{app_name}} \
    localhost/{{app_name}}:{{tag}}

#!/usr/bin/env bash
set -euo pipefail

install_homebrew() {
  if [[ "$(command -v brew)" ]]; then
    echo "Homebrew installed"
  else
    echo "Installing homebrew"
    bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install.sh)"
  fi
}

brew install just
brew install jq
brew install openjdk
brew install httpie
brew install docker
brew install podman
sudo /usr/local/Cellar/podman/5.0.1/bin/podman-mac-helper install
https://podman-desktop.io/docs/migrating-from-docker/using-the-docker_host-environment-variable

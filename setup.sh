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
brew install httpie
brew install docker
brew install podman

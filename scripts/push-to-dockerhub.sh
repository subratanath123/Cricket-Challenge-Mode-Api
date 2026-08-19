#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$ROOT_DIR"

if [[ ! -f .env ]]; then
  echo "Missing .env. Copy .env.example to .env and set Docker Hub + Mongo credentials."
  exit 1
fi

set -a
# shellcheck disable=SC1091
source .env
set +a

if [[ -z "${DOCKERHUB_USERNAME:-}" || -z "${DOCKERHUB_TOKEN:-}" || -z "${DOCKERHUB_IMAGE:-}" ]]; then
  echo "DOCKERHUB_USERNAME, DOCKERHUB_TOKEN, and DOCKERHUB_IMAGE must be set in .env"
  exit 1
fi

IMAGE="${DOCKERHUB_USERNAME}/${DOCKERHUB_IMAGE}:${IMAGE_TAG:-latest}"

echo "$DOCKERHUB_TOKEN" | docker login -u "$DOCKERHUB_USERNAME" --password-stdin
docker compose -f docker-compose.yml -f docker-compose.build.yml build api
docker push "$IMAGE"
echo "Pushed $IMAGE"

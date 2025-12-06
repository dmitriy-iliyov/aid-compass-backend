#!/bin/bash

set -euo pipefail

echo ">>>   build frontend"
FRONTEND_DIR="aid-compass-frontend"
BACKEND_DIR="aid-compass-backend"
BACKEND_STATIC_DIR="app/src/main/resources/static"

if [ ! -d "../../${FRONTEND_DIR}" ]; then
  echo ">>>   frontend dir not found: ../../${FRONTEND_DIR}"
  exit 1
fi

cd "../../${FRONTEND_DIR}"

npm install
npm run build -- --configuration production

if [ ! -d "dist/aid-compass-frontend/browser" ]; then
  echo ">>>   frontend build failed: dist/aid-compass-frontend/browser not found"
  exit 1
fi

cd "../${BACKEND_DIR}"

echo ">>>   refresh backend static"
if [ ! -d "app" ]; then
  echo ">>>   backend dir not found"
  exit 1
fi

rm -rf "$BACKEND_STATIC_DIR"
mkdir -p "$BACKEND_STATIC_DIR"

cp -r "../${FRONTEND_DIR}/dist/aid-compass-frontend/browser/" "${BACKEND_STATIC_DIR}"

echo ">>>   build and run infrastructure"
docker compose -f infrastructure/docker-compose.yaml -p aid-compass up -d

echo ">>>   successfully built and run"
#!/bin/sh

if [ -n "$1" ]
then
    VAULT_DEV_TOKEN="$1"
    CONFIG_DIR="."
fi

vault login ${VAULT_DEV_TOKEN}

vault secrets disable secret/
vault secrets enable -path=secret -version=1 kv

vault kv put secret/auth-service/dev @${CONFIG_DIR}/auth-service-credentials-dev.json
vault kv put secret/auth-service/local @${CONFIG_DIR}/auth-service-credentials-local.json
vault kv put secret/auth-service/docker @${CONFIG_DIR}/auth-service-credentials-docker.json

vault kv put secret/product-service/dev @${CONFIG_DIR}/product-service-credentials-dev.json
vault kv put secret/product-service/local @${CONFIG_DIR}/product-service-credentials-local.json
vault kv put secret/product-service/docker @${CONFIG_DIR}/product-service-credentials-docker.json
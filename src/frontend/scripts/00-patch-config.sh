#!/bin/sh
set -e

SCRIPT_NAME=`basename "$0"`
CONFIG_PATH=/usr/share/nginx/html/assets/config.json
CONFIG=$(cat ${CONFIG_PATH})
rm ${CONFIG_PATH}

echo ${CONFIG} | \
    jq --arg data "${API_ENDPOINT}" '.api_endpoint |= $data' | \
    jq --arg data "${GITHUB_CLIENT_ID}" '.github.client_id |= $data' | \
    jq --arg data "${GITHUB_CALLBACK_URL}" '.github.callback_url |= $data' | \
    cat >> ${CONFIG_PATH}

echo "${SCRIPT_NAME}: info: Config updated"
exit 0

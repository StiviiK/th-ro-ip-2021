#!/bin/sh
set -e

SCRIPT_NAME=`basename "$0"`
CONFIG_PATH=/usr/share/nginx/html/assets/config.json
CONFIG=$(cat ${CONFIG_PATH})
rm ${CONFIG_PATH}

echo ${CONFIG} | \
    jq --arg data "${API_ENDPOINT}" '.api_endpoint |= $data' | \
#    jq --arg data "${AUTH_DOMAIN}" '.authConfig.domain |= $data' | \
#    jq --arg data "${AUTH_CLIENTID}" '.authConfig.clientId |= $data' | \
#    jq --arg data "${AUTH_AUDIENCE}" '.authConfig.audience |= $data' | \
#    jq --arg data "${AUTH_REDIRECT_URI}" '.authConfig.redirectUri |= $data' | \
    cat >> ${CONFIG_PATH}

echo "${SCRIPT_NAME}: info: Config updated"
exit 0

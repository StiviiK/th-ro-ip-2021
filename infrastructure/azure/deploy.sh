#!/bin/bash
set -e

ARM_ACCESS_KEY=$(az storage account keys list --resource-group rg-tfstate-throip2021-de --account-name tfstate0throip20210de --query '[0].value' -o tsv)

echo "Planning Terraform..."
terraform plan -out ./tfplan.out

echo -e "\n\n\n"
read -n 1 -s -r -p "Press any key to continue."
echo -e "\n\n\n"

echo "Applying Terraform..."
terraform apply ./tfplan.out
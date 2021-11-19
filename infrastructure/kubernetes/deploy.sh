#!/bin/bash
set -e

echo "Planning Helm releases..."
terraform plan -out ./tfplan.out -target "helm_release.cert-manager"

echo -e "\n\n\n"
read -n 1 -s -r -p "Press any key to continue."
echo -e "\n\n\n"

echo "Applying Helm releases..."
terraform apply ./tfplan.out



echo "Planning cert-manager cluster issuer..."
terraform plan -out ./tfplan.out

echo -e "\n\n\n"
read -n 1 -s -r -p "Press any key to continue."
echo -e "\n\n\n"

echo "Applying cert-manager cluster issuer..."
terraform apply ./tfplan.out
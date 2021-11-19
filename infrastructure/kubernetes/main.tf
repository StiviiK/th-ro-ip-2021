terraform {
  required_version = ">= 0.12"

  required_providers {
    azurerm = {
      source  = "hashicorp/azurerm"
      version = ">=2.80.0"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = ">= 2.0.3"
    }
    helm = {
      source  = "hashicorp/helm"
      version = ">=2.4.1"
    }
  }

  backend "azurerm" {
    resource_group_name = "rg-tfstate-throip2021-de"
    storage_account_name = "tfstate0throip20210de"
    container_name = "tfstate-dev"
    key = "terraform.tfstate.kubernetes"
  }
}

data "terraform_remote_state" "azure" {
  backend = "azurerm"
  config = {
    resource_group_name = "rg-tfstate-throip2021-de"
    storage_account_name = "tfstate0throip20210de"
    container_name = "tfstate-dev"
    key = "terraform.tfstate.azure"
  }
}

provider "kubernetes" {
  host = data.terraform_remote_state.azure.outputs.kubernetes_host

  client_certificate     = base64decode(data.terraform_remote_state.azure.outputs.kubernetes_client_certificate)
  client_key             = base64decode(data.terraform_remote_state.azure.outputs.kubernetes_client_key)
  cluster_ca_certificate = base64decode(data.terraform_remote_state.azure.outputs.kubernetes_ca_certificate)
}

provider "helm" {
  kubernetes {
    host = data.terraform_remote_state.azure.outputs.kubernetes_host

    client_certificate     = base64decode(data.terraform_remote_state.azure.outputs.kubernetes_client_certificate)
    client_key             = base64decode(data.terraform_remote_state.azure.outputs.kubernetes_client_key)
    cluster_ca_certificate = base64decode(data.terraform_remote_state.azure.outputs.kubernetes_ca_certificate)
  }
}

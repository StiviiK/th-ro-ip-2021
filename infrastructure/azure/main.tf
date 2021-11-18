terraform {
  required_version = ">= 0.12"

  required_providers {
    random = {
      source  = "hashicorp/random"
      version = "3.1.0"
    }
    azuread = {
      source  = "hashicorp/azuread"
      version = "2.6.0"
    }
    azurerm = {
      source  = "hashicorp/azurerm"
      version = "2.80.0"
    }
    kubernetes = {
      source  = "hashicorp/kubernetes"
      version = ">= 2.0.3"
    }
    helm = {
      source  = "hashicorp/helm"
      version = "2.4.1"
    }
  }
}

provider "random" {}

provider "azuread" {}

provider "azurerm" {
  features {}
}

provider "kubernetes" {
  host = azurerm_kubernetes_cluster.default.kube_config.0.host

  client_certificate     = base64decode(azurerm_kubernetes_cluster.default.kube_config.0.client_certificate)
  client_key             = base64decode(azurerm_kubernetes_cluster.default.kube_config.0.client_key)
  cluster_ca_certificate = base64decode(azurerm_kubernetes_cluster.default.kube_config.0.cluster_ca_certificate)
}

provider "helm" {
  kubernetes {
    host = azurerm_kubernetes_cluster.default.kube_config.0.host

    client_certificate     = base64decode(azurerm_kubernetes_cluster.default.kube_config.0.client_certificate)
    client_key             = base64decode(azurerm_kubernetes_cluster.default.kube_config.0.client_key)
    cluster_ca_certificate = base64decode(azurerm_kubernetes_cluster.default.kube_config.0.cluster_ca_certificate)
  }
}

data "azurerm_client_config" "current" {}

resource "azurerm_resource_group" "default" {
  name     = "rg-${var.name_suffix}"
  location = var.default_location
  tags     = {}
}

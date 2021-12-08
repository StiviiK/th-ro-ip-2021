terraform {
  required_version = ">= 0.12"

  required_providers {
    random = {
      source  = "hashicorp/random"
      version = ">=3.1.0"
    }
    azuread = {
      source  = "hashicorp/azuread"
      version = ">=2.6.0"
    }
    azurerm = {
      source  = "hashicorp/azurerm"
      version = ">=2.80.0"
    }
  }

  backend "azurerm" {
    resource_group_name = "rg-tfstate-throip2021-de"
    storage_account_name = "tfstate0throip20210de"
    container_name = "tfstate-dev"
    key = "terraform.tfstate.azure"
  }
}

provider "random" {}

provider "azuread" {}

provider "azurerm" {
  features {}
}

data "azurerm_client_config" "current" {}

resource "azurerm_resource_group" "default" {
  name     = "rg-${var.name_suffix}"
  location = var.default_location
  tags     = {}
}

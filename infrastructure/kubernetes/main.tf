terraform {
  required_version = ">= 0.12"

  required_providers {
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

data "terraform_remote_state" "azure" {
  backend = "local"
  config = {
    path = "../azure/terraform.tfstate"
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

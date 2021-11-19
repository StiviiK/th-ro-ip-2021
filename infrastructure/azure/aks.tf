resource "azurerm_kubernetes_cluster" "default" {
  name                = "aks-${var.name_suffix}"
  location            = azurerm_resource_group.default.location
  resource_group_name = azurerm_resource_group.default.name
  dns_prefix          = "aks-${var.name_suffix}"

  depends_on = [
    azurerm_role_assignment.aks_network,
    azurerm_role_assignment.aks_acr,
    azurerm_subnet.kubenet
  ]

  kubernetes_version = var.kubernetes_version

  default_node_pool {
    name       = "default"
    node_count = var.kubernetes_node_count
    vm_size    = var.kubernetes_node_type

    vnet_subnet_id = azurerm_subnet.kubenet.id
  }

  service_principal {
    client_id     = azuread_application.default.application_id
    client_secret = azuread_service_principal_password.default.value
  }

  role_based_access_control {
    enabled = true
  }

  network_profile {
    network_plugin    = "azure"
    load_balancer_sku = "standard"
  }
}

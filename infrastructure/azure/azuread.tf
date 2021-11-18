resource "azuread_application" "default" {
  display_name = "app-aks-${var.name_suffix}"
}

resource "azuread_service_principal" "default" {
  application_id = azuread_application.default.application_id
}

resource "azuread_service_principal_password" "default" {
  service_principal_id = azuread_service_principal.default.id
}

resource "azurerm_role_assignment" "aks_network" {
  scope                = "/subscriptions/${data.azurerm_client_config.current.subscription_id}/resourceGroups/${azurerm_resource_group.default.name}"
  role_definition_name = "Network Contributor"
  principal_id         = azuread_service_principal.default.id
}

resource "azurerm_role_assignment" "aks_acr" {
  scope                = "/subscriptions/${data.azurerm_client_config.current.subscription_id}/resourceGroups/${azurerm_resource_group.default.name}/providers/Microsoft.ContainerRegistry/registries/${azurerm_container_registry.default.name}"
  role_definition_name = "AcrPull"
  principal_id         = azuread_service_principal.default.id
}

locals {
  acr_name = replace("acr-${var.name_suffix}", "-", "0")
}

resource "azurerm_container_registry" "default" {
  name                = local.acr_name
  resource_group_name = azurerm_resource_group.default.name
  location            = azurerm_resource_group.default.location
  sku                 = "Basic"
  admin_enabled       = false
  tags                = {}
}

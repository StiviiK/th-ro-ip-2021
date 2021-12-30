resource "azurerm_virtual_network" "default" {
  name                = "vnet-${var.name_suffix}"
  resource_group_name = azurerm_resource_group.default.name
  location            = azurerm_resource_group.default.location
  tags                = {}

  address_space = ["10.0.0.0/8"]
}

resource "azurerm_subnet" "kubenet" {
  name                 = "kubenet"
  resource_group_name  = azurerm_resource_group.default.name
  virtual_network_name = azurerm_virtual_network.default.name

  address_prefixes = ["10.240.0.0/16"]
}

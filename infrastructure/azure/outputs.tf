output "kubernetes_host" {
  value = azurerm_kubernetes_cluster.default.kube_config.0.host
}

output "kubernetes_client_certificate" {
  value = azurerm_kubernetes_cluster.default.kube_config.0.client_certificate
}

output "kubernetes_client_key" {
  value = azurerm_kubernetes_cluster.default.kube_config.0.client_key
}

output "kubernetes_ca_certificate" {
  value = azurerm_kubernetes_cluster.default.kube_config.0.cluster_ca_certificate
}
variable "name_suffix" {
  type    = string
  default = "dev-throip2021-de"
}

variable "default_location" {
  type    = string
  default = "germanywestcentral"
}

variable "kubernetes_version" {
  type    = string
  default = "1.21.2"
}

variable "kubernetes_node_count" {
  type    = number
  default = 1
}

variable "kubernetes_node_type" {
  type    = string
  default = "Standard_B2ms"
}
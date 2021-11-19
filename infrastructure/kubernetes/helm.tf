resource "helm_release" "nginx-ingress" {
  name       = "nginx-ingress"
  chart      = "ingress-nginx"
  namespace  = "nginx-ingress"
  repository = "https://kubernetes.github.io/ingress-nginx"

  atomic           = true
  reuse_values     = true
  cleanup_on_fail  = true
  create_namespace = true

  set {
    name  = "controller.replicaCount"
    value = "1"
  }

  set {
    name = "service.spec.externalTrafficPolicy"
    value = "Local"
  }

  # set {
  #   name  = "controller.nodeSelector"
  #   value = jsonencode({
  #     "kubernetes.io/os": "linux",
  #     "kubernetes.io/arch": "amd64"
  #   })
  # }

  # set {
  #   name  = "controller.admissionWebhook.nodeSelector"
  #   value = jsonencode({
  #     "kubernetes.io/os": "linux",
  #     "kubernetes.io/arch": "amd64"
  #   })
  # }

  # set {
  #   name  = "defaultBackend.nodeSelector"
  #   value = jsonencode({
  #     "kubernetes.io/os": "linux",
  #     "kubernetes.io/arch": "amd64"
  #   })
  # }
}

resource "helm_release" "cert-manager" {
  depends_on = [
    helm_release.nginx-ingress
  ]

  name       = "cert-manager"
  chart      = "cert-manager"
  namespace  = "cert-manager"
  version    = "v1.5.4"
  repository = "https://charts.jetstack.io"

  atomic           = true
  reuse_values     = true
  cleanup_on_fail  = true
  create_namespace = true

  set {
    name  = "installCRDs"
    value = "true"
  }

  # set {
  #   name  = "nodeSelector"
  #   value = jsonencode({
  #     "kubernetes.io/os": "linux",
  #     "kubernetes.io/arch": "amd64"
  #   })
  # }
}

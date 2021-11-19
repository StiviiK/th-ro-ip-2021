resource "kubernetes_manifest" "clusterissuer" {
  depends_on = [
    helm_release.cert-manager
  ]

  manifest = {
    "apiVersion" : "cert-manager.io/v1"
    "kind" : "ClusterIssuer"
    "metadata" : {
      "name" : "letsencrypt"
    }
    "spec" : {
      "acme" : {
        "server" : "https://acme-v02.api.letsencrypt.org/directory"
        "email" : "stefan.kuerzeder@stud.th-rosenheim.de"
        "privateKeySecretRef" : {
          "name" : "letsencrypt"
        }
        "solvers" : [
          {
            "http01" : {
              "ingress" : {
                "class" : "nginx"
                "podTemplate" : {
                  "spec" : {
                    "nodeSelector" : {
                      "kubernetes.io/os" : "linux"
                    }
                  }
                }
              }
            }
          }
        ]
      }
    }
  }
}

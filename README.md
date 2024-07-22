# Setup Deployment To Retrieve Secrets Using Google Secret Manager Add-on

Current implementation allows deployment to retrieve ***multiple*** `value only` secrets from Google Project Secret Manager and mount them to the container allowing the deployed application to use the secrets

## How The System Works

### [Deployment Yaml](deployment.yaml)
- `serviceAccountName` refers to the [Kubernetes Service Account](service-account.yaml) that the deployment will use to retrieve secret
- `volumeMounts.mountPath` dictates where the volume will be mounted.
    - If the path includes `/config` the application will be able to import the contexts of the volume
    - Path value after `/config` will be key application uses
- `volumeMounts.name` refers to the volume that will be mounted to the container
- `volume.csi.driver` Google Secret Manager Provider
- `secretProviderClass` refers to the Secret Provider that will retrieve the secrets

### [Kubernetes Service Account Yaml](service-account.yaml)
- Service account with [Secret Manager Secret Accessor](https://cloud.google.com/secret-manager/docs/access-control#secretmanager.secretAccessor) role assigned
- `name` value referenced as `serviceAccountName` in [Deployment](deployment.yaml)

### [Secret Provider Yaml](secret-provider.yaml)
- `name` value referenced as `secretProviderClass` in [Deployment](deployment.yaml)
- `resourceName` path to secret in Google Secret Manager
- `path` key used within the application to reference content of the volume
    - If value of `path` is `application/vars/username` then the content can be accessed using `${application.vars.username}`
- Multiple secret can be retrieved and mounted at once by adding more resourceName/path blocks

### [Application Yaml](src%2Fmain%2Fresources%2Fapplication.yaml)
- `spring.config.import` allows application to import the volumes in the specified directory
- `sasl.jaas.config` references the content of mounted volumes `CONFLUENT-USERNAME` and `CONFLUENT-PASSWORD`

### [Deployment Workflow](.github%2Fworkflows%2Fdeploy-to-gke.yaml)



## What's Needed To Get Working 

### Cluster/Project Housing Secret
- Install Secret Manager Add-on in Cluster
  - https://cloud.google.com/secret-manager/docs/secret-manager-managed-csi-component#install-managed-CSI-component


### Automation 
- Assign Secret Accessor Role to GCP IAM Service Account

### End User
- Allow Kubernetes Service Account to impersonate GCP IAM Service Account
  - [Link Kubernetes Service Account to IAM](https://cloud.google.com/kubernetes-engine/docs/how-to/workload-identity#kubernetes-sa-to-iam)
- Deploy Secret Provider, Service Account and Application Deployment 
- Reference secrets in application using `path` value from Secret Provider YAML

#### TODO
- Test usability with roles assigned GKE Service Accounts
  - Update Service account documentation
- Test method of parsing json object or multi line secrets
- Validate which cluster add-on needs to be installed to
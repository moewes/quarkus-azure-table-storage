# azure-table-storage
Quarkus Extension for Azure Table Storage

# How to use

* Add Extension to your project
* Define your entities as subclasses of com.microsoft.azure.storage.table.TableServiceEntity and pojo
* Inject a Dao instace as Dao<Entity> in your service class
  
# Konfiguration
  
* quarkus.storage-account.account-name 
* quarkus.storage-account.account-key 
* quarkus.storage-account.use-azurite 
  
  

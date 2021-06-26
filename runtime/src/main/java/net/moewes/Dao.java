package net.moewes;

import java.net.URISyntaxException;
import java.util.List;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.TableEntity;
import com.microsoft.azure.storage.table.TableQuery;

public interface Dao<T extends TableEntity> {

    List<T> getAll() throws URISyntaxException, StorageException;

    void save(T entity) throws URISyntaxException, StorageException;

    void delete(T entity) throws URISyntaxException, StorageException;

    T get(String partitionKey, String rowKey) throws URISyntaxException, StorageException;

    List<T> query(TableQuery<T> query) throws URISyntaxException, StorageException;

    void setTable(String tableName);

    String getTable();
}

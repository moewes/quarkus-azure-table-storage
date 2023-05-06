package net.moewes;

import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.table.*;
import jakarta.enterprise.inject.spi.CDI;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<T extends TableEntity> implements Dao<T> {

    private final Class<T> type;

    private final TableStorage tableStorage;

    private String tableName;

    public GenericDao() {
        super();
        Class<? extends GenericDao> aClass = getClass();
        while (!(aClass.getGenericSuperclass() instanceof ParameterizedType)) {
            aClass = (Class<? extends GenericDao>) aClass.getSuperclass();
        }
        Type t = aClass.getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class) pt.getActualTypeArguments()[0];
        tableStorage = CDI.current().select(TableStorage.class).get();
        tableName = type.getSimpleName();
    }

    public List<T> getAll() throws URISyntaxException, StorageException {
        CloudTable cloudTable = tableStorage.getCloudTable(tableName);

        List<T> result = new ArrayList<>();

        TableQuery<T> query = TableQuery.from(type);
        cloudTable.execute(query).forEach(result::add);

        return result;
    }

    @Override
    public void save(T entity) throws URISyntaxException, StorageException {
        CloudTable cloudTable = tableStorage.getCloudTable(tableName);

        cloudTable.execute(
                TableOperation.insertOrMerge(entity));
    }

    @Override
    public void delete(T entity) throws URISyntaxException, StorageException {
        CloudTable cloudTable = tableStorage.getCloudTable(tableName);
        cloudTable.execute(
                TableOperation.delete(entity));
    }

    @Override
    public T get(String partitionKey, String rowKey) throws URISyntaxException, StorageException {
        CloudTable cloudTable = tableStorage.getCloudTable(tableName);
        TableResult result =
                cloudTable.execute(TableOperation.retrieve(partitionKey, rowKey, type));
        return result.getResultAsType();
    }

    @Override
    public List<T> query(TableQuery<T> query) throws URISyntaxException, StorageException {
        List<T> result = new ArrayList<>();
        CloudTable cloudTable = tableStorage.getCloudTable(tableName);
        cloudTable.execute(query).forEach(result::add);
        return result;
    }

    @Override
    public void setTable(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public String getTable() {
        return tableName;
    }
}

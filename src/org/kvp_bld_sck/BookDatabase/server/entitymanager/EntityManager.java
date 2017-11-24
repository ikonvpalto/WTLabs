package org.kvp_bld_sck.BookDatabase.server.entitymanager;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

//dangerous
public class EntityManager<E> {

    private E entity;

    public EntityManager(E entity) {
        this.entity = entity;
    }

    public E getEntity() {
        return entity;
    }

    public boolean suppressedSet(String fieldName, Object value) {
        try {
            Class entityClass = entity.getClass();
            Field field = entityClass.getDeclaredField(fieldName);
            field.set(entity, value);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void set(String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Class entityClass = entity.getClass();
        Field field = entityClass.getDeclaredField(fieldName);
        field.set(entity, value);
    }

    public Object suppressedGet(String fieldName) {
        try {
            Class entityClass = entity.getClass();
            Field field = entityClass.getDeclaredField(fieldName);
            return field.get(entity);
        } catch (Exception e) {
            return null;
        }
    }

    public Object get(String fieldName) throws NoSuchFieldException, IllegalAccessException {
        Class entityClass = entity.getClass();
        Field field = entityClass.getDeclaredField(fieldName);
        return field.get(entity);
    }

    public Stream<String> getFieldNamesStream() {
        Class entityClass = entity.getClass();
        return Stream.of(entityClass.getDeclaredFields()).map(Field::getName);
    }

    public Stream<Map.Entry<String, Object>> getFieldsStream() {
        return getFieldNamesStream().map(s -> Map.entry(s, suppressedGet(s)));
    }
}

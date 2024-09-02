package com.example.hotelmanagement.helper;

public interface IMyTemplateHelper<T> {
    /**
     * This method is used to check if the entity is related to the given id or anything
     */
    boolean isRelated(T entity, Object ofUpdatedEntity);
}

package ru.georgy.NauJava.repository;

import java.util.Optional;

/**
 * Базовый интерфейс для работы с хранилищем сущностей
 * @param <T> Тип сущности
 * @param <ID> Тип идентификатора сущности
 */
public interface CrudRepository<T, ID>
{
    /**
     * Создать новую сущность в хранилище
     * @param entity Сущность для сохранения
     * @throws IllegalArgumentException если сущность с таким ID уже существует
     */
    void create(T entity);

    /**
     * Найти сущность по идентификатору
     * @param id Идентификатор
     * @return Optional, содержащий сущность, если она найдена, или пустой Optional
     */
    Optional<T> read(ID id);

    /**
     * Обновить существующую сущность
     * @param entity Обновленная сущность
     * @throws IllegalArgumentException если сущность с указанным ID не существует
     */
    void update(T entity);

    /**
     * Удалить сущность по идентификатору
     * @param id Идентификатор сущности для удаления
     * @throws IllegalArgumentException если сущность с указанным ID не существует
     */
    void delete(ID id);
}
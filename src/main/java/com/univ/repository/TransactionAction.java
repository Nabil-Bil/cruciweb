package com.univ.repository;

@FunctionalInterface
public interface TransactionAction<T> {
    T execute() throws Exception;
}

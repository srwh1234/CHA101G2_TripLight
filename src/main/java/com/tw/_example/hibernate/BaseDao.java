package com.tw._example.hibernate;

import java.util.List;

public interface BaseDao<T> {

	public void insert(T t);

	public void deleteById(Integer id);

	public void update(T t);

	public T selectById(Integer id);

	public List<T> selectAll();
}

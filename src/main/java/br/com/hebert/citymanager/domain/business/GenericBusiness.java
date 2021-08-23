package br.com.hebert.citymanager.domain.business;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface GenericBusiness<T,ID> extends Serializable {
	public List<T> findAll();
	public Optional<T> findById(ID id);
	public T save(T entity);
	public T update(T entity);
	public void delete(T entity);
}

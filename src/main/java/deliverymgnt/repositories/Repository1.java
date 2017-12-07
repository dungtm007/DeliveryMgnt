package deliverymgnt.repositories;

import java.util.Collection;

public interface Repository1<T> {
	
	// Singleton SessionFactory
	public void add(T entity);
	public void remove(final T entity);
	public void update(final T entity);
	public Collection<T> getAll();
}


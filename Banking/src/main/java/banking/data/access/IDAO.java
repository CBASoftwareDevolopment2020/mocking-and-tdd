package banking.data.access;

import java.util.List;

public interface IDAO<T> {
    T get(String id);
    List<T> getAll();
    T save(T t);
}

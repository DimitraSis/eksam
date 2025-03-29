package dk.dao;



import java.util.List;

public interface IDao<T, D,E> {

    T create(T t);
    T readById(D d) ;
    List<T> readAll();
    T update(D d, T t);
    void delete(D d);
    List<T>doctorBySpeciality(E e);



}

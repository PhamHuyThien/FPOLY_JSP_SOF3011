package modelcontroler;


import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MSII
 * @param <T>: object in model
 */
public interface DataAccessObject<T> {

    List<T> getAll();

    List<T> getAll(int start, int end);

    List<T> query(String sql);

    List<T> query(String sql, int start, int end);

    List<T> query(String sql, int start, int end, String... param);

    boolean save(T t);

    boolean update(T t);

    boolean delete(T t);

    T get(int id);
    
    String getError();
}

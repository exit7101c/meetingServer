package com.cronies.smartmes.service;

import java.util.HashMap;
import java.util.List;

public interface ProductService {


    public List<HashMap<String, Object>> getProdList(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> insertProduct(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> updateProduct(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> deleteProduct(HashMap<String, Object> param) throws Exception;
    public HashMap<String, Object> selectProdOne(HashMap<String, Object> param) throws Exception;
}
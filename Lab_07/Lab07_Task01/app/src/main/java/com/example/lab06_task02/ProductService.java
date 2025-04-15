package com.example.lab06_task02;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface ProductService {
    @GET("products")
    Call<ProductResponse> getProductsList();
}

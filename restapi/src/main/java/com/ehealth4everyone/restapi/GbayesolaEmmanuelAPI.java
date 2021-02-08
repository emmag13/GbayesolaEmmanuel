package com.ehealth4everyone.restapi;

import com.ehealth4everyone.restapi.models.FilterLists;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GbayesolaEmmanuelAPI {
    @GET("{id}")
    Call<List<FilterLists>> getFilterLists( @Path("id") String id);
}

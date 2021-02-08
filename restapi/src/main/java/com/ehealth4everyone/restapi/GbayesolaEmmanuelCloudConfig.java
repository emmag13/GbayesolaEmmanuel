package com.ehealth4everyone.restapi;

public class GbayesolaEmmanuelCloudConfig {
    private static GbayesolaEmmanuelCloudConfig instance;

    private static String baseUrl;


    private GbayesolaEmmanuelCloudConfig() {
        if (BuildConfig.DEBUG) {
            baseUrl = BuildConfig.DEV_API_BASE_URL;
        } else {
            baseUrl = BuildConfig.PRODUCTION_API_BASE_URL;

        }
    }

    static GbayesolaEmmanuelCloudConfig getInstance() {
        if (instance == null) {
            synchronized (GbayesolaEmmanuelCloud.class) {
                if (instance == null) {
                    instance = new GbayesolaEmmanuelCloudConfig();
                }
            }
        }
        return instance;
    }

    String getBaseUrl() {
        return baseUrl;
    }
}

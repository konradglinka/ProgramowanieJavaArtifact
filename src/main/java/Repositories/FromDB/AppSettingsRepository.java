package Repositories.FromDB;

import Objects.FromDB.AppSettings;

public class AppSettingsRepository {
    private final AppSettings appSettings;


    public AppSettingsRepository(AppSettings appSettings) {
        this.appSettings = appSettings;
    }
    public AppSettings getAppSettings() {
        return appSettings;
    }

}

package ru.innopolis.yorsogettingxbox.repository;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;

/**
 * @author Artur Badretdinov
 */
public final class RepositoryProvider {

    private static DataRepository sDataRepository;

    private RepositoryProvider() {
    }

    @NonNull
    public static DataRepository provideDataRepository() {
        if (sDataRepository == null) {
            sDataRepository = new OnlineDataRepository();
        }
        return sDataRepository;
    }

    public static void setDataRepository(@NonNull OnlineDataRepository githubRepository) {
        sDataRepository = githubRepository;
    }

    @MainThread
    public static void init() {
        sDataRepository = new OnlineDataRepository();
    }
}

package com.adventure.book.infrastructure;

import com.adventure.book.domain.model.Book;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.util.UUID;

@Configuration
public class MapDBConfig {

    @Bean(destroyMethod = "close")
    public DB db(@Value("${app.mapdb.path}") String path) {
        File file = new File(path);

        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        return DBMaker
                .fileDB(file)
                .closeOnJvmShutdown()
                .transactionEnable()
                .fileMmapEnableIfSupported() // better support 64 bits
                .make();
    }

    @Bean
    public HTreeMap<UUID, Book> books(DB db) {
        return db
                .hashMap("books")
                .keySerializer(Serializer.UUID)
                .valueSerializer(Serializer.JAVA)
                .createOrOpen();
    }

}

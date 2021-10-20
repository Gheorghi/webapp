package com.endava.webapp;

import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.core.io.ResourceLoader.CLASSPATH_URL_PREFIX;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Sql(scripts = "classpath:queries/fillTables.sql")
public abstract class FileLoader {

    @Autowired
    private ResourceLoader resourceLoader;

    public String loadJson(final String path) throws IOException {
        val input = resourceLoader.getResource(CLASSPATH_URL_PREFIX + path).getInputStream();
        try (Reader reader = new InputStreamReader(input, UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}

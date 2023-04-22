package com.gmail.lairmartes.shyftlab.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestFileLoader {

    public String loadTestFile(final String path) throws IOException {
        Class<TestFileLoader> clazz = TestFileLoader.class;

        InputStream is = clazz.getResourceAsStream(path);

        return readFromInputStream(is);
    }

    private String readFromInputStream(final InputStream inputStream)
            throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        return resultStringBuilder.toString();
    }
}

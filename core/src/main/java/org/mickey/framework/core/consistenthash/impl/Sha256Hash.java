package org.mickey.framework.core.consistenthash.impl;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.mickey.framework.core.consistenthash.FileHash;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
@Slf4j
public class Sha256Hash implements FileHash {
    @Override
    public String hash(File file) {
        try (FileInputStream stream = new FileInputStream(file);) {
            return hash(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String hash(InputStream stream) throws IOException {
        int nread = 0;
        byte[] dataBytes = new byte[1024];
        Hasher hasher = Hashing.sha256().newHasher();
        while ((nread = stream.read(dataBytes)) != -1) {
            hasher.putBytes(dataBytes, 0, nread);
        }
        HashCode hashBytes = hasher.hash();
        return hashBytes.toString();
    }

    @Override
    public String hash(byte[] bytes) {
        return Hashing.sha256()
                .newHasher()
                .putBytes(bytes).hash().toString();
    }
}

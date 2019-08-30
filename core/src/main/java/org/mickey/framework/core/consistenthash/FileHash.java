package org.mickey.framework.core.consistenthash;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * description
 *
 * @author mickey
 * 23/07/2019
 */
public interface FileHash {
    String hash(File file);

    String hash(InputStream stream) throws IOException;

    String hash(byte[] bytes);
}

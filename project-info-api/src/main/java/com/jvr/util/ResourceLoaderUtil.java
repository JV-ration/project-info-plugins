package com.jvr.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author viktor@jv-ration.com
 */
public class ResourceLoaderUtil {

    private ResourceLoaderUtil() {}

    public static String loadResourceContent(Class clazz, String name ) {
        String content = null;
        InputStream is = loadResourceStream( clazz.getClassLoader(), name );
        if (is != null) {
            content = convertStreamToString(is);
        }
        return content;
    }

    private static InputStream loadResourceStream(ClassLoader loader, String name ) {

        InputStream stream = null;

        if( loader != null ) {
            stream = loader.getResourceAsStream( name );
            if( stream == null ) {
                stream = loadResourceStream( loader.getParent(), name );
            }
        }

        return stream;
    }

    public static void stringToFile(File file, String content) throws IOException {
        Files.write(Paths.get(file.getAbsolutePath()), content.getBytes());
    }

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

}

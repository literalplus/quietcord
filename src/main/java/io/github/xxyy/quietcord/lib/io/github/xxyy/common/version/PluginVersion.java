/*
 * This file hs been taken from the xy_common library by Literallie with permission to relicense under MIT.
 */

package io.github.xxyy.quietcord.lib.io.github.xxyy.common.version;

import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class offers a static utility method to extract manifest version information from JAR files by a class.
 * Instances hold the retrieved immutable information. This class has been taken from the XYC library and relicensed
 * for this project.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 */
public final class PluginVersion {

    private final String implementationTitle;
    private final String implementationVersion;
    private final String implementationBuild;

    private PluginVersion(String implementationTitle, String implementationVersion, String implementationBuild) {
        this.implementationTitle = implementationTitle;
        this.implementationVersion = implementationVersion;
        this.implementationBuild = implementationBuild;
    }

    /**
     * Attempts to retrieve version information for a class from its enclosing JAR archive's manifest. If the class is
     * not in such archive, or the information cannot be acquired for any other reason, a special instance with
     * {@link Class#getSimpleName()} as implementation title and "unknown" as version and build number will be
     * returned. Note that any or all of the attributes may be unspecified and therefore null.
     *
     * @param clazz the class to retrieve version information for
     * @return the version information for given class
     */
    public static PluginVersion ofClass(Class<?> clazz) {
        CodeSource codeSource = clazz.getProtectionDomain().getCodeSource();

        if (codeSource != null) {
            URL url = codeSource.getLocation();

            if (url != null && url.toExternalForm().endsWith(".jar")) {
                try (JarInputStream jis = new JarInputStream(url.openStream())) {
                    if (jis.getManifest() != null) {
                        Attributes attrs = jis.getManifest().getMainAttributes();

                        return new PluginVersion(
                                attrs.getValue(Name.IMPLEMENTATION_TITLE),
                                attrs.getValue(Name.IMPLEMENTATION_VERSION),
                                attrs.getValue("Implementation-Build")
                        );
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PluginVersion.class.getName()).log(Level.SEVERE,
                            "Error occurred while reading JAR version info for " + clazz.getName(), ex);
                }
            }
        }

        return new PluginVersion(clazz.getSimpleName(), "unknown", "unknown");
    }

    @Override
    public String toString() {
        return implementationTitle + " Version " + implementationVersion + " Build " + implementationBuild;
    }

    public String getImplementationTitle() {
        return this.implementationTitle;
    }

    public String getImplementationVersion() {
        return this.implementationVersion;
    }

    public String getImplementationBuild() {
        return this.implementationBuild;
    }
}

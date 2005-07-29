/**
 * $Id$
 *
 * Gasp: Generic Application Service Platform
 * http://gasp.berlios.de
 * Copyright (c) 2005 Gasp team

 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.eu.gasp.ant;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;

import junit.framework.TestCase;


public class PluginManifestTaskTest extends TestCase {
    private static final String PLUGIN_MANIFEST = "test-plugin.manifest";


    public void testExecute() throws Exception {
        final String pluginId = "org.eu.gasp.test";
        final String pluginVersion = "1.0";
        final String pluginClass = "org.eu.gasp.test.Plugin";
        final String destFile = PLUGIN_MANIFEST;

        final PluginManifestTask task = new PluginManifestTask();
        task.setDestFile(destFile);
        task.setPluginId(pluginId);
        task.setPluginVersion(pluginVersion);
        task.setPluginClass(pluginClass);

        // TODO write tests for Plugin-Requires and Plugin-Provides entries

        task.execute();

        final String output = loadResource(new File(destFile).toURI().toURL(),
                "US-ASCII");
        final String expectedOutput = loadResource(getClass().getResource(
                "/expected-plugin.manifest"), "US-ASCII");
        assertEquals(expectedOutput, output);
    }


    protected void setUp() throws Exception {
        deleteArtifacts();
    }


    protected void tearDown() throws Exception {
        deleteArtifacts();
    }


    private void deleteArtifacts() {
        final File pluginManifestFile = new File(PLUGIN_MANIFEST);
        if (pluginManifestFile.exists()) {
            pluginManifestFile.delete();
        }
        assertFalse("Test file '" + pluginManifestFile.getName()
                + "' must be deleted", pluginManifestFile.exists());
    }


    private String loadResource(URL url, String encoding) {
        final byte[] buf = new byte[1024];
        final ByteArrayOutputStream content = new ByteArrayOutputStream();

        InputStream input = null;
        try {
            input = url.openStream();
            for (int bytesRead = 0; (bytesRead = input.read(buf)) != -1;) {
                content.write(buf, 0, bytesRead);
            }

            return content.toString(encoding);
        } catch (Exception e) {
            throw new IllegalStateException("Error while loading resource: "
                    + url, e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception ignore) {
                }
            }
        }

    }
}

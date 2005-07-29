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


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;


public class PluginManifestTask extends AbstractTask {
    private String pluginId;
    private String pluginVersion;
    private String pluginClass;
    private String destFile;


    protected void doExecute() throws Exception {
        checkAttribute("pluginid", pluginId);
        checkAttribute("pluginversion", pluginVersion);
        checkAttribute("destfile", destFile);

        final String lineSeparator = "\r\n";
        final StringBuffer buf = new StringBuffer();
        buf.append("Plugin: ").append(pluginId).append(lineSeparator).append(
                "Plugin-Version: ").append(pluginVersion);

        if (!StringUtils.isBlank(pluginClass)) {
            buf.append(lineSeparator).append("Plugin-Class: ").append(
                    pluginClass);
        }

        // TODO write Plugin-Requires and Plugin-Provides entries

        OutputStream output = null;
        try {
            output = new BufferedOutputStream(new FileOutputStream(destFile));
            final Writer writer = new OutputStreamWriter(output, "US-ASCII");
            writer.write(buf.toString());
            writer.flush();
        } finally {
            if (output != null) {
                try {
                    output.close();
                } catch (Exception ignore) {
                }
            }
        }
    }


    public void setDestFile(String destFile) {
        this.destFile = destFile;
    }


    public void setPluginClass(String pluginClass) {
        this.pluginClass = pluginClass;
    }


    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }


    public void setPluginVersion(String pluginVersion) {
        this.pluginVersion = pluginVersion;
    }
}

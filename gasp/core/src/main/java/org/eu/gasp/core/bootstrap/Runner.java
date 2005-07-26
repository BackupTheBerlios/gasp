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

package org.eu.gasp.core.bootstrap;


import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.lang.StringUtils;


/**
 * Platform launcher.
 */
public class Runner {
    private static final String APPLICATION_NAME = "gasp";


    @SuppressWarnings("static-access")
    public void run(String... args) throws Exception {
        // options for command line
        final Options options = new Options();
        options.addOption(OptionBuilder.withLongOpt("base").withArgName(
                "directory").hasArg().withDescription("set the base directory")
                .create('b'));
        options.addOption(OptionBuilder.withLongOpt("log4j")
                .withArgName("file").hasArg().withDescription(
                        "path to the log4j property file").create('l'));
        options.addOption(OptionBuilder.withLongOpt("property").withArgName(
                "file").hasArg().withDescription(
                "path to the Gasp property file").create('p'));
        options.addOption(OptionBuilder.withLongOpt("security").withArgName(
                "file").hasArg().withDescription(
                "path to the Java security policy file").create('s'));
        options.addOption(OptionBuilder.withLongOpt("version").withDescription(
                "print version").create("v"));
        options.addOption(OptionBuilder.withLongOpt("help").withDescription(
                "show this help").create("h"));

        // default values for arguments
        String homeDir = System.getProperty("gasp.home", System
                .getProperty("user.dir"));
        String propFilePath = null;
        String log4jFilePath = null;
        String policyFilePath = null;

        // parsing options from command line
        final CommandLine line = new PosixParser().parse(options, args);
        if (line.hasOption("l")) {
            log4jFilePath = line.getOptionValue("l");
        }
        if (line.hasOption("p")) {
            propFilePath = line.getOptionValue("p");
        }
        if (line.hasOption("s")) {
            policyFilePath = line.getOptionValue("s");
        }
        if (line.hasOption("b")) {
            homeDir = line.getOptionValue("b");
        }
        if (line.hasOption("h")) {
            // print help to the standard output
            final HelpFormatter helpFormatter = new HelpFormatter();
            helpFormatter.printHelp(APPLICATION_NAME, options);
            System.exit(0);
        }
        if (line.hasOption("v")) {
            // print version
            final Package pkg = Runner.class.getPackage();
            String app = StringUtils.defaultString(pkg.getSpecificationTitle(),
                    APPLICATION_NAME);
            String version = StringUtils.defaultString(pkg
                    .getSpecificationVersion(), "<?>");

            System.out.println(app + " " + version);
            System.exit(0);
        }

        // we are ready to go!
        final Bootstrap bootstrap = new Bootstrap(homeDir, policyFilePath,
                log4jFilePath, propFilePath);
        final Thread thread = new BootstrapThread(bootstrap);
        thread.start();
        thread.join();
    }
}

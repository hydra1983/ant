/*
 * Copyright (C) The Apache Software Foundation. All rights reserved.
 *
 * This software is published under the terms of the Apache Software License
 * version 1.1, a copy of which has been included with this distribution in
 * the LICENSE.txt file.
 */
package org.apache.antlib.nativelib;

import java.io.File;
import java.util.Properties;
import org.apache.aut.nativelib.Os;
import org.apache.myrmidon.api.AbstractTask;
import org.apache.myrmidon.api.TaskException;
import org.apache.myrmidon.framework.nativelib.Execute;
import org.apache.myrmidon.framework.nativelib.Argument;
import org.apache.myrmidon.framework.nativelib.EnvironmentData;
import org.apache.myrmidon.framework.nativelib.EnvironmentVariable;

/**
 * Executes a native command.
 *
 * @author <a href="mailto:duncan@x180.com">JDD</a>
 * @author <a href="mailto:rubys@us.ibm.com">Sam Ruby</a>
 * @author <a href="mailto:peter@apache.org">Peter Donald</a>
 * @author <a href="mailto:stefan.bodewig@epost.de">Stefan Bodewig</a>
 * @author <a href="mailto:mariusz@rakiura.org">Mariusz Nowostawski</a>
 * @author <a href="mailto:thomas.haas@softwired-inc.com">Thomas Haas</a>
 * @ant.task name="exec"
 */
public class Exec
    extends AbstractTask
{
    private final Execute m_exe = new Execute();
    private final EnvironmentData m_env = new EnvironmentData();
    private String m_os;

    /**
     * The working directory of the process
     */
    public void setDir( final File dir )
        throws TaskException
    {
        m_exe.setWorkingDirectory( dir );
    }

    /**
     * The command to execute.
     */
    public void setExecutable( final String value )
        throws TaskException
    {
        m_exe.setExecutable( value );
    }

    /**
     * Use a completely new environment
     */
    public void setNewenvironment( final boolean newEnvironment )
    {
        m_exe.setNewenvironment( newEnvironment );
    }

    /**
     * Only execute the process if running on the specified OS family.
     */
    public void setOs( final String os )
    {
        m_os = os;
    }

    /**
     * Timeout in milliseconds after which the process will be killed.
     */
    public void setTimeout( final long timeout )
    {
        m_exe.setTimeout( timeout );
    }

    /**
     * Add a nested env element - an environment variable.
     */
    public void addEnv( final EnvironmentVariable var )
    {
        m_env.addVariable( var );
    }

    /**
     * Add a nested arg element - a command line argument.
     */
    public void addArg( final Argument argument )
    {
        m_exe.addArgument( argument );
    }

    public void execute()
        throws TaskException
    {
        if( null != m_os && Os.isFamily( m_os ) )
        {
            return;
        }

        // Setup environment vars
        final Properties environment = m_env.getVariables();
        m_exe.setEnvironment( environment );

        // execute the command
        m_exe.execute( getContext() );
    }

}

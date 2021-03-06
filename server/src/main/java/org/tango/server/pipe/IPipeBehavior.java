/**
 * Copyright (C) :     2012
 *
 * 	Synchrotron Soleil
 * 	L'Orme des merisiers
 * 	Saint Aubin
 * 	BP48
 * 	91192 GIF-SUR-YVETTE CEDEX
 *
 * This file is part of Tango.
 *
 * Tango is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tango is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Tango.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tango.server.pipe;

import org.tango.server.StateMachineBehavior;

import fr.esrf.Tango.DevFailed;

public interface IPipeBehavior {

    /**
     * 
     * @return pipe configuration
     */
    PipeConfiguration getConfiguration();

    /**
     * Read pipe
     * 
     * @return the read value
     * @throws DevFailed
     */
    PipeValue getValue() throws DevFailed;

    /**
     * Write pipe
     * 
     * @param value
     * @throws DevFailed
     */
    void setValue(final PipeValue value) throws DevFailed;

    /**
     * 
     * @return The state machine of this attribute
     * @throws DevFailed
     */
    StateMachineBehavior getStateMachine() throws DevFailed;

}

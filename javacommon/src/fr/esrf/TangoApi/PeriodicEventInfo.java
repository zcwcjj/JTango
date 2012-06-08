//+======================================================================
// $Source$
//
// Project:   Tango
//
// Description:  java source code for the PeriodicEventInfo class definition .
//
// $Author$
//
// Copyright (C) :      2004,2005,2006,2007,2008,2009
//						European Synchrotron Radiation Facility
//                      BP 220, Grenoble 38043
//                      FRANCE
//
// This file is part of Tango.
//
// Tango is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Tango is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public License
// along with Tango.  If not, see <http://www.gnu.org/licenses/>.
//
// $Revision$
//
// $Log$
// Revision 1.6  2008/10/10 11:33:07  pascal_verdier
// Headers changed for LGPL conformity.
//
// Revision 1.5  2007/09/13 09:22:31  ounsy
// Add java.io.serializable to the dtata classe
//
// Revision 1.4  2007/08/23 08:32:58  ounsy
// updated change from api/java
//
// Revision 3.3  2005/12/02 09:52:32  pascal_verdier
// java import have been optimized.
//
// Revision 3.2  2004/12/07 09:30:29  pascal_verdier
// Exception classes inherited from DevFailed added.
//
// Revision 3.1  2004/11/05 11:59:21  pascal_verdier
// Attribute Info TANGO 5 compatibility.
//
//-======================================================================
package fr.esrf.TangoApi;

import fr.esrf.Tango.PeriodicEventProp;


/**
 *	Class Description: This class is the same class as PeriodicEventProp, 
 *	                   but created for C++ compatibility.
 *
 * @author  verdier
 * @version  $Revision$
 */
public class PeriodicEventInfo implements java.io.Serializable
{
	public String period = "";
	public String[] extensions;
	//-======================================================================
	//-======================================================================
	public PeriodicEventInfo(String period, String[] extensions)
	{
		this.period     = period;
		this.extensions = extensions;
	}
	//-======================================================================
	//-======================================================================
	public PeriodicEventInfo(PeriodicEventProp ev_prop)
	{
		this.period     = ev_prop.period;
		this.extensions = ev_prop.extensions;
	}
	//-======================================================================
	//-======================================================================
	public PeriodicEventProp getTangoObj()
	{
		return new PeriodicEventProp(period, extensions);
	}
}
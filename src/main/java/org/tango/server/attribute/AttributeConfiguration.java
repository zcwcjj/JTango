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
package org.tango.server.attribute;

import java.lang.reflect.Array;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.tango.attribute.AttributeTangoType;
import org.tango.server.PolledObjectConfig;

import fr.esrf.Tango.AttrDataFormat;
import fr.esrf.Tango.AttrWriteType;
import fr.esrf.Tango.DevFailed;
import fr.esrf.Tango.DispLevel;
import fr.esrf.TangoDs.TangoConst;

public final class AttributeConfiguration implements PolledObjectConfig {
    private String name = "";
    private AttrDataFormat format = AttrDataFormat.SCALAR;
    private AttrWriteType writable = AttrWriteType.READ;
    private Class<?> type = String.class;
    private int tangoType = TangoConst.Tango_DEV_STRING;
    private AttributeTangoType enumType = AttributeTangoType.DEVSTRING;
    private DispLevel dispLevel = DispLevel.OPERATOR;
    private int maxX = Integer.MAX_VALUE;
    private int maxY = Integer.MAX_VALUE;
    private boolean isMemorized = false;
    private boolean isMemorizedAtInit = true;
    private int pollingPeriod = 0;
    private boolean isPolled = false;
    private AttributePropertiesImpl attributeProperties = new AttributePropertiesImpl();

    public AttributeConfiguration() {

    }

    public AttributeConfiguration(final AttributeConfiguration config) {
        name = config.name;
        format = config.format;
        writable = config.writable;
        type = config.type;
        dispLevel = config.dispLevel;
        maxX = config.maxX;
        maxY = config.maxY;
        isMemorized = config.isMemorized;
        isMemorizedAtInit = config.isMemorizedAtInit;
        pollingPeriod = config.pollingPeriod;
        isPolled = config.isPolled;
        attributeProperties = config.attributeProperties;
    }

    DispLevel getDispLevel() {
        return dispLevel;
    }

    public void setDispLevel(final DispLevel dispLevel) {
        this.dispLevel = dispLevel;
    }

    @Override
    public String getName() {
        return name;
    }

    public AttrDataFormat getFormat() {
        return format;
    }

    public AttrWriteType getWritable() {
        return writable;
    }

    public Class<?> getType() {
        return type;
    }

    public Class<?> getScalarType() {
        return enumType.getType();
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setWritable(final AttrWriteType writable) {
        this.writable = writable;
    }

    /**
     * Set the attribute type with Java class. Can be scalar, array or matrix
     * 
     * @param type
     * @throws DevFailed
     */
    public void setType(final Class<?> type) throws DevFailed {
        this.type = type;
        enumType = AttributeTangoType.getTypeFromClass(type);
        tangoType = enumType.getTangoIDLType();
        if (type.isArray()) {
            if (type.getComponentType().isArray()) {
                format = AttrDataFormat.IMAGE;
            } else {
                format = AttrDataFormat.SPECTRUM;
                maxY = 0;
            }
        } else {
            format = AttrDataFormat.SCALAR;
            maxX = 1;
            maxY = 0;
        }
    }

    public void setMaxX(final int maxX) {
        this.maxX = maxX;
    }

    public void setMaxY(final int maxY) {
        this.maxY = maxY;
    }

    @Override
    public String toString() {
        final ToStringBuilder sb = new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);
        sb.append("name", name);
        sb.append("format", format.value());
        final StringBuilder s = new StringBuilder().append(type.getCanonicalName()).append(",").append(enumType)
                .append("=").append(tangoType);
        sb.append("type", s.toString());
        sb.append("writable", writable.value());
        sb.append("dispLevel", dispLevel.value());
        sb.append("isMemorized", isMemorized);
        sb.append("isMemorizedAtInit", isMemorizedAtInit);
        sb.append("isPolled", isPolled);
        if (isPolled) {
            sb.append("pollingPeriod", pollingPeriod);
        }
        sb.appendToString(attributeProperties.toString());
        return sb.toString();
    }

    public boolean isMemorized() {
        return isMemorized;
    }

    public void setMemorized(final boolean isMemorized) {
        this.isMemorized = isMemorized;
    }

    public AttributePropertiesImpl getAttributeProperties() {
        return attributeProperties;
    }

    public void setAttributeProperties(final AttributePropertiesImpl attributeProperties) {
        this.attributeProperties = new AttributePropertiesImpl(attributeProperties);
        if (this.attributeProperties.getLabel().isEmpty()
                || this.attributeProperties.getLabel().equalsIgnoreCase(AttributePropertiesImpl.NOT_SPECIFIED)) {
            this.attributeProperties.setLabel(name);
        }
    }

    public int getTangoType() {
        return tangoType;
    }

    /**
     * Set the attribute type with Tango type.
     * 
     * @see TangoConst for possible values
     * @param tangoType
     * @throws DevFailed
     */
    public void setTangoType(final int tangoType, final AttrDataFormat format) throws DevFailed {
        setFormat(format);
        this.tangoType = tangoType;
        enumType = AttributeTangoType.getTypeFromTango(tangoType);
        if (format.equals(AttrDataFormat.SCALAR)) {
            type = enumType.getType();
        } else if (format.equals(AttrDataFormat.SPECTRUM)) {
            type = Array.newInstance(enumType.getType(), 0).getClass();
        } else {
            type = Array.newInstance(enumType.getType(), 0, 0).getClass();
        }
    }

    public void setFormat(final AttrDataFormat format) {
        this.format = format;
        if (format.equals(AttrDataFormat.SCALAR)) {
            maxX = 1;
            maxY = 0;
        } else if (format.equals(AttrDataFormat.SPECTRUM)) {
            maxY = 0;
        }
    }

    public boolean isMemorizedAtInit() {
        return isMemorizedAtInit;
    }

    public void setMemorizedAtInit(final boolean isMemorizedAtInit) {
        this.isMemorizedAtInit = isMemorizedAtInit;
    }

    public int getPollingPeriod() {
        return pollingPeriod;
    }

    @Override
    public void setPollingPeriod(final int pollingPeriod) {
        this.pollingPeriod = pollingPeriod;
    }

    public boolean isPolled() {
        return isPolled;
    }

    @Override
    public void setPolled(final boolean isPolled) {
        this.isPolled = isPolled;
    }
}
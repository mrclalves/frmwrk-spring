/*
 * SURICATTUS
 * Copyright 2011, SURICATTUS CONSULTORIA LTDA, 
 * and individual contributors as indicated by the @authors tag
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package br.com.suricattus.surispring.jsf.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.suricattus.surispring.framework.util.CpfUtil;

/**
 * Converter de CPF
 * 
 * @author Lucas Lins
 *
 */
@FacesConverter("br.com.suricattus.surispring.jsf.converter.CpfConverter")
public class CpfConverter implements Converter{

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return CpfUtil.unformat(value);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		return CpfUtil.format((String)value);
	}
	
}

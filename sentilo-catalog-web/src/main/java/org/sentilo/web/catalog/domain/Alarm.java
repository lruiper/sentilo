/*
 * Sentilo
 *   
 * Copyright (C) 2013 Institut Municipal d’Informàtica, Ajuntament de  Barcelona.
 *   
 * This program is licensed and may be used, modified and redistributed under the
 * terms  of the European Public License (EUPL), either version 1.1 or (at your 
 * option) any later version as soon as they are approved by the European 
 * Commission.
 *   
 * Alternatively, you may redistribute and/or modify this program under the terms
 * of the GNU Lesser General Public License as published by the Free Software 
 * Foundation; either  version 3 of the License, or (at your option) any later 
 * version. 
 *   
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
 * CONDITIONS OF ANY KIND, either express or implied. 
 *   
 * See the licenses for the specific language governing permissions, limitations 
 * and more details.
 *   
 * You should have received a copy of the EUPL1.1 and the LGPLv3 licenses along 
 * with this program; if not, you may find them at: 
 *   
 *   https://joinup.ec.europa.eu/software/page/eupl/licence-eupl
 *   http://www.gnu.org/licenses/ 
 *   and 
 *   https://www.gnu.org/licenses/lgpl.txt
 */
package org.sentilo.web.catalog.domain;

import java.util.Date;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.sentilo.web.catalog.utils.Constants;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;


@Document
public class Alarm implements CatalogDocument {

	private static final long serialVersionUID = 1L;

	public enum Type {
		EXTERNAL, INTERNAL;
	}

	public enum Trigger {
		GT, GTE, LT, LTE, EQ, CHANGE, CHANGE_DELTA,FROZEN;
	}
	
	@Id
	@NotBlank
	@Pattern(regexp = Constants.VALIDATION_ENTITY_NAME_REGEXP)
	private String id;

	private String name;

	private String description;

	@DateTimeFormat(pattern = Constants.DATE_FORMAT)
	private Date createdAt;

	@DateTimeFormat(pattern = Constants.DATE_FORMAT)
	private Date updateAt;

	private Type type;
	private Trigger trigger;

	private String expression;
	
	/**
	 * Identificador del proveedor al cual esta asociada la alarma en el caso de
	 * que sea interna.
	 */
	private String providerId;
	
	/**
	 * Identificador del componente al cual esta asociada la alarma en el caso de
	 * que sea interna.
	 */
	private String componentId;

	/**
	 * Identificador del sensor al cual esta asociada la alarma en el caso de
	 * que sea interna.
	 */
	private String sensorId;

	/**
	 * Identificador de la aplicación que registra la alarma en el caso de que
	 * esta sea externa.
	 */
	private String applicationId;

	public Alarm() {

	}

	public Alarm(String id) {
		this();
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Alarm) || id == null) {
			return false;
		}
		Alarm other = (Alarm) obj;
		return id.equals(other.id);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result*super.hashCode();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}	

	public String getClientApplication() {
		return applicationId;
	}

	public void setClientApplication(String clientApplication) {
		this.applicationId = clientApplication;
	}

	public Trigger getTrigger() {
		return trigger;
	}

	public void setTrigger(Trigger trigger) {
		this.trigger = trigger;
	}	

	public void setUpdateAt(Date updatedAt) {
		this.updateAt = updatedAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	
	public String getComponentId() {
		return componentId;
	}
	
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
}

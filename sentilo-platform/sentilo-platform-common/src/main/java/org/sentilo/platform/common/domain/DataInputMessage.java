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
package org.sentilo.platform.common.domain;

import java.util.Date;
import java.util.List;

import org.sentilo.common.domain.PlatformInputMessage;
import org.sentilo.common.domain.PlatformSearchInputMessage;
import org.sentilo.common.domain.QueryFilterParams;


public class DataInputMessage implements PlatformInputMessage, PlatformSearchInputMessage{
		
	private String providerId;
	private String sensorId;
	private List<Observation> observations;	
	
	private QueryFilterParams queryFilters;
	
	public DataInputMessage(){
		this((String)null);
	}
	
	public DataInputMessage(String providerId) {
		this(providerId, (String)null);
	}
	
	public DataInputMessage(String providerId, List<Observation> observations) {
		this(providerId, (String)null, observations);
	}
	
	public DataInputMessage(String providerId, String sensorId) {
		this(providerId, sensorId, null);
	}

	public DataInputMessage(String providerId, String sensorId, List<Observation> observations) {
		super();
		this.providerId = providerId;
		this.sensorId = sensorId;
		this.observations = observations;
		
	}

	public DataInputMessage(String providerId, String sensorId, Date from, Date to, Integer limit) {
		this(providerId, sensorId);
		this.queryFilters = new QueryFilterParams(from, to, limit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.sentilo.platform.client.core.domain.PlatformSearchInputMessage#getQueryFilters()
	 */
	public QueryFilterParams getQueryFilters(){
		return queryFilters;
	}
		
	/*
	 * (non-Javadoc)
	 * @see org.sentilo.platform.client.core.domain.PlatformSearchInputMessage#hasQueryFilters()
	 */
	public boolean hasQueryFilters(){
		return queryFilters!=null;
	}

	public String getProviderId() {
		return providerId;
	}

	public String getSensorId() {
		return sensorId;
	}

	public List<Observation> getObservations() {
		return observations;
	}

}

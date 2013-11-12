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
package org.sentilo.common.domain;

import java.util.Date;

/**
 * Encapsula la información con la cual filtrar las busquedas dentro de la plataforma de datos, alarmas y ordenes. 
 */
public class QueryFilterParams {	
	private Date from;
	private Date to;
	private Integer limit;
	
	public QueryFilterParams(Integer limit) {
		this((Date)null, (Date)null, limit);
	}
		
	public QueryFilterParams(Long from, Long to, Integer limit) {
		this(new Date(from), new Date(to), limit);
	}
	
	public QueryFilterParams(Date from, Date to) {
		this(from, to, null);
	}
	
	
	public QueryFilterParams(Date from, Date to, Integer limit) {
		this.from = from;
		this.to = to;
		this.limit = limit;
	}
	
	
	public Date getFrom() {
		return from;
	}

	public Date getTo() {
		return to;
	}

	public Integer getLimit() {
		return limit;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\n--- QueryFilterParams ---");
						
		sb.append("\n\t from:" + from);
		sb.append("\n\t to:" + to);
		sb.append("\n\t limit:" + limit);					
		
		return sb.toString();
	}
	
	
}

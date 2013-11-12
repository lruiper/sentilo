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
package org.sentilo.platform.server.parser;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpStatus;
import org.sentilo.platform.common.domain.AlarmSubscription;
import org.sentilo.platform.common.domain.DataSubscription;
import org.sentilo.platform.common.domain.OrderSubscription;
import org.sentilo.platform.common.domain.Subscription;
import org.sentilo.platform.common.exception.PlatformException;
import org.sentilo.platform.server.dto.SubscriptionMessage;
import org.sentilo.platform.server.dto.SubscriptionsMessage;
import org.sentilo.platform.server.request.SentiloRequest;
import org.sentilo.platform.server.response.SentiloResponse;
import org.springframework.util.StringUtils;

import org.sentilo.common.domain.SubscribeType;


public class SubscribeParser extends PlatformJsonMessageConverter {
	
	public Subscription parseRequest(SentiloRequest request) throws PlatformException{
		return parseRequest(request, null);
	}
	
	public Subscription parseBasicRequest(SentiloRequest request) throws PlatformException{
		return parseRequest(request, null, true);
	}
	
	public Subscription parseRequest(SentiloRequest request, SubscribeType defaultSubsType ) throws PlatformException{
		return parseRequest(request, defaultSubsType, false);
	}
		
	private Subscription parseRequest(SentiloRequest request, SubscribeType defaultSubsType, boolean isBasicRequest ) throws PlatformException{	
		boolean resourceHasEvent = resourceHasEvent(request);
		SubscribeType subscribeType = (resourceHasEvent?getSubscribeType(request):defaultSubsType);				
						
		SubscriptionMessage inputMessage = (isBasicRequest ?null:(SubscriptionMessage)readInternal(SubscriptionMessage.class, request));
				
		String entityId = request.getEntitySource();
		String endpoint = (inputMessage!=null?inputMessage.getEndpoint():null);
		
		String providerId, sensorId;
		if(subscribeType != null){
			switch (subscribeType) {
				case DATA:				
					providerId = (resourceHasEvent?request.getResourcePart(1):request.getResourcePart(0));
					sensorId = (resourceHasEvent?request.getResourcePart(2):request.getResourcePart(1));				
					return new DataSubscription(entityId, endpoint, providerId, sensorId);				
				case ORDER:					
					providerId = request.getResourcePart(1);
					sensorId = request.getResourcePart(2);
					return new OrderSubscription(entityId, providerId, sensorId, endpoint);				
				case ALARM:
					String alertId = request.getResourcePart(1);						
					return new AlarmSubscription(entityId, null, endpoint, alertId);
				default:
					throw new PlatformException(HttpStatus.SC_INTERNAL_SERVER_ERROR, "Illegal subscribe event type:" + subscribeType);
			}
		}else{
			//TODO Mikel: Revisar ya que ahora fijamos source / target al mismo entityId. Por aqui solo pasaran subscripciones del tipo GET o DELETE
			return new Subscription(entityId, entityId, endpoint);
		}
		
	}
	
	public void writeResponse(SentiloResponse response, List<Subscription> subscriptions) throws PlatformException{
		//transformar a objeto de tipo SubscriptionsMessage
		Object message = parseSubscriptionListToSubscriptionsMessage(subscriptions);
		
		try{
			writeInternal(message, response);
		}catch(IOException ex){
			throw new PlatformException(HttpStatus.SC_INTERNAL_SERVER_ERROR, ex);
		}	
	}
	
	private SubscriptionsMessage parseSubscriptionListToSubscriptionsMessage(List<Subscription> subscriptionsList){
		SubscriptionsMessage subscriptions = new SubscriptionsMessage();
		for(Subscription subscription : subscriptionsList){
			subscriptions.addSubscription(parseSubscriptionToSubscribeMessage(subscription));
		}
		
		return subscriptions;
	}
	
	private SubscriptionMessage parseSubscriptionToSubscribeMessage(Subscription subscription){
		SubscriptionMessage message = new SubscriptionMessage();
		message.setEndpoint(subscription.getEndpoint());
		message.setType(subscription.getType().toString());
		switch(subscription.getType()){
			case DATA:
				message.setProvider(((DataSubscription)subscription).getProviderId());
				message.setSensor(((DataSubscription)subscription).getSensorId());
				break;
			case ALARM:
				message.setAlert(((AlarmSubscription)subscription).getAlertId());				
				break;
			case ORDER:
				message.setProvider(((OrderSubscription)subscription).getOwnerEntityId());
				message.setSensor(((OrderSubscription)subscription).getSensorId());
				break;
		}
		
		return message;
	}		
	
	
	public boolean resourceHasEvent(SentiloRequest request){
		return (getSubscribeType(request)!=null?true:false);												
	}
	
	public SubscribeType getSubscribeType(SentiloRequest request){
		SubscribeType subscribeType = null;
		try{
			String resourcePart = request.getResourcePart(0);
			if(StringUtils.hasText(resourcePart)){
				subscribeType = SubscribeType.valueOf(resourcePart.toUpperCase());				
			}						
		}catch(IllegalArgumentException e){						
		}
		
		return subscribeType;
	}
		
	
}

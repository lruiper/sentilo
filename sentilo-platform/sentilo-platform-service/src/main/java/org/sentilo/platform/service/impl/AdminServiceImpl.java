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
package org.sentilo.platform.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.sentilo.platform.common.domain.Statistics;
import org.sentilo.platform.common.domain.Subscription;
import org.sentilo.platform.common.domain.Statistics.Events;
import org.sentilo.platform.common.domain.Statistics.Performance;
import org.sentilo.platform.common.service.AdminService;
import org.sentilo.platform.common.service.SubscribeService;
import org.sentilo.platform.service.dao.JedisSequenceUtils;
import org.sentilo.platform.service.dao.JedisTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl implements AdminService {
	
	private final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);
	
	private static final String MAX_AVG_RATE_KEY = "stats:avg:max";	
	
	@Autowired
	private JedisSequenceUtils jedisSequenceUtils;
	
	@Autowired
	private JedisTemplate<String, String> jedisTemplate;
	
	@Autowired
	private SubscribeService subscribeService;
	
	private Long lastTotalEvents = new Long(0);
	private Float maxAvgRate = new Float(0);
	private Float currentEventsPerSecond = new Float(0);
	private Float dailyMaxAvgRate = new Float(0);	
	private int currentDay;
	
	@Override
	public Statistics getStatistics() {
		logger.debug("Querying platform statistics");		
		
		Events events = getEvents();
		Performance performance = new Performance(currentEventsPerSecond, dailyMaxAvgRate, maxAvgRate);
		
		return new Statistics(events, performance);
	}
	
	@Override
	public List<Subscription> getSubscriptions(String entityId) {
		Subscription subscription = new Subscription(entityId);
		return subscribeService.get(subscription);
	}
	
	private Events getEvents(){
		Long numAlarms = jedisSequenceUtils.getCurrentAmid();
		Long numOrders = jedisSequenceUtils.getCurrentSoid();
		Long numObserv = jedisSequenceUtils.getCurrentSdid();
		Long total = numAlarms + numOrders + numObserv;
		
		logger.debug("alarms {} , orders {} , observations {} ", numAlarms, numOrders, numObserv);
		
		Events events = new Events(total, numAlarms, numObserv, numOrders);
		
		return events;
	}
	
	private Float getMaxAvgRate(){
		if(maxAvgRate == 0){
			String value = jedisTemplate.get(MAX_AVG_RATE_KEY);				
			maxAvgRate =  (JedisSequenceUtils.NIL.equals(value) || value == null ?new Float(0):Float.valueOf(value));						
		}
		
		return maxAvgRate;
	}

	private void updateMaxAvgRateIfNecessary(){
		if(currentEventsPerSecond > getMaxAvgRate()){
			maxAvgRate = currentEventsPerSecond;
			jedisTemplate.set(MAX_AVG_RATE_KEY, currentEventsPerSecond.toString());
		}
	}
	
	private void updateDailyMaxAvgRateIfNecessary(){
		if(currentEventsPerSecond > dailyMaxAvgRate){
			dailyMaxAvgRate = currentEventsPerSecond;			
		}
	}
	
	@Scheduled(initialDelay=10000, fixedRate=30000)
	public void getCurrentPerformance(){
		 Long totalEvents = getEvents().getTotal();
		 Long delta = (lastTotalEvents!=0 ? totalEvents - lastTotalEvents : new Long(0));
		 
		 BigDecimal eventsPerSecond = new BigDecimal(delta);
		 currentEventsPerSecond = (delta == 0)? new Float(0):eventsPerSecond.divide(new BigDecimal(30), 2, BigDecimal.ROUND_HALF_EVEN).floatValue();
		 updateMaxAvgRateIfNecessary();
		
		 // Perform daily average rate
		 Calendar cal = Calendar.getInstance();		 
		 int day = cal.get(Calendar.DATE);		 
		 
		 if(currentDay == day){
			 updateDailyMaxAvgRateIfNecessary();
		 }else{
			 currentDay = day;
			 dailyMaxAvgRate = currentEventsPerSecond;
		 }		 		 
		 		 
		 lastTotalEvents = totalEvents;			 		 
	}				
	
	public void setJedisSequenceUtils(JedisSequenceUtils jedisSequenceUtils) {
		this.jedisSequenceUtils = jedisSequenceUtils;
	}		
}

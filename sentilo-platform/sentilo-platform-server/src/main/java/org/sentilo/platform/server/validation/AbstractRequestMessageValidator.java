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
package org.sentilo.platform.server.validation;

import org.sentilo.platform.server.exception.MessageValidationException;

import org.sentilo.common.domain.PlatformInputMessage;

public abstract class AbstractRequestMessageValidator<T extends PlatformInputMessage> implements RequestMessageValidator<T> {

	@Override
	public void validateRequestMessageOnDelete(T requestMessage) throws MessageValidationException {
		// do nothing	
	}

	@Override
	public void validateRequestMessageOnPut(T requestMessage) throws MessageValidationException {
		// do nothing
	}

	@Override
	public void validateRequestMessageOnGet(T requestMessage) throws MessageValidationException {
		// do nothing
	}

	@Override
	public void validateRequestMessageOnPost(T requestMessage) throws MessageValidationException {
		// do nothing
	}

}

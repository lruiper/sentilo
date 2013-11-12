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
package org.sentilo.web.catalog.controller.admin;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.sentilo.web.catalog.controller.CrudController;
import org.sentilo.web.catalog.domain.Sensor;
import org.sentilo.web.catalog.domain.SensorType;
import org.sentilo.web.catalog.exception.CatalogException;
import org.sentilo.web.catalog.search.SearchFilter;
import org.sentilo.web.catalog.search.SearchFilterResult;
import org.sentilo.web.catalog.service.CrudService;
import org.sentilo.web.catalog.service.SensorService;
import org.sentilo.web.catalog.service.SensorTypesService;
import org.sentilo.web.catalog.utils.Constants;
import org.sentilo.web.catalog.utils.FormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/admin/sensortypes")
public class SensorTypesController extends CrudController<SensorType> {

	@Autowired
	private SensorTypesService sensorTypesService;

	@Autowired
	private SensorService sensorService;

	@ModelAttribute(Constants.MODEL_ACTIVE_MENU)
	public String getActiveMenu() {
		return Constants.MENU_SENSOR_TYPE;
	}

	@Override
	protected SensorType buildNewEntity(String id) {
		return new SensorType(id);
	}

	@Override
	protected String getEntityModelKey() {
		return Constants.MODEL_SENSOR_TYPE;
	}

	@Override
	protected CrudService<SensorType> getService() {
		return sensorTypesService;
	}

	@Override
	protected List<String> toRow(SensorType sensorType) {
		List<String> row = new ArrayList<String>();
		row.add(sensorType.getId()); //checkbox
		row.add(sensorType.getId());
		row.add(sensorType.getName());
		row.add(sensorType.getDescription());
		row.add(FormatUtils.formatDate(sensorType.getCreatedAt()));
		return row;
	}

	@Override
	protected void initViewNames() {
		viewNames.put(LIST_ACTION, Constants.VIEW_SENSOR_TYPE_LIST);
		viewNames.put(DETAIL_ACTION, Constants.VIEW_SENSOR_TYPE_DETAIL);
		viewNames.put(NEW_ACTION, Constants.VIEW_NEW_SENSOR_TYPE);
	}

	@Override
	protected void doBeforeCreateResource(SensorType sensorType, Model model) {
		// Todos los id de tipo de sensores los almacenamos en minusculas
		// para poder hacer la comparativa de manera rapida en el alta de sensores
		// via API.
		sensorType.setId(sensorType.getId().toLowerCase());
	}

	@Override
	protected void doBeforeDeleteResource(String[] selectedIds, HttpServletRequest request, Model model) {
		for (String sensorType : selectedIds) {
			throwExceptionIfSensorsFoundWithType(sensorType);
		}
	}

	private void throwExceptionIfSensorsFoundWithType(String sensorType) {
		SearchFilter filter = new SearchFilter();
		filter.addAndParam("type", sensorType);
		SearchFilterResult<Sensor> sensors = sensorService.search(filter);
		if (!CollectionUtils.isEmpty(sensors.getContent())) {
			throw new CatalogException("sensortype.error.cannot.delete", new Object[] { sensorType });
		}
	}
}

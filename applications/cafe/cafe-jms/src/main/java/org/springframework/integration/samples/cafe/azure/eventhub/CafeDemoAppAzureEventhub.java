/*
 * Copyright 2002-2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.integration.samples.cafe.azure.eventhub;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.samples.cafe.Cafe;
import org.springframework.integration.samples.cafe.DrinkType;
import org.springframework.integration.samples.cafe.Order;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main class for sending orders that will be handled in separate, distributed
 * processes. See the README.md file for more information on the order in which
 * to start the processes.
 *
 * @author Christian Posta
 * @author Guillermo Tantachuco
 */
public class CafeDemoAppAzureEventhub {

	/**
	 * Place some orders.
	 *
	 * @param context spring context
	 * @param count the number of standard orders
	 */
	public static void order(AbstractApplicationContext context, int count){
		Date curDate = new Date();

		Cafe cafe = (Cafe) context.getBean("cafe");
		for (int i = 1; i <= count; i++) {
			Order order = new Order(getSequenceNumber(curDate, i));
			order.addItem(DrinkType.LATTE, 2, false);
			order.addItem(DrinkType.MOCHA, 3, true);
			System.out.println("Sending order [" + order.getNumber() + "] to Azure's EventHub");
			cafe.placeOrder(order);
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"/META-INF/spring/integration/azure-eventhub/cafeDemo-azure-config.xml",
				"/META-INF/spring/integration/azure-eventhub/cafeDemo-azure-xml.xml");

		System.out.println("Class of cafeOrdersQueue: " + context.getBean("cafeOrdersQueue").getClass().getName());
		order(context, 10);
		context.close();
	}
	
	private static int getSequenceNumber(Date date, int i) {
		SimpleDateFormat format = new SimpleDateFormat("hhmmss");
		String dateToStr = format.format(date);
		return new Integer(dateToStr + i);

	}
}

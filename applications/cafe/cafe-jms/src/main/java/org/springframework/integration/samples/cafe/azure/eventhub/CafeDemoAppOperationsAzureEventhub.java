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

import java.io.IOException;

/**
 * Main class for starting up the distributed task of processing orders including
 * splitting the orders, sending them to the hot/cold baristas, aggregating the orders
 * and delivering them to the waiters.
 *
 * See the README.md file for more information on the order in which
 * to start the processes.
 *
 * @author Christian Posta
 * @author Guillermo Tantachuco
 */
public class CafeDemoAppOperationsAzureEventhub {


	public static void main(String[] args) throws InterruptedException, IOException {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
				"/META-INF/spring/integration/activemq/cafeDemo-amq-config.xml",
				"/META-INF/spring/integration/azure-eventhub/cafeDemo-azure-config.xml",
				"/META-INF/spring/integration/azure-eventhub/cafeDemo-azure-operations.xml");

		System.out.println("Press Enter/Return to exit");
		System.in.read();
		context.close();
	}
}
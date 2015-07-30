Café Sample Application - JMS Implementation
============================================

See the parent-level **README.md** for more details, but the flow of the implementation should follow this diagram:

                     |Azure Event Hub|          | ActiveMQ |            | ActiveMQ |
                     
	                                                                                          Barista
	                                                                     hotDrinks       ____________________
	                                                                    |==========| -->|                    |
	                     orders                   drinks               /                | prepareHotDrink()  |
	Place Order ->Cafe->|======|->OrderSplitter->|======|->DrinkRouter                  |                    |
	                                                                   \ coldDrinks     | prepareColdDrink() |
	                                                                    |==========| -->|                    |
	                                                                                    |____________________|
	
	                                                Legend: |====| - channels


## Working with Azure Event Hub
Listed below are the changes made to the CafeDemo app to make it work with Azure Event Hubs:

* Given Event Hubs speak AMQP 1.0 only, leveraged __QPID JMS AMPQ 1.0__ implementation which Microsoft suggests. As of July 2015, RabbitMQ supports only AMQP 0.9
* Created __eventhub.properties__ file which contains Event Hub's connection factory URL, hub names and partition information. Update this file with your Event Hub settings
* Created __CafeDemoAppAzureEventHub__ which places the orders to Azure Event Hub
* Created __CafeDemoAppOperationsAzureEventHub__ which gets orders from Azure Event Hub and starts the Cafe Operations (order splitter, drink router, etc)
* Created __cafeDemo-azure-config.xml__ to define Event Hub as JMS connection factories, queues and topics
* Created __cafeDemo-azure-operations.xml__ to define message-driven consumers that read messages from a given offset
* Created __EventHubOffsetManager__ which is responsible for checkpointing and providing offset information per partition

## Instructions for running the CafeDemo JMS sample

### Distributed components
To run this configuration, start an instance of ActiveMQ with the openwire/TCP connector available on the default port (61616). There are no credentials of which to be aware. Please execute the following classes in order:

   1. **CafeDemoAppBaristaColdActiveMQ     - starts the ColdDrink Barista
   2. **CafeDemoAppBaristaHotActiveMQ      - starts the HotDrink Barista
   3. **CafeDemoAppOperationsAzureEventHub - starts the Cafe Operations (order splitter, drink router, etc).
   4. **CafeDemoAppAzureEventHub           - places the orders

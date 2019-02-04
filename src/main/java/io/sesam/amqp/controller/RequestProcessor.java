package io.sesam.amqp.controller;

import io.sesam.amqp.AppConfig;
import io.sesam.amqp.model.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.jms.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 100tsa
 */
@RestController
public class RequestProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(RequestProcessor.class);
    private static final String CT = "application/json";

    @Autowired
    AppConfig config;

    @RequestMapping(value = "/{topicName}", method = {RequestMethod.GET}, produces = CT)
    public List<Message> getFromTopic(@PathVariable("topicName") String topicName) throws NamingException, JMSException {
        List<TextMessage> messages = getMessages(this.config.getUrl(), topicName);
        List<Message> messageData = new ArrayList<>(messages.size());
        for (TextMessage message : messages) {
            messageData.add(new Message(java.util.UUID.randomUUID().toString(), message.getText()));
        }
        return messageData;
    }

    @RequestMapping(value = "/{topicName}", method = {RequestMethod.POST}, produces = CT, consumes = CT)
    public void sendToTopic(@PathVariable("topicName") String topicName) {
        System.out.println(topicName);
        //getClient(URL, topicName);
    }

    private List<TextMessage> getMessages(final String url, final String receiveQueue) throws NamingException, JMSException {
        List<TextMessage> receivedMessages = new ArrayList<>();

        Properties env = new Properties();
        env.setProperty("java.naming.factory.initial", "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        env.setProperty("connectionfactory.myFactoryLookupTLS", url);
        env.setProperty("queue.receiveQueue", receiveQueue);
        final Context context = new InitialContext(env);

        ConnectionFactory connectionFactory
                = (ConnectionFactory) context.lookup("myFactoryLookupTLS");

        try (Connection connection = connectionFactory.createConnection()) {
            connection.setExceptionListener((JMSException exception) -> {
                LOG.error("Exception occured in JSM connection", exception);
            });
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer messageConsumer = session.createConsumer((Destination) context.lookup("receiveQueue"));

            int count = 0;
            while (count < 1024) {//max 1024 messages per run
                TextMessage responseMessage = (TextMessage) messageConsumer.receiveNoWait();
                if (null == responseMessage) {
                    break;
                }
                receivedMessages.add(responseMessage);
                count += 1;
            }
        }

        return receivedMessages;
    }
}

package com.lansoft.fs.esl;

import org.freeswitch.esl.client.inbound.IEslEventListener;
import org.freeswitch.esl.client.internal.Context;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class InboundListener implements IEslEventListener {
    private static Logger logger = LoggerFactory.getLogger(InboundListener.class);

    @Override
    public void onEslEvent(Context ctx, EslEvent event) {
        logger.info("INBOUND onEslEvent: {}-------------------{}\r\n", event.getEventName(), event.toString());
        if ("CHANNEL_CREATE".equals(event.getEventName())) {
            logger.info("CHANNEL_CREATE: -------------------{}", event.getEventHeaders().toString());
            String uuid = event.getEventHeaders().get("variable_origination_uuid");
            if ("1".equals(uuid)) {
                new Thread(() -> {
                    EslMessage msg = ctx.sendApiCommand("uuid_record", "1 start /tmp/11141.wav");
                }).start();
            }
        }
        if ("CHANNEL_HANGUP".equals(event.getEventName())) {
            logger.info("CHANNEL_HANGUP: -------------------{}", event.getEventHeaders().toString());
            String uuid = event.getEventHeaders().get("variable_origination_uuid");
            logger.info("variable_origination_uuid: -------------------{}", uuid);
        }
        if ("CHANNEL_ANSWER".equals(event.getEventName())) {
            logger.info("CHANNEL_ANSWER HEADER: ************************{}", event.getEventHeaders().toString());
            String uuid = event.getEventHeaders().get("variable_origination_uuid");
            if (!"1".equals(uuid))
                return;
//            int len = event.getEventBodyLines().size();
//            for(int index =0; index < len; index++) {
//                logger.info("CHANNEL_ANSWER BODY: -------------------{}", event.getEventBodyLines().get(index));
//            }
            new Thread(() -> {
                logger.info("*****************************************build leg2");
                EslMessage msg = ctx.sendApiCommand("originate", "{origination_uuid=2}sofia/internal/63041113@192.168.152.225 &app_uuid_bridge(1,2) XML default '63044441' 63044441");
//                EslMessage msg = ctx.sendApiCommand("originate", "{origination_caller_id_number=82994707,ignore_early_media=true,originate_timeout=90,hangup_after_bridge=true,uuid=781bf1ec-a6a7-480f-b2b9-5d36b591c6b7,leg=2}sofia/internal/13601034834@192.168.152.225 XML default '82994707' 82994707");

                String line = msg.getBodyLines().get(0);
                String[] tmp = line.split(" ");
                final String uuid1 = tmp[1];
                logger.info("*****************************************leg1 uuid:{}", uuid);
                logger.info("*****************************************leg2 uuid:{}", uuid1);
/*
                new Thread(() -> {
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    logger.info("*****************************************build bridge");
                    EslMessage msg1 = ctx.sendApiCommand(" uuid_bridge", uuid + " " + uuid1);
                    logger.info("*****************************************{}", msg1.toString());
                }).start();
*/
            }).start();
        }
    }
}

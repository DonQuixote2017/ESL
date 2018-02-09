package com.lansoft.fs.esl.controller;

import com.lansoft.fs.esl.mapper.UserInfoMapper;
import org.apache.log4j.Logger;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.transport.message.EslMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Call {
    private static final Logger logger = Logger.getLogger(Call.class);
    @Autowired
    private Client inboudClient;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @RequestMapping(value = "/call", produces = "text/plain;charset=UTF-8")
    public String call(String caller, String callee) {
        EslMessage msg = null;
        //1、根据主叫号码查询接入号
        List<String> uanList = userInfoMapper.selectUanbyUserMobile(caller);
        String uan = null;
        if (uanList != null && uanList.size() > 0) {
            uan = uanList.get(0);
        }
        logger.info("UAN:" + uan);
        //2、生成origination_uuid
        //3、向数据库中插入记录
        //4、创建origination_uuid的映射关系
        String command = null;
        if (uan != null) {
            command = "{origination_uuid=1}sofia/internal/18518099139@192.168.152.225 " + uan + " XML default '" + uan + "' " + uan;
            msg = inboudClient.sendApiCommand("originate", command);
        }
        return "caller:" + caller + "; callee:" + callee + "; command:" + command;

    }
}

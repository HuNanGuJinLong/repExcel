package com.meihao.uploadexcel.service;

import com.meihao.uploadexcel.entity.Messages;
import com.meihao.uploadexcel.mapper.MessagesMapper;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessagesService {
    private static Logger logger = Logger.getLogger(MessagesService.class);
    @Resource
    private MessagesMapper messagesMapper;


    public int insert(Messages record) {
        return messagesMapper.insert(record);
    }


    public int insertSelective(Messages record) {
        return messagesMapper.insertSelective(record);
    }

    public Boolean insertLists(List<Messages> list) {
        int i = messagesMapper.insertList(list);
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

}


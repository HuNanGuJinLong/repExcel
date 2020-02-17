package com.meihao.uploadexcel.mapper;

import com.meihao.uploadexcel.entity.Messages;

import java.util.List;

public interface MessagesMapper {
    int insert(Messages record);

    int insertList(List<Messages> messages);

    int insertSelective(Messages record);
}
package com.jmscottnovels.forumapi.service;

import com.jmscottnovels.forumapi.model.Topic;
import com.jmscottnovels.forumapi.repo.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;


//    public List<Topic> buildTopicList(Integer recentNum) {
//
//        List<Topic> topics = new ArrayList<>();
//
//        if(recentNum != null && recentNum != 0) {
//            Page<Topic> page = topicRepository.findAllTopics(PageRequest.of(0, recentNum, Sort.by(Sort.Order.desc("last_post_date")))).getContent();
//        } else {
//            topics = topicRepository.findAllTopics();
//        }
//
//        for(Topic topic : topics) {
//            System.out.println(topic.toString());
//        }
//        return topics;
//    }
}
